package com.huce.quiz_app.Request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    private String password;
    private String fullname;

    public @Size(min = 3, message = "USERNAME_INVALID") String getUsername() {
        return username;
    }

    public void setUsername(@Size(min = 3, message = "USERNAME_INVALID") String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public @Size(min = 8, message = "INVALID_PASSWORD") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8, message = "INVALID_PASSWORD") String password) {
        this.password = password;
    }
}