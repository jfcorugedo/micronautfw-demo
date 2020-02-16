package micronautfw.demo.quotes;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import micronautfw.demo.quotes.dto.Quote;


@Controller("/quotes")
@RequiredArgsConstructor
public class QuoteResource {

    private final QuoteDAO quoteDAO;

    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    public Single<MutableHttpResponse<Quote>> getOne(@PathVariable("id") String id) {

        return quoteDAO
            .findOne(id)
            .map(HttpResponse::ok)
            .toSingle(HttpResponse.notFound());
    }
}
