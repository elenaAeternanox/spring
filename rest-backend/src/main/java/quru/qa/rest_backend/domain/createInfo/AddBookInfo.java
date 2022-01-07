package quru.qa.rest_backend.domain.createInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quru.qa.rest_backend.domain.info.AuthorInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookInfo {

    private AuthorInfo author;
    private String title;
    private Integer pagesNumber;
    private Boolean isInStock;
}
