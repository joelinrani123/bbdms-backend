package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.dto.MessageResponse;
import com.bloodbank.bbdms.model.Donor;
import com.bloodbank.bbdms.repository.DonorRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/donors")
public class AdminDonorController {

    private final DonorRepository donorRepository;

    public AdminDonorController(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @GetMapping
    public Page<Donor> listDonors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return donorRepository.findAll(pageable);
    }

    @PostMapping
    public Donor addDonor(@Valid @RequestBody Donor donor) {
        donor.setId(null);
        return donorRepository.save(donor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonor(@PathVariable Long id) {
        if (!donorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        donorRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Donor deleted"));
    }
}
