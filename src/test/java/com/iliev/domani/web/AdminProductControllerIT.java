package com.iliev.domani.web;

import com.iliev.domani.model.view.ProductView;
import com.iliev.domani.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void testGetMenuManagement_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/menu/management"))
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


}
