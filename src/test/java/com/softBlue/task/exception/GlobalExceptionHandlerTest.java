package com.softBlue.task.exception;

import com.softBlue.task.constants.TranslationConstants;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleGitHubFileNotFoundExceptio() {
        String errorMessage = TranslationConstants.REPOSITORY_NOT_FOUND.toString();
        FileNotFoundException ex = new FileNotFoundException(errorMessage);

        ResponseEntity<GitHubErrorResponse> responseEntity = exceptionHandler.handleGitHubFileNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody().getMessage());
    }
}
