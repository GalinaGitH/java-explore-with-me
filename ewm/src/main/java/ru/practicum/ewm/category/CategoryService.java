package ru.practicum.ewm.category;

import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    /**
     * PUBLIC:
     * получение всех категорий
     */
    List<CategoryDto> getCategories(int from, int size);

    /**
     * PUBLIC:
     * получение подборки событий по его id
     */
    CategoryDto getCategoryById(long catId);

    /**
     * ADMIN:
     * добавление новой категории
     */
    CategoryDto createCategory(CategoryDto categoryDto);

    /**
     * ADMIN:
     * изменение категории
     */
    CategoryDto updateCategory(CategoryDto categoryDto);

    /**
     * ADMIN:
     * удаление категории
     */
    void deleteCategory(long catId);
}
