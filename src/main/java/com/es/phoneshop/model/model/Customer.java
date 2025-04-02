package com.es.phoneshop.model.model;

import java.io.Serializable;

public class Customer implements Serializable, Cloneable {
    private String firstName;
    private String lastName;
    private String phone;

    public Customer() {}

    public Customer(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public Customer clone() {
        Customer clone;
        try {
            clone = (Customer) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
