package io.cjf.jimservice.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import io.cjf.jimservice.constant.GlobalConstant;
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

import java.util.Date;

@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    private BCrypt.Hasher hasher = BCrypt.withDefaults();

    private BCrypt.Verifyer verifyer = BCrypt.verifyer();

    private JWTCreator.Builder builder = JWT.create();

    @Autowired
    private UserService userService;

    @PostMapping("/registerByUsername")
    public UserLoginOutDTO registerByUsername(@RequestBody UsernameInDTO usernameInDTO) throws ClientException {
        String username = usernameInDTO.getUsername();
        String loginPassword = usernameInDTO.getLoginPassword();
        if (username == null || username.isEmpty() || username.length() < 6 || !Character.isLetter(username.toCharArray()[0]) ||
                loginPassword == null || loginPassword.isEmpty() || loginPassword.length() < 6) {
            throw new ClientException("invalid params");
        }

        User user = userService.getByUsername(username);
        if (user != null) {
            throw new ClientException("username already used");
        }

        user = new User();
        user.setUsername(username);
        String encPassword = hasher.hashToString(4, loginPassword.toCharArray());
        user.setLoginPassword(encPassword);
        User save = userService.create(user);

        UserLoginOutDTO userLoginOutDTO = issue(save);

        return userLoginOutDTO;
    }

    @PostMapping("/loginByUsername")
    public UserLoginOutDTO loginByUsername(@RequestBody UsernameInDTO usernameInDTO) throws ClientException {
        String username = usernameInDTO.getUsername();
        String loginPassword = usernameInDTO.getLoginPassword();
        if (username == null || username.isEmpty() || username.length() < 6 || !Character.isLetter(username.toCharArray()[0]) ||
                loginPassword == null || loginPassword.isEmpty() || loginPassword.length() < 6) {
            throw new ClientException("invalid params");
        }
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new ClientException("invalid username or password");
        }
        String dbLoginPassword = user.getLoginPassword();
        BCrypt.Result result = verifyer.verify(loginPassword.toCharArray(), dbLoginPassword);
        if (!result.verified) {
            throw new ClientException("invalid username or password");
        }

        UserLoginOutDTO userLoginOutDTO = issue(user);

        return userLoginOutDTO;
    }

    private UserLoginOutDTO issue(User user) {
        long now = System.currentTimeMillis();
        long accessExpireTime = now + 86400000L;
        String accessToken = builder
                .withClaim("type", "Access")
                .withClaim("userId", user.getUserId())
                .withExpiresAt(new Date(accessExpireTime))
                .sign(GlobalConstant.algorithmHS);

        UserLoginOutDTO userLoginOutDTO = new UserLoginOutDTO();
        userLoginOutDTO.setUserId(user.getUserId());
        userLoginOutDTO.setTokenIssueTime(now);
        userLoginOutDTO.setAccessToken(accessToken);
        userLoginOutDTO.setAccessExpireTime(accessExpireTime);
        return userLoginOutDTO;
    }

}
