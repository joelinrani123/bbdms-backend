package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.dto.PageDataUpdateRequest;
import com.bloodbank.bbdms.model.PageContent;
import com.bloodbank.bbdms.repository.PageContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/pages")
public class AdminPageController {

    private final PageContentRepository pageContentRepository;

    public AdminPageController(PageContentRepository pageContentRepository) {
        this.pageContentRepository = pageContentRepository;
    }

    @GetMapping
    public Page<PageContent> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pageContentRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageContent> get(@PathVariable Long id) {
        return pageContentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PageDataUpdateRequest request) {
        return pageContentRepository.findById(id).map(pageContent -> {
            pageContent.setPageData(request.getPageData());
            pageContentRepository.save(pageContent);
            return ResponseEntity.ok(pageContent);
        }).orElse(ResponseEntity.notFound().build());
    }
}
