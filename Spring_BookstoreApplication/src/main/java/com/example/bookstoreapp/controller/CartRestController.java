package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.CartDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.model.CartData;
import com.example.bookstoreapp.service.ICartService;
import com.example.bookstoreapp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartRestController {
    @Autowired
    private ICartService iCartService;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/addcart")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO cartDTO) {
        CartData cartData = iCartService.addToCart(cartDTO);
        String token = tokenUtil.createToken(cartData.getCartId());
        ResponseDTO responseDTO = new ResponseDTO("Books Is Been Added To Cart", cartData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> findAllCarts() {
        Iterable<CartData> allCarts = iCartService.findAllCarts();
        ResponseDTO responseDTO = new ResponseDTO("Getting Items in Carts", allCarts);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{token}")
    public ResponseEntity<ResponseDTO> getCartById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        CartData cartData = iCartService.getCartById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Getting Cart by Id", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deletecart/{token}")
    public ResponseEntity<ResponseDTO> deleteCart(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        iCartService.deleteCart(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Deleting By Id", "Deleted cart id : " + tokenId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/updatequantity/{token}")
    public ResponseEntity<ResponseDTO> updateBookQuantity(@PathVariable("token") String token,@RequestParam(value = "quantity") int quantity) {
        int tokenId = tokenUtil.decodeToken(token);
        CartData cartData = iCartService.updateQuantity(tokenId, quantity);
        ResponseDTO responseDTO = new ResponseDTO("Updated quantity for Id", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}

