package io.cjf.jimservice.controller;

import com.alibaba.fastjson.JSON;
import io.cjf.jimservice.dto.out.UserProfileOutDTO;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.User;
import io.cjf.jimservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        String userStr = JSON.toJSONString(user);
        UserProfileOutDTO userProfileOutDTO = JSON.parseObject(userStr, UserProfileOutDTO.class);
        return userProfileOutDTO;
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody User updateUser,
                              @RequestAttribute String currentUserId) throws ClientException {
        User user = userService.load(currentUserId);
        if (user == null) {
            throw new ClientException("no user");
        }



    }

}
