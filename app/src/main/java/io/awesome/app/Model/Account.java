package io.awesome.app.Model;

/**
 * Created by sung on 06/06/2018.
 */

public class Account {
    private String displayName;
    private String email;
    private String token;

    public Account(String displayName, String email, String token) {
        this.displayName = displayName;
        this.email = email;
        this.token = token;
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
}
