package micronautfw.demo.quotes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.http.ContentType;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class QuoteResourceRestAssuredTest {

    @Inject
    private EmbeddedServer embeddedServer;

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

