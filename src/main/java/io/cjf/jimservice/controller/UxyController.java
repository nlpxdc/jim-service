package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UxIdInDTO;
import io.cjf.jimservice.dto.in.UyIdInDTO;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.Uxy;
import io.cjf.jimservice.service.UxyService;
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

    @PostMapping("/loadByUy")
    public Uxy loadByUy(@RequestBody UyIdInDTO uyIdInDTO,
                        @RequestAttribute String currentUserId) throws ClientException {
        String uyId = uyIdInDTO.getUyId();
        if (uyId == null || uyId.isEmpty()) {
            throw new ClientException("invalid params");
        }
        String uxyId = String.format("%sV%s", currentUserId, uyId);
        Uxy uxy = uxyService.load(uxyId);
        return uxy;
    }

    @PostMapping("/loadByUx")
    public Uxy loadByUx(@RequestBody UxIdInDTO uxIdInDTO,
                        @RequestAttribute String currentUserId) throws ClientException {
        String uxId = uxIdInDTO.getUxId();
        if (uxId == null || uxId.isEmpty()) {
            throw new ClientException("invalid params");
        }
        String uxyId = String.format("%sV%s", uxId, currentUserId);
        Uxy uxy = uxyService.load(uxyId);
        return uxy;
    }

    @PostMapping("/applyFriend")
    public void applyFriend(@RequestBody UyIdInDTO uyIdInDTO,
                            @RequestAttribute String currentUserId) throws ClientException {
        String uyId = uyIdInDTO.getUyId();
        if (uyId == null || uyId.isEmpty() || uyId.equals(currentUserId)) {
            throw new ClientException("invalid params");
        }
        String uxyId = String.format("%sV%s", currentUserId, uyId);
        Uxy uxy = uxyService.load(uxyId);
        if (uxy == null) {
            uxy = new Uxy();
            uxy.setUxyId(String.format("%sV%s", currentUserId, uyId));
            uxy.setUxId(currentUserId);
            uxy.setUyId(uyId);
        }
        uxy.setApplyFriend(true);
        long now = System.currentTimeMillis();
        uxy.setApplyFriendTime(now);
        Uxy save = uxyService.save(uxy);
    }

    @PostMapping("/batchGetApplyFriend")
    public List<Uxy> batchGetApplyFriend(@RequestAttribute String currentUserId) {
        List<Uxy> xs = uxyService.batchGetByUxId(currentUserId);
        List<Uxy> ys = uxyService.batchGetByUyId(currentUserId);
        List<Uxy> xys = Stream.of(xs, ys).flatMap(Collection::stream).collect(Collectors.toList());
        List<Uxy> applyFriends = xys.stream().filter(Uxy::getApplyFriend).collect(Collectors.toList());
        return applyFriends;
    }

}
