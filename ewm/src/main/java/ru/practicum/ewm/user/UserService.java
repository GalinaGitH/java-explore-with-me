package ru.practicum.ewm.user;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    /**
     * регистрация/сохранение нового пользователя
     */
    UserDto saveUser(UserDto userDto);

    /**
     * получение пользователя по id
     */
    UserDto get(long userId);

    /**
     * получение списка всех пользователей
     */
    List<UserDto> findUsers(List<Long> ids, int from, int size);

    /**
     * удаление пользователя
     */
    void deleteUserById(long userId);

}
