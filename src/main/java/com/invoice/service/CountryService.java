package com.invoice.service;

import com.invoice.entity.Country;
import com.invoice.repository.CountryRepository;
import com.invoice.repository.CustomCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CountryService
{
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CustomCountryRepository repository;

    public List<Country> getAllCountry() {

        return countryRepository.findAll();
    }

    public Optional<Country> getCountryByID(UUID id) {
        return countryRepository.findById(id);
    }

    public Optional<Country> getCountryCode(String countryCode) {
        return countryRepository.findByCountryCode(countryCode);
    }

    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Map<String,Object> country, UUID id) {

        return repository.updateCountry(country,id);

    }

    public void deleteCountry(UUID id) {

            countryRepository.deleteById(id);
    }
}
