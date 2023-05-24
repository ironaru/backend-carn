package project.DB.server.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Password {
    private String actual;
    private String nueva;
    private String repetida;
}
