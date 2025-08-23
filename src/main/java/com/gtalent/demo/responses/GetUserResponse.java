package com.gtalent.demo.responses;

import com.gtalent.demo.models.User;

public class GetUserResponse {
    private String username;
    private String role;

    public GetUserResponse(String username) {
        this.username = username;

    }
    public GetUserResponse(User user) {
        this.username = user.getUsername();
        this.role=user.getRole();
    }

    public GetUserResponse(String username, int id) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
