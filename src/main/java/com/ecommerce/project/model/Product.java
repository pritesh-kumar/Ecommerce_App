package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @Valid
    @Size(min = 5, max = 50, message = "The number of Characters must be above 5")
    private String productName;
    private String description;
    private String image;
//    @Valid
//    @NotBlank(message = "The quantity cannot be left blank")
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
    @ManyToOne
    @JoinColumn(name ="Category_Id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    @OneToMany(mappedBy="product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();
    public void setCategory(Category category) {
        this.category = category;
        if(!category.getProducts().contains(this)) {
            category.getProducts().add(this);
        }
    }

}
