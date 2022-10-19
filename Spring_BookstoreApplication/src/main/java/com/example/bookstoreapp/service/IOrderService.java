package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.model.OrderData;
import java.util.List;

public interface IOrderService {

    OrderData placeOrder(OrderDTO orderDTO);

    List<OrderData> getAllOrders();

    OrderData getOrderById(int orderId);

    OrderData cancelOrder(int orderId);

    OrderData verifyOrder(String token);
}



