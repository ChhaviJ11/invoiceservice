package com.invoice.repository;

import com.invoice.entity.InvoiceSequential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface InvoiceSequentialRepository extends JpaRepository<InvoiceSequential, UUID> {

}
