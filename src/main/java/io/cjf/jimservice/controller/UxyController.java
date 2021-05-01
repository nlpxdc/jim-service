package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UxIdIndTO;
import io.cjf.jimservice.dto.in.UyIdInDTO;
import io.cjf.jimservice.dto.out.UxyShowOutDTO;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.Uxy;
import io.cjf.jimservice.service.UxyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/uxy")
public class UxyController {

    @Autowired
    private UxyService uxyService;

    @PostMapping("/loadAsUxByUy")
    public UxyShowOutDTO loadAsUxByUy(@RequestBody UyIdInDTO uyIdInDTO,
                                      @RequestAttribute String currentUserId) throws ClientException {
        String uyId = uyIdInDTO.getUyId();
        if (uyId == null || uyId.isEmpty()) {
            throw new ClientException("invalid params");
        }
        if (!uyId.equals(currentUserId)) {
            throw new ClientException("same");
        }
        String uxyId = String.format("%sV%s", currentUserId, uyId);
        Uxy uxy = uxyService.load(uxyId);
        if (uxy == null) {
            throw new ClientException("non db uxy");
        }
        UxyShowOutDTO uxyShowOutDTO = new UxyShowOutDTO();
        BeanUtils.copyProperties(uxy, uxyShowOutDTO);
        return uxyShowOutDTO;
    }

    @PostMapping("/loadAsUyByUx")
    public UxyShowOutDTO loadAsUyByUx(@RequestBody UxIdIndTO uxIdIndTO,
                                      @RequestAttribute String currentUserId) throws ClientException {
        String uxId = uxIdIndTO.getUxId();
        if (uxId == null || uxId.isEmpty()) {
            throw new ClientException("invalid params");
        }
        if (!uxId.equals(currentUserId)) {
            throw new ClientException("same");
        }
        String uxyId = String.format("%sV%s", uxId, currentUserId);
        Uxy uxy = uxyService.load(uxyId);
        if (uxy == null) {
            throw new ClientException("non db uxy");
        }
        UxyShowOutDTO uxyShowOutDTO = new UxyShowOutDTO();
        BeanUtils.copyProperties(uxy, uxyShowOutDTO);
        return uxyShowOutDTO;
    }

    @PostMapping("/applyFriend")
    public UxyShowOutDTO applyFriend(@RequestBody UyIdInDTO uyIdInDTO,
                                     @RequestAttribute String currentUserId) throws ClientException, IllegalAccessException {
        String uyId = uyIdInDTO.getUyId();
        if (uyId == null || uyId.isEmpty()) {
            throw new ClientException("invalid params");
        }
        if (uyId.equals(currentUserId)) {
            throw new ClientException("same");
        }

        String uxyId = String.format("%sV%s", currentUserId, uyId);
        Uxy uxy = uxyService.load(uxyId);
        if (uxy == null) {
            uxy = new Uxy();
            uxy.setUxyId(uxyId);
            uxy.setUxId(currentUserId);
            uxy.setUyId(uyId);
        }
        if (uxy.getApplyFriend() != null && uxy.getApplyFriend()) {
            throw new ClientException("already apply");
        }
        uxy.setApplyFriend(true);
        long now = System.currentTimeMillis();
        uxy.setApplyFriendTime(now);

        Uxy save = uxyService.save(uxy);
        UxyShowOutDTO uxyShowOutDTO = new UxyShowOutDTO();
        BeanUtils.copyProperties(save, uxyShowOutDTO);
        return uxyShowOutDTO;
    }

    @PostMapping("/batchGetNewFriend")
    public List<UxyShowOutDTO> batchGetNewFriend(@RequestAttribute String currentUserId) {
        List<Uxy> xs = uxyService.batchGetByUx(currentUserId);
        List<Uxy> ys = uxyService.batchGetByUy(currentUserId);
        List<Uxy> xys = Stream.of(xs, ys).flatMap(Collection::stream).collect(Collectors.toList());
        List<Uxy> newFriends = xys.stream()
                .filter(uxy -> uxy.getApplyFriend() != null && uxy.getApplyFriend())
                .sorted(Comparator.comparingLong(Uxy::getApplyFriendTime))
                .collect(Collectors.toList());
        Collections.reverse(newFriends);
        List<UxyShowOutDTO> uxyShowOutDTOS = newFriends.stream().map(uxy -> {
            UxyShowOutDTO uxyShowOutDTO = new UxyShowOutDTO();
            BeanUtils.copyProperties(uxy, uxyShowOutDTO);
            return uxyShowOutDTO;
        }).collect(Collectors.toList());
        return uxyShowOutDTOS;
    }

}
