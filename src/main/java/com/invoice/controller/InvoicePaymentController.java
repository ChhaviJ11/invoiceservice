package com.invoice.controller;

import com.invoice.entity.InvoicePayment;
import com.invoice.entity.Tax;
import com.invoice.service.InvoicePaymentService;
import com.invoice.util.HTTPResponse;
import com.invoice.util.HttpConstant;
import com.invoice.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
public class InvoicePaymentController
{
    public static Logger logger = LoggerFactory.getLogger(InvoicePaymentController.class);
    @Autowired
    private InvoicePaymentService invoicePaymentService;

    @GetMapping("/payment")
    public String index() {
        return "Invoice payment service get started";
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------GET INVOICE PAYMENT BY CUSTOMER ID------------------------------------------------------------------------
@GetMapping("/invoice/payment/list")
public ResponseEntity<HTTPResponse> getInvoicePaymentList(@RequestParam(value = "customer_id") UUID customerId) {
    logger.info("getInvoicePaymentList() called");
    logger.info("  ");
    List< InvoicePayment> invoicePayment = invoicePaymentService.getAllInvoicePaymentByCustomerId(customerId);
    if (invoicePayment.size() > 0) {
        return ResponseEntity.ok(new HTTPResponse(invoicePayment, "success", HttpConstant.SUCCESS_STATUS_CODE));
    } else {
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }
}
//---------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------GET INVOICE PAYMENT BY ID-------------------------------------------------------------------------

    @GetMapping("/invoice/payment")
    public ResponseEntity<HTTPResponse> getInvoicePaymentById(@RequestParam UUID id) {
        logger.info("getInvoicePaymentById() called");
        logger.info("id: {}",id);
        Optional<InvoicePayment> invoicePayment = invoicePaymentService.getInvoicePaymentById(id);
        if (invoicePayment.isPresent()) {
            return ResponseEntity.ok(new HTTPResponse(invoicePayment.get(), "success", HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------ADD INVOICE PAYMENT-------------------------------------------------------------------------
    @PostMapping("/invoice/payment")
    public ResponseEntity<HTTPResponse> addInvoicePayment(@RequestParam(value = "customer_id") UUID customerId,
                                               @RequestParam(value = "payment_amount") Double paymentAmount,
                                               @RequestParam(value = "payment_mode") String paymentMode,
                                               @RequestParam(value = "payment_date") String paymentDate,
                                               @RequestParam(value = "remark") String remark,
                                               @RequestParam(value = "status") String status) {

        logger.info("addTax() called");
        logger.info("");
        logger.info("customerId: {}",customerId);
        logger.info("paymentAmount: {}",paymentAmount);
        logger.info("paymentMode: {}",paymentMode);
        logger.info("paymentDate: {}",paymentDate);
        logger.info("remark: {}",remark);
        logger.info("status: {}",status);
        if (customerId==null || customerId.equals(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "customer id should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (paymentAmount==null || paymentAmount.equals(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "payment amount should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (!(paymentAmount>0))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "payment amount is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }

        if (paymentMode == null || paymentMode.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "payment mode should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if(!paymentMode.equalsIgnoreCase("COD") &&!paymentMode.equalsIgnoreCase("UPI")&&!paymentMode.equalsIgnoreCase("NET BANKING"))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "payment mode is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (paymentDate == null || paymentDate.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "payment date should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }

        if (remark == null || remark.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "remark should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (status == null || status.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "status should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if(!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("done")&& !status.equalsIgnoreCase("failure"))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "status is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if(!Validator.validateDate(paymentDate))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "payment date is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        UUID id = UUID.randomUUID();
        InvoicePayment invoicePayment = new InvoicePayment();
        invoicePayment.setId(id);
        invoicePayment.setCustomerId(customerId);
        invoicePayment.setPaymentAmount(paymentAmount);
        invoicePayment.setPaymentMode(paymentMode);
        invoicePayment.setPaymentDate(paymentDate);
        invoicePayment.setRemark(remark);
        invoicePayment.setStatus(status);
        invoicePayment.setCreationTs(System.currentTimeMillis()/1000);
        invoicePayment.setDeleted(true);
        invoicePayment.setModifiedTs(0);
        invoicePaymentService.addInvoicePayment(invoicePayment);
        return ResponseEntity.ok(new HTTPResponse(invoicePayment,"data is created successfully",HttpConstant.SUCCESS_STATUS_CODE));

    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------UPDATE INVOICE PAYMENT------------------------------------------------------------------------
    @PutMapping("/invoice/payment")
    public ResponseEntity<HTTPResponse> updateIncomePayment(@RequestParam(value = "id") UUID id,
                                                  @RequestParam(value = "customer_id") UUID customerId,
                                               @RequestParam(value = "payment_amount",required = false) Double paymentAmount,
                                               @RequestParam(value = "payment_mode",required = false) String paymentMode,
                                               @RequestParam(value = "payment_date",required = false) String paymentDate,
                                               @RequestParam(value = "remark",required = false) String remark,
                                               @RequestParam(value = "status",required = false) String status,
                                               @RequestParam(value = "is_deleted",required = false) Boolean isDeleted) {
        logger.info("updateIncomePayment() called");
        logger.info("");
        logger.info("id: {}", id);
        logger.info("customerId: {}",customerId);
        logger.info("paymentAmount: {}",paymentAmount);
        logger.info("paymentMode: {}",paymentMode);
        logger.info("paymentDate: {}",paymentDate);
        logger.info("remark: {}",remark);
        logger.info("status: {}",status);
        logger.info("isDeleted: {}",isDeleted);
        Optional<InvoicePayment> invoicePayment = invoicePaymentService.getInvoicePaymentById(id);
        Map<String, Object> invoicePaymentData = new HashMap<>();
        if (invoicePayment.isPresent()) {
            if (paymentAmount != null) {
                if (!paymentAmount.equals("") && !paymentAmount.equals(invoicePayment.get().getPaymentAmount()))
                {
                    invoicePaymentData.put("paymentAmount",paymentAmount);
                }
            }
            //payment date issue occur when we update the date
            if (paymentDate != null) {
                if (!paymentDate.equals("") && !invoicePayment.get().getPaymentDate().equalsIgnoreCase(paymentDate)) {
                    try {
                        if (Validator.validateDate(paymentDate)) {
                            invoicePaymentData.put("invoiceDate", paymentDate);
                        }

                    }catch(Exception e)
                    {
                        return ResponseEntity.badRequest().body(new HTTPResponse(null,"date is not in correct format",HttpConstant.BAD_REQUEST_STATUS_CODE));
                    }
                }
            }
            if (paymentMode != null) {
                if (!paymentMode.equals("") && !invoicePayment.get().getPaymentMode().equalsIgnoreCase(paymentMode))
                {
                    if(!paymentMode.equalsIgnoreCase("COD") &&!paymentMode.equalsIgnoreCase("UPI")&&!paymentMode.equalsIgnoreCase("NET BANKING"))
                    {
                        return ResponseEntity.badRequest().body(new HTTPResponse(null, "payment mode is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
                    }
                    invoicePaymentData.put("paymentMode",paymentMode);
                }
            }
            if (remark != null) {
                if (!remark.equals("") &&  !invoicePayment.get().getRemark().equalsIgnoreCase(remark)) {
                    invoicePaymentData.put("remark",remark);
                }
            }
            if (status != null) {
                if (!status.equals("") && !invoicePayment.get().getStatus().equalsIgnoreCase(status)) {
                    if(!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("done")&& !status.equalsIgnoreCase("failure"))
                    {    return ResponseEntity.badRequest().body(new HTTPResponse(null, "status is not correct", HttpConstant.BAD_REQUEST_STATUS_CODE));
                    }

                    invoicePaymentData.put("status",status);
                }
            }
            if (isDeleted != null) {
                if (!isDeleted.equals("") && invoicePayment.get().isDeleted()!=(isDeleted)) {
                    invoicePaymentData.put("isDeleted",isDeleted);
                }
            }

           invoicePaymentData.put("modifiedTs", System.currentTimeMillis() / 1000);
            InvoicePayment result = invoicePaymentService.updateInvoicePayment(invoicePaymentData,id);
            return ResponseEntity.ok(new HTTPResponse(result, "data is updated successfully", HttpConstant.SUCCESS_STATUS_CODE));
        }


        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.BAD_REQUEST_STATUS_CODE));

    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------DELETE INVOICE PAYMENT-------------------------------------------------------------------------

    @DeleteMapping("/invoice/payment")
    public ResponseEntity<HTTPResponse> deleteInvoicePayment(@RequestParam UUID id) {
        logger.info("deleteInvoicePayment() called");
        logger.info("id: {}",id);
        Optional<InvoicePayment> invoicePayment = invoicePaymentService.getInvoicePaymentById(id);
        if (invoicePayment.isPresent()) {
            invoicePaymentService.deleteInvoicePayment(id);
            return ResponseEntity.ok(new HTTPResponse(null, "data is deleted successfully", HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }
}
