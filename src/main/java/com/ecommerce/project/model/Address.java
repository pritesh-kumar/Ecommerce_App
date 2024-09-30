package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "The StreetName must be at least 5 characters long")
    private String street;

    @NotBlank
    @Size(min = 5, message = "The building name must be at least 5 characters long")
    private String building;

    @NotBlank
    @Size(min = 2, message = "The city name must be 2 at least characters long")
    private String city;

    @NotBlank
    @Size(min = 2, message = "The state name must be must be at least 2 characters long")
    private String state;

    @NotBlank
    @Size(min = 6, message = "The zipcode must be 6 characters long")
    private String zip;

    @NotBlank
    @Size(min = 5, message = "The countryName must be 5 characters long")
    private String country;

    public Address(String street, String building, String city, String state, String zip, String country) {
        this.street = street;
        this.building = building;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    List<User> users = new ArrayList<>();
}
