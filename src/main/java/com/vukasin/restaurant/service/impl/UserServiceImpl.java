package com.vukasin.restaurant.service.impl;

import com.vukasin.restaurant.converter.UserConverter;
import com.vukasin.restaurant.dto.UserRegistrationRequestDTO;
import com.vukasin.restaurant.dto.UserResponseDTO;
import com.vukasin.restaurant.dto.UserUpdateRequestDTO;
import com.vukasin.restaurant.model.Role;
import com.vukasin.restaurant.model.User;
import com.vukasin.restaurant.repository.UserRepository;
import com.vukasin.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Bad credentials.");

        }
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            throw new UsernameNotFoundException("Unathorized");
        }

        return user;
    }

    @Override
    public UserResponseDTO registerUser(UserRegistrationRequestDTO requestDTO) {

        if (userRepository.findUserByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists.");

        }

        User user = userConverter.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        user = userRepository.save(user);


        return userConverter.toDTO(user);
    }

    @Override
    public UserResponseDTO getById(Long id) {
       Optional<User> userOptional = userRepository.findById(id);
       if (userOptional.isEmpty()) {
           throw new RuntimeException("User not found with id: " + id);
       }
       User user = userOptional.get();
       return userConverter.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();

        for (User user : users) {
            UserResponseDTO userResponseDTO = userConverter.toDTO(user);
            userResponseDTOs.add(userResponseDTO);
        }

        return userResponseDTOs;
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }

        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank()) {
            requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }

        userConverter.updateEntity(user, requestDTO);

        User updatedUser = userRepository.save(user);

        return userConverter.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.delete(user);

    }
}
