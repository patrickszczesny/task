package com.softBlue.task.controller;

import com.softBlue.task.dto.RepositoryDetailsResponse;
import com.softBlue.task.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/repositories/public")
@Tag(name = "RepositoryController", description = "Endpoints for getting details of given Github repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    @Autowired
    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/{owner}/{repositoryName}")
    @Operation(summary = "Get selected details of chosen public Github Repository using library (fullName, description, cloneUrl, stargazers, createdAt)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Github Repository",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepositoryDetailsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid owner or repositoryName",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Github Repository not found",
                    content = @Content)})
    public ResponseEntity<RepositoryDetailsResponse> getRepositoryDetailsUsingLib(@PathVariable("owner") String owner,
                                                                                  @PathVariable("repositoryName") String repositoryName) throws IOException {
        return repositoryService.getRepositoryDetails(owner, repositoryName);
    }

    @GetMapping("raw/{owner}/{repositoryName}")
    @Operation(summary = "Get selected details of chosen public Github Repository using API (fullName, description, cloneUrl, stargazers, createdAt)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Github Repository",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepositoryDetailsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid owner or repositoryName",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Github Repository not found",
                    content = @Content)})
    public ResponseEntity<RepositoryDetailsResponse> getRepositoryDetailsRaw(@PathVariable("owner") String owner,
                                                                             @PathVariable("repositoryName") String repositoryName) throws FileNotFoundException {
        return repositoryService.getRepositoryDetailsRaw(owner, repositoryName);
    }
}
