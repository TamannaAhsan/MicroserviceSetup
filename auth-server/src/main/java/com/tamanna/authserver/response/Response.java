package com.tamanna.authserver.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    @Builder.Default
    private String status = HttpStatus.OK.getReasonPhrase();

    @Builder.Default
    private Integer statusCode = HttpStatus.OK.value();

    private Integer offset;

    private Integer limit;

    private Long total;

    private String order;

    private String orderBy;

    private String message;

    private T data;

    private JSONObject summary;

}
