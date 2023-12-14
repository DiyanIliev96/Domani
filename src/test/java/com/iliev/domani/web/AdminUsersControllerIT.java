package com.iliev.domani.web;

import com.iliev.domani.repository.CartItemRepository;
import com.iliev.domani.user.DomaniUserDetail;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminUsersControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartItemRepository cartItemRepository;

    public static UserDetails admin() {
        return new DomaniUserDetail(5L,"admin adminov","admin@admin.com",
                "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")));
    }
    @Test
    @WithMockUser(value = "admin@admin.com",password = "admin")
    void testUsersPageShown_hasRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users").with(user(admin())))
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
    @Transactional
    void testAdminDeleteUser_hasRoleAdmin() throws Exception {
        cartItemRepository.deleteAllByUser_Id(1L);
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
                .andExpect(view().name("edit-user"));
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
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/users/edit/3").with(csrf())
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

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testUserEditPageThrowWhenUserDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/edit/0").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
