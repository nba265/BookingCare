package com.example.doctorcare.api.exception;

import com.example.doctorcare.api.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(MethodArgumentNotValidException methodEx, WebRequest request) {
        ApiExceptionResponse response = new ApiExceptionResponse();

        methodEx.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
            String errorMsg = error.getDefaultMessage();
            response.addValidationError(ApiError.builder().msg(errorMsg).field(fieldName).build());
        });

        response.setCode(ErrorCode.BAD_REQUEST);
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ErrorCode.BAD_REQUEST.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = { UserNotFoundException.class, RoleNotFoundException.class,
            ArticleNotFoundException.class})
    @ResponseBody
    public ResponseEntity<?> handleExceptionChecked(Exception e) {
        log.error("EntityException: {}", e.getMessage());
        ApiExceptionResponse response = mappingResponseException(e, ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({DataAccessException.class})
    @ResponseBody
    public String databaseException(Exception e) {
        return "database error";
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<?>  conflict() {
        ApiExceptionResponse response = new ApiExceptionResponse();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimestamp(LocalDateTime.now());
        response.setCode(ErrorCode.CONFLICT_ERROR);
        response.setMessage("Data conflict");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<?> handleUnwantedException(Exception e, WebRequest request) {
        // Hide error details from client, only show in log
        log.error("SERVER ERROR: {}", e.getMessage());

        e.printStackTrace();

        ApiExceptionResponse response = new ApiExceptionResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimestamp(LocalDateTime.now());
        response.setCode(ErrorCode.SERVER_ERROR);
        response.setMessage("Unknown error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = { CategoryException.class, TokenException.class, TokenRefreshException.class,
            UserAlreadyExistException.class, IllegalArgumentException.class, OTPException.class})
    @ResponseBody
    public ResponseEntity<?> handleExceptionBadRequest(Exception e) {
        log.error("Bad request: {}", e.getMessage());
        ApiExceptionResponse response = mappingResponseException(e, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = { UnAuthorizationException.class })
    @ResponseBody
    public ResponseEntity<?> handleExceptionUnauthorized(Exception e) {
        log.error("Unauthorized request: {}", e.getMessage());
        ApiExceptionResponse response = mappingResponseException(e, ErrorCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiExceptionResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiExceptionResponse response = mappingResponseException(ex, ErrorCode.ACCESS_DENIED,
                HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<ApiExceptionResponse> handleTooManyRequestException(TooManyRequestException ex) {
        ApiExceptionResponse response = mappingResponseException(ex, ErrorCode.TOO_MANY_REQUEST,
                HttpStatus.TOO_MANY_REQUESTS);
        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }

    private ApiExceptionResponse mappingResponseException(Exception e, ErrorCode code, HttpStatus status) {
        ApiExceptionResponse response = new ApiExceptionResponse();
        response.setCode(code);
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status.value());
        response.setMessage(e.getMessage());
        return response;
    }

}
