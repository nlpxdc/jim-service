package io.cjf.jimservice.service.impl;

import io.cjf.jimservice.dao.UserRepository;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.User;
import io.cjf.jimservice.service.UserService;
import io.cjf.jimservice.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User load(String userId) {
        User user = null;
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public List<User> batchLoad(List<String> userIds) {
        Iterable<User> dbUsers = userRepository.findAllById(userIds);
        Map<String, User> map = StreamSupport.stream(dbUsers.spliterator(), false).collect(Collectors.toMap(User::getUserId, user -> user));
        List<User> users = new LinkedList<>();
        for (String userId : userIds) {
            User user = map.get(userId);
            users.add(user);
        }
        return users;
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User save(User user) {
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public User create(User user) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        user.setUserId(uuid);
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public User update(User user) throws ClientException, IllegalAccessException {
        User save = update(user, null);
        return save;
    }

    @Override
    public User update(User user, User dbUser) throws ClientException, IllegalAccessException {
        if (dbUser == null) {
            String userId = user.getUserId();
            dbUser = load(userId);
            if (dbUser == null) {
                throw new ClientException("non db user");
            }
        }
        String[] nulls = BeanUtil.getNulls(user);
        BeanUtils.copyProperties(user, dbUser, nulls);
        User save = userRepository.save(dbUser);
        return save;
    }

}
