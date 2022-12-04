package xyz.dmfe.ignite.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private final String id;
    private final String message;
    private final String description;
}
