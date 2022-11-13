package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        return null;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(long catId) {

    }
}
