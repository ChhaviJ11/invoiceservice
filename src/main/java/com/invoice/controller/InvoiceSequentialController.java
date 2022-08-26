package com.invoice.controller;

import com.invoice.entity.InvoiceSequential;
import com.invoice.service.InvoiceSequentialService;
import com.invoice.util.HTTPResponse;
import com.invoice.util.HttpConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InvoiceSequentialController
{
    public static Logger logger = LoggerFactory.getLogger(InvoiceSequentialController.class);

    @Autowired
    private InvoiceSequentialService invoiceSequentialService;


    @GetMapping("/sequential")
    public String index() {
        return "Invoice sequential service get started";
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------GET INVOICE SEQUENTIAL LIST------------------------------------------------------------------------
    @GetMapping("/invoice/sequential/list")
    public ResponseEntity<HTTPResponse> getInvoiceSequentialList() {
        logger.info("getInvoiceSequentialList() called");
        logger.info("  ");
        List<InvoiceSequential> invoiceSequential = invoiceSequentialService.getAllInvoiceSequential();
        if (invoiceSequential.size() > 0) {
            return ResponseEntity.ok(new HTTPResponse(invoiceSequential, "success", HttpConstant.SUCCESS_STATUS_CODE));
        } else {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------GET INVOICE SEQUENTIAL BY ID-------------------------------------------------------------------------

    @GetMapping("/invoice/sequential")
    public ResponseEntity<HTTPResponse> getInvoiceSequentialById(@RequestParam UUID id) {
        logger.info("getInvoiceSequentialById() called");
        logger.info("id: {}",id);
        Optional<InvoiceSequential> invoiceSequential = invoiceSequentialService.getInvoiceSequentialById(id);
        if (invoiceSequential.isPresent()) {
            return ResponseEntity.ok(new HTTPResponse(invoiceSequential.get(), "success", HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------ADD INVOICE SEQUENTIAL-------------------------------------------------------------------------
    @PostMapping("/invoice/sequential")
    public ResponseEntity<HTTPResponse> addInvoiceSequential(@RequestParam(value = "org_id") UUID orgId,
                                                          @RequestParam(value = "branch_id") UUID branchId,
                                                          @RequestParam(value = "seq_start_no") Long seqStartNo,
                                                          @RequestParam(value = "seq_text") String seqText,
                                                          @RequestParam(value = "invoice_no_format") String invoiceNoFormat)

    {

        logger.info("addInvoiceSequential() called");
        logger.info("");
        logger.info("orgId: {}",orgId);
        logger.info("branchId: {}",branchId);
        logger.info("seqStartNo: {}",seqStartNo);
        logger.info("seqText: {}",seqText);
        logger.info("invoiceNoFormat: {}",invoiceNoFormat);
        if (orgId==null || orgId.equals(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "org id should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (branchId==null || branchId.equals(""))
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "branch id should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }

        if (seqStartNo == null || seqStartNo.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "sequential start no should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (seqText == null || seqText.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "seq text should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        if (invoiceNoFormat == null || invoiceNoFormat.equals("")) {
            return ResponseEntity.badRequest().body(new HTTPResponse(null, "invoice number format should not be empty", HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
        for(InvoiceSequential invoiceSequential:invoiceSequentialService.getAllInvoiceSequential())
        {
            if(invoiceSequential.getInvoiceNoFormat().equalsIgnoreCase(invoiceNoFormat))
            {
                return ResponseEntity.badRequest().body(new HTTPResponse(null,"invoice no format already exists",HttpConstant.BAD_REQUEST_STATUS_CODE));
            }
            if(invoiceSequential.getSeqStartNo()==seqStartNo)
            {
                return ResponseEntity.badRequest().body(new HTTPResponse(null,"seq start no already exists",HttpConstant.BAD_REQUEST_STATUS_CODE));
            }
        }

        UUID id = UUID.randomUUID();
        InvoiceSequential invoiceSequential = new InvoiceSequential();
        invoiceSequential.setId(id);
        invoiceSequential.setOrgId(orgId);
        invoiceSequential.setBranchId(branchId);
        invoiceSequential.setSeqStartNo(seqStartNo);
        invoiceSequential.setSeqCurrentNo(seqStartNo);
        invoiceSequential.setSeqText(seqText);
        if(invoiceNoFormat.equalsIgnoreCase("ST")&&invoiceNoFormat.equalsIgnoreCase("SEQNO")&&invoiceNoFormat.equalsIgnoreCase("FY")) {
            invoiceSequential.setInvoiceNoFormat(invoiceNoFormat);
        }
        else
        {
            return ResponseEntity.badRequest().body(new HTTPResponse(null,"invoice no format is not correct",HttpConstant.BAD_REQUEST_STATUS_CODE));
        }
            invoiceSequential.setCreationTs(System.currentTimeMillis()/1000);
        invoiceSequential.setDeleted(true);
        invoiceSequential.setModifiedTs(0);
        invoiceSequentialService.addInvoiceSequential(invoiceSequential);
        return ResponseEntity.ok(new HTTPResponse(invoiceSequential,"data is created successfully",HttpConstant.SUCCESS_STATUS_CODE));

    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------UPDATE INVOICE SEQUENTIAL------------------------------------------------------------------------
    @PutMapping("/invoice/sequential")
    public ResponseEntity<HTTPResponse> updateIncomeSequential(@RequestParam(value = "id") UUID id,
                                                               @RequestParam(value = "org_id",required = false) UUID orgId,
                                                               @RequestParam(value = "branch_id",required = false) UUID branchId,
                                                               @RequestParam(value = "seq_start_no",required = false) Long seqStartNo,
                                                               @RequestParam(value = "seq_text",required = false) String seqText,
                                                               @RequestParam(value = "invoice_no_format",required = false) String invoiceNoFormat,
                                                               @RequestParam(value = "is_deleted",required = false) Boolean isDeleted) {
        logger.info("updateIncomePayment() called");
        logger.info("");
        logger.info("orgId: {}",orgId);
        logger.info("branchId: {}",branchId);
        logger.info("seqStartNo: {}",seqStartNo);
        logger.info("seqText: {}",seqText);
        logger.info("invoiceNoFormat: {}",invoiceNoFormat);
        logger.info("isDeleted: {}",isDeleted);
        Optional<InvoiceSequential> invoiceSequential = invoiceSequentialService.getInvoiceSequentialById(id);
        Map<String, Object> invoiceSequentialData = new HashMap<>();
        if (invoiceSequential.isPresent()) {
            if (orgId != null) {
                if (!orgId.equals("") && !orgId.equals(invoiceSequential.get().getOrgId()))
                {
                   invoiceSequentialData.put("orgId",orgId);
                }
            }
            if (branchId != null) {
                if (!branchId.equals("") && !branchId.equals(invoiceSequential.get().getBranchId()))
                {
                    invoiceSequentialData.put("branchId",branchId);
                }
            }
            if (seqStartNo != null) {
                if (!seqStartNo.equals("") && invoiceSequential.get().getSeqStartNo()!=seqStartNo) {
                    invoiceSequentialData.put("seqStartNo",seqStartNo);
                }
            }
            if (seqText != null) {
                if (!seqText.equals("") && !invoiceSequential.get().getSeqText().equalsIgnoreCase(seqText)) {
                    invoiceSequentialData.put("seqText", seqText);
                }
            }
            if (invoiceNoFormat!= null) {
                if (!invoiceNoFormat.equals("") &&  !invoiceSequential.get().getInvoiceNoFormat().equalsIgnoreCase(invoiceNoFormat))
                {
                    if(invoiceNoFormat.equalsIgnoreCase("ST")&&invoiceNoFormat.equalsIgnoreCase("SEQNO")&&invoiceNoFormat.equalsIgnoreCase("FY")) {
                        invoiceSequentialData.put("invoiceNoFormat",invoiceNoFormat);
                    }
                    else
                    {
                        return ResponseEntity.badRequest().body(new HTTPResponse(null,"invoice no format is not correct",HttpConstant.BAD_REQUEST_STATUS_CODE));
                    }

                }
            }
            if (isDeleted != null) {
                if (!isDeleted.equals("") && invoiceSequential.get().isDeleted()!=isDeleted) {
                    invoiceSequentialData.put("isDeleted",isDeleted);
                }
            }

            invoiceSequentialData.put("modifiedTs", System.currentTimeMillis() / 1000);
            InvoiceSequential result = invoiceSequentialService.updateInvoiceSequential(invoiceSequentialData,id);
            return ResponseEntity.ok(new HTTPResponse(result, "data is updated successfully", HttpConstant.SUCCESS_STATUS_CODE));
        }


        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.BAD_REQUEST_STATUS_CODE));

    }


    //---------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------DELETE INVOICE SEQUENTIAL-------------------------------------------------------------------------

    @DeleteMapping("/invoice/sequential")
    public ResponseEntity<HTTPResponse> deleteInvoiceSequential(@RequestParam UUID id) {
        logger.info("deleteInvoiceSequential() called");
        logger.info("id: {}",id);
        Optional<InvoiceSequential> invoiceSequential = invoiceSequentialService.getInvoiceSequentialById(id);
        if (invoiceSequential.isPresent()) {
            invoiceSequentialService.deleteInvoiceSequential(id);
            return ResponseEntity.ok(new HTTPResponse(null, "data is deleted successfully", HttpConstant.SUCCESS_STATUS_CODE));
        }
        return ResponseEntity.badRequest().body(new HTTPResponse(null, "data not found", HttpConstant.NOT_FOUND_STATUS_CODE));
    }

}
