package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UsernameInDTO;
import io.cjf.jimservice.dto.out.UserLoginOutDTO;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.User;
import io.cjf.jimservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerByUsername")
    public UserLoginOutDTO registerByUsername(@RequestBody UsernameInDTO usernameInDTO) throws ClientException {
        String username = usernameInDTO.getUsername();
        String password = usernameInDTO.getPassword();
        if (username == null || username.isEmpty() || username.length() < 6 || !Character.isLetter(username.toCharArray()[0]) ||
                password == null || password.isEmpty() || password.length() < 6) {
            throw new ClientException("invalid params");
        }

        User user = userService.getByUsername(username);
        if (user != null) {
            throw new ClientException("username already used");
        }

        user = new User();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setUserId(String.format("U%s", uuid));
        user.setUsername(username);
        user.setLoginPassword(password);
        User save = userService.save(user);

        UserLoginOutDTO userLoginOutDTO = new UserLoginOutDTO();
        userLoginOutDTO.setUserId(save.getUserId());

        return userLoginOutDTO;
    }

    @PostMapping("/loginByUsername")
    public UserLoginOutDTO loginByUsername(@RequestBody UsernameInDTO usernameInDTO) {
        return null;
    }

}
