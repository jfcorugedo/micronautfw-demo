package micronautfw.demo.starwars;

import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import javax.inject.Inject;

@MicronautTest
public class PeopleResourceTest {

    @Inject
    private EmbeddedServer server;

    @Test
    public void getLukeSkywalker() {

        given()
            .accept(ContentType.JSON)
        .when()
            .get(
                String.format(
                    "%s://%s:%d/starwars/people/1",
                    server.getScheme(),
                    server.getHost(),
                    server.getPort()
                )
            )
        .then()
            .statusCode(HttpStatus.OK.getCode())
            .contentType(ContentType.JSON)
            .body("name", equalTo("Luke Skywalker"))
            .and()
            .body(
                "films",
                hasItems(
                    "A New Hope",
                    "The Empire Strikes Back" ,
                    "Return of the Jedi",
                    "Revenge of the Sith",
                    "The Force Awakens"
                )
            );
    }
}
