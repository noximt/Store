package by.company.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<E> {
    private boolean bool;
    private String description;
    private E element;

    public Response(boolean bool, String description) {
        this.bool = bool;
        this.description = description;
    }
}
