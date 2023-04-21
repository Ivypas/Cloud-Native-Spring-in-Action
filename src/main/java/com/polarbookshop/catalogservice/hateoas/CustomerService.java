package com.polarbookshop.catalogservice.hateoas;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    List<Customer> customers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setCustomerId("1");
        customer1.setCustomerName("Jane");
        customer1.setCompanyName("Google");
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setCustomerId("2");
        customer2.setCustomerName("May");
        customer2.setCompanyName("Amazon");
        customers.add(customer2);
        return customers;
    }

    public Customer getCustomerDetail(String customerId) {
        return customers().stream().filter(customer -> customer.getCustomerId().equals(customerId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("404"));
    }

    public List<Customer> allCustomers() {
        return customers();
    }

}
