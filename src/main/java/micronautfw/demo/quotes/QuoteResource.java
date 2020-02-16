package micronautfw.demo.quotes;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import micronautfw.demo.quotes.dto.Quote;

import java.util.Arrays;
import java.util.List;

@Controller("/quotes")
public class QuoteResource {

    private static final List<Quote> quotes = Arrays.asList(
        new Quote().setId("1").setText("Don't leave for tomorrow what you can do today - Anonymous"),
        new Quote().setId("2").setText("At the beginning I was listening but... - Davind 'flowers'"),
        new Quote().setId("3").setText("A man has to do what a man has to do. - Conan the barbarian"),
        new Quote().setId("4").setText("Leave for tomorrow what you can do today because you might not have to do it at all - Corugedo")
    );

    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Quote> getOne(@PathVariable("id") String id) {

        return quotes
            .stream()
            .filter(quote -> quote.getId().equals(id))
            .findAny()
            .map(HttpResponse::ok)
            .orElse(HttpResponse.notFound());
    }
}
