package org.example.budgetmanager.controller;

import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUserById_Success() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .balance(100.0)
                .preferredCurrency(Currency.USD)
                .monthlyBudget(1000.0)
                .role(Role.USER)
                .build();

        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testCreateUser_Success() throws Exception {
        Mockito.doNothing().when(userService).addUser(any(User.class));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jane Doe",
                                    "email": "jane.doe@example.com",
                                    "preferredCurrency": "USD",
                                    "monthlyBudget": 2000.0,
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEditUser_Success() throws Exception {
        Mockito.doNothing().when(userService).editUser(any(User.class));

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "John Doe",
                                    "email": "john.updated@example.com",
                                    "preferredCurrency": "EUR",
                                    "monthlyBudget": 1500.0,
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void testDefineMonthlyBudget_Success() throws Exception {
        Mockito.doNothing().when(userService).defineMonthlyBudget(1L, 1000.0);

        mockMvc.perform(patch("/user/1/monthlyBudget=1000?budget=1000"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllUsers_Success() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .balance(100.0)
                .preferredCurrency(Currency.USD)
                .monthlyBudget(1000.0)
                .role(Role.USER)
                .build();

        Mockito.when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }
}
