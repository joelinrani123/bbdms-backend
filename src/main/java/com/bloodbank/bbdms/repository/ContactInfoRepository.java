package com.bloodbank.bbdms.repository;

import com.bloodbank.bbdms.model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
}
