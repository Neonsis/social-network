package org.neonsis.socialnetwork.rest.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorRequest {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ApiErrorRequest() {
        timestamp = LocalDateTime.now();
    }

    public ApiErrorRequest(HttpStatus status) {
        this();
        this.status = status;
    }


    public ApiErrorRequest(HttpStatus status, String message) {
        this(status);
        this.message = message;
    }
}
