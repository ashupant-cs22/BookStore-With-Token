package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.BookDTO;
import com.example.bookstoreapp.exceptions.UserRegistrationCustomException;
import com.example.bookstoreapp.model.BookData;
import com.example.bookstoreapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService implements IBookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookData addBook(BookDTO bookDTO) {
        BookData bookData = new BookData(bookDTO);
        return bookRepository.save(bookData);
    }

    @Override
    public List<BookData> getBookList() {
        return bookRepository.findAll();
    }

    @Override
    public BookData getBookById(int bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new UserRegistrationCustomException("Book with id " + bookId + " not found"));
    }

    @Override
    public List<BookData> getBookByAuthor(String bookAuthor) {
        return bookRepository.findBookDataByBookAuthor(bookAuthor);
    }

    @Override
    public List<BookData> sortBookAscendingOrder() {
        return bookRepository.sortBookAscendingOrder();
    }

    @Override
    public List<BookData> sortBookDescendingOrder() {
        return bookRepository.sortBookDescendingOrder();
    }

    @Override
    public BookData updateBookById(int bookId, BookDTO bookDTO) {
        BookData bookData = this.getBookById(bookId);
        bookData.updateBookData(bookDTO);
        return bookRepository.save(bookData);
    }

    @Override
    public void deleteBookById(int bookId) {
        BookData bookData = this.getBookById(bookId);
        bookRepository.delete(bookData);
    }
}
