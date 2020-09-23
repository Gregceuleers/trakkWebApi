package com.trakknamur.demo.exceptions.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ErrorDTO {

    String message;

    Instant timestamp = Instant.now();

    public ErrorDTO(String message) {
        this.message = message;
    }

}
