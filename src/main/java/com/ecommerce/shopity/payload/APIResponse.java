package com.ecommerce.shopity.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    public Object data;
    private String errorMessage ;
    private int statusCode;

    public ResponseEntity<APIResponse> sendResponse(){
        return new ResponseEntity<>(this, HttpStatus.valueOf(this.statusCode));
    }
}
