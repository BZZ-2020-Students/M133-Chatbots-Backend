package dev.zwazel.chatbots.classes.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private String id;

    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"username\":\"" + name + "\"" +
                "}";
    }
}
