package io.cjf.jimservice.dto.out;

import io.cjf.jimservice.dto.UserIdDTO;

public class UserLoginOutDTO extends UserIdDTO {
    private Long tokenIssueTime;
    private Long accessExpireTime;
    private String accessToken;

    public Long getTokenIssueTime() {
        return tokenIssueTime;
    }

    public void setTokenIssueTime(Long tokenIssueTime) {
        this.tokenIssueTime = tokenIssueTime;
    }

    public Long getAccessExpireTime() {
        return accessExpireTime;
    }

    public void setAccessExpireTime(Long accessExpireTime) {
        this.accessExpireTime = accessExpireTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
