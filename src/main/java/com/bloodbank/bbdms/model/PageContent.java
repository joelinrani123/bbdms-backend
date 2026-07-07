package com.bloodbank.bbdms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "page_contents")
public class PageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_name", nullable = false)
    private String pageName;

    @Column(name = "page_type", nullable = false, unique = true)
    private String pageType;

    @Column(name = "page_data", nullable = false, columnDefinition = "LONGTEXT")
    private String pageData;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPageName() { return pageName; }
    public void setPageName(String pageName) { this.pageName = pageName; }
    public String getPageType() { return pageType; }
    public void setPageType(String pageType) { this.pageType = pageType; }
    public String getPageData() { return pageData; }
    public void setPageData(String pageData) { this.pageData = pageData; }
}
