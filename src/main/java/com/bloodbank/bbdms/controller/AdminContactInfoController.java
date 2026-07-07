package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.model.ContactInfo;
import com.bloodbank.bbdms.repository.ContactInfoRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/contact-info")
public class AdminContactInfoController {

    private final ContactInfoRepository contactInfoRepository;

    public AdminContactInfoController(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }

    @GetMapping
    public ContactInfo get() {
        return contactInfoRepository.findById(1L).orElseGet(ContactInfo::new);
    }

    @PutMapping
    public ContactInfo update(@RequestBody ContactInfo request) {
        ContactInfo info = contactInfoRepository.findById(1L).orElseGet(() -> {
            ContactInfo fresh = new ContactInfo();
            fresh.setId(1L);
            return fresh;
        });
        info.setAddress(request.getAddress());
        info.setEmailId(request.getEmailId());
        info.setContactNumber(request.getContactNumber());
        return contactInfoRepository.save(info);
    }
}
