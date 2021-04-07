package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.Uxy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UxyRepository extends MongoRepository<Uxy, String> {
}
