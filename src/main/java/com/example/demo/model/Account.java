package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    enum Industry {
        PRODUCE,
        ECOMMERCE,
        MANUFACTURING,
        MEDICAL,
        OTHER;
    }
    @Id
    @Column(name="id")
    private int id;
    private static int initialID = 0;

    @Enumerated(EnumType.STRING)
    @Column(name="industry")
    private Industry industry;

    @Column(name = "employee_count")
    private int employeeCount;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "contactList")
    private List<Contact> contactList;

    @OneToMany(mappedBy = "opportunityList")
    private List<Opportunity> opportunityList;

    public Account(String industry, int employeeCount, String city, String country) {
        setUniqueId();
        setIndustry(industry);
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
        this.contactList = new ArrayList();
        this.opportunityList = new ArrayList();
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

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        if (industry.equals("ecommerce")) {
            this.industry = Industry.ECOMMERCE;
        } else if (industry.equals("production")) {
            this.industry = Industry.PRODUCE;
        } else if (industry.equals("manufacturing")) {
            this.industry = Industry.MANUFACTURING;
        } else if (industry.equals("medical")) {
            this.industry = Industry.MEDICAL;
        } else {
            this.industry = Industry.OTHER;
        };
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List getContactList() {
        return contactList;
    }

    public void setContactList(List contactList) {
        this.contactList = contactList;
    }

    public List getOpportunityList() {
        return opportunityList;
    }
    public void setOpportunityList(List opportunityList) {
        this.opportunityList = opportunityList;
    }

}
