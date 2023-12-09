package com.iliev.domani.web;

import com.iliev.domani.user.DomaniUserDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    public static UserDetails admin() {
        return new DomaniUserDetail(5L,"admin adminov","admin@admin.com",
                "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")));
    }

    public static MockMultipartFile image() throws IOException {
       return new MockMultipartFile(
                "image",
                "background.jpg",
                "image/jpg",
                Files.readAllBytes(Paths.get("src/main/resources/static/images/background.jpg"))
        );
    }
    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testGetMenuManagement_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/menu/management").with(user(admin())))
                .andExpect(model().attributeExists("allProducts"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu-management"));
    }

    @Test
    @WithMockUser
    void testGetMenuManagement_Unsuccessfully_NoPermission() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/menu/management"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testGetAddProductPage_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/menu/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-menu-item"));
    }

    @Test
    @WithMockUser
    void testGetAddProductPage_Unsuccessfully_NoPermission() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/menu/add"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testDeleteProduct_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/menu/delete/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/menu/management"));

    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    public void testSubmitForm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/menu/add")
                        .file(image())
                        .with(csrf())
                        .param("name", "Test Item")
                        .param("category", "Breakfast")
                        .param("description", "Test description")
                        .param("price", "10.99"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/menu/management"));

    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    public void testAddProduct_shortName() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/menu/add")
                        .file(image())
                        .with(csrf())
                        .param("name", "T")
                        .param("category", "Breakfast")
                        .param("description", "Test description")
                        .param("price", "10.99"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/menu/add"));

    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    public void testAddProduct_categoryNull() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/menu/add")
                        .file(image())
                        .with(csrf())
                        .param("name", "Test Item")
                        .param("category", "")
                        .param("description", "Test description")
                        .param("price", "10.99"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/menu/add"));

    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    public void testAddProduct_blankDescription() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/menu/add")
                        .file(image())
                        .with(csrf())
                        .param("name", "Test Item")
                        .param("category", "Breakfast")
                        .param("description", "")
                        .param("price", "10.99"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/menu/add"));

    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testAddProduct_NoPermission() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/menu/add")
                        .file(image())
                        .with(csrf())
                        .param("name", "Test Item")
                        .param("category", "Breakfast")
                        .param("description", "Test description")
                        .param("price", "10.99"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                ;

    }
}
