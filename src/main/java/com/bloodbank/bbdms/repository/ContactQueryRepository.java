package com.bloodbank.bbdms.repository;

import com.bloodbank.bbdms.model.ContactQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactQueryRepository extends JpaRepository<ContactQuery, Long> {
    Page<ContactQuery> findAllByOrderByPostingDateDesc(Pageable pageable);
}
