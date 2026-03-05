package ee.mihkel.webshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CronService {
    // Et Scheduled töötaks peab olema üle terve rakenduse @EnableScheduling

    // * --> sekundid (kuni 59)
    // * * --> minutid (kuni 59)
    // * * * --> tunnid (kuni 23)
    // * * * * --> kuupäev (kuni 31)
    // * * * * * --> kuu (kuni 12)
    // * * * * * * --> nädalapäev (0-7), kus nii 0 kui 7 on pühapäev

    @Scheduled(cron = "0 * 23 31 12 1-5")
    public void runEverySecond(){
        System.out.println("Every second has been called");
    }

    @Scheduled(cron = "0 0 17 L * 1-5")
    public void runAtEndOfMonthIfMondayOrFriday(){
        System.out.println("Every second has been called");
    }

    @Scheduled(cron = "0 0 17 * * 5L")
    public void runAtLastFridayOfTheMonth(){
        System.out.println("Every second has been called");
    }

    @Scheduled(cron = "0 0 17 LW * *")
    public void runAtLastWorkdayOfMonth(){
        System.out.println("Sending report...");
    }


    @Value("${cron.5min.urls:}")
    private List<String> urls;

    @Value("${cron.type:}")
    private String type;

    @Scheduled(cron = "${cron.expression:0 */5 * * * *}")
    public void adjustableCron() {
       if (type.equals("cleaning")) {
           //
       } else if (type.equals("")) {

       }
    }

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "0 */10 * * * *")
    public void callUrlEveryTenMinutes() {
        String url = "https://backend-serverisse-ickb.onrender.com/products";
        restTemplate.getForObject(url, String.class);
        System.out.println(LocalDateTime.now() + " - Called " + url + " -> OK");
    }
}
