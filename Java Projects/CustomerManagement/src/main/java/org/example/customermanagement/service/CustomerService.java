package org.example.customermanagement.service;

import org.example.customermanagement.model.Customer;

public interface CustomerService {
    void addCustomer(Customer customer);
    Customer getCustomer(int id);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);

    // Setter method for Customer (for demonstration)
    void setCustomer(Customer customer);
}
