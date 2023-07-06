package com.example.hra.repository;

import com.example.hra.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,String> {

    Country findByCountryId(String countryId);
}