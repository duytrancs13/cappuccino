package io.awesome.app.Model;

/**
 * Created by sung on 06/06/2018.
 */

public class Account {
    private String avatarUrl;
    private long createAt;
    private String displayName;
    private String email;
    private String role;
    private String userId;

    public Account(String avatarUrl, long createAt, String displayName, String email, String role, String userId) {
        this.avatarUrl = avatarUrl;
        this.createAt = createAt;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
