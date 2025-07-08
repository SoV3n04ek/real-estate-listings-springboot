package com.frent.apartments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frent.apartments.dto.AuthenticationRequest;
import com.frent.apartments.dto.ChangePasswordRequest;
import com.frent.apartments.model.User;
import com.frent.apartments.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // create a test user
        User user = User.builder()
                .email("test@example.com")
                .password(passwordEncoder.encode("oldPassword"))
                .username("testUser")
                .build();
        userRepository.save(user);
    }

    @Test
    void changePasswordTest() throws Exception {

        String authResponse = mockMvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        mapper.writeValueAsString(
                                new AuthenticationRequest(
                                        "test@example.com",
                                        "oldPassword"
                                )
                        )
                ))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = new ObjectMapper().readTree(authResponse).get("token").asText();

        // then change password
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("oldPassword");
        request.setNewPassword("newSecurePassword");
        request.setConfirmPassword("newSecurePassword");

        mockMvc.perform(post("/api/auth/changePassword")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
