package br.com.anibal.armory.springretry.services;

import br.com.anibal.armory.springretry.config.RetryConfig;
import br.com.anibal.armory.springretry.models.Criminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class CriminalCatcher {

    @Autowired
    private RetryConfig retryConfig;

    public String captureCriminal(Criminal criminal) throws Throwable {
        RetryTemplate retryTemplate = retryConfig.createRetryTemplate();

        boolean captured = retryTemplate.execute(getCatcher(criminal), context -> false );

        return captured ? "Captured" : "Escaped";
    }

    private CriminalCatcherCallback getCatcher(Criminal criminal) {
        CriminalCatcherCallback catcher = new CriminalCatcherCallback();
        catcher.setCriminal(criminal);
        return catcher;
    }
}
