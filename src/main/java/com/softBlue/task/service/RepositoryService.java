package com.softBlue.task.service;

import com.softBlue.task.componets.RestClient;
import com.softBlue.task.constants.GitHubUrl;
import com.softBlue.task.dto.RepositoryDetailsResponse;
import com.softBlue.task.utils.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class RepositoryService {

    private static final Logger logger = LogManager.getLogger(RepositoryService.class);
    private final RestClient restClient;

    @Autowired
    public RepositoryService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<RepositoryDetailsResponse> getRepositoryDetailsRaw(String owner, String repositoryName) throws FileNotFoundException {
        RepositoryDetailsResponse details = getRepositoryDetailsFromApi(owner, repositoryName);
        return ResponseEntity.ok(details);
    }

    private RepositoryDetailsResponse getRepositoryDetailsFromApi(String owner, String repositoryName) throws FileNotFoundException {
        String url = GitHubUrl.BASE_URL_REPOSITORY_DATA;
        return restClient.getChosenRepositoryDetails(url, owner, repositoryName);
    }

    public ResponseEntity<RepositoryDetailsResponse> getRepositoryDetails(String owner, String repositoryName) throws IOException {
        GitHub github = GitHub.connectAnonymously();
        GHRepository repository = github.getRepository(owner + "/" + repositoryName);
        RepositoryDetailsResponse details = new RepositoryDetailsResponse(repository, TimeUtils.formatDate(repository.getCreatedAt()));
        return ResponseEntity.ok(details);
    }
}
