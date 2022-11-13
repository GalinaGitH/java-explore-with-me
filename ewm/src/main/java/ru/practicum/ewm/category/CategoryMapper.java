package ru.practicum.ewm.category;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.dto.CategoryDto;

@Component
public class CategoryMapper {
    public CategoryDto toDto(EventCategory eventCategory) {
        return new CategoryDto(
                eventCategory.getId(),
                eventCategory.getName()
        );
    }

    public EventCategory toEventCategory(CategoryDto categoryDto) {
        return new EventCategory(
                categoryDto.getId(),
                categoryDto.getName()
        );
    }
}
