package com.zorro.usermanagement.controller;

import com.zorro.usermanagement.dto.UserRequestDto;
import com.zorro.usermanagement.dto.UserResponseDto;
import com.zorro.usermanagement.entity.User;
import com.zorro.usermanagement.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserManagementService userService;

    public UserController(UserManagementService userService) {
        this.userService = userService;
    }

    // *** Create User *** //
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        UserResponseDto created = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // *** Get All Users *** //
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // *** Get by ID *** //
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // *** Get by Email *** //
    @GetMapping("/email/{mail}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String mail) {
        return ResponseEntity.ok(userService.getUserByEmail(mail));
    }

    // *** Get by Username *** //
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    // *** Get by City *** //
    @GetMapping("/city/{city}")
    public ResponseEntity<List<UserResponseDto>> getUsersByCity(@PathVariable String city) {
        return ResponseEntity.ok(userService.getUsersByCity(city));
    }

    // *** Get by Status *** //
    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserResponseDto>> getUsersByStatus(@PathVariable User.UserStatus status) {
        return ResponseEntity.ok(userService.getUsersByStatus(status));
    }

    // *** Get by Country and Status *** //
    @GetMapping("/filter")
    public ResponseEntity<List<UserResponseDto>> getUsersByCountryAndStatus(@RequestParam String country,@RequestParam User.UserStatus status) {
        return ResponseEntity.ok(userService.getUsersByCountryAndStatus(country, status));
    }

    // *** Get by Country and Status *** //
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    // *** Update User *** //
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    // *** Delete User *** //
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
