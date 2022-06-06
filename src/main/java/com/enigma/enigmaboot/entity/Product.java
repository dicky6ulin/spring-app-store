package com.enigma.enigmaboot.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mst_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "product_id")
    private String id;
    @Column(name = "name")
    private String productName;
    private Integer productPrice;
    private Integer stock;
    private String productImages;

    public Product(String id, String productName, Integer productPrice, Integer stock) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getProductPrice(), product.getProductPrice()) && Objects.equals(getStock(), product.getStock());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getProductPrice(), getStock());
    }
}
