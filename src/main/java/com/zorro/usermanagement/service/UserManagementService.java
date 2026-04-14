package com.zorro.usermanagement.service;

import com.zorro.usermanagement.dto.UserRequestDto;
import com.zorro.usermanagement.dto.UserResponseDto;
import com.zorro.usermanagement.entity.User;
import com.zorro.usermanagement.exception.DuplicateUserException;
import com.zorro.usermanagement.exception.UserNotFoundException;
import com.zorro.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserManagementService {

    private final UserRepository userRepository;

    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // *** CREATE *** //
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("Email already in use: " + dto.getEmail());
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUserException("Username already taken: " + dto.getUsername());
        }
        User user = mapToEntity(dto);
        User saved = userRepository.save(user);
        return mapToDto(saved);
    }

    // *** GET ALL *** //
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // *** GET BY ID *** //
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return mapToDto(user);
    }

    // *** GET BY EMAIL *** //
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return mapToDto(user);
    }

    // *** GET BY USERNAME *** //
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return mapToDto(user);
    }

    // *** GET BY CITY *** //
    public List<UserResponseDto> getUsersByCity(String city) {
        return userRepository.findByCity(city).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // *** GET BY STATUS *** //
    public List<UserResponseDto> getUsersByStatus(User.UserStatus status) {
        return userRepository.findByStatus(status).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // *** GET BY COUNTRY AND STATUS *** //
    public List<UserResponseDto> getUsersByCountryAndStatus(String country, User.UserStatus status) {
        return userRepository.findByCountryAndStatus(country, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // *** SEARCH (full-text) *** //
    public List<UserResponseDto> searchUsers(String query) {
        return userRepository.searchUsers(query)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // *** UPDATE *** //
    @Transactional
    public UserResponseDto updateUser(UUID id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        // Validate email uniqueness only if changed
        if (!user.getEmail().equalsIgnoreCase(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("Email already in use: " + dto.getEmail());
        }

        // Validate username uniqueness only if changed
        if (!user.getUsername().equalsIgnoreCase(dto.getUsername())
                && userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUserException("Username already taken: " + dto.getUsername());
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setGender(dto.getGender());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setCountry(dto.getCountry());
        if (dto.getStatus() != null) {
            user.setStatus(dto.getStatus());
        }

        User updated = userRepository.save(user);
        return mapToDto(updated);
    }

    // *** DELETE *** //
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // *** MAPPER HELPERS *** //
    private User mapToEntity(UserRequestDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .city(dto.getCity())
                .country(dto.getCountry())
                .status(dto.getStatus() != null ? dto.getStatus() : User.UserStatus.ACTIVE)
                .build();
    }

    private UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setGender(user.getGender());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setCountry(user.getCountry());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
