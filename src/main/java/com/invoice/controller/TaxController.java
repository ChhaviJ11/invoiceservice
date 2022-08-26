package com.invoice.controller;

import com.invoice.entity.Country;
import com.invoice.entity.Tax;
import com.invoice.service.CountryService;
import com.invoice.service.TaxService;
import com.invoice.util.HTTPResponse;
import com.invoice.util.HttpConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TaxController {
    public static Logger logger = LoggerFactory.getLogger(TaxController.class);
    @Autowired
    private TaxService service;

    @Autowired
    private CountryService countryService;

    @GetMapping("/tax")
    public String index() {
        return "Tax service get started";
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------GET INVOICE TAX------------------------------------------------------------------------

    @GetMapping("/invoice/tax/list")
    public ResponseEntity<HTTPResponse> getTax() {
        logger.info("getTax() called");
        logger.info("  ");
        List<Tax> tax = service.getAllTax();
        if (tax.size() > 0) {
            return ResponseEntity.ok(new HTTPResponse(tax, null, HttpConstant.SUCCESS_STATUS_CODE));
        } else {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------ADD TAX-------------------------------------------------------------------------
    @PostMapping("/invoice/tax")
    public ResponseEntity<HTTPResponse> addTax(@RequestParam(value = "name") String name,
                                      @RequestParam(value = "value_type") String valueType,
                                      @RequestParam(value = "country_code") String countryCode,
                                      @RequestParam(value = "value") Double value,
                                      @RequestParam(value = "tax_type") String taxType) {

        logger.info("addTax() called");
        logger.info("");
        logger.info("name: {}",name);
        logger.info("valueType: {}",valueType);
        logger.info("value: {}",value);
        logger.info("countryCode: {}",countryCode);
        logger.info("taxType: {}",taxType);
        if (countryCode==null || countryCode.equals(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "country code should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (name==null || name.equals(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "name should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (value == null || value.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "value should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (!(value>0)) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "value is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if(valueType==null||valueType.equalsIgnoreCase(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "value type should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if(taxType==null||taxType.equalsIgnoreCase(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "tax type should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if(!valueType.equalsIgnoreCase("Percent") &&!valueType.equalsIgnoreCase("Amount"))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "value type is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if(!taxType.equalsIgnoreCase("invoice") && !taxType.equalsIgnoreCase("item"))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "tax type is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }


        UUID id = UUID.randomUUID();
        Tax taxList = new Tax();
        taxList.setId(id);
        Optional<Country> country=countryService.getCountryCode(countryCode);
        try {
            if (countryCode.equalsIgnoreCase(country.get().getCountryCode())) {
                taxList.setCountryCode(countryCode);
            }
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "country code not mention in the country list", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        taxList.setName(name);
        taxList.setValueType(valueType);
        taxList.setValue(value);
        taxList.setTaxType(taxType);
        taxList.setCreationTs(System.currentTimeMillis()/1000);
        taxList.setModifiedTs(0);
        service.addTax(taxList);
        return ResponseEntity.ok(new HTTPResponse(taxList,"data is created successfully",HttpConstant.SUCCESS_STATUS_CODE));

    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------UPDATE TAX-------------------------------------------------------------------------
    @PutMapping("/invoice/tax")
    public ResponseEntity<HTTPResponse> updateTax(@RequestParam(value = "id") UUID id,
                                         @RequestParam(value = "name",required = false) String name,
                                         @RequestParam(value = "value_type",required = false) String valueType,
                                         @RequestParam(value = "country_code",required = false) String countryCode,
                                         @RequestParam(value = "value",required = false) Double value,
                                         @RequestParam(value = "tax_type",required = false) String taxType)
    {
        logger.info("updateTax() called");
        logger.info("");
        logger.info("id: {}",id);
        logger.info("country_code: {}",countryCode);
        logger.info("name: {}",name);
        logger.info("value_type: {}",valueType);
        logger.info("value: {}",value);
        logger.info("tax_type: {}",taxType);

        Optional<Tax> tax= service.getTaxByID(id);


        Map<String, Object> taxData = new HashMap<>();
        if (tax.isPresent()) {
            if(countryCode!=null)
            {
               if (!countryCode.equals("")&&!countryCode.equalsIgnoreCase(tax.get().getCountryCode())) {
                   try {
                       Optional<Country> country = countryService.getCountryCode(countryCode);
                       if (countryCode.equalsIgnoreCase(country.get().getCountryCode())) {
                           taxData.put("countryCode",countryCode);
                       }
                   }
                   catch(Exception e){
                       return ResponseEntity.badRequest().body(new HTTPResponse(null, "country code not mention in the country list", HttpConstant.BAD_REQUEST_STATUS_CODE));
                   }

                }
            }
            if (name != null) {
                if (!name.equals("") && !tax.get().getName().equalsIgnoreCase(name)) {
                    taxData.put("name", name);
                }
            }
            if (valueType != null) {
                if (!valueType.equals("") && !tax.get().getValueType().equalsIgnoreCase(valueType)) {
                    if(!valueType.equalsIgnoreCase("Percent") &&!valueType.equalsIgnoreCase("Amount"))
                    {
                        return ResponseEntity.badRequest().body(new HTTPResponse(null, "value type is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
                    }
                    taxData.put("valueType", valueType);
                }
            }
            if (value!=null) {
                if (!value.equals("") && tax.get().getValue()!=value) {
                    taxData.put("value", value);
                }
            }
            if (taxType != null) {
                if (!taxType.equals("") && !tax.get().getTaxType().equalsIgnoreCase(taxType)) {
                    if(!taxType.equalsIgnoreCase("invoice") && !taxType.equalsIgnoreCase("item"))
                    {
                        return ResponseEntity.badRequest().body(new HTTPResponse(null, "tax type is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
                    }

                    taxData.put("taxType", taxType);
                }
            }
            taxData.put("modifiedTs", System.currentTimeMillis() / 1000);
            Tax result = service.updateTax(taxData, id);
            return ResponseEntity.ok(new HTTPResponse(result, "data is updated successfully", HttpConstant.SUCCESS_STATUS_CODE));
        }


        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.BAD_REQUEST_STATUS_CODE));

    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------DELETE TAX-------------------------------------------------------------------------

    @DeleteMapping("/invoice/tax")
    public ResponseEntity<HTTPResponse> deleteTax(@RequestParam UUID id) {
        logger.info("deleteTax() called");
        logger.info("id: {}",id);
        Optional<Tax> tax =service.getTaxByID(id);
        if (tax.isPresent()) {
            service.deleteTax(id);
            return ResponseEntity.ok(new HTTPResponse(null, "data is deleted successfully", HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------GET TAX BY ID-------------------------------------------------------------------------

    @GetMapping("/invoice/tax")
    public ResponseEntity<HTTPResponse> getTaxById(@RequestParam UUID id) {
        logger.info("getTaxById() called");
        logger.info("id: {}",id);
        Optional<Tax> tax = service.getTaxByID(id);
        if (tax.isPresent()) {
            return ResponseEntity.ok(new HTTPResponse(tax.get(), "success", HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }

}
