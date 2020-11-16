package br.com.house.digital.projetointegrador.controller.exception;

import br.com.house.digital.projetointegrador.service.exceptions.DataIntegrityException;
import br.com.house.digital.projetointegrador.service.exceptions.EmailExistsException;
import br.com.house.digital.projetointegrador.service.exceptions.ObjectNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ResponseEntity<StandardError> handleResponse(StandardError err) {
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return handleResponse(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return handleResponse(err);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<StandardError> handleConstraintViolation(Exception e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return handleResponse(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e) {
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation Errors");
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return handleResponse(err);
    }

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
    public ResponseEntity<StandardError> handleInvalidToken(Exception e) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), "Invalid token.");
        return handleResponse(err);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<StandardError> handleExpiredToken(ExpiredJwtException e) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), "Expired token.");
        return handleResponse(err);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> handleBadCredentials(BadCredentialsException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Wrong email or password.");
        return handleResponse(err);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<StandardError> handleExistentEmail(EmailExistsException e) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return handleResponse(err);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> handleUserNotFound(UsernameNotFoundException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return handleResponse(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception e) {
        logger.error("Internal Server Error", e);
        StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        return handleResponse(err);
    }

}
