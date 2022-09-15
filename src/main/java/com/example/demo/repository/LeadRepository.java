package com.example.demo.repository;

import com.example.demo.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Integer> {

    public Lead getLeadById(Integer id);
}
