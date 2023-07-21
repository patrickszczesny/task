package com.softBlue.task.exception;

public class GitHubApiConnectionException extends RuntimeException {
    public GitHubApiConnectionException(String message) {
        super(message);
    }
}