package com.example.bookstoreapp.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CartDTO
{
    public int userId;
    public int bookId;
    public int quantity;
}


