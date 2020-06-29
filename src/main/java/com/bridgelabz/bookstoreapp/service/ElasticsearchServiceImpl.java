package com.bridgelabz.bookstoreapp.service;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ElasticsearchServiceImpl implements IElasticsearchService {

    String INDEX = "bookstore";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    // Method to load Book Data on Elasticsearch Server
    @Override
    public String createBook(Book book) throws IOException {

            String data = objectMapper.writeValueAsString(book);

            System.out.println(data);

            IndexRequest index = new IndexRequest(INDEX);

            index.id(Integer.toString(book.getId()));

            index.source(data, XContentType.JSON);

            IndexResponse indexResponse = client.index(index, RequestOptions.DEFAULT);

            return indexResponse.getResult().name();
    }

    // Method to Search Book by Name and Author
    public List<Book> searchBook(String serachText) throws IOException {

        SearchRequest searchRequest = new SearchRequest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
                            QueryBuilders.queryStringQuery("*"+serachText+"*")
                            .analyzeWildcard(true)
                            .field("nameOfBook")
                            .field("author"));

        searchSourceBuilder.query(queryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(response);
        }

        private List<Book> getSearchResult(SearchResponse response) {

            SearchHit[] searchHit = response.getHits().getHits();

            List<Book> books = new ArrayList<>();

            if (searchHit.length > 0) {

                Arrays.stream(searchHit)
                        .forEach(hit -> books
                                .add(objectMapper
                                        .convertValue(hit.getSourceAsMap(),
                                                Book.class))
                        );
            }
            return books;
        }
}


