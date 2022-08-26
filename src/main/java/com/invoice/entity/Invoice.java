package com.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="invoice")
@JsonIgnoreProperties(value={"creation_ts","modified_ts"})
public class Invoice
{
    @Id
    private UUID id;
    private String title;
    private String description;
    @JsonProperty(value="invoice_no")
    @Column(name = "invoice_no")
    private UUID invoiceNo;
    @JsonProperty(value="org_id")
    @Column(name = "org_id")
    private UUID orgId;
    @JsonProperty(value="branch_id")
    @Column(name = "branch_id")
    private UUID branchId;
    @Column(name="customer_id")
    @JsonProperty(value = "customer_id")
    private UUID customerId;
    @Column(name="customer_code")
    @JsonProperty(value = "customer_code")
    private String customerCode;
    @Column(name="currency_id")
    @JsonProperty(value = "currency_id")
    private UUID currencyId;
    @Column(name="logo_url")
    @JsonProperty(value = "logo_url")
    private String logoUrl;
    private String date;
    @Column(name="due_date")
    @JsonProperty(value = "due_date")
    private String DueDate;
    @Column(name="payment_id")
    @JsonProperty(value = "payment_id")
    private UUID paymentId;
    private String status;
    private String note;
    @Column(name="sub_total")
    @JsonProperty(value = "sub_total")
    private double subTotal;
    @Column(name="tax_id")
    @JsonProperty(value = "tax_id")
    private UUID taxID;
    @Column(name="tax_amount")
    @JsonProperty(value = "tax_amount")
    private Double taxAmount;
    @Column(name="discount_id")
    @JsonProperty(value = "discount_id")
    private UUID discountId;
    @Column(name="discount_amount")
    @JsonProperty(value = "discount_amount")
    private Double discountAmount;
    @Column(name="discount_name")
    @JsonProperty(value = "discount_name")
    private String discountName;
    @Column(name="discount_value_type")
    @JsonProperty(value = "discount_value_type")
    private String discountValueType;
    private double total;
    @Column(name="is_deleted")
    @JsonProperty(value = "is_deleted")
    private Boolean isDeleted;
    @Column(name="creation_ts")
    @JsonProperty(value = "creation_ts")
    private long creationTs;
    @Column(name="modified_ts")
    @JsonProperty(value = "modified_ts")
    private long modifiedTs;
}
