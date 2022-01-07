package quru.qa.rest_backend.domain.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInfo {

    private Integer authorId;
    private String fullName;
    private List<Book> authorsBooks;
}
