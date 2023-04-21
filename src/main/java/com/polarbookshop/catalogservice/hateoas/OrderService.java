package com.polarbookshop.catalogservice.hateoas;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    List<Order> orders() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderId("1");
        order1.setPrice(1.11);
        order1.setQuantity(1);
        orders.add(order1);

        Order order2 = new Order();
        order2.setOrderId("2");
        order2.setPrice(2.22);
        order2.setQuantity(2);
        orders.add(order2);

        Order order3 = new Order();
        order3.setOrderId("3");
        order3.setPrice(3.33);
        order3.setQuantity(3);
        orders.add(order3);
        return orders;
    }

    public List<Order> getAllOrdersForCustomer(String customerId) {
        return orders();
    }

    public Order getOrderById(String customerId, String orderId) {
        return orders().stream().filter(order -> order.getOrderId().equals(orderId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("404"));
    }

}
