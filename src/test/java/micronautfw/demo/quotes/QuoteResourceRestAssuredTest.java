package micronautfw.demo.quotes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import com.mongodb.reactivestreams.client.MongoClient;
import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.http.ContentType;
import javax.inject.Inject;

import micronautfw.demo.quotes.dto.Quote;
import micronautfw.demo.utils.PrintSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@MicronautTest
public class QuoteResourceRestAssuredTest {

    @Inject
    private MongoClient mongoClient;

    @Inject
    private EmbeddedServer embeddedServer;

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

        given()
            .accept(ContentType.JSON)
            .when()
            .get(
                String.format(
                    "%s://%s:%d/quotes/2",
                    embeddedServer.getScheme(),
                    embeddedServer.getHost(),
                    embeddedServer.getPort()
                )
            )
            .then()
            .statusCode(HttpStatus.OK.getCode())
            .contentType(ContentType.JSON)
            .body("id", equalTo("2"))
            .and()
            .body("text", equalTo("At the beginning I was listening but... - Davind 'flowers'"));
    }

    @Test
    public void tryToGetQuoteButNotFound() {

        given()
            .accept(ContentType.JSON)
            .when()
            .get(
                String.format(
                    "%s://%s:%d/quotes/456",
                    embeddedServer.getScheme(),
                    embeddedServer.getHost(),
                    embeddedServer.getPort()
                )
            )
            .then()
            .statusCode(HttpStatus.NOT_FOUND.getCode());
    }
}

