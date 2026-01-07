package io.github.marrafon91.dscatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError {

    private List<FieldMessage> err = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void addError(String fieldName, String message) {
        err.removeIf(x -> x.getFieldName().equals(fieldName));
        err.add(new FieldMessage(fieldName, message));
    }
}
