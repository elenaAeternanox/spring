package quru.qa.rest_backend.domain.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String title;
    private Integer pagesNumber;
    private Integer price;
    private Boolean isInStock;
}
