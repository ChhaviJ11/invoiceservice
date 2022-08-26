package com.invoice.repository;

import com.invoice.entity.Invoice;
import com.invoice.entity.InvoicePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID>
{
}