package io.cjf.jimservice.constant;

import com.auth0.jwt.algorithms.Algorithm;

public class GlobalConstant {

    public static final Algorithm algorithmHS = Algorithm.HMAC256("123456");

}
