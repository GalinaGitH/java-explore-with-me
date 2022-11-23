package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.category.dto.CategoryDto;


import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
public class AdminCategoriesController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Create new category");
        return categoryService.createCategory(categoryDto);
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Update category");
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable("catId") long catId) {
        log.info("Delete category by categoryId={}", catId);
        categoryService.deleteCategory(catId);
    }
}
