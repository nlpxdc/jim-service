package io.cjf.jimservice.service;

import io.cjf.jimservice.po.User;

import java.util.List;

public interface UserService {

    User load(String userId);

    List<User> batchLoad(List<String> userIds);

    User getByUsername(String username);

    User save(User user);

}
