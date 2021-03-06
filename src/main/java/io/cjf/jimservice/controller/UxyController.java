package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UserIdIndTO;
import io.cjf.jimservice.dto.in.UxIdIndTO;
import io.cjf.jimservice.dto.in.UyIdInDTO;
import io.cjf.jimservice.dto.out.UxyShowOutDTO;
import io.cjf.jimservice.enumeration.ConversationType;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.Conversation;
import io.cjf.jimservice.po.UserConversation;
import io.cjf.jimservice.po.Uxy;
import io.cjf.jimservice.service.ConversationService;
import io.cjf.jimservice.service.UserConversationService;
import io.cjf.jimservice.service.UxyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/uxy")
public class UxyController {

    @Autowired
    private UxyService uxyService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserConversationService userConversationService;

    @PostMapping("/loadAsUxByUy")
    public UxyShowOutDTO loadAsUxByUy(@RequestBody UyIdInDTO uyIdInDTO,
                                      @RequestAttribute String currentUserId) throws ClientException {
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
        if (uxId.equals(currentUserId)) {
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
        List<Uxy> asys = uxyService.batchGetByUy(currentUserId);
        List<Uxy> newFriends = asys.stream()
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

    @PostMapping("/agreeFriend")
    public void agreeFriend(@RequestBody UserIdIndTO userIdIndTO,
                            @RequestAttribute String currentUserId) throws ClientException {
        String userId = userIdIndTO.getUserId();
        if (userId == null || userId.isEmpty()) {
            throw new ClientException("invalid params");
        }

        long now = System.currentTimeMillis();

        Set<Uxy> pair = new HashSet<>();

        String uvcId = String.format("%sV%s", userId, currentUserId);
        Uxy uvc = uxyService.load(uvcId);
        if (uvc == null || uvc.getApplyFriend() == null || !uvc.getApplyFriend()) {
            throw new ClientException("non uvc apply friend");
        }
        if (uvc.getBeFriend() != null && uvc.getBeFriend()) {
            throw new ClientException("already friend");
        }
        uvc.setBeFriend(true);
        uvc.setBeFriendTime(now);
        boolean add = pair.add(uvc);

        String cvuId = String.format("%sV%s", currentUserId, userId);
        Uxy cvu = uxyService.load(cvuId);
        if (cvu == null) {
            cvu = new Uxy();
            cvu.setUxyId(cvuId);
            cvu.setUxId(currentUserId);
            cvu.setUyId(userId);
        }
        if (cvu.getBeFriend() != null && cvu.getBeFriend()) {
            throw new ClientException("already friend");
        }
        cvu.setBeFriend(true);
        cvu.setBeFriendTime(now);
        boolean add1 = pair.add(cvu);

        uxyService.batchSave(pair);

        Conversation conversation = new Conversation();
        String conversationId = UUID.randomUUID().toString().replace("-", "");
        conversation.setConversationId(conversationId);
        conversation.setType(ConversationType.Single.ordinal());
        conversation.setCreateTime(now);
        conversationService.save(conversation);

        Set<UserConversation> conPair = new HashSet<>();

        UserConversation xCon = new UserConversation();
        xCon.setUserConversationId(String.format("%sV%s", currentUserId, conversationId));
        xCon.setUserId(currentUserId);
        xCon.setConversationId(conversationId);
        xCon.setJoinTime(now);
        conPair.add(xCon);

        UserConversation yCon = new UserConversation();
        yCon.setUserConversationId(String.format("%sV%s", userId, conversationId));
        yCon.setUserId(userId);
        yCon.setConversationId(conversationId);
        yCon.setJoinTime(now);
        conPair.add(yCon);

        userConversationService.batchSave(conPair);

    }

    @PostMapping("/batchGetFriend")
    public Set<UxyShowOutDTO> batchGetFriend(@RequestAttribute String currentUserId) {
        List<Uxy> asxs = uxyService.batchGetByUx(currentUserId);
        Set<Uxy> friends = asxs.stream()
                .filter(uxy -> uxy.getBeFriend() != null && uxy.getBeFriend())
                .collect(Collectors.toSet());

        Set<UxyShowOutDTO> uxyShowOutDTOS = friends.stream().map(uxy -> {
            UxyShowOutDTO uxyShowOutDTO = new UxyShowOutDTO();
            BeanUtils.copyProperties(uxy, uxyShowOutDTO);
            return uxyShowOutDTO;
        }).collect(Collectors.toSet());
        return uxyShowOutDTOS;
    }

}
