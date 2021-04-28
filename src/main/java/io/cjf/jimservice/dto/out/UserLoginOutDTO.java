package io.cjf.jimservice.dto.out;

public class UserLoginOutDTO {
    private String userId;
    private Long tokenIssueTime;
    private Long accessExpireTime;
    private String accessToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
