package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.BookDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.model.BookData;
import com.example.bookstoreapp.service.IBookService;
import com.example.bookstoreapp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookRestController {
    @Autowired
    private IBookService iBookService;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/addbook")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookData bookData = iBookService.addBook(bookDTO);
        String token = tokenUtil.createToken(bookData.getBookId());
        ResponseDTO responseDTO = new ResponseDTO("Book is been added ", bookData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getBooksList() {
        List<BookData> bookDataList = iBookService.getBookList();
        ResponseDTO responseDTO = new ResponseDTO("Getting Record of All Books", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{token}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        BookData bookData = iBookService.getBookById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Getting Book by Id", bookData, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseDTO> deleteBookById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        iBookService.deleteBookById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Deleted Book By Id", "Deleted Id : " + tokenId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyauthor/{bookAuthor}")
    public ResponseEntity<ResponseDTO> getBookByAuthor(@PathVariable("bookAuthor") String bookAuthor) {
        List<BookData> bookDataList = iBookService.getBookByAuthor(bookAuthor);
        ResponseDTO responseDTO = new ResponseDTO("Getting books by author", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/orderascending")
    public ResponseEntity<ResponseDTO> sortBookAscendingOrder() {
        List<BookData> bookDataList = iBookService.sortBookAscendingOrder();
        ResponseDTO responseDTO = new ResponseDTO("Getting books in ascending order", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/orderdescending")
    public ResponseEntity<ResponseDTO> sortBookDescendingOrder() {
        List<BookData> bookDataList = iBookService.sortBookDescendingOrder();
        ResponseDTO responseDTO = new ResponseDTO("Getting books in descending order", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateBookById(@PathVariable("token") String token,@RequestBody BookDTO bookDTO) {
        int tokenId = tokenUtil.decodeToken(token);
        BookData bookData = iBookService.updateBookById(tokenId, bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated book for Id " + tokenId, bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}

