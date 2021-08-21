package bookstore.repository;

import bookstore.document.Book;
import bookstore.document.BookCapped;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 04-08-2021 12:05
 */
public interface BookCappedRepository extends ReactiveMongoRepository<BookCapped, String> {
    @Tailable
    Flux<BookCapped> findByAuthor(String author);

}
