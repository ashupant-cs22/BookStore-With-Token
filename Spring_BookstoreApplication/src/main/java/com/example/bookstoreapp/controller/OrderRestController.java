package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.model.EmailData;
import com.example.bookstoreapp.model.OrderData;
import com.example.bookstoreapp.service.IEmailService;
import com.example.bookstoreapp.service.IOrderService;
import com.example.bookstoreapp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/order")
public class OrderRestController {
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private TokenUtil tokenUtil;


    @PostMapping("/addorder")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        OrderData orderData = iOrderService.placeOrder(orderDTO);
        String token = tokenUtil.createToken(orderData.getOrderId());
        EmailData emailData = new EmailData(orderData.getCartId().getUserId().getEmail(),
                "Order confirmed",
                "Hi " + orderData.getCartId().getUserId().getFirstName() +
                        " " + orderData.getCartId().getUserId().getLastName() +
                        ", Click on the given below link to get details \n" + iEmailService.getOrderLink(token));
        iEmailService.sendEmail(emailData);
        ResponseDTO responseDTO = new ResponseDTO("Order is Been placed ", orderData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAllOrders() {
        List<OrderData> orderDataList = iOrderService.getAllOrders();
        ResponseDTO responseDTO = new ResponseDTO("Getting all the orders", orderDataList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/cancelorder/{token}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        OrderData orderData = iOrderService.cancelOrder(tokenId);
        EmailData emailData = new EmailData(orderData.getCartId().getUserId().getEmail(),
                "Cancelled order",
                "Hi " + orderData.getCartId().getUserId().getFirstName() +
                        " " + orderData.getCartId().getUserId().getLastName() +
                        ", Your order has been cancelled successfully");
        iEmailService.sendEmail(emailData);
        ResponseDTO responseDTO = new ResponseDTO("Order is Been Cancelled", "Order id " + tokenId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{token}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        OrderData orderData = iOrderService.getOrderById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Getting order by Id", orderData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyOrder(@PathVariable("token") String token) {
        OrderData orderData = iOrderService.verifyOrder(token);
        ResponseDTO responseDTO = new ResponseDTO("Your order is ", orderData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}

