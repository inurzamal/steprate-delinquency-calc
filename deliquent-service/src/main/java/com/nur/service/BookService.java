package com.nur.service;

import com.nur.BookConstants;
import com.nur.model.Book;
import com.nur.model.SearchRequest;
import com.nur.model.SearchResponse;
import com.nur.repository.BookRepository;
import com.nur.repository.BookRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    public static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRepositoryImpl bookRepositoryImpl;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(String id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
    }

    public String addABookToLibrary(Book book){
        if (book != null){
            bookRepository.save(book);
            return "Book added Successfully";
        }
        return "nothing added";
    }

    public Book updateBook(String id, Book book){
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("book is not present"));

            existingBook.setBookTitle(book.getBookTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setIsbn(book.getIsbn());
            return bookRepository.save(existingBook);
    }

    public String deleteBook(String id){
        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isPresent()){
            bookRepository.deleteById(id);
            return "Book deleted with id %id";
        }
        return "Book not available";
    }

    public List<Book> getBookByName(String bookName){
        return bookRepositoryImpl.findBookByName(bookName);
    }

    public List<SearchResponse> mongoSearch(SearchRequest searchRequest){
        LOGGER.info("Method mongoSearch start..");
        Map<String, String> searchCriteriaMap = getSearchCriteriaMap(searchRequest);
        List<Book> bookList = bookRepositoryImpl.searchByCriteria(searchCriteriaMap);

        List<SearchResponse> searchResponses = new ArrayList<>();
        for (Book book: bookList){
            SearchResponse seachRecord = new SearchResponse();
            seachRecord.setBookTitle(book.getBookTitle());
            seachRecord.setAuthor(book.getAuthor());
            seachRecord.setIsbn(book.getIsbn());
            seachRecord.setPrice(book.getPrice());
            seachRecord.setPromoDate(book.getPromoDate());
            seachRecord.setReleaseDate(book.getReleaseDate());
            searchResponses.add(seachRecord);
        }
        LOGGER.info("Method mongoSearch end..");
        return searchResponses;
    }

    /**
     *
     * @param searchRequest
     * @return map
     * if searchRequest is not empty then add to map, key will be db field, value is searchRequest field
     */
    private Map<String, String> getSearchCriteriaMap(SearchRequest searchRequest) {
        LOGGER.info("Method getSearchCriteriaMap start.");
        Map<String, String> map = new HashMap<>();

        /* In the map, we need to add db fieldName as key and search request fields as value */
       //map.put(BookConstants.BOOK_TITLE, searchRequest.getTitle());

        checkAndAddToMap(map, searchRequest.getBookTitle(), BookConstants.BOOK_TITLE);
        checkAndAddToMap(map, searchRequest.getAuthor(), BookConstants.BOOK_AUTHOR);
        checkAndAddToMap(map, searchRequest.getIsbn(), BookConstants.BOOK_ISBN);
        checkAndAddToMap(map, searchRequest.getPrice(), BookConstants.PRICE);
        checkAndAddToMap(map, searchRequest.getPromoDate(), BookConstants.PROMOTION_DATE);
        checkAndAddToMap(map, searchRequest.getReleaseDate(), BookConstants.PUBLISHED_DATE);
        LOGGER.info("Method getSearchCriteriaMap end.");
        return map;
    }

    /**
    * if attribute or searchRequest is not empty then add to map
     */
    private void checkAndAddToMap(Map<String, String> map, String attribute, String mapKey) {
        LOGGER.info("Method checkAndAddToMap start.");
        if(attribute != null && !attribute.isEmpty()){
            map.put(mapKey, attribute);
        }
        LOGGER.info("Method checkAndAddToMap end.");
    }

}
