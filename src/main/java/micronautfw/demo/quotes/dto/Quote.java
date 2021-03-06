package micronautfw.demo.quotes.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors( chain = true )
public class Quote {

    private String id;
    private String text;
}
