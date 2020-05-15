package br.com.anibal.armory.springretry.services;

import br.com.anibal.armory.springretry.exceptions.DangerousCriminalException;
import br.com.anibal.armory.springretry.exceptions.UnhandledCriminalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class RetryService {

    private List<String> CRIMINAL_NAMES = Arrays.asList("Joe", "Manner", "Rabb", "Crab", "Goy", "Anon");
    private Logger logger = LoggerFactory.getLogger(RetryService.class);

    private final String UNHANDLED_CRIMINAL = "Anon";
    private final String DANGEROUS_CRIMINAL = "Goy";

    @Retryable(
            maxAttempts = 2,
            value = { DangerousCriminalException.class, UnhandledCriminalException.class } ,
            backoff = @Backoff(delay = 100)
    )
    public String captureCriminal() throws Exception {
        int criminal_id = new Random().nextInt(CRIMINAL_NAMES.size());

        String criminal = CRIMINAL_NAMES.get(criminal_id);

        if (UNHANDLED_CRIMINAL.equals(criminal)) {
            throw new UnhandledCriminalException(criminal);
        }

        if (DANGEROUS_CRIMINAL.equals(criminal)) {
            throw new DangerousCriminalException(criminal);
        }

        return "We captured this one: ".concat(criminal);
    }

    @Recover
    public String carCrash(Exception e) {
        logger.info("Car is crashed, not trying anymore");
        return "Couldn't keep pursuing: " + e.getMessage();
    }

    @Recover
    public String dangerousRecover(DangerousCriminalException e) {
        logger.info("Wow, we've recovered from a dangerous capture.");
        return "It was dangerous, but we captured: ".concat(e.getMessage());
    }

    @Recover
    public String unhandledRecover(UnhandledCriminalException e) {
        logger.info("Wow, we almost didn't recover from this pursuit.");
        return "It is impossible to get him: ".concat(e.getMessage());
    }

}
