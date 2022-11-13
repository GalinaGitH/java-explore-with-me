package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public UserDto saveUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto get(long userId) {
        return null;
    }

    @Override
    public List<UserDto> findUsers(List<Long> ids, int from, int size) {
        return null;
    }

    @Override
    public void deleteUserById(long userId) {

    }
}
