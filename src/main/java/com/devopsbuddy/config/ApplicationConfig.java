package com.devopsbuddy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@EnableJpaRepositories(basePackages = "com.devopsbuddy.backend.persistence.repositories")
@EntityScan(basePackages = "com.devopsbuddy.backend.persistence.domain.backend")
@EnableTransactionManagement
//@PropertySource("file:d:/projects/udemy/full_stack/fullstack_intellij/application-common.properties")
@PropertySource("file:d:/workspaces/sample_projects/intellij/application-common.properties")
public class ApplicationConfig {
	
	@Value("${aws.s3.profile}")
    private String awsProfileName;

    @Bean
    public AmazonS3Client s3Client() {
        AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(awsProfileName);
        
        
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
        		.withCredentials(credentialsProvider)
        		.withRegion(Regions.US_EAST_1);
        
        return (AmazonS3Client) builder.build();
    }

}
