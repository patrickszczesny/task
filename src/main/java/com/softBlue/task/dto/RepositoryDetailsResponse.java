package com.softBlue.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kohsuke.github.GHRepository;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class RepositoryDetailsResponse {

    private String fullName;
    private String description;
    private String cloneUrl;
    private int stargazers;
    private String createdAt;

    public RepositoryDetailsResponse(GHRepository repository, String createdAtDateInString) {
        fullName = repository.getFullName();
        description = repository.getDescription();
        cloneUrl = repository.getGitTransportUrl();
        stargazers = repository.getStargazersCount();
        createdAt = createdAtDateInString;
    }

    public RepositoryDetailsResponse(GitHubRepositoryResponse response, String createdAtDateInString) {
        fullName = response.getFull_name();
        description = response.getDescription();
        cloneUrl = response.getClone_url();
        stargazers = response.getStargazers_count();
        createdAt = createdAtDateInString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryDetailsResponse that = (RepositoryDetailsResponse) o;
        return stargazers == that.stargazers && Objects.equals(fullName, that.fullName) && Objects.equals(description, that.description) && Objects.equals(cloneUrl, that.cloneUrl) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, description, cloneUrl, stargazers, createdAt);
    }
}
