package com.invoice.service;

import com.invoice.entity.Country;
import com.invoice.entity.Tax;
import com.invoice.repository.CountryRepository;
import com.invoice.repository.CustomCountryRepository;
import com.invoice.repository.CustomTaxRepository;
import com.invoice.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaxService
{
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private CustomTaxRepository repository;

    public List<Tax> getAllTax() {

        return taxRepository.findAll();
    }

    public Optional<Tax> getTaxByID(UUID id) {
        return taxRepository.findById(id);
    }

    public Tax addTax(Tax tax) {
        return taxRepository.save(tax);
    }

    public Tax updateTax(Map<String,Object> country, UUID id) {

        return repository.updateTax(country,id);

    }

    public void deleteTax(UUID id) {

        taxRepository.deleteById(id);
    }
}
