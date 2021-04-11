package io.cjf.jimservice.service;

import io.cjf.jimservice.po.Uxy;

public interface UxyService {

    Uxy load(String uxyId);

    Uxy save(Uxy uxy);

}
