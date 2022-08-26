package com.invoice.service;

import com.invoice.entity.InvoiceSequential;
import com.invoice.repository.CustomInvoiceSequentialRepository;
import com.invoice.repository.InvoiceSequentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceSequentialService
{
    @Autowired
    private InvoiceSequentialRepository invoiceSequentialRepository;

    @Autowired
    private CustomInvoiceSequentialRepository customInvoiceSequentialRepository;

    public List<InvoiceSequential> getAllInvoiceSequential()
    {
        return invoiceSequentialRepository.findAll();
    }

    public Optional<InvoiceSequential> getInvoiceSequentialById(UUID id)
    {
        return invoiceSequentialRepository.findById(id);
    }

    public InvoiceSequential addInvoiceSequential(InvoiceSequential invoiceSequential)
    {
        return invoiceSequentialRepository.save(invoiceSequential);
    }

    public InvoiceSequential updateInvoiceSequential(Map<String,Object> invoiceSequence, UUID id)
    {
        return customInvoiceSequentialRepository.updateInvoiceSequential(invoiceSequence, id);
    }

    public void deleteInvoiceSequential(UUID id)
    {
        invoiceSequentialRepository.deleteById(id);
    }

}
