package com.ecomerce.projeto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ecomerce.projeto.controller.dto.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ProjetoApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() throws Exception {

//            String user = "user_test_6";
            String password = "passwordABC123";
            String email = "user_test_6@gmail.com";

            var registerRequest = String.format("""
                            {
                                "email":"%s"
                                "password":"%s",
                            }
                            """, email, password);

            // Register a user
            mockMvc.perform(post("/api/auth/register")
                            .contentType("application/json")
                            .content(registerRequest))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.message",
                                            org.hamcrest.Matchers.is("User registered successfully")));

            var loginRequest = String.format("""
                            {
                                "email":"%s",
                                "password":"%s"
                            }
                            """, email, password);
            // Login with the registered user and get a token
            var responseString = mockMvc.perform(post("/api/auth/login")
                            .contentType("application/json")
                            .content(loginRequest))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.token",
                                            org.hamcrest.Matchers.notNullValue()))
                            .andReturn()
                            .getResponse().getContentAsString();

            var loginResponse = new ObjectMapper().readValue(responseString, LoginResponse.class);

            // Use the token to access a protected resource
            mockMvc.perform(get("/api/dashboard")
                            .header("Authorization", "Bearer " + loginResponse.token()))
                            .andExpect(status().isOk())
                            .andExpect(content().string(Matchers.is("Hello " + email)));
    }

}
