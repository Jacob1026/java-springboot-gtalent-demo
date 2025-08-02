package com.gtalent.demo.responses;

public class CreateUserResponse {
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CreateUserResponse(String username) {
        this.username = username;
    }

}