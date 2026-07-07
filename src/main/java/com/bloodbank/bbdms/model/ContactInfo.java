package com.bloodbank.bbdms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    private Long id = 1L;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "contact_number")
    private String contactNumber;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
