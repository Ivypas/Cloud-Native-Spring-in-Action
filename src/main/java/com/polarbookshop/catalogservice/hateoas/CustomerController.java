package com.polarbookshop.catalogservice.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;


    /**
     * 1
     *
     * @param customerId
     * @return
     */
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable String customerId) {
        Customer customer = customerService.getCustomerDetail(customerId);
        customer.add(linkTo(CustomerController.class)
                .slash(customer.getCustomerId())
                .withSelfRel());
        return customer;
    }


    /**
     * 2.1
     *
     * @param customerId
     * @return
     */
    @GetMapping(value = "/{customerId}/orders", produces = {"application/hal+json"})
    public CollectionModel<Order> getOrdersForCustomer(@PathVariable final String customerId) {
        List<Order> orders = orderService.getAllOrdersForCustomer(customerId);
        orders.forEach(order -> order.add(linkTo(methodOn(CustomerController.class)
                .getOrderById(customerId, order.getOrderId()))
                .withSelfRel()));
        return CollectionModel.of(orders, linkTo(methodOn(CustomerController.class)
                .getOrdersForCustomer(customerId))
                .withSelfRel());
    }

    /**
     * 2.2
     *
     * @param customerId
     * @param orderId
     * @return
     */
    @GetMapping("/{customerId}/orders/{orderId}")
    public Order getOrderById(@PathVariable String customerId,
                              @PathVariable String orderId) {
        return orderService.getOrderById(customerId, orderId);
    }


    /**
     * 3
     *
     * @return
     */
    @GetMapping(produces = {"application/hal+json"})
    public CollectionModel<Customer> getAllCustomers() {
        List<Customer> allCustomers = customerService.allCustomers();

        allCustomers.forEach(customer -> {
            String customerId = customer.getCustomerId();
            customer.add(linkTo(CustomerController.class)
                    .slash(customerId)
                    .withSelfRel());
            if (orderService.getAllOrdersForCustomer(customerId).size() > 0) {
                customer.add(linkTo(methodOn(CustomerController.class)
                        .getOrdersForCustomer(customerId))
                        .withRel("allOrders"));
            }
        });

        return CollectionModel.of(allCustomers, linkTo(CustomerController.class)
                .withSelfRel());
    }

}
