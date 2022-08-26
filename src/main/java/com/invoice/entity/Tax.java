package com.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tax")
@JsonIgnoreProperties(value = {"creation_ts","modified_ts"})
public class Tax
{
    @Id
    private UUID id;
    private String name;
    @JsonProperty(value="country_code")
    @Column(name = "country_code")
    private String countryCode;
    @JsonProperty(value="value_type")
    @Column(name = "value_type")
    private String valueType;
    private double value;
    @JsonProperty(value="tax_type")
    @Column(name = "tax_type")
    private String taxType;
    @JsonProperty(value="creation_ts")
    @Column(name = "creation_ts")
    private long creationTs;
    @JsonProperty(value="modified_ts")
    @Column(name = "modified_ts")
    private long modifiedTs;
}
