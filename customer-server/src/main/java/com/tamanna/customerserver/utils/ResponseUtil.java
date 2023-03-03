package com.tamanna.customerserver.utils;

import com.tamanna.customerserver.dto.Response;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil{

    public static ResponseEntity<?> buildResponse(Response response){
        return ResponseEntity.ok().body(response);
    }

    public static ResponseEntity<Object> buildErrorResponse(JSONObject response, HttpStatus status){
        return ResponseEntity.status(status).body(response);
    }
}
