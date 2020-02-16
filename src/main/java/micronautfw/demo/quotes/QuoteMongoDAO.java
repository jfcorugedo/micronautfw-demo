package micronautfw.demo.quotes;

import io.reactivex.Maybe;
import micronautfw.demo.quotes.dto.Quote;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class QuoteMongoDAO implements QuoteDAO {

    private static final List<Quote> quotes = Arrays.asList(
        new Quote().setId("1").setText("Don't leave for tomorrow what you can do today - Anonymous"),
        new Quote().setId("2").setText("At the beginning I was listening but... - Davind 'flowers'"),
        new Quote().setId("3").setText("A man has to do what a man has to do. - Conan the barbarian"),
        new Quote().setId("4").setText("Leave for tomorrow what you can do today because you might not have to do it at all - Corugedo")
    );

    @Override
    public Maybe<Quote> findOne(String id) {
        return quotes.stream()
            .filter(quote -> quote.getId().equals(id))
            .map(Maybe::just)
            .findAny()
            .orElse(Maybe.empty());
    }

}
