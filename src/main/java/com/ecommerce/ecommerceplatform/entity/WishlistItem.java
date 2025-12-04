package com.ecommerce.ecommerceplatform.entity;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name="Wishlist_items")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="wishlist_id")
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="variant_id")
    private ProductVariant productVariant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
        if(wishlist.getWishlistItems() == null)
            wishlist.setWishlistItems(new ArrayList<>());
        wishlist.getWishlistItems().add(this);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        product.getWishlistItems().add(this);
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
        if(productVariant.getWishlistItems() == null)
            productVariant.setWishlistItems(new ArrayList<>());
        productVariant.getWishlistItems().add(this);
    }
}
