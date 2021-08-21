package bookstore.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 12-08-2021 12:08
 */

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCapped {
    @Id
    private String id;
    private String title;
    private String author;
    private String description;
    private Double price;
}
