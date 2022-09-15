package com.example.demo.repository;

import com.example.demo.model.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepRepository extends JpaRepository<SalesRep, Integer> {

    public SalesRep findSalesRepsByName(String name);
}
