package io.cjf.jimservice.po;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Uxy {
    @MongoId
    private String uxyId;
    @HashIndexed
    private String uxId;
    @HashIndexed
    private String uyId;

    private Boolean applyFriend;
    private Long applyFriendTime;
    private Boolean beFriend;
    private Long beFriendTime;
    private String remarkName;
    private String showName;

    public String getUxyId() {
        return uxyId;
    }

    public void setUxyId(String uxyId) {
        this.uxyId = uxyId;
    }

    public String getUxId() {
        return uxId;
    }

    public void setUxId(String uxId) {
        this.uxId = uxId;
    }

    public String getUyId() {
        return uyId;
    }

    public void setUyId(String uyId) {
        this.uyId = uyId;
    }

    public Boolean getApplyFriend() {
        return applyFriend;
    }

    public void setApplyFriend(Boolean applyFriend) {
        this.applyFriend = applyFriend;
    }

    public Long getApplyFriendTime() {
        return applyFriendTime;
    }

    public void setApplyFriendTime(Long applyFriendTime) {
        this.applyFriendTime = applyFriendTime;
    }

    public Boolean getBeFriend() {
        return beFriend;
    }

    public void setBeFriend(Boolean beFriend) {
        this.beFriend = beFriend;
    }

    public Long getBeFriendTime() {
        return beFriendTime;
    }

    public void setBeFriendTime(Long beFriendTime) {
        this.beFriendTime = beFriendTime;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}
