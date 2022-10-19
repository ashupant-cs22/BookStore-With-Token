package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.exceptions.UserRegistrationCustomException;
import com.example.bookstoreapp.model.CartData;
import com.example.bookstoreapp.model.OrderData;
import com.example.bookstoreapp.repository.OrderRepository;
import com.example.bookstoreapp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ICartService iCartService;

    @Autowired
    private TokenUtil tokenUtil;


    @Override
    public OrderData placeOrder(OrderDTO orderDTO) {
        CartData cartData = iCartService.getCartById(orderDTO.getCartId());
        int totalPrice = cartData.getTotalPrice();
        OrderData orderData = new OrderData(cartData, orderDTO);
        orderData.setTotalPrice(totalPrice);
        return orderRepository.save(orderData);
    }

    @Override
    public List<OrderData> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderData getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new UserRegistrationCustomException("Order with id " + orderId + " not found"));
    }

    @Override
    public OrderData cancelOrder(int orderId) {
        OrderData orderData = this.getOrderById(orderId);
        orderData.setCancel(true);
        return orderRepository.save(orderData);
    }

    @Override
    public OrderData verifyOrder(String token) {
        OrderData orderData = this.getOrderById(tokenUtil.decodeToken(token));
        return orderRepository.save(orderData);
    }
}
