package org.example.customermanagement;
import org.example.customermanagement.model.Customer;
import org.example.customermanagement.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CustomerService customerService = context.getBean(CustomerService.class);

        // Adding customers
        Customer customer1 = new Customer(1, "Patrick Xiong", "PXStiegler@gmail.com", "8009786514");
        customerService.addCustomer(customer1);

        // Retrieving a customer
        Customer retrievedCustomer = customerService.getCustomer(1);
        System.out.println("Retrieved Customer: " + retrievedCustomer.getName());

        // Updating a customer
        customer1.setName("Lumina Xiong");
        customerService.updateCustomer(customer1);

        // Deleting a customer
        customerService.deleteCustomer(1);
    }
}
