package ru.practicum.ewm.user;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User toUser(UserDto userDto) {
        return new User(
                null,
                userDto.getName(),
                userDto.getEmail()
        );
    }

    public UserShortDto toShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }
}
