package com.example.app.controllers;

import com.example.app.dtos.JWTToken;
import com.example.app.dtos.LoginForm;
import com.example.app.dtos.MemberInfoView;
import com.example.app.dtos.SignUpForm;
import com.example.app.entities.Account;
import com.example.app.exceptions.NoEntityFoundException;
import com.example.app.responses.SuccessResponse;
import com.example.app.security.jwt.JWTFilter;
import com.example.app.security.jwt.TokenProvider;
import com.example.app.services.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Api(value="MemberController", tags="2. Member")
@RestController
@RequestMapping("/v1/member")
public class MemberController {
   private final Logger log = LoggerFactory.getLogger(MemberController.class);

   private final TokenProvider tokenProvider;

   private final AuthenticationManagerBuilder authenticationManagerBuilder;

   private final MemberService memberService;

   public MemberController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, MemberService memberService) {
      this.tokenProvider = tokenProvider;
      this.authenticationManagerBuilder = authenticationManagerBuilder;
      this.memberService = memberService;
   }

   @ResponseStatus(HttpStatus.CREATED)
   @PostMapping("/join")
   @ApiOperation(value = "유저 등록", notes="memnber join")
   @ApiResponses({
           @ApiResponse(code = 201, message = "등록성공", response=SuccessResponse.class),
           @ApiResponse(code = 400, message = "잘못된 파라메터"),
           @ApiResponse(code = 401, message = "잘못된 계정"),
           @ApiResponse(code = 500, message = "서버 에러"),
   })
   public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
      Account account = memberService.saveUser(signUpForm).orElseThrow(()->new RuntimeException("user create failed."));
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.set("userId", String.valueOf(account.getId()));
      return new ResponseEntity<>(SuccessResponse.response("user created"), httpHeaders, HttpStatus.CREATED);
   }

   @ResponseStatus(HttpStatus.OK)
   @PostMapping("/login")
   @ApiOperation(value = "jwt 토큰 발급", notes="Token 발급")
   @ApiResponses({
           @ApiResponse(code = 200, message = "발급성공"),
           @ApiResponse(code = 400, message = "잘못된 파라메터"),
           @ApiResponse(code = 401, message = "잘못된 계정"),
           @ApiResponse(code = 500, message = "서버 에러"),
   })
   public ResponseEntity<SuccessResponse<JWTToken>> authorize(@Valid @RequestBody LoginForm loginForm) {

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginForm.getId(), loginForm.getPassword());
      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = tokenProvider.createToken(authentication, loginForm.getId());
      memberService.updateLastLoginAt(loginForm.getId());

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      JWTToken jwtToken = new JWTToken(jwt);

      return new ResponseEntity<>(new SuccessResponse<>(jwtToken), httpHeaders, HttpStatus.OK);

   }

   @ResponseStatus(HttpStatus.OK)
   @GetMapping("/info")
   @ApiOperation(value = "회원정보 조회")
   @ApiResponses({
           @ApiResponse(code = 200, message = "조회 성공"),
           @ApiResponse(code = 400, message = "잘못된 파라메터"),
           @ApiResponse(code = 401, message = "권한 없음"),
           @ApiResponse(code = 500, message = "서버 에러"),
   })
   public ResponseEntity<SuccessResponse<MemberInfoView>> info(){
      Account account = memberService.getAccount().orElseThrow(() -> new NoEntityFoundException("Member", "member Email"));
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
      LocalDateTime lastLoginAt = account.getLastLoginAt();
      String stringDate = lastLoginAt == null ? "" : lastLoginAt.format(formatter);
      MemberInfoView memberInfoView = new MemberInfoView(account.getName(), account.getEmailAddr(), stringDate );
      return new ResponseEntity<>(new SuccessResponse<>(memberInfoView), HttpStatus.OK);
   }
}
