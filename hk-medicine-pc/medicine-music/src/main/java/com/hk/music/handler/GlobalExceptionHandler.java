package com.hk.music.handler;

import com.hk.common.core.base.Result;
import com.hk.music.exception.HkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author pengzhengfa
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HkException.class)
    public ResponseEntity<Result<Object>> handleCustomException(HkException e) {
        log.error("Business Exception:", e);
        return createErrorResponse(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Object>> handleException(Exception e) {
        log.error("Internal server exception:", e);
        return createErrorResponse(500, "Server network abnormality, please try again later");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Result<Object>> handleMaxSizeException(MaxUploadSizeExceededException e) {
        log.error("The image file is too large:", e);
        return createErrorResponse(500, "The image file is too large");
    }

    private ResponseEntity<Result<Object>> createErrorResponse(Integer code, String msg) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        result.setSuccess(false);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
