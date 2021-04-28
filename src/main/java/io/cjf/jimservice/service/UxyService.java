package io.cjf.jimservice.service;

import io.cjf.jimservice.po.Uxy;

import java.util.List;

public interface UxyService {

    Uxy load(String uxyId);

    Uxy save(Uxy uxy);

    List<Uxy> batchLoad(List<String> uxyIds);

    List<Uxy> batchGetByUx(String uxId);

    List<Uxy> batchGetByUy(String uyId);

}
