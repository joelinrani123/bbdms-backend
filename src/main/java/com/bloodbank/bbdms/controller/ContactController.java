package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.model.ContactQuery;
import com.bloodbank.bbdms.repository.ContactQueryRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactQueryRepository contactQueryRepository;

    public ContactController(ContactQueryRepository contactQueryRepository) {
        this.contactQueryRepository = contactQueryRepository;
    }

    // "Contact Us" -> "Send us a Message" form
    @PostMapping
    public ContactQuery submitMessage(@Valid @RequestBody ContactQuery query) {
        query.setId(null);
        query.setStatus("Pending");
        return contactQueryRepository.save(query);
    }
}
