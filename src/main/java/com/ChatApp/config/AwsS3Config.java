package com.ChatApp.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {
    @Value("${s3.access-key}")
    private String access;
    @Value("${s3.secret-key}")
    private String secret;
    @Value("${s3.region}")
    private String region;
    @Bean
    public AmazonS3 AmazonS3() {
        BasicAWSCredentials credentials =
                new BasicAWSCredentials(access, secret);
        return AmazonS3Client.builder()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
