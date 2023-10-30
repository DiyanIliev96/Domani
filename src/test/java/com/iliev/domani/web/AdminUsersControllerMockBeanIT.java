package com.iliev.domani.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
}
