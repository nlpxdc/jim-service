package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.Uxy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UxyRepository extends MongoRepository<Uxy, String> {

    List<Uxy> findAllByUxId(String uxId);

    List<Uxy> findAllByUyId(String uyId);

}
