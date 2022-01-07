package quru.qa.rest_backend.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import quru.qa.rest_backend.domain.info.AuthorInfo;
import quru.qa.rest_backend.domain.info.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.swing.UIManager.put;

@RestController
public class BooksShopController {

    Book book1 = new Book("Book 1", 400, 500, true);
    Book book2 = new Book("Book 2", 392, 439, true);
    Book book3 = new Book("Book 3", 200, 350, true);
    List<Book> books = Stream.of(book1, book2, book3).collect(Collectors.toList());

    Map<Integer, AuthorInfo> authors = new HashMap<>() {
        {
            put(1, AuthorInfo.builder().fullName("Oleg Vovevodin").authorsBooks(List.of(book1, book3)).build());
            put(2, AuthorInfo.builder().fullName("Anna Kolesnikova").authorsBooks(List.of()).build());
            put(3, AuthorInfo.builder().fullName("Ivan Pogodin").authorsBooks(List.of(book2)).build());
        }
    };

    @PostMapping("/addAuthor")
    @ApiOperation("Add an author")
    public String addAuthor(@RequestBody String name, @RequestParam List<Book> books) {
        Integer authorId = authors.size() + 1;

        authors.put(authorId, AuthorInfo.builder().fullName(name).authorsBooks(books).build());
        return "New author was added";
    }

    @PostMapping("/addBook")
    @ApiOperation("Add a book to author")
    public String addBook(@RequestBody Book book) {
        books.add(book);
        return "New book was added";
    }

    @GetMapping("/getBooksByAuthor")
    @ApiOperation("Get books by author")
    public List<List<Book>> getBooksByAuthor(@RequestParam String name) {
        List <AuthorInfo> authorInfos = new ArrayList<>();
        for (Map.Entry<Integer, AuthorInfo> entry : authors.entrySet()) {
            authorInfos.add(entry.getValue());
        }

        return authors.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(authorInfo -> authorInfo.getFullName().equals(name))
                .map(authorInfo -> authorInfo.getAuthorsBooks())
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllAuthors")
    @ApiOperation("Get list of all authors")
    public Map<Integer, AuthorInfo> getAuthorList() {
        return authors;
    }

    @GetMapping("/getAllBooks")
    @ApiOperation("Get list of all books")
    public List<Book> getAllBooks() {
        return books;
    }
}
