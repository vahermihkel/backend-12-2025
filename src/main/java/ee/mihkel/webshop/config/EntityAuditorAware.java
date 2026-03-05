package ee.mihkel.webshop.config;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // After Step 12, you will replace "SYSTEM" with:
        String email = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        return Optional.of(email);
    }
}
