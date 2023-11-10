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
public class AdminUsersControllerIT {

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

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testAdminCreateUserPageShown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-user"));
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

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditPageShown_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/edit/2").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-profile"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditWithBadSizeFullName_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/2").with(csrf())
                        .param("fullName","test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/edit/2"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditWithBadTypeEmail_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/2").with(csrf())
                        .param("fullName","testtest")
                        .param("email","nonExistingTypeOfEmail"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/edit/2"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditWithWrongLengthFullName_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/2").with(csrf())
                        .param("fullName","te")
                        .param("email","wrong@length.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/edit/2"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testSuccessUserEdit_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/2").with(csrf())
                        .param("fullName","testtest")
                        .param("email","test@testtest.com")
                        .param("roles", String.valueOf(0)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }
    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testSuccessUserEditRole_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/2").with(csrf())
                        .param("fullName","testtest")
                        .param("email","test@editRole.com")
                        .param("roles","2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testSuccessEditRoleToUser_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/2").with(csrf())
                        .param("fullName","testtest")
                        .param("email","test@editRole.com")
                        .param("roles","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void testUserEditPageForbidden_hasRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/2").with(csrf()))
                .andExpect(status().isForbidden());
    }
}
