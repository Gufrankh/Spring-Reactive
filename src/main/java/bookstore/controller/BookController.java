package bookstore.controller;

import bookstore.document.Book;
import bookstore.exception.NotFoundException;
import bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bookstore.constants.BookConstants.BOOKS_END_POINT;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 04-08-2021 12:07
 */
@RestController
@Slf4j
public class BookController {

    @Autowired
    BookRepository bookRepository;

    /**
     * Created By Gufran Khan | 04-May-2021  |Get All Books Details
     */
    @GetMapping(BOOKS_END_POINT)
    public Flux<Book> getAllBookDetails() {
        log.info("Inside Class BookController Method getAllBookDetails");
        return bookRepository.findAll();
    }

    /**
     * Created By Gufran Khan | 04-May-2021  |Get Books Detail By Id
     */
    @GetMapping(BOOKS_END_POINT + "/{id}")
    public Mono<ResponseEntity<Book>> getBookDetailsById(@PathVariable String id) {
        log.info("Inside Class BookController Method getBookDetailsById");
        return bookRepository.findById(id)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .switchIfEmpty(Mono.error(new NotFoundException("NotFoundException  message!")));
    }

    /**
     * Created By Gufran Khan | 04-May-2021  |Create Book Detail
     */
    @PostMapping(BOOKS_END_POINT)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> createBookDetails(@RequestBody Mono<Book> book) {
        log.info("Inside Class BookController Method createBookDetails");
        return book.flatMap(resultBook -> {
            return bookRepository.save(resultBook);
        });
    }

    /**
     * Created By Gufran Khan | 04-May-2021  |Update Book Detail By Id
     */
    @PutMapping(BOOKS_END_POINT)
    public Mono<ResponseEntity<Book>> updateBook(@RequestBody Book book) {

        log.info("Inside Class BookController Method updateBook");
        return bookRepository.findById(book.getId())
                .flatMap(resultBook -> {
                    resultBook.setAuthor(book.getAuthor());
                    return bookRepository.save(resultBook);
                })
                .map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Created By Gufran Khan | 04-May-2021  |Get Book Detail By Author Name
     */
    @GetMapping(BOOKS_END_POINT + "/author/{author}")
    public Flux<Book> getBookDetailsByAuthor(@PathVariable String author) {
        log.info("Inside Class BookController Method getBookDetailsByAuthor");
        return bookRepository.findByAuthor(author);
    }

    /**
     * Created By Gufran Khan | 04-May-2021  |Delete Book Detail By Id
     */
    @DeleteMapping(BOOKS_END_POINT + "/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        log.info("Inside Class BookController Method deleteBook");
        return bookRepository.deleteById(id);
    }
}
