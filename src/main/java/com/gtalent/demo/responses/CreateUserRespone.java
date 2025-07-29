package com.gtalent.demo.responses;

public class CreateUserRespone {
    private String username;

    public CreateUserRespone(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
