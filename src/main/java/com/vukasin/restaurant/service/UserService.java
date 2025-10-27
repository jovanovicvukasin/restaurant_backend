package com.vukasin.restaurant.service;

import com.vukasin.restaurant.dto.UserRegistrationRequestDTO;
import com.vukasin.restaurant.dto.UserResponseDTO;
import com.vukasin.restaurant.dto.UserUpdateRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserResponseDTO registerUser(UserRegistrationRequestDTO requestDTO);
    UserResponseDTO getById(Long id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO);
    void deleteUser(Long id);
}
