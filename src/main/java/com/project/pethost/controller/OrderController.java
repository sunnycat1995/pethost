package com.project.pethost.controller;

import com.project.pethost.dbo.OrderDbo;
import com.project.pethost.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/pethost")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(path = "/orders")
    public @ResponseBody Iterable<OrderDbo> getAllOrders() {
        return orderRepository.findAll();
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public @ResponseBody String createOrder(){
        return "Added new order";
    }

    @RequestMapping(value = "/waitingOrders", method = RequestMethod.GET)
    public @ResponseBody String waitingOrders(){
        return "Returned all waiting orders";
    }

    @RequestMapping(value = "/changeOrderStatus", method = RequestMethod.GET)
    public @ResponseBody String changeOrderStatus(){
        return "Changed order status";
    }
}