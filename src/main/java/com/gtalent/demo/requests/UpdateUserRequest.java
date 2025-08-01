package com.gtalent.demo.requests;

public class UpdateUserRequest {
    private String userName;

    public UpdateUserRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
