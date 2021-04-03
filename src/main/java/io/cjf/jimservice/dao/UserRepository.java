package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
