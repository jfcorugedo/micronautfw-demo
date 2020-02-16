package micronautfw.demo.quotes;

import io.reactivex.Maybe;
import micronautfw.demo.quotes.dto.Quote;

public interface QuoteDAO {

    public Maybe<Quote> findOne(String id);
}
