package com.gtalent.demo.responses;

import com.gtalent.demo.models.User;
public final class GetUserResponse {

    private final String username;
    private final int id;


    public GetUserResponse(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public GetUserResponse(User user) {
        this.username = user.getUsername();
        this.id = user.getId();
    }


    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}