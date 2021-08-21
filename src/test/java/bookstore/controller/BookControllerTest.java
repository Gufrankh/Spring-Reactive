package bookstore.controller;

import bookstore.document.Book;
import bookstore.exception.NotFoundException;
import bookstore.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bookstore.constants.BookConstants.BOOKS_END_POINT;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 04-08-2021 13:34
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
public class BookControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void getAllBookDetails() {
        Mockito.when(bookRepository.findAll())
                .thenReturn(Flux.just(Book
                        .builder()
                        .description("Education")
                        .build()
                ));
        webTestClient.get().uri(BOOKS_END_POINT)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("[0].description").isEqualTo("Education");
    }


    @Test
    public void getSingleBook() {
        Mockito.when(bookRepository.findById("610a60b9bb8af75d1304810c"))
                .thenReturn(Mono.just(Book
                        .builder()
                        .description("Education")
                        .price(1999.0)
                        .build()
                ));
        webTestClient.get().uri(BOOKS_END_POINT.concat("/{id}"), "610a60b9bb8af75d1304810c")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo("1999.0");
    }

    @Test
    public void getSingleBook_notFound() {
        Mockito.when(bookRepository.findById("610a60b9bb8af75d1304810c"))
                .thenReturn(Mono.error(new NotFoundException("NotFoundException error message!")));
        webTestClient.get().uri(BOOKS_END_POINT.concat("/{id}"), "610a60b9bb8af75d1304810c")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void getBookByAuthor() {
        Mockito.when(bookRepository.findByAuthor("Gufran"))
                .thenReturn(Flux.just(Book
                        .builder()
                        .description("Education")
                        .price(1999.0)
                        .build()
                ));
        webTestClient.get().uri(BOOKS_END_POINT.concat("/author/{author}"), "Gufran")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("[0].description").isEqualTo("Education");
    }

    @Test
    public void createBookDetail() {
        Book book = Book
                .builder()
                .id("ad3dxf")
                .title("GK")
                .author("Abc")
                .description("Story")
                .price(100.00)
                .build();

        Mockito.when(bookRepository.save(book))
                .thenReturn(Mono.just(book));
        webTestClient.post().uri(BOOKS_END_POINT)
                .bodyValue(book)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Book.class);

    }

    @Test
    public void updateBookDetail() {
        Book book = Book
                .builder()
                .id("ad3dxf")
                .title("GK")
                .author("Abc")
                .description("Story")
                .price(100.00)
                .build();

        Mockito.when(bookRepository.findById("ad3dxf"))
                .thenReturn(Mono.just(book));
        Mockito.when(bookRepository.save(book))
                .thenReturn(Mono.just(book));
        webTestClient.put().uri(BOOKS_END_POINT)
                .bodyValue(book)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class);

    }

    @Test
    public void deleteBook() {

        Mockito.when(bookRepository.deleteById("610a3d674dc2926fb9af407c"))
                .thenReturn(Mono.empty());
        webTestClient.delete().uri(BOOKS_END_POINT.concat("/{id}"), "610a3d674dc2926fb9af407c")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    public void getAllBookDetailsBackPressure() {
        webTestClient.get().uri(BOOKS_END_POINT)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

    }
}
