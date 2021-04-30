package io.cjf.jimservice.dto.in;

public class UxySaveInDTO {
    private String uyId;

    private Boolean applyFriend;
    private Boolean beFriend;
    private String remarkName;
    private String showName;

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

    public Boolean getBeFriend() {
        return beFriend;
    }

    public void setBeFriend(Boolean beFriend) {
        this.beFriend = beFriend;
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
