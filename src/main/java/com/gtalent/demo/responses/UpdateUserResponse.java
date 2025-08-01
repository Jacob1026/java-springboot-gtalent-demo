package com.gtalent.demo.responses;

public class UpdateUserResponse {
    private String username;

    public UpdateUserResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
