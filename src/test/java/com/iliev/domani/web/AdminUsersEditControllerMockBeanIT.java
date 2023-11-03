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
public class AdminUsersEditControllerMockBeanIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditPageShown_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/edit/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("editProfile"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditWithBadSizeFullName_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/edit/1").with(csrf())
                        .param("fullName","test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/edit/1"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditWithBadTypeEmail_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/edit/1").with(csrf())
                        .param("fullName","testtest")
                        .param("email","nonExistingTypeOfEmail"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/edit/1"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testSuccessUserEdit_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/edit/1").with(csrf())
                        .param("fullName","testtest")
                        .param("email","test@test.com")
                        .param("role",""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }
    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testSuccessUserEditRole_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/edit/1").with(csrf())
                        .param("fullName","testtest")
                        .param("email","test@test.com")
                        .param("role","ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void testUserEditPageForbidden_hasRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/edit/1").with(csrf()))
                .andExpect(status().isForbidden());
    }
}
