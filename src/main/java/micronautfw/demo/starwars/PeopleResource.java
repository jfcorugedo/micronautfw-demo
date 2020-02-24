package micronautfw.demo.starwars;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import micronautfw.demo.starwars.dto.Person;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller(value = "starwars/people", produces = MediaType.APPLICATION_JSON)
public class PeopleResource {

    @Inject
    @Client("https://swapi.co/api/")
    private RxHttpClient httpClient;

    @Get("{id}")
    public Maybe<Person> getSinglePerson(@PathVariable("id") String id) {

        return httpClient
            .retrieve(HttpRequest.GET("/people/" + id), Person.class)
            .firstElement()
            .flatMap(person -> {
                List<Single<String>> films = person.getFilms().stream().map(filmUrl -> getFilmTitle(filmUrl)).collect(Collectors.toList());
                films.get(0).
            });
    }

    private Single<String> getFilmTitle(String filmUrl) {
        Maybe<Map> film = httpClient.retrieve(HttpRequest.GET(filmUrl), Map.class).firstElement();

        return film.map(filmData -> (String)filmData.get("title")).toSingle("ERROR");
    }
}
