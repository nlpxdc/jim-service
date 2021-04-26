package io.cjf.jimservice.service;

import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.User;

import java.util.List;

public interface UserService {

    User load(String userId);

    List<User> batchLoad(List<String> userIds);

    User getByUsername(String username);

    User save(User user);

    User create(User user);

    User update(User user) throws ClientException, IllegalAccessException;

    User update(User user, User dbUser) throws ClientException, IllegalAccessException;

}
