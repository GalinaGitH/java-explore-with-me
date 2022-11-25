package ru.practicum.ewm.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.practicum.ewm.user.UserService;
import ru.practicum.ewm.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                   @RequestParam(name = "from", defaultValue = "0") int from,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        log.debug("Total number of users: {}. Method: GET/findUsers in AdminUserController ", userService.findUsers(ids, from, size).size());
        return userService.findUsers(ids, from, size);
    }


    @PostMapping
    public UserDto registerUser(@RequestBody @Valid UserDto userDto) {
        UserDto userDtoSaved = userService.saveUser(userDto);
        log.debug("Number of added users: {}. Method: POST/registerUser in AdminUserController ", 1);
        return userDtoSaved;
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long userId) {
        userService.deleteUserById(userId);
        log.debug("User with id= {} deleted. Method: DELETE/deleteUser in AdminUserController", userId);
    }


}