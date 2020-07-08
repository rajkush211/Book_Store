package com.bridgelabz.bookstoreapp.service;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchServiceImpl implements IElasticsearchService {

    private static final String TYPE = "_doc";
    String INDEX = "bookstore";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    // Method to LOAD Book Data on Elasticsearch Server
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

    // Method to SEARCH Book by Name and Author
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

    // Method to GET Search Result.
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

    // Method to UPDATE the Book Data on ElasticSearch Server by its BookId
    @Override
    public Book updateBook(int id, Book book) throws IOException {

        Map<String, Object> bookMap = objectMapper.convertValue(book, Map.class);

        UpdateRequest updateRequest = new UpdateRequest(INDEX,String.valueOf(book.getId()));

        updateRequest.doc(bookMap);

        client.update(updateRequest,RequestOptions.DEFAULT);

        return book;
    }

    // Method to DELETE Book by its BookId
    @Override
    public String deleteBook(int id) throws IOException {

        DeleteRequest deleteRequest = new DeleteRequest(INDEX,String.valueOf(id));

        DeleteResponse response = client.delete(deleteRequest,RequestOptions.DEFAULT);

        return response.getResult().name();
    }

}


