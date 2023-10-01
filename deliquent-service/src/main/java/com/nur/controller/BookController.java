package com.nur.controller;

import com.nur.model.Book;
import com.nur.model.SearchRequest;
import com.nur.model.SearchResponse;
import com.nur.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<String> addABookToLibrary(@RequestBody Book book)
    {
        String response = bookService.addABookToLibrary(book);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks()
    {
        List<Book> allBooks = bookService.getAllBooks();
        if (allBooks.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") String id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateABook(@PathVariable("id") String id, @RequestBody Book book)
    {
        Book updateBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updateBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteABook(@PathVariable("id") String id)
    {
        String msg = bookService.deleteBook(id);
        return new ResponseEntity<>(msg, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Book>> getBooksByName(@RequestParam("bookName") String bookName){
        List<Book> bookList = bookService.getBookByName(bookName);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/mongoSearch")
    public ResponseEntity<List<SearchResponse> > mongoSearchBook(@RequestBody SearchRequest searchRequest){
        try {
            LOGGER.info(String.format("Searching in BookController with search request: %s",searchRequest));
            List<SearchResponse> searchResponses = bookService.mongoSearch(searchRequest);
            LOGGER.info("Search completed in BookController");
            return new ResponseEntity<>(searchResponses, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("An error occurred during mongoSearchBook() in controller BookController API {} api/v1/books/mongoSearch"+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
