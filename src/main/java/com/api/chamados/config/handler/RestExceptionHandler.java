package com.api.chamados.config.handler;

import com.api.chamados.config.exceptions.BadRequestException;
import com.api.chamados.config.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RestErrorMessage> recursoNaoEncontradoException(NotFoundException notFoundException, HttpServletRequest request, Locale locale){
        logger.error(notFoundException.getMessage(), notFoundException);
        final RestErrorMessage message= gerarRestErrorMessageDinamico(HttpStatus.NOT_FOUND, notFoundException, request, locale);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<RestErrorMessage> violocaoDeRegraException(BadRequestException exception, HttpServletRequest request, Locale locale){
        logger.error(exception.getMessage(), exception);
        final RestErrorMessage message= gerarRestErrorMessageDinamico(HttpStatus.BAD_REQUEST, exception, request, locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<RestErrorMessage> handleRequerUmCorpo(HttpMessageNotReadableException ex, HttpServletRequest request, Locale locale){
        logger.error(ex.getMessage(), ex);
        final RestErrorMessage message= RestErrorMessage.builder()
                .error(ex.getClass().getSimpleName())
                .message("A requisição necessita de um corpo")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(OffsetDateTime.now())
                .locale(locale)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ValidateExceptionResponse> handleArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request, Locale locale) {

        logger.error(ex.getMessage(), ex);

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        var fieldList = processarErros(fieldErrors);

        var httpStatus = HttpStatus.BAD_REQUEST;

        var message = ValidateExceptionResponse.builder()
                .status(httpStatus.value())
                .error(ex.getClass().getSimpleName())
                .message("Um ou mais campos inválidos")
                .path(request.getRequestURI())
                .locale(locale)
                .timestamp(OffsetDateTime.now())
                .fields(fieldList)
                .build();

        return ResponseEntity.status(httpStatus).body(message);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    protected ResponseEntity<RestErrorMessage> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request, Locale locale) {
        logger.error(ex.getMessage(), ex);
        var message = this.gerarRestErrorMessageDinamico(HttpStatus.BAD_REQUEST, ex, request, locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<RestErrorMessage> handlePSQLException(DisabledException ex, HttpServletRequest request, Locale locale) {

        logger.error(ex.getMessage(), ex);

        var messagemPersonalizada = ex.getMessage() + ". Para fazer login, acesse o campo ATIVAR CONTA, acesse seu e-mail e clique no link que te enviamos.";

        var message = this.gerarRestErrorMessageDinamico(HttpStatus.FORBIDDEN, new DisabledException(messagemPersonalizada), request, locale);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestErrorMessage> handleAuthException(BadCredentialsException ex, HttpServletRequest request, Locale locale) {

        logger.error(ex.getMessage(), ex);

        var message = this.gerarRestErrorMessageDinamico(HttpStatus.BAD_REQUEST, ex, request, locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }


    private List<ValidateExceptionResponse.Field> processarErros(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> ValidateExceptionResponse.Field.builder()
                        .fieldName(fieldError.getField())
                        .fieldErrorMessage(fieldError.getDefaultMessage())
                        .build()
                ).toList();
    }

    private RestErrorMessage gerarRestErrorMessageDinamico(HttpStatus httpStatus, Throwable ex, HttpServletRequest request, Locale locale) {
        return RestErrorMessage.builder()
                .status(httpStatus.value())
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .locale(locale)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    private RestErrorMessage gerarRestErrorMessageErroInterno( Throwable ex, HttpServletRequest request, Locale locale) {
        return RestErrorMessage.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ex.getClass().getSimpleName())
                .message("Houve um erro interno. Não se preocupe, já estamos trabalhando nisso.")
                .path(request.getRequestURI())
                .locale(locale)
                .timestamp(OffsetDateTime.now())
                .build();
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<RestErrorMessage> handleGlobalException(Exception ex, HttpServletRequest request, Locale locale) {
//        logger.error(ex.getMessage(), ex);
//        var message = gerarRestErrorMessageErroInterno(ex, request, locale);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
//    }

    private String extrairDadoUnico(String message) {
        String[] parts = message.split("Detalhe: Chave .*?=\\(");
        if (parts.length > 1) {
            return parts[1].split("\\)")[0];
        }
        return null;
    }



}
