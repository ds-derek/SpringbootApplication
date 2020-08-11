package com.example.app.controllers;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test Case :: Unknown Member Try To Login")
    public void test_unknown_member_login() throws Exception {
        String mockReq = "{'id' : 'testAccount@test.com', 'password' : '12345password!!!'}";
        String req = JsonParser.parseString(mockReq).getAsJsonObject().toString();

        this.mockMvc.perform(post("/v1/member/login").contentType("application/json").content(req))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("Test Case :: Unknown Member Try To Get Info")
    public void test_unAuthorized_member_access() throws Exception{
        this.mockMvc.perform(get("/v1/member/info").contentType("application/json"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

}