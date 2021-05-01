package io.cjf.jimservice.dto.out;

public class UxyNewFriendOutDTO {

    private String uxId;
    private UserShowOutDTO ux;

    private Long applyFriendTime;
    private Boolean beFriend;
    private Long beFriendTime;
    private String remarkName;
    private String showName;

    public String getUxId() {
        return uxId;
    }

    public void setUxId(String uxId) {
        this.uxId = uxId;
    }

    public UserShowOutDTO getUx() {
        return ux;
    }

    public void setUx(UserShowOutDTO ux) {
        this.ux = ux;
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
