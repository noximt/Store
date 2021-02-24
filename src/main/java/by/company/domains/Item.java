package by.company.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private long id;
    private User user;
    private String name;
    private String description;

    public Item(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
