package com.invoice.repository;

import com.invoice.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaxRepository extends JpaRepository<Tax, UUID>
{
}
