package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.model.ContactInfo;
import com.bloodbank.bbdms.model.PageContent;
import com.bloodbank.bbdms.repository.ContactInfoRepository;
import com.bloodbank.bbdms.repository.PageContentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PublicContentController {

    private final PageContentRepository pageContentRepository;
    private final ContactInfoRepository contactInfoRepository;

    public PublicContentController(PageContentRepository pageContentRepository,
                                    ContactInfoRepository contactInfoRepository) {
        this.pageContentRepository = pageContentRepository;
        this.contactInfoRepository = contactInfoRepository;
    }

    // e.g. /api/pages/aboutus  /api/pages/donor  /api/pages/whydonate  /api/pages/needforblood
    @GetMapping("/pages/{pageType}")
    public PageContent getPage(@PathVariable String pageType) {
        Optional<PageContent> page = pageContentRepository.findByPageType(pageType);
        if (page.isPresent()) return page.get();
        PageContent empty = new PageContent();
        empty.setPageType(pageType);
        empty.setPageData("");
        return empty;
    }

    @GetMapping("/contact-info")
    public ContactInfo getContactInfo() {
        return contactInfoRepository.findById(1L).orElseGet(ContactInfo::new);
    }
}
