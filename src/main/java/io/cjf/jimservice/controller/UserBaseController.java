package io.cjf.jimservice.controller;

import com.alibaba.fastjson.JSON;
import io.cjf.jimservice.dto.in.UserProfileInDTO;
import io.cjf.jimservice.dto.out.UserProfileOutDTO;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.User;
import io.cjf.jimservice.service.UserService;
import io.cjf.jimservice.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/userBase")
public class UserBaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/getProfile")
    public UserProfileOutDTO getProfile(@RequestAttribute String currentUserId) throws ClientException {
        User user = userService.load(currentUserId);
        if (user == null) {
            throw new ClientException("no user");
        }
        UserProfileOutDTO userProfileOutDTO = new UserProfileOutDTO();
        BeanUtils.copyProperties(user, userProfileOutDTO);
        return userProfileOutDTO;
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody UserProfileInDTO userProfileInDTO,
                              @RequestAttribute String currentUserId) throws ClientException, IllegalAccessException {
        User user = userService.load(currentUserId);
        if (user == null) {
            throw new ClientException("no user");
        }
        String[] nulls = BeanUtil.getNulls(userProfileInDTO);
        BeanUtils.copyProperties(userProfileInDTO, user, nulls);
        userService.save(user);
    }



}
