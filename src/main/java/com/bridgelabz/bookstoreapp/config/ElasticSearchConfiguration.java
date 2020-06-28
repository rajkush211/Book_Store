package com.bridgelabz.bookstoreapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// ELastic Search Configuration Class
@Configuration
public class ElasticSearchConfiguration {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestHighLevelClient client() {
        RestHighLevelClient c = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        return c;
    }

}
