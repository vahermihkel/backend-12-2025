package ee.mihkel.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

    // 1. 02.12 - Spring algus: andmebaasiga ühendamine, controller, entity, repository
    // 2. 04.12 - korralikud veateated, tabelite omavaheline ühendus, @Service, validations
    // 3.T 09.12 - 13.00 rendipood, DTO
    // 4.N 11.12 - 13.00 unit testid, integration tests
    // 5.T 16.12 - 13.00 front-end. backendi päring
    // 6.N 18.12 - 13.00 front-end. react-router-dom. i18next. localStorage. .env
    // 7.E 22.12 - 9.00 context. array localStorage-sse.
    // 8.E 29.12 - 13.00 redux, URLi muutuja
    // 9.E 05.01 - 13.00 Pagination, @Autowired. custom hook. tellimus backendi
    //10.N 08.01 - 13.00 RestTemplate - teistelt rakendustelt andmeid (pakiautomaadid). Makse.
    //11.E 12.01 - 13.00 auth -> paneme API otspunktid kinni - auth tunnusega sisse
    //12.N 15.01 - 13.00 auth -> JWT token
    //13.E 19.01 - 13.00 auth -> front-endis kinni (ei saa teatud URL-dele ja nuppe näha)
    //14.N 22.01 - 13.00 auth -> rollid
    //15.E 26.01 - 13.00 MINU tellimused. profiil.
    //16.N 29.01 - 13.00 cache, emaili saamine, CRON
    //17.E 02.02 - 13.00 serverisse ülespanek
    //18.Lõpuprojekt päev
}
