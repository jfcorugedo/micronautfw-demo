package micronautfw.demo.starwars;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;
import micronautfw.demo.starwars.dto.Person;

import javax.inject.Inject;


@Controller(value = "starwars/people", produces = MediaType.APPLICATION_JSON)
public class PeopleResource {

    @Inject
    @Client("https://swapi.co/api/")
    private RxHttpClient httpClient;

    @Get("{id}")
    public Maybe<Person> getSinglePerson(@PathVariable("id") String id) {

        return httpClient
            .retrieve(HttpRequest.GET("/people/" + id), Person.class)
            .firstElement();
    }
}
