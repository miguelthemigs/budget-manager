//package org.example.budgetmanager.controller;
//
//import org.example.budgetmanager.model.Currency;
//import org.example.budgetmanager.model.Role;
//import org.example.budgetmanager.model.User;
//import org.example.budgetmanager.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    void testGetUserById_Success() throws Exception {
//        User user = User.builder()
//                .id(1L)
//                .name("John Doe")
//                .email("john.doe@example.com")
//                .balance(100.0)
//                .preferredCurrency(Currency.USD)
//                .monthlyBudget(500.0)
//                .role(Role.USER)
//                .build();
//
//        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user));
//
//        mockMvc.perform(get("/user/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
//                .andExpect(jsonPath("$.balance").value(100.0))
//                .andExpect(jsonPath("$.preferredCurrency").value("USD"))
//                .andExpect(jsonPath("$.monthlyBudget").value(500.0))
//                .andExpect(jsonPath("$.role").value("USER"));
//    }
//
//    @Test
//    void testGetUserById_NotFound() throws Exception {
//        Mockito.when(userService.findById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/user/1"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testCreateUser_Success() throws Exception {
//        Mockito.doNothing().when(userService).addUser(any(User.class));
//
//        mockMvc.perform(post("/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                {
//                                    "name": "Jane Doe",
//                                    "email": "jane.doe@example.com",
//                                    "password": "password123",
//                                    "balance": 200.0,
//                                    "preferredCurrency": "EUR",
//                                    "monthlyBudget": 1000.0,
//                                    "role": "USER"
//                                }
//                                """))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void testDeleteUser_Success() throws Exception {
//        Mockito.doNothing().when(userService).deleteUser(1L);
//
//        mockMvc.perform(delete("/user/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testEditUser_Success() throws Exception {
//        Mockito.doNothing().when(userService).editUser(any(User.class));
//
//        mockMvc.perform(put("/user/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                {
//                                    "id": 1,
//                                    "name": "John Updated",
//                                    "email": "john.updated@example.com",
//                                    "balance": 300.0,
//                                    "preferredCurrency": "USD",
//                                    "monthlyBudget": 800.0,
//                                    "role": "USER"
//                                }
//                                """))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testDefineMonthlyBudget_Success() throws Exception {
//        Mockito.doNothing().when(userService).defineMonthlyBudget(eq(1L), eq(1200.0));
//
//        mockMvc.perform(patch("/user/1/monthlyBudget=1200")
//                        .param("budget", "1200.0"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testGetAllUsers_Success() throws Exception {
//        User user1 = User.builder()
//                .id(1L)
//                .name("Alice Brown")
//                .email("alice.brown@example.com")
//                .balance(500.0)
//                .preferredCurrency(Currency.GBP)
//                .monthlyBudget(2000.0)
//                .role(Role.USER)
//                .build();
//
//        User user2 = User.builder()
//                .id(2L)
//                .name("Bob White")
//                .email("bob.white@example.com")
//                .balance(300.0)
//                .preferredCurrency(Currency.EUR)
//                .monthlyBudget(1500.0)
//                .role(Role.ADMIN)
//                .build();
//
//        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
//
//        mockMvc.perform(get("/user/all"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("Alice Brown"))
//                .andExpect(jsonPath("$[0].preferredCurrency").value(Currency.GBP.toString()))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].role").value("ADMIN"));
//    }
//}
