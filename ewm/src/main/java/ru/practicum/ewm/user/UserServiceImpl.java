package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.exception.ConflictException;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.exception.EmailNotUniqueException;
import ru.practicum.ewm.user.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new EmailNotUniqueException("Already exist");
        }
        try {
            User savedUser = userRepository.save(user);
            return userMapper.toDto(savedUser);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public UserDto get(long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> findUsers(List<Long> ids, int from, int size) {
        int page = from / size;
        List<User> allUsers = userRepository.findAllUsersByIdIn(ids, PageRequest.of(page, size));
        return allUsers.stream()
                .map((User user) -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserById(long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
    }
}
