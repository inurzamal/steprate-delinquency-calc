package com.nur.service;

import com.nur.BookConstants;
import com.nur.model.Book;
import com.nur.model.SearchRequest;
import com.nur.model.SearchResponse;
import com.nur.repository.BookRepository;
import com.nur.repository.BookRepositoryImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookService {

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

            existingBook.setTitle(book.getTitle());
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

//    public List<SearchResponse> search(SearchRequest searchRequest){
//        List<Book> bookRecords = null;
//
//        if (isSearchReqEmpty(searchRequest)){
//            bookRepository.findAll();
//        }
//        else {
//            String title = searchRequest.getTitle();
//            String author = searchRequest.getAuthor();
//            String isbn = searchRequest.getIsbn();
//            Double price = searchRequest.getPrice();
//            LocalDate promoDate = searchRequest.getPromoDate();
//            LocalDate releaseDate = searchRequest.getReleaseDate();
//
//            Book book = new Book();
//
//            if (title != null && !title.isEmpty()){
//                book.setTitle(title);
//            }
//            if (author != null && !author.isEmpty()){
//                book.setAuthor(author);
//            }
//            if (isbn != null && !isbn.isEmpty()){
//                book.setIsbn(isbn);
//            }
//            if (price != null){
//                book.setPrice(price);
//            }
//            if (promoDate != null && releaseDate != null){
//                book.setPromoDate(promoDate);
//                book.setPromoDate(releaseDate);
//            }
//
//            Example<Book> of = Example.of(book);
//            bookRecords = bookRepository.findAll(of);
//        }
//
//        List<SearchResponse> searchResponses = new ArrayList<>();
//
//        for (Book bookRecord: bookRecords){
//            SearchResponse sr = new SearchResponse();
//            BeanUtils.copyProperties(bookRecord, sr);
//            searchResponses.add(sr);
//
//        }
//
//        return searchResponses;
//    }

//    private boolean isSearchReqEmpty(SearchRequest searchRequest) {
//        if ( searchRequest.getTitle() != null || !searchRequest.getTitle().equals("")){
//            return false;
//        }
//
//        return true;
//    }

    public List<Book> getBookByName(String bookName){
        return bookRepositoryImpl.findBookByName(bookName);
    }

    public List<SearchResponse> mongoSearch(SearchRequest searchRequest){
        Map<String, String> searchCriteriaMap = getSearchCriteriaMap(searchRequest);
        List<Book> bookList = bookRepositoryImpl.searchByCriteria(searchCriteriaMap);

        List<SearchResponse> searchResponses = new ArrayList<>();
        for (Book book: bookList){
            SearchResponse seachRecord = new SearchResponse();
            seachRecord.setTitle(book.getTitle());
            seachRecord.setAuthor(book.getAuthor());
            seachRecord.setIsbn(book.getIsbn());
            seachRecord.setPrice(book.getPrice());
            seachRecord.setPromoDate(book.getPromoDate());
            seachRecord.setReleaseDate(book.getReleaseDate());
            searchResponses.add(seachRecord);
        }

        return searchResponses;
    }

    /**
     *
     * @param searchRequest
     * @return map
     * if searchRequest is not empty then add to map, key will be db field, value is searchRequest field
     */
    private Map<String, String> getSearchCriteriaMap(SearchRequest searchRequest) {

        Map<String, String> map = new HashMap<>();
        //In the map, we need to add db fieldName as key and search request fields as value
//        map.put(BookConstants.BOOK_TITLE, searchRequest.getTitle());
        checkAndAddToMap(map, searchRequest.getTitle(), BookConstants.BOOK_TITLE);
        checkAndAddToMap(map, searchRequest.getAuthor(), BookConstants.BOOK_AUTHOR);
        checkAndAddToMap(map, searchRequest.getIsbn(), BookConstants.BOOK_ISBN);
        checkAndAddToMap(map, searchRequest.getPrice(), BookConstants.PRICE);
        checkAndAddToMap(map, searchRequest.getPromoDate(), BookConstants.BOOK_TITLE);
        checkAndAddToMap(map, searchRequest.getReleaseDate(), BookConstants.BOOK_TITLE);
        return map;
    }

    /*
    * if attribute or searchRequest is not empty then add to map
     */
    private void checkAndAddToMap(Map<String, String> map, String attribute, String mapKey) {
        if(attribute != null && !attribute.isEmpty()){
            map.put(mapKey, attribute);
        }
    }

}
