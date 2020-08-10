# SAMPLE SPRING BOOTY APPLICATION

## About
본 프로젝트는 스프링부트를 이용한 백엔드 웹어플리케이션 샘플 입니다.  
회원 가입, 로그인 및 정보 조회가 가능하도록 구현되었습니다. 
회원 인증을 위해서 JWT 토큰을 이용했습니다.

## Dependencies
본 프로젝트를 구현하기 위해서 사용된 주요 의존성 목록은 다음과 같습니다.

* Spring Boot Stater            : Spring boot / Core / Logging
* Spring Boot Stater Security   : Authentication / Authorization 
* Spring Boot Stater Data JPA   : JDBC / JPA / Hibernate
* Spring Boot Stater Web        : Spring Web MVC & Tomcat
* Spring Boot Stater Test       : Test / Mock
* Spring fox Swagger-ui         : Documentation
* Lombok


## Requirements
본 프로젝트는 Maven 을 통해 빌드 하였으며, Java 8 이상이 필요합니다.  
또한 Lombok 0.30 이 지원되는 IDE 를 사용하여야 합니다.

## Usage
Maven 을 통해 프로젝트를 실행하면 (mvn spring-boot:run) http://localhost:8080 에서 실행됩니다.  
테스트용 메모리 데이터 베이스에 접속하기 위해서는 http://localhost:8080/h2-console 주소를 이용합니다.  
h2-console 초기 접속 user name 은 sa 입니다.

## Restful API
어플리케이션이 실행되면 다음 경로를 통해 API 를 이용할 수 있습니다.
```
http://localhost/v1/member/join - 멤버 조인 API, 이메일로된 아이디와 성명, 패스워드를 전송합니다.
http://localhost/v1/member/lonin - 멤버 로그인 API, 이메일형식의 아이디와 패스워드를 전송하면 토큰이 발급됩니다.
http://localhost/v1/member/info - 멤버 정보 API, 인증받은 토큰과 함께 정보를 요청합니다.
```

## Documentation
본 어플리케이션을 실행시킨 후 다음 경로(http://localhost:8080/swagger-ui.html) 로 접속하면 API swagger-ui 로 작성된 도큐먼트를 볼 수 있으며,  
swagger-ui 는 문서뿐만 아니라 직접 Api Request 와 Response 를 테스트 해 볼 수 있습니다.  

## 유의사항
본 어플리케이션은 테스트 용으로 종료시 데이터가 유지되지 않습니다.  

## 구현되지 않은 부분
본 어플리케이션은 샘플 프로젝트로써 JWT 규격을 이용하지만 보안 강화를 위한 리프레시 토큰까지 구현하지는 않았습니다.  
리프레시 토큰 구현하기 위해서는 리프래시 토큰을 저장하는 DB에 발급시 저장하고,  
TokenSet 에 AccessToken 과 refreshToken 을 함께 발급 후 AccessToken 이 만료되면 refreshToken을 DB 와 비교해서 재인가 받도록 구현할 수 있습니다.   
