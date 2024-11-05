package org.example.customermanagement.service;

import org.example.customermanagement.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private List<Customer> customers = new ArrayList<>();
    private Customer customer;  // This will be injected via setter injection

    @Autowired
    private NotificationService notificationService;

    // Setter Injection for Customer
    @Autowired
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void addCustomer(Customer customer) {
        customers.add(customer);
        notificationService.sendNotification("Customer added: " + customer.getName());
    }

    @Override
    public Customer getCustomer(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void updateCustomer(Customer customer) {
        Customer existingCustomer = getCustomer(customer.getId());
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setContactNumber(customer.getContactNumber());
            notificationService.sendNotification("Customer updated: " + customer.getName());
        }
    }

    @Override
    public void deleteCustomer(int id) {
        Customer customer = getCustomer(id);
        if (customer != null) {
            customers.remove(customer);
            notificationService.sendNotification("Customer deleted: " + customer.getName());
        }
    }
}
