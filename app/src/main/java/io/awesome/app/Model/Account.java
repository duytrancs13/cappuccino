package io.awesome.app.Model;

/**
 * Created by sung on 06/06/2018.
 */

public class Account {
    private String avatarUrl;
    private String displayName;
    private String email;
    private String token;
    private String userId;

    public Account(String avatarUrl, String displayName, String email, String token, String userId) {
        this.avatarUrl = avatarUrl;
        this.displayName = displayName;
        this.email = email;
        this.token = token;
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
