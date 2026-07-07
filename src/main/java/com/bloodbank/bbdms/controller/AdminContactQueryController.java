package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.dto.MessageResponse;
import com.bloodbank.bbdms.dto.StatusUpdateRequest;
import com.bloodbank.bbdms.model.ContactQuery;
import com.bloodbank.bbdms.repository.ContactQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/contact-queries")
public class AdminContactQueryController {

    private final ContactQueryRepository contactQueryRepository;

    public AdminContactQueryController(ContactQueryRepository contactQueryRepository) {
        this.contactQueryRepository = contactQueryRepository;
    }

    @GetMapping
    public Page<ContactQuery> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactQueryRepository.findAllByOrderByPostingDateDesc(pageable);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        return contactQueryRepository.findById(id).map(query -> {
            query.setStatus(request.getStatus());
            contactQueryRepository.save(query);
            return ResponseEntity.ok(query);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!contactQueryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contactQueryRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Query deleted"));
    }
}
