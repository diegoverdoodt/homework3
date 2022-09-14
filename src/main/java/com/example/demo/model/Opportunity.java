
package com.example.demo.model;

import javax.persistence.*;

public class Opportunity {
    public enum Status {
        OPEN,
        CLOSED_WON,
        CLOSED_LOST;
    }
    enum Product {
        HYBRID,
        FLATBED,
        BOX;
    }

    @Id
    @Column(name = "id")
    private int id;
    private static int initialID = 0;
    @Column(name = "product")
    @Enumerated(EnumType.STRING)
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @OneToOne
    @Column(name = "decision_maker")
    private Contact decisionMaker;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @Column(name = "sales_rep")
    private SalesRep salesRep;

    public Opportunity(String product, int quantity, Contact decisionMaker, SalesRep salesRep) {
        setUniqueId();
        setProduct(product);
        this.quantity = quantity;
        this.decisionMaker = decisionMaker;
        this.status = Status.OPEN;
        this.salesRep = salesRep;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(String product) {
        switch (product) {
            case "hybrid":
                this.product = Product.HYBRID;
                break;
            case "flatbed":
                this.product = Product.FLATBED;
                break;
            case "box":
                this.product = Product.BOX;
                break;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Contact getDecisionMaker() {
        return decisionMaker;
    }

    public void setDecisionMaker(Contact decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(String status) {
        if (status.contains("lost")){
            this.status = Status.CLOSED_LOST;
        } else if (status.contains("won")){
            this.status = Status.CLOSED_WON;
        }
    }

    public SalesRep getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(SalesRep salesRep) {
        this.salesRep = salesRep;
    }
}



