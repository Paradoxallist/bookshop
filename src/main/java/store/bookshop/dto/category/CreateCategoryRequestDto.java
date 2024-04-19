package store.bookshop.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryRequestDto {
    private String name;
    private String description;

    public CreateCategoryRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
