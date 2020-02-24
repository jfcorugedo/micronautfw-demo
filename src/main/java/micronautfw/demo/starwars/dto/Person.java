package micronautfw.demo.starwars.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors( chain = true )
public class Person {
    private String name;
    private List<String> films;
}
