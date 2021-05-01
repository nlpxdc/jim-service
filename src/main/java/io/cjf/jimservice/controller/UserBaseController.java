package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UserIdIndTO;
import io.cjf.jimservice.dto.in.UserIdsInDTO;
import io.cjf.jimservice.dto.in.UserProfileInDTO;
import io.cjf.jimservice.dto.in.UsernameInDTO;
import io.cjf.jimservice.dto.out.UserProfileOutDTO;
import io.cjf.jimservice.dto.out.UserShowOutDTO;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.User;
import io.cjf.jimservice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userBase")
public class UserBaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/loadProfile")
    public UserProfileOutDTO loadProfile(@RequestAttribute String currentUserId) throws ClientException {
        User user = userService.load(currentUserId);
        if (user == null) {
            throw new ClientException("no user");
        }
        UserProfileOutDTO userProfileOutDTO = new UserProfileOutDTO();
        BeanUtils.copyProperties(user, userProfileOutDTO);
        return userProfileOutDTO;
    }

    @PostMapping("/updateProfile")
    public UserProfileOutDTO updateProfile(@RequestBody UserProfileInDTO userProfileInDTO,
                                           @RequestAttribute String currentUserId) throws ClientException, IllegalAccessException {
        User user = new User();
        user.setUserId(currentUserId);
        BeanUtils.copyProperties(userProfileInDTO, user);
        User save = userService.update(user);
        UserProfileOutDTO out = new UserProfileOutDTO();
        BeanUtils.copyProperties(save, out);
        return out;
    }

    @PostMapping("/load")
    public UserShowOutDTO load(@RequestBody UserIdIndTO userIdIndTO) throws ClientException {
        String userId = userIdIndTO.getUserId();
        if (userId == null) {
            throw new ClientException("invalid params");
        }
        User user = userService.load(userId);
        if (user == null) {
            throw new ClientException("no user id");
        }
        UserShowOutDTO userShowOutDTO = new UserShowOutDTO();
        BeanUtils.copyProperties(user, userShowOutDTO);
        return userShowOutDTO;
    }

    @PostMapping("/batchLoad")
    public List<UserShowOutDTO> batchLoad(@RequestBody UserIdsInDTO userIdsInDTO) throws ClientException {
        List<String> userIds = userIdsInDTO.getUserIds();
        if (userIds.isEmpty() || userIds.size() > 1000) {
            throw new ClientException("invalid params");
        }
        for (String userId : userIds) {
            if (userId == null) {
                throw new ClientException("invalid params");
            }
        }
        List<String> dUserIds = userIds.stream().distinct().collect(Collectors.toList());
        if (dUserIds.size() < userIds.size()) {
            throw new ClientException("contains same userIds");
        }
        List<User> users = userService.batchLoad(userIds);
        List<UserShowOutDTO> outs = users.stream().map(user -> {
            UserShowOutDTO out = null;
            if (user != null) {
                out = new UserShowOutDTO();
                BeanUtils.copyProperties(user, out);
            }
            return out;
        }).collect(Collectors.toList());
        return outs;
    }

    @PostMapping("/getByUsername")
    public UserShowOutDTO getByUsername(@RequestBody UsernameInDTO usernameInDTO) {
        String username = usernameInDTO.getUsername();
        if (username == null || username.isEmpty() || username.length() < 6) {
            return null;
        }
        User user = userService.getByUsername(username);
        UserShowOutDTO userShowOutDTO = null;
        if (user != null) {
            userShowOutDTO = new UserShowOutDTO();
            BeanUtils.copyProperties(user, userShowOutDTO);
        }
        return userShowOutDTO;
    }

}
