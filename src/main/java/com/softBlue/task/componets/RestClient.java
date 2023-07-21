package com.softBlue.task.componets;

import com.softBlue.task.constants.TranslationConstants;
import com.softBlue.task.dto.GitHubRepositoryResponse;
import com.softBlue.task.dto.RepositoryDetailsResponse;
import com.softBlue.task.utils.TimeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileNotFoundException;

@Component
public class RestClient {

    public RepositoryDetailsResponse getChosenRepositoryDetails(String baseUrl, String owner, String repositoryName) throws FileNotFoundException {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/{owner}/{repositoryName}")
                .buildAndExpand(owner, repositoryName)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GitHubRepositoryResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                GitHubRepositoryResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody() && responseEntity.getBody() != null) {
            GitHubRepositoryResponse gitHubRepositoryResponse = responseEntity.getBody();
            return new RepositoryDetailsResponse(gitHubRepositoryResponse, TimeUtils.formatDate(gitHubRepositoryResponse.getCreated_at()));
        } else {
            throw new FileNotFoundException(TranslationConstants.GIT_HUB_REPO_ERROR.toString());
        }
    }
}