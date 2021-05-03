package io.cjf.jimservice.po;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class UserConversation {
    @MongoId
    private String userConversationId;
    @HashIndexed
    private String userId;
    @HashIndexed
    private String conversationId;
    private Long joinTime;
    private Boolean muteEnabled;

    public String getUserConversationId() {
        return userConversationId;
    }

    public void setUserConversationId(String userConversationId) {
        this.userConversationId = userConversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Long joinTime) {
        this.joinTime = joinTime;
    }

    public Boolean getMuteEnabled() {
        return muteEnabled;
    }

    public void setMuteEnabled(Boolean muteEnabled) {
        this.muteEnabled = muteEnabled;
    }
}
