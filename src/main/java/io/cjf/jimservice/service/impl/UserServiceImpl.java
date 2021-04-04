package io.cjf.jimservice.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.cjf.jimservice.dao.UserRepository;
import io.cjf.jimservice.po.User;
import io.cjf.jimservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        ImmutableMap<String, User> map = Maps.uniqueIndex(dbUsers, User::getUserId);
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

}
