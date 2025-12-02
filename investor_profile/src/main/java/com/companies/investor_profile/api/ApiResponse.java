package com.companies.investor_profile.api;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String status;   // "SUCCESS" or "ERROR"
    private String message;  // human-friendly message
    private T data;          // can be DTO, list, page, map, etc.

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status("SUCCESS")
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return ApiResponse.<T>builder()
                .status("ERROR")
                .message(message)
                .data(data)
                .build();
    }
}
