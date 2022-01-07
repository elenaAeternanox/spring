package quru.qa.rest_backend;

import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import quru.qa.rest_backend.domain.info.Book;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class BooksShopTest {

    private RequestSpecification specification =
            with()
                    .baseUri("http://localhost:8080")
                    .basePath("/")
                    .contentType(JSON)
                    .log().all();

    @Test
    void getAllAuthorsTest() {
        specification
                .get("/getAllAuthors")
                .then()
                .statusCode(200)
                .body("results", not(emptyArray()));
    }

    @Test
    void getAllBooksTest() {
        specification
                .get("/getAllBooks")
                .then()
                .statusCode(200)
                .body("results", not(emptyArray()));
    }

    @Test
    void addAuthorSuccessfulTest() {
        Map<String, Object> body = Map.of(
                "fullName", "Olga Ivanova",
                "authorsBooks", List.of(new Book("New Book", 239, 100, true)));
        specification
                .body(body)
                .post("/addAuthor")
                .then()
                .statusCode(200)
                .body(CoreMatchers.containsString("New author was added"));

    }

    @Test
    void addAuthorFailedTest() {
        specification
                .post("/addAuthor")
                .then()
                .statusCode(400)
                .body("error", equalTo("Bad Request"));

    }

    @Test
    void addBookSuccessfulTest() {
        Map<String, Object> body = Map.of(
                "title", "New book",
                "pagesNumber", 789,
                "price", 578,
                "inStock", false);
        specification.body(body)
                .post("/addBook")
                .then()
                .statusCode(200)
                .body(CoreMatchers.containsString("New book was added"));

    }

    @Test
    void addBookFailedTest() {
        specification
                .post("/addBook")
                .then()
                .statusCode(400)
                .body("error", equalTo("Bad Request"));

    }

    @Test
    void getBooksByAuthorTest() {
        String fullName = "Oleg Vovevodin";
        specification
                .params("fullName", fullName)
                .get("/getBooksByAuthor")
                .then()
                .statusCode(200)
                .body("author.fullName", equalTo(fullName))
                .body("book", hasItem(List.of("Book 1", 400, 500, true)));
    }
}
