package com.gtalent.demo.responses;

public class AllUserResponse {
    private String userName;

    public AllUserResponse(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
