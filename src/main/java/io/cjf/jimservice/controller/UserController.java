package io.cjf.jimservice.controller;

import io.cjf.jimservice.dto.in.UsernameInDTO;
import io.cjf.jimservice.dto.out.UserLoginOutDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/registerByUsername")
    public UserLoginOutDTO registerByUsername(@RequestBody UsernameInDTO usernameInDTO) {
        return null;
    }

    @PostMapping("/loginByUsername")
    public UserLoginOutDTO loginByUsername(@RequestBody UsernameInDTO usernameInDTO) {
        return null;
    }

}
