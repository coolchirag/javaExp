package com.example.springjpa.config;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.springjpa.controller.TestController;
import com.raapid.common.exception.CommonErrorResponse;

//@ControllerAdvice
public class ErrorHandler /*extends ResponseEntityExceptionHandler*/ {
	
	private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);
	
	//@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>("Hello : "+errors, HttpStatus.BAD_REQUEST);
    }
	
	//@ExceptionHandler(Exception.class)
    /*public ResponseEntity<String> handleValidationExceptions(Exception e) {
		LOG.error("Error : "+e.getMessage(),e);
       StackTraceElement[] stackTrace = e.getStackTrace();
        return new ResponseEntity<>("Hello : "+e.getMessage(), HttpStatus.BAD_REQUEST);
    }*/
	
	//@ExceptionHandler(Exception.class)
	protected ResponseEntity<CommonErrorResponse> handleInternalException(Exception ex) {
		LOG.error(ex.getClass().getName()+": "+ ex.getMessage()+"\n cause : "+ex.getCause(), ex);
		final HttpStatus status;
		if("org.springframework.security.access.AccessDeniedException".equalsIgnoreCase(ex.getClass().getName()))
		{
			status = HttpStatus.FORBIDDEN;
		} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		final CommonErrorResponse commonErrorResponse = new CommonErrorResponse(status.getReasonPhrase(),
				status.getReasonPhrase());
		return new ResponseEntity<CommonErrorResponse>(commonErrorResponse, status);
	}
}


