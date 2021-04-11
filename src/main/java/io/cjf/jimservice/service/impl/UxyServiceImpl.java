package io.cjf.jimservice.service.impl;

import io.cjf.jimservice.dao.UxyRepository;
import io.cjf.jimservice.po.Uxy;
import io.cjf.jimservice.service.UxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UxyServiceImpl implements UxyService {

    @Autowired
    private UxyRepository uxyRepository;

    @Override
    public Uxy load(String uxyId) {
        Uxy uxy = null;
        Optional<Uxy> optional = uxyRepository.findById(uxyId);
        if (optional.isPresent()) {
            uxy = optional.get();
        }
        return uxy;
    }

    @Override
    public Uxy save(Uxy uxy) {
        Uxy save = uxyRepository.save(uxy);
        return save;
    }

}
