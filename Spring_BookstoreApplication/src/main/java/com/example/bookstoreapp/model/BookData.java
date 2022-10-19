package com.example.bookstoreapp.model;

import com.example.bookstoreapp.dto.BookDTO;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "book_table")
public @Data class BookData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_description")
    private String bookDescription;

    @Column(name = "book_image")
    private String bookImage;

    @Column(name = "book_price")
    private int bookPrice;

    public BookData() {
    }

    public BookData(BookDTO bookDTO) {
        this.updateBookData(bookDTO);
    }

    public void updateBookData(BookDTO bookDTO) {
        this.bookName = bookDTO.bookName;
        this.bookAuthor = bookDTO.bookAuthor;
        this.bookDescription = bookDTO.bookDescription;
        this.bookImage = bookDTO.bookImage;
        this.bookPrice = bookDTO.bookPrice;
    }
}

