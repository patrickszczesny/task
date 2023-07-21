package com.softBlue.task.exception;


import com.softBlue.task.constants.TranslationConstants;
import com.softBlue.task.utils.TimeUtils;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(GitHubApiConnectionException.class)
    @ApiResponse(responseCode = "400", description = "ConnectionError", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GitHubErrorResponse.class)))
    public ResponseEntity<GitHubErrorResponse> handleGitHubApiConnectionException(GitHubApiConnectionException ex) {
        GitHubErrorResponse errorResponse = new GitHubErrorResponse(TimeUtils.getNowTimestamp(), ex.getMessage());
        logger.error(TranslationConstants.EXCEPTION_MESSAGE + " : " + ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(FileNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "GitHubNotFound", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GitHubErrorResponse.class)))
    public ResponseEntity<GitHubErrorResponse> handleGitHubFileNotFoundException(FileNotFoundException ex) {
        GitHubErrorResponse errorResponse = new GitHubErrorResponse(TimeUtils.getNowTimestamp(), ex.getMessage());
        logger.error(TranslationConstants.EXCEPTION_MESSAGE + " : " + ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RestClientResponseException.class)
    @ApiResponse(responseCode = "404", description = "GitHubNotFound", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GitHubErrorResponse.class)))
    public ResponseEntity<GitHubErrorResponse> handleRestClientResponseException(FileNotFoundException ex) {
        GitHubErrorResponse errorResponse = new GitHubErrorResponse(TimeUtils.getNowTimestamp(), ex.getMessage());
        logger.error(TranslationConstants.EXCEPTION_MESSAGE + " : " + ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GitHubErrorResponse.class)))
    public ResponseEntity<GitHubErrorResponse> handleException(Exception ex) {
        GitHubErrorResponse errorResponse = new GitHubErrorResponse(TimeUtils.getNowTimestamp(), TranslationConstants.SOMETHING_WENT_WRONG_CONTACT_WITH_HELP_DESK.toString());
        logger.error(TranslationConstants.EXCEPTION_MESSAGE + " : " + ex.getMessage(), ex);
        ex.printStackTrace(System.err);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}