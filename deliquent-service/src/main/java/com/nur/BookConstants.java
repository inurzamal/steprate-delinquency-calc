package com.nur;

import java.time.format.DateTimeFormatter;

public final class BookConstants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * assign database fields into constants
     */
    public static final String BOOK_TITLE = "bookTitle";
    public static final String BOOK_AUTHOR = "author"; //AUTHOR_CITY = "author.address.city";
    public static final String BOOK_ISBN = "isbn";
    public static final String PRICE = "price";
    public static final String PROMOTION_DATE = "promoDate";
    public static final String PUBLISHED_DATE = "releaseDate";

//    public static final String SQL_ERROR = "Unable to complete transaction, database execution failed";
}
