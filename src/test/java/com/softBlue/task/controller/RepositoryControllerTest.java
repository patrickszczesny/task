package com.softBlue.task.controller;

import com.softBlue.task.constants.TranslationConstants;
import com.softBlue.task.dataFactory.RepositoryTestDataProvider;
import com.softBlue.task.dto.RepositoryDetailsResponse;
import com.softBlue.task.service.RepositoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileNotFoundException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(RepositoryController.class)
class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryService repositoryService;


    @Test
    void getRepositoryDetailsUsingLib_ReturnsValidResponse() throws Exception {
        String owner = "example";
        String repositoryName = "example-repo";

        RepositoryDetailsResponse expectedResponse = RepositoryTestDataProvider.getSampleRepositoryForControllerTest();

        when(repositoryService.getRepositoryDetails(owner, repositoryName))
                .thenReturn(ResponseEntity.ok(expectedResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/" + owner + "/" + repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.fullName").value(expectedResponse.getFullName()))
                .andExpect(jsonPath("$.description").value(expectedResponse.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(expectedResponse.getCloneUrl()))
                .andExpect(jsonPath("$.stargazers").value(expectedResponse.getStargazers()))
                .andExpect(jsonPath("$.createdAt").value(expectedResponse.getCreatedAt()));
    }

    @Test
    void getRepositoryDetailsUsingLib_ReturnsErrorResponse() throws Exception {
        String owner = "example";
        String repositoryName = "nonexistent-repo";
        when(repositoryService.getRepositoryDetails(owner, repositoryName))
                .thenThrow(new FileNotFoundException(TranslationConstants.REPOSITORY_NOT_FOUND.toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/" + owner + "/" + repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getRepositoryDetailsRaw_ReturnsValidResponse() throws Exception {
        String owner = "example";
        String repositoryName = "example-repo";

        RepositoryDetailsResponse expectedResponse = RepositoryTestDataProvider.getSampleRepositoryForControllerTest();

        when(repositoryService.getRepositoryDetailsRaw(owner, repositoryName))
                .thenReturn(ResponseEntity.ok(expectedResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/raw/" + owner + "/" + repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.fullName").value(expectedResponse.getFullName()))
                .andExpect(jsonPath("$.description").value(expectedResponse.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(expectedResponse.getCloneUrl()))
                .andExpect(jsonPath("$.stargazers").value(expectedResponse.getStargazers()))
                .andExpect(jsonPath("$.createdAt").value(expectedResponse.getCreatedAt()));
    }

    @Test
    void getRepositoryDetailsRaw_ReturnsErrorResponse() throws Exception {
        String owner = "example";
        String repositoryName = "nonexistent-repo";
        when(repositoryService.getRepositoryDetailsRaw(owner, repositoryName))
                .thenThrow(new FileNotFoundException(TranslationConstants.REPOSITORY_NOT_FOUND.toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/raw/" + owner + "/" + repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
