package com.myjdbc.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * SimpleProduct class.
 */
public class SimpleProduct {

    // Fields of the class SimpleProduct.
    private UUID productId;
    private String name;
    private BigDecimal price;
    private UUID vendorId;
    private String vendorName;
    private String contact;
    private String phoneNumber;
    private String email;
    private String address;

    // Getters and setters.


    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SimpleProduct that = (SimpleProduct) o;
        return Objects.equals(productId, that.productId) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(vendorId, that.vendorId) && Objects.equals(vendorName, that.vendorName) && Objects.equals(contact, that.contact) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(email, that.email) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price, vendorId, vendorName, contact, phoneNumber, email, address);
    }

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", vendorId=" + vendorId +
                ", vendorName='" + vendorName + '\'' +
                ", contact='" + contact + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
