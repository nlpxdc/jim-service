package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UxyIdsInDTO;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.Uxy;
import io.cjf.jimservice.service.UxyService;
import io.cjf.jimservice.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/uxy")
public class UxyController {

    @Autowired
    private UxyService uxyService;

    @PostMapping("/load")
    public Uxy load(@RequestBody Uxy in,
                    @RequestAttribute String currentUserId) throws ClientException {
        String uxId = in.getUxId();
        String uyId = in.getUyId();
        if (uxId == null || uxId.isEmpty() || uyId == null || uyId.isEmpty()) {
            throw new ClientException("invalid params");
        }
        if (!uxId.equals(currentUserId) && !uyId.equals(currentUserId)) {
            throw new ClientException("forbidden");
        }
        String uxyId = String.format("%sV%s", uxId, uyId);
        Uxy uxy = uxyService.load(uxyId);
        if (uxy == null) {
            throw new ClientException("non db uxy");
        }
        return uxy;
    }

    @PostMapping("/batchLoad")
    public List<Uxy> batchLoad(@RequestBody List<Uxy> ins,
                               @RequestAttribute String currentUserId) throws ClientException {
        if (ins == null || ins.isEmpty()) {
            throw new ClientException("invalid params");
        }
        for (Uxy in : ins) {
            if (!in.getUxId().equals(currentUserId) && !in.getUyId().equals(currentUserId)) {
                throw new ClientException("forbidden");
            }
        }
        List<String> uxyIds = ins.stream().map(uxy -> {
            String uxyId = String.format("%sV%s", uxy.getUxId(), uxy.getUyId());
            return uxyId;
        }).collect(Collectors.toList());
        List<Uxy> uxys = uxyService.batchLoad(uxyIds);
        return uxys;
    }

    @PostMapping("/update")
    public Uxy update(@RequestBody Uxy uxy,
                       @RequestAttribute String currentUserId) throws ClientException, IllegalAccessException {
        String uyId = uxy.getUyId();
        if (uyId == null || uyId.isEmpty()) {
            throw new ClientException("invalid params");
        }
        if (uyId.equals(currentUserId)) {
            throw new ClientException("same xy");
        }

        String uxyId = String.format("%sV%s", currentUserId, uyId);
        Uxy dbUxy = uxyService.load(uxyId);
        if (dbUxy == null) {
            dbUxy = new Uxy();
            dbUxy.setUxyId(uxyId);
            dbUxy.setUxId(currentUserId);
            dbUxy.setUyId(uyId);
        }
        String[] nulls = BeanUtil.getNulls(uxy);
        BeanUtils.copyProperties(uxy, dbUxy, nulls);

        long now = System.currentTimeMillis();
        if (uxy.getApplyFriend() != null && uxy.getApplyFriend()) {
            dbUxy.setApplyFriendTime(now);
        }
        if (uxy.getBeFriend() != null && uxy.getBeFriend()) {
            dbUxy.setBeFriendTime(now);
        }
        Uxy save = uxyService.save(dbUxy);
        return save;
    }

    @PostMapping("/batchGetApplyFriend")
    public List<Uxy> batchGetApplyFriend(@RequestAttribute String currentUserId) {
        List<Uxy> xs = uxyService.batchGetByUx(currentUserId);
        List<Uxy> ys = uxyService.batchGetByUy(currentUserId);
        List<Uxy> xys = Stream.of(xs, ys).flatMap(Collection::stream).collect(Collectors.toList());
        List<Uxy> applyFriends = xys.stream().filter(Uxy::getApplyFriend).collect(Collectors.toList());
        return applyFriends;
    }

}
