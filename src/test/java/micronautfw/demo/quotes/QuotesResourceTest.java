package micronautfw.demo.quotes;

import com.mongodb.reactivestreams.client.MongoClient;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import micronautfw.demo.quotes.dto.Quote;
import micronautfw.demo.utils.PrintSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class QuotesResourceTest {

    @Inject
    @Client("/")
    private RxHttpClient httpClient;

    @Inject
    private MongoClient mongoClient;

    private static final List<Quote> quotes = Arrays.asList(
        new Quote().setId("1").setText("Don't leave for tomorrow what you can do today - Anonymous"),
        new Quote().setId("2").setText("At the beginning I was listening but... - Davind 'flowers'"),
        new Quote().setId("3").setText("A man has to do what a man has to do. - Conan the barbarian"),
        new Quote().setId("4").setText("Leave for tomorrow what you can do today because you might not have to do it at all - Corugedo")
    );

    @BeforeEach
    public void cleanAndPopulate() throws InterruptedException {

        PrintSubscriber subscriber = new PrintSubscriber(System.out::println);
        mongoClient
            .getDatabase("model-quotes")
            .getCollection("quotes", Quote.class)
            .drop()
            .subscribe(subscriber);
        subscriber.await();

        subscriber = new PrintSubscriber(System.out::println);
        mongoClient
            .getDatabase("model-quotes")
            .getCollection("quotes", Quote.class)
            .insertMany(quotes)
            .subscribe(subscriber);
        subscriber.await();
    }

    @Test
    public void getOneQuote() {
        Quote quote = httpClient.toBlocking().retrieve(HttpRequest.GET("/quotes/2"), Quote.class);

        assertAll(
            () -> assertEquals("2", quote.getId()),
            () -> assertEquals("At the beginning I was listening but... - Davind 'flowers'", quote.getText())
        );
    }
}
