package com.example.users.REST.DTO;

import java.util.List;

public class ResponseFailed implements Response{
    public final Boolean SUCCESS = false;
    public List<String> errors;

    public ResponseFailed() {
    }

    public ResponseFailed(List<String> errors) {
        this.errors = errors;
    }

}
