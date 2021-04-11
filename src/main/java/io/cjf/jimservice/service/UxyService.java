package io.cjf.jimservice.service;

import io.cjf.jimservice.po.Uxy;

import java.util.List;

public interface UxyService {

    Uxy load(String uxyId);

    Uxy save(Uxy uxy);

    List<Uxy> batchGetByUxId(String uxId);

    List<Uxy> batchGetByUyId(String uyId);

}
