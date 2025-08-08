package com.gtalent.demo.responses;

import com.gtalent.demo.models.User;

public class GetUserResponse {
    private String username;

    public GetUserResponse(String username) {
        this.username = username;
    }
    public GetUserResponse(User user) {
        this.username = user.getUsername();
    }

    public GetUserResponse(String username, int id) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
