package ee.mihkel.webshop.security;

import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.model.AuthToken;
import ee.mihkel.webshop.repository.PersonRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Autowired
    private PersonRepository personRepository;

    String superSecretKey = "JojzZYmVkg4tObLwAkaD2dYOCL4BDsAVbwEnJTeSkoI";
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

    public AuthToken getToken(Person person) {
        AuthToken authToken = new AuthToken();
        Date expiration_time = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2));

        //     "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIn0.ZIECDCRsgUrSNMc6hjM434HLxYrEuyUaCIW37vkvGgw"
        //      "token": "eyJhbGciOiJub25lIn0.eyJqdGkiOiIyIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIn0."

        String token = Jwts.builder()
                .id(person.getId().toString())
                .subject(person.getEmail())
                .expiration(expiration_time) // parseris kui on see Date aegunud, siis saab exceptioni
                .signWith(secretKey)
                .compact();
        authToken.setToken(token);
        authToken.setExpires(expiration_time.getTime());

        return authToken;
    }

    public Person validateToken(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return personRepository.findById(Long.parseLong(claims.getId())).orElseThrow();
    }
}
