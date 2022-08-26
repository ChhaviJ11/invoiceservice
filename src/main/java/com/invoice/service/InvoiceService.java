package com.invoice.service;

import com.invoice.entity.Country;
import com.invoice.entity.Invoice;
import com.invoice.repository.CustomInvoiceRepository;
import com.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService
{
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomInvoiceRepository customInvoiceRepository;

    public List<Invoice> getAllInvoice() {

        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceByID(UUID id) {
        return invoiceRepository.findById(id);
    }

    public Invoice addInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Map<String,Object> invoice, UUID id) {

        return customInvoiceRepository.updateInvoice(invoice,id);

    }

    public void deleteInvoice(UUID id) {

        invoiceRepository.deleteById(id);
    }
}
