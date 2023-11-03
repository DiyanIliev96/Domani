package com.iliev.domani.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminUsersCreateMockBeanIT {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testAdminCreateUserPageShown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createUser"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testAdminCreateAdminSuccessfully() throws Exception {
        mockMvc.perform(post("/admin/users/create")
                        .param("fullName","Admin test")
                        .param("email","admin@test.com")
                        .param("password","test123")
                        .param("confirmPassword","test123")
                        .param("role","ADMIN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testAdminCreateUserSuccessfully() throws Exception {
        mockMvc.perform(post("/admin/users/create")
                        .param("fullName","Create user")
                        .param("email","create@user.com")
                        .param("password","test123")
                        .param("confirmPassword","test123")
                        .param("role","USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testAdminCreateUser_WrongLengthFullName() throws Exception {
        mockMvc.perform(post("/admin/users/create")
                        .param("fullName","A")
                        .param("email","a@a.com")
                        .param("password","test123")
                        .param("confirmPassword","test123")
                        .param("role","ADMIN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/create"));
    }
}
