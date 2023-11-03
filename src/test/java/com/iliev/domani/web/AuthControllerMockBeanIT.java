package com.iliev.domani.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerMockBeanIT {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void testRegistrationPageShown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/user/register")
                .param("fullName","Test Testov")
                .param("email","test@test.com")
                .param("password","test123")
                .param("confirmPassword","test123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    void testUserRegistrationBadCredentials() throws Exception {
        mockMvc.perform(post("/user/register")
                .param("fullName","t")
                .param("email","test@test.com")
                .param("password","test123")
                .param("confirmPassword","test123")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/register"));
    }

    @Test
    void testGetLoginPage() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginUnsuccessfully() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/login")
                .param("username", "admin@admin.com")
                .param("password", "admin")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login-failed"));
    }
    @Test
    void testLoginSuccessfully() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/login")
                .param("email","admin@admin.com")
                .param("password","admin")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @WithMockUser
    void testLoginSuccessPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login-success").with(csrf()))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testLoginUnSuccessfullyPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login-failed").with(csrf())
                .param("email","wrongEmail")
                .param("password","wrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }
}
