package micronautfw.demo.quotes;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import micronautfw.demo.dto.Quote;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class QuotesResourceTest {

    @Inject
    @Client("/")
    private RxHttpClient httpClient;

    @Test
    public void getOneQuote() {
        Quote quote = httpClient.toBlocking().retrieve(HttpRequest.GET("/quotes/2"), Quote.class);

        assertAll(
            () -> assertEquals("2", quote.getId()),
            () -> assertEquals("At the beginning I was listening but...", quote.getText())
        );
    }
}
