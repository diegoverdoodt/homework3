package com.example.demo.repository;

import com.example.demo.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRespository extends JpaRepository<Lead, Integer> {
    public Lead getLeadById(Integer id);
}
