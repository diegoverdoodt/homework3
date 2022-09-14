package com.example.demo.repository;

import com.example.demo.model.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepRepository extends JpaRepository<SalesRep, Integer> {

    public SalesRep findSalesRepByName(String name);
}
