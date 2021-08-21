package bookstore.controller;

import bookstore.document.Book;
import bookstore.document.BookCapped;
import bookstore.repository.BookCappedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bookstore.constants.BookConstants.BOOKS_STREAM_END_POINT;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 12-08-2021 12:57
 */
@RestController
@Slf4j
public class BookCappedController {

    @Autowired
    BookCappedRepository bookCappedRepository;


    /**
     * Created By Gufran Khan | 12-May-2021  |Create Book Detail
     */
    @PostMapping(BOOKS_STREAM_END_POINT)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookCapped> createStreamBookDetails(@RequestBody Mono<BookCapped> book) {
        log.info("Inside Class BookCappedController Method createBookDetails");
        return book.flatMap(resultBook -> {
            return bookCappedRepository.save(resultBook);
        });
    }



    /**
     * Created By Gufran Khan | 12-May-2021  |Get Book Detail By Author Name
     */
    @GetMapping(value=BOOKS_STREAM_END_POINT + "/author/{author}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookCapped> getStreamBookDetailsByAuthor(@PathVariable String author) {
        log.info("Inside Class BookCappedController Method getBookDetailsByAuthor");
        return bookCappedRepository.findByAuthor(author);
    }


}
