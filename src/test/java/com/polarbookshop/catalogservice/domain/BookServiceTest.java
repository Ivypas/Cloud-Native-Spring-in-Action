package com.polarbookshop.catalogservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookToCreateAlreadyExistsThenThrows() {
        var bookIsbn = "1234561232";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, "Polarsopia");

        when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true);

        assertThatThrownBy(
                // if (bookRepository.existsByIsbn(book.isbn())) throw new BookAlreadyExistsException(book.isbn());
                () -> bookService.addBookToCatalog(bookToCreate)
        )
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage(String.format("A book with ISBN %s already exists.", bookIsbn));
    }

    @Test
    void whenBookToReadDoesNotExistThenThrows() {
        var bookIsbn = "123456232";

        when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());

        assertThatThrownBy(
                // bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
                () -> bookService.viewBookDetails(bookIsbn)
        )
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage(String.format("The book with ISBN %s was not found.", bookIsbn));
    }

}