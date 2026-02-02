package ee.mihkel.webshop.model;

import lombok.Data;

import java.util.Date;

@Data
public class AuthToken {
    private String token;
    private Long expires;
}
