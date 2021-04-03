package io.cjf.jimservice.service;

import io.cjf.jimservice.po.User;

public interface UserService {

    User load(String userId);

    User getByUsername(String username);

    User save(User user);

}
