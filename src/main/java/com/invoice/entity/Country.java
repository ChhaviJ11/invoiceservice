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
@Table(name="country")
@JsonIgnoreProperties(value={"creation_ts","modified_ts"})
public class Country
{
    @Id
    private UUID id;
    @Column(name="country_name")
    @JsonProperty(value = "country_name")
    private String countryName;
    @Column(name="country_code")
    @JsonProperty(value = "country_code")
    private String countryCode;
    @Column(name="creation_ts")
    @JsonProperty(value = "creation_ts")
    private long creationTs;
    @Column(name="modified_ts")
    @JsonProperty(value = "modified_ts")
    private long modifiedTs;
}