package com.bloodbank.bbdms.repository;

import com.bloodbank.bbdms.model.PageContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageContentRepository extends JpaRepository<PageContent, Long> {
    Optional<PageContent> findByPageType(String pageType);
    Page<PageContent> findAll(Pageable pageable);
}
