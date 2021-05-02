package io.cjf.jimservice.service.impl;

import io.cjf.jimservice.dao.UxyRepository;
import io.cjf.jimservice.po.Uxy;
import io.cjf.jimservice.service.UxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<Uxy> batchLoad(List<String> uxyIds) {
        LinkedList<Uxy> list = new LinkedList<>();
        Iterable<Uxy> uxys = uxyRepository.findAllById(uxyIds);
        Map<String, Uxy> map = StreamSupport.stream(uxys.spliterator(), false).collect(Collectors.toMap(Uxy::getUxyId, uxy -> uxy));
        for (String uxyId : uxyIds) {
            Uxy uxy = map.get(uxyId);
            boolean add = list.add(uxy);
        }
        return list;
    }

    @Override
    public void batchSave(Iterable<Uxy> uxys) {
        List<Uxy> saves = uxyRepository.saveAll(uxys);
    }

    @Override
    public List<Uxy> batchGetByUx(String uxId) {
        List<Uxy> uxys = uxyRepository.findAllByUxId(uxId);
        return uxys;
    }

    @Override
    public List<Uxy> batchGetByUy(String uyId) {
        List<Uxy> uxys = uxyRepository.findAllByUyId(uyId);
        return uxys;
    }

}
