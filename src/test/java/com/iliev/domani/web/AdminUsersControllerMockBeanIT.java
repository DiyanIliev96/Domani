package com.iliev.domani.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminUsersControllerMockBeanIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUsersPageShown_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    @Test
    @WithMockUser
    void testUsersPage_hasRoleUser_shouldRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testAdminDeleteUser_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/users/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }
}
