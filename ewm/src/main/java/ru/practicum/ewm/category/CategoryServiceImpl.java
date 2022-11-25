package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.exception.ConflictException;
import ru.practicum.ewm.user.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        List<EventCategory> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        final EventCategory eventCategory = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Not found category with id = " + catId));
        return categoryMapper.toDto(eventCategory);
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        EventCategory eventCategory = categoryMapper.toEventCategory(categoryDto);
        try {
            EventCategory savedCategory = categoryRepository.save(eventCategory);
            return categoryMapper.toDto(savedCategory);
        } catch (DataIntegrityViolationException | ConflictException ex) {
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        EventCategory categoryInStorage = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException("Not found category with id = " + categoryDto.getId()));
        if (categoryDto.getName() != null) {
            categoryInStorage.setName(categoryDto.getName());
        }
        try {
            categoryRepository.saveAndFlush(categoryInStorage);
            return categoryMapper.toDto(categoryInStorage);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        EventCategory eventCategory = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Not found category with id = " + catId));
        categoryRepository.delete(eventCategory);
    }
}
