package com.nur.controller;

import com.nur.model.Book;
import com.nur.model.SearchRequest;
import com.nur.model.SearchResponse;
import com.nur.repository.BookRepository;
import com.nur.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

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

//    @GetMapping("/search")
//    public ResponseEntity<List<SearchResponse> > searchBook(@RequestBody SearchRequest searchRequest){
//        List<SearchResponse> searchResponses = bookService.search(searchRequest);
//        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
//    }

    @GetMapping("/name")
    public ResponseEntity<List<Book>> getBooksByName(@RequestParam("bookName") String bookName){
        List<Book> bookList = bookService.getBookByName(bookName);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/mongoSearch")
    public ResponseEntity<List<SearchResponse> > mongoSearchBook(@RequestBody SearchRequest searchRequest){
        List<SearchResponse> searchResponses = bookService.mongoSearch(searchRequest);
        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
    }

}
