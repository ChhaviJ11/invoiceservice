package com.invoice.repository;

import com.invoice.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface CountryRepository extends JpaRepository<Country, UUID>
{
    @Query("select c from Country c where lower(c.countryCode)=lower(:countryCode)")
    public Optional<Country> findByCountryCode(@Param("countryCode") String countryCode);

}
