package com.huce.quiz_app.Exception;

import com.huce.quiz_app.Request.ApiResponse;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Builder
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Xử lý tất cả ngoại lệ chung
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleException(Exception exception) {
        log.error("Unhandled exception occurred: {}", exception.getMessage(), exception);

        ApiResponse apiResponse = createApiResponse(ErrorCode.UNCATEGORIZED_EXCEPTION);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Xử lý ngoại lệ ứng dụng đặc biệt (AppException)
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException exception) {
        log.error("Application exception occurred: {}", exception.getMessage(), exception);

        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = createApiResponse(errorCode);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Xử lý lỗi xác thực (MethodArgumentNotValidException)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String enumKey = null;
        if (exception.getFieldError() != null) {
            enumKey = exception.getFieldError().getDefaultMessage();
        }

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            if (enumKey != null) {
                errorCode = ErrorCode.valueOf(enumKey);
            }
        } catch (IllegalArgumentException e) {
            // Ghi log khi enum không hợp lệ
            log.error("Invalid ErrorCode enum value: {}", enumKey, e);
        }

        ApiResponse apiResponse = createApiResponse(errorCode);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Phương thức tiện ích để tạo ApiResponse từ ErrorCode
    private ApiResponse createApiResponse(ErrorCode errorCode) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return apiResponse;
    }
}
