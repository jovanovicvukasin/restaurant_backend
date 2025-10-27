package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.UserRegistrationRequestDTO;
import com.vukasin.restaurant.dto.UserResponseDTO;
import com.vukasin.restaurant.dto.UserUpdateRequestDTO;
import com.vukasin.restaurant.model.Role;
import com.vukasin.restaurant.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserResponseDTO toDTO(User entity) {
        if (entity == null) return null;

        return UserResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .role(entity.getRole() != null ? entity.getRole().name() : null)
                .build();
    }

    public User toEntity(UserRegistrationRequestDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .role(Role.CUSTOMER) // default role
                .build();
    }

    public void updateEntity(User entity, UserUpdateRequestDTO dto) {
        if (dto == null) return;

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getSurname() != null) entity.setSurname(dto.getSurname());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getAddress() != null) entity.setAddress(dto.getAddress());
        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());
    }


}
