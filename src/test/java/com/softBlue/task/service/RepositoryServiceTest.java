package com.softBlue.task.service;

import com.softBlue.task.componets.RestClient;
import com.softBlue.task.constants.GitHubUrl;
import com.softBlue.task.constants.TranslationConstants;
import com.softBlue.task.dataFactory.RepositoryTestDataProvider;
import com.softBlue.task.dto.RepositoryDetailsResponse;
import com.softBlue.task.utils.TimeUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepositoryServiceTest {

    @Mock
    private RestClient restClient;

    @InjectMocks
    private RepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repositoryService = new RepositoryService(restClient);
    }

    @SneakyThrows
    @Test
    void getRepositoryDetailsRaw_ReturnsValidResponse() {
        String owner = "example";
        String repositoryName = "example-repo";
        String url = GitHubUrl.BASE_URL_REPOSITORY_DATA;
        RepositoryDetailsResponse expectedResponse = RepositoryTestDataProvider.getSampleRepositoryDetailsResponse();

        when(restClient.getChosenRepositoryDetails(url, owner, repositoryName))
                .thenReturn(expectedResponse);

        ResponseEntity<RepositoryDetailsResponse> responseEntity = repositoryService.getRepositoryDetailsRaw(owner, repositoryName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @SneakyThrows
    @Test
    void getRepositoryDetailsRaw_ReturnsErrorResponse() {
        String owner = "example";
        String repositoryName = "nonexistent-repo";
        String url = GitHubUrl.BASE_URL_REPOSITORY_DATA;
        when(restClient.getChosenRepositoryDetails(url, owner, repositoryName))
                .thenThrow(new FileNotFoundException(TranslationConstants.REPOSITORY_NOT_FOUND.toString()));
        assertThrows(FileNotFoundException.class,
                ()->{
                    repositoryService.getRepositoryDetailsRaw(owner, repositoryName);
                });
    }

    @Test
    void getRepositoryDetails_ReturnsValidResponse() throws Exception {
        String owner = "example";
        String repositoryName = "example-repo";

        GHRepository mockRepository = mock(GHRepository.class);
        when(mockRepository.getFullName()).thenReturn("example/example-repo");
        when(mockRepository.getDescription()).thenReturn("Sample repository");
        when(mockRepository.getGitTransportUrl()).thenReturn("https://github.com/example/example-repo.git");
        when(mockRepository.getStargazersCount()).thenReturn(10);
        // library is messing up with mock - @WithBridgeMethods with the String.class - sometimes there's error with the method
        // it's needed to take the test once more
        doReturn(Date.from(Instant.parse("2023-07-18T10:30:00Z"))).when(mockRepository).getCreatedAt();

        RepositoryService repositoryService = new RepositoryService(restClient);
        GitHub mockGitHub = mock(GitHub.class);
        try (MockedStatic<GitHub> gitHubMockedStatic = Mockito.mockStatic(GitHub.class)) {
            gitHubMockedStatic.when(GitHub::connectAnonymously).thenReturn(mockGitHub);
            when(mockGitHub.getRepository(owner + "/" + repositoryName)).thenReturn(mockRepository);
            ResponseEntity<RepositoryDetailsResponse> responseEntity = repositoryService.getRepositoryDetails(owner, repositoryName);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            RepositoryDetailsResponse expectedResponse = RepositoryTestDataProvider.getSampleRepositoryDetailsResponse();

            assertEquals(expectedResponse, responseEntity.getBody());

            verify(mockGitHub, times(1)).getRepository(owner + "/" + repositoryName);
        }
    }

    @Test
    void getRepositoryDetails_ReturnsErrorResponse() throws Exception {
        String owner = "example";
        String repositoryName = "nonexistent-repo";

        RepositoryService repositoryService = new RepositoryService(restClient);

        try (MockedStatic<GitHub> gitHubMockedStatic = Mockito.mockStatic(GitHub.class)) {
            gitHubMockedStatic.when(GitHub::connectAnonymously).thenThrow(new FileNotFoundException());
            assertThrows(FileNotFoundException.class,
                    ()->{
                        repositoryService.getRepositoryDetails(owner, repositoryName);
                    });

        }
    }
}
