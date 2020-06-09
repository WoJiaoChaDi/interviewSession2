package utils.current.transferValue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Person {

    private Integer id;
    private String name;

    public Person(String name) {
        this.name = name;
    }

}
