package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.Create;
import ru.practicum.ewm.user.Update;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
public class AdminCategoriesController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto addCategory(@RequestBody @Valid @Validated({Create.class}) CategoryDto categoryDto) {
        log.info("Create new category");
        return categoryService.createCategory(categoryDto);
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Valid @Validated({Update.class}) CategoryDto categoryDto) {
        log.info("Update category");
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable("catId") long catId) {
        log.info("Delete category by categoryId={}", catId);
        categoryService.deleteCategory(catId);
    }
}
