package com.softBlue.task.dataFactory;


import com.softBlue.task.dto.RepositoryDetailsResponse;

public class RepositoryTestDataProvider {

    public static RepositoryDetailsResponse getSampleRepositoryDetailsResponse() {
        RepositoryDetailsResponse response = new RepositoryDetailsResponse();
        response.setFullName("example/example-repo");
        response.setDescription("Sample repository");
        response.setCloneUrl("https://github.com/example/example-repo.git");
        response.setStargazers(10);
        response.setCreatedAt("2023-07-18 12:30:00");
        return response;
    }

    public static RepositoryDetailsResponse getSampleRepositoryForControllerTest() {
        RepositoryDetailsResponse response = new RepositoryDetailsResponse();
        response.setFullName("example/example-repo");
        response.setDescription("Sample repository");
        response.setCloneUrl("https://github.com/example/example-repo.git");
        response.setStargazers(10);
        response.setCreatedAt("2023-07-18 10:30:00");
        return response;
    }
}
