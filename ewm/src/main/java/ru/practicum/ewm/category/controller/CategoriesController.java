package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/categories")
@Slf4j
public class CategoriesController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Get List of categories of events. Method: GET/getCategories in CategoriesController");
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable("catId") long catId) {
        log.info("Get category by categoryId = {} , Method: GET/getCategoryById in CategoriesController", catId);
        return categoryService.getCategoryById(catId);
    }

}
