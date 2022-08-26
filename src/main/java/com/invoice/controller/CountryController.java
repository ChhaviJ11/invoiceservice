package com.invoice.controller;
import com.invoice.entity.Country;
import com.invoice.service.CountryService;
import com.invoice.util.HTTPResponse;
import com.invoice.util.HttpConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CountryController
{

    public static Logger logger = LoggerFactory.getLogger(CountryController.class);
    @Autowired
    private CountryService countryService;

    @GetMapping("/country")
    public String index() {
        return "Country service get started";
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------GET COUNTRY------------------------------------------------------------------------

    @GetMapping("/invoice/country/list")
    public ResponseEntity<HTTPResponse> getCountryList() {
        logger.info("getCountryList() called");
        logger.info("  ");
        List<Country> countryList = countryService.getAllCountry();
        if (countryList.size() > 0) {
            return ResponseEntity.ok(new HTTPResponse(countryList, "success", HttpConstant.SUCCESS_STATUS_CODE));
        } else {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------ADD COUNTRY-------------------------------------------------------------------------
    @PostMapping("/invoice/country")
    public ResponseEntity<HTTPResponse> addCountry(@RequestParam("country_name") String countryName,
                                                   @RequestParam("country_code") String countryCode) {
        logger.info("addCountry() called");
        logger.info("");
        logger.info("country_name: {}",countryName);
        logger.info("country_code: {}",countryCode);
        if (countryName == null || countryName.equals("")) {
            logger.error("country name should not be empty");
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "country name should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (countryCode == null || countryCode.equals("")) {
            logger.error("country code should not be empty");
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "country code should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        for (Country country : countryService.getAllCountry()) {
            if (country.getCountryName().equalsIgnoreCase(countryName))
            {
                return ResponseEntity.ok(new HTTPResponse(null,"country name already exists",HttpConstant.CONTENT_NOT_FOUND_STATUS_CODE));
            }
            if(country.getCountryCode().equalsIgnoreCase(countryCode))
            {
                return ResponseEntity.ok(new HTTPResponse(null,"country code already exists",HttpConstant.CONTENT_NOT_FOUND_STATUS_CODE));
            }
        }
        UUID id = UUID.randomUUID();
        long creation_ts = System.currentTimeMillis() / 1000;
        Country countryList = countryService.addCountry(new Country(id, countryName, countryCode, creation_ts, 0));
        return ResponseEntity.ok(new HTTPResponse(countryList, "data is created successfully", HttpConstant.SUCCESS_STATUS_CODE));
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------UPDATE COUNTRY-------------------------------------------------------------------------
    @PutMapping("/invoice/country")
    public ResponseEntity<HTTPResponse> updateCountry(@RequestParam(value = "id") UUID id,
                                                      //pass only those fields which you want to update
                                                      @RequestParam(value = "country_name",required = false) String countryName,
                                                      @RequestParam(value = "country_code",required = false) String countryCode) {

        logger.info("Update Country() called");
        logger.info("");
        logger.info("id: {}",id);
        logger.info("country_name: {}",countryName);
        logger.info("country_code: {}",countryCode);
        Optional<Country> country= countryService.getCountryByID(id);
        Map<String,Object> countryData = new HashMap<>();
        if(country.isPresent())
        {
            if(countryName!=null)
            {
                if(!countryName.equals("")&&!country.get().getCountryName().equalsIgnoreCase(countryName))
                {
                    countryData.put("countryName",countryName);
                }
            }
            if(countryCode!=null)
            {
                if(!countryCode.equals("")&&!country.get().getCountryCode().equalsIgnoreCase(countryCode))
                {
                    countryData.put("countryCode",countryCode);
                }
            }
            countryData.put("modifiedTs", System.currentTimeMillis() / 1000);
            Country result=countryService.updateCountry(countryData,id);

            return ResponseEntity.ok(new HTTPResponse(result,"data is updated successfully",HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null,"data not found",HttpConstant.BAD_REQUEST_STATUS_CODE));

    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------DELETE COUNTRY-------------------------------------------------------------------------

    @DeleteMapping("/invoice/country")
    public ResponseEntity<HTTPResponse> deleteCountry(@RequestParam(value = "id") UUID id) {
        logger.info("deleteCountry() called");
        logger.info("id: {}",id);
        Optional<Country> country =countryService.getCountryByID(id);
        if (country.isPresent()) {
            countryService.deleteCountry(id);
            return ResponseEntity.ok(new HTTPResponse(null, "data is deleted successfully", HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------GET COUNTRY BY ID-------------------------------------------------------------------------

    @GetMapping("/invoice/country")
    public ResponseEntity<HTTPResponse> getCountryBYID(@RequestParam(value = "id") UUID id) {
        logger.info("getCountryBYID() called");
        logger.info("id: {}",id);
        Optional<Country> country = countryService.getCountryByID(id);
        System.out.println(country);

        if (country.isPresent())
        {
            return ResponseEntity.ok(new HTTPResponse(country.get(), "success",HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));

    }

}