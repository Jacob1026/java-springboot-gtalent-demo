package com.gtalent.demo.responses;

import com.gtalent.demo.models.User;
public record GetUserResponse(String username, int id) {
    /**
     * 這是額外增加的建構子，方便直接從 User 物件建立 GetUserResponse。
     * 在 record 中，任何自訂的建構子都必須透過 this(...) 呼叫 record 主要的建構子。
     * @param user a User object.
     */
    public GetUserResponse(User user) {
        this(user.getUsername(), user.getId());
    }
}



