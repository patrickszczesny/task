package com.softBlue.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositoryResponse {
    private String full_name;
    private String description;
    private Date created_at;
    private String clone_url;
    private int stargazers_count;
}
