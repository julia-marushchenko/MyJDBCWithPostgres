package com.myjdbc.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Vendor class.
 */
public class Vendor {

    UUID vendorId;
    String name;
    private String contact;
    private String phone;
    private String email;
    private String address;

    // Getters and setters.

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        Vendor vendor = (Vendor) o;
        return Objects.equals(vendorId, vendor.vendorId) && Objects.equals(name, vendor.name) && Objects.equals(contact, vendor.contact) && Objects.equals(phone, vendor.phone) && Objects.equals(email, vendor.email) && Objects.equals(address, vendor.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorId, name, contact, phone, email, address);
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId=" + vendorId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
