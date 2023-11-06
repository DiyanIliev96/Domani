package com.iliev.domani.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class CloudConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String cloudApiKey;

    @Value("${cloudinary.api-secret}")
    private String cloudApiSecret;

    @Bean
    public Cloudinary createCloudinaryConfig() {
        HashMap<String,Object> config = new HashMap<>();
        config.put("cloud_name",cloudName);
        config.put("api_key",cloudApiKey);
        config.put("api_secret",cloudApiSecret);
        return new Cloudinary(config);
    }
}
