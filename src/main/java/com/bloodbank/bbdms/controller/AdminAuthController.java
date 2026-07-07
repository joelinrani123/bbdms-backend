package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.dto.ChangePasswordRequest;
import com.bloodbank.bbdms.dto.LoginRequest;
import com.bloodbank.bbdms.dto.MessageResponse;
import com.bloodbank.bbdms.model.Admin;
import com.bloodbank.bbdms.repository.AdminRepository;
import com.bloodbank.bbdms.security.TokenStore;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminRepository adminRepository;
    private final TokenStore tokenStore;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminAuthController(AdminRepository adminRepository, TokenStore tokenStore) {
        this.adminRepository = adminRepository;
        this.tokenStore = tokenStore;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(request.getUsername());

        if (adminOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), adminOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid username or password"));
        }

        String token = tokenStore.issueToken(request.getUsername());
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", request.getUsername());
        return ResponseEntity.ok(body);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            tokenStore.invalidate(header.substring(7));
        }
        return ResponseEntity.ok(new MessageResponse("Logged out"));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, HttpServletRequest httpRequest) {
        String username = (String) httpRequest.getAttribute("adminUsername");
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Current password is incorrect"));
        }
        if (request.getNewPassword() == null || !request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("New password and confirm password do not match"));
        }

        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminRepository.save(admin);
        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }
}
