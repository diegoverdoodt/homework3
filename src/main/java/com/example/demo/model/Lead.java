package com.example.demo.model;

public class Lead {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String companyName;

    private SalesRep salesRep;
    private static int initialID = 0;

    public Lead(String name, String phoneNumber, String email, String companyName, SalesRep salesRep) {
        this.name = name;
        setPhoneNumber(phoneNumber);
        setEmail(email);
        this.companyName = companyName;
        setUniqueId();
        this.salesRep = salesRep;
    }


    // Crear constructor sobrecargado para un cambio de Status de usuario por la conversión. Elimina la cuenta anterior y crea una nueva con nuevo id
    public String getName() {
        return name;
    }

    public void setUniqueId() {
        this.id = initialID++;
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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public SalesRep getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(SalesRep salesRep) {
        this.salesRep = salesRep;
    }
}
