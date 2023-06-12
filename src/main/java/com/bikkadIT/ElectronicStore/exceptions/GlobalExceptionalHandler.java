package com.bikkadIT.ElectronicStore.exceptions;

import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionalHandler.class);


    //handle resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

        logger.info("Exception handler invoked !!");

        ApiResponse ar = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

        // String message = ex.getMessage();
        // ApiResponse ar = new ApiResponse(message, true);

        return new ResponseEntity<ApiResponse>(ar, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors(); //here we get all errors from the fields & traverse that one by one

        Map<String, Object> response = new HashMap<>();  //store that all errors in map in key value
        allErrors.forEach((error) -> {

            String field = ((FieldError) error).getField(); // store key from error and add in response
            String message = error.getDefaultMessage();    // store value from error and add in response

            response.put(field, message);
        });
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiException.class)
    public ResponseEntity<ApiResponse> handleBadApiRequest(BadApiException ex) {

        logger.info("BadApi Request !!!");

        ApiResponse apiResponse = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();

//        String message = ex.getMessage();
//        ApiResponse apiResponse = new ApiResponse(message, false, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
