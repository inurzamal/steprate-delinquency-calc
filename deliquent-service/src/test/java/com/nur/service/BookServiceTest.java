package com.nur.service;

import com.nur.BookConstants;
import com.nur.model.Book;
import com.nur.model.SearchRequest;
import com.nur.model.SearchResponse;
import com.nur.repository.BookRepositoryImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepositoryImpl bookRepositoryImpl;

    @Test
    public void testMongoSearch() {
        SearchRequest searchRequest = Instancio.create(SearchRequest.class);
        searchRequest.setPromoDate(LocalDateTime.now().minusDays(1).format(BookConstants.DATE_TIME_FORMATTER));
        searchRequest.setReleaseDate(LocalDateTime.now().format(BookConstants.DATE_TIME_FORMATTER));
        searchRequest.setIsbn(null);
        searchRequest.setBookTitle("");

        when(bookRepositoryImpl.searchByCriteria(any())).thenReturn(getModelList());
        List<SearchResponse> searchResponses = bookService.mongoSearch(searchRequest);
        assertNotNull(searchResponses);
        assertEquals(searchResponses.get(0).getBookTitle(), "My Life");
    }

    private List<Book> getModelList() {
        List<Book> list = new ArrayList<>();
        Book model = Instancio.create(Book.class);
        model.setPromoDate(LocalDateTime.now().minusDays(5));
        model.setBookTitle("My Life");
        list.add(model);
        return list;
    }
}
