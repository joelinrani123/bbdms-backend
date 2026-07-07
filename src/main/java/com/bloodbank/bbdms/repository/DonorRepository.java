package com.bloodbank.bbdms.repository;

import com.bloodbank.bbdms.model.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    Page<Donor> findByBloodGroup(String bloodGroup, Pageable pageable);
}
