package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Contact {

    @Id
    @Column(name="id")
    private int id;
    private static int initialID = 0;
    @Column(name="name")
    private String name;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="email")
    private String email;
    @Column(name="company_name")
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "contact_list")
    private Contact contactList;

    public Contact(Lead lead) {
        setUniqueId();
        this.name = lead.getName();
        this.phoneNumber = lead.getPhoneNumber();
        this.email = lead.getEmail();
        this.companyName = lead.getCompanyName();
    }
    public void setUniqueId() {
        this.id = initialID++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
