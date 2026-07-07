package com.bloodbank.bbdms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_queries")
public class ContactQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "posting_date")
    private LocalDateTime postingDate;

    @Column(nullable = false)
    private String status = "Pending";

    @PrePersist
    public void prePersist() {
        this.postingDate = LocalDateTime.now();
        if (this.status == null) this.status = "Pending";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getPostingDate() { return postingDate; }
    public void setPostingDate(LocalDateTime postingDate) { this.postingDate = postingDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
