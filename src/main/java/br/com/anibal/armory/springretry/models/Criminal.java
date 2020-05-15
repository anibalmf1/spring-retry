package br.com.anibal.armory.springretry.models;

import br.com.anibal.armory.springretry.exceptions.CriminalHasEscaped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Criminal {

    private Integer timesToEscape = 1;
    private Integer attemptsToCatch = 0;

    private Logger logger = LoggerFactory.getLogger(Criminal.class);

    public Boolean catchCriminal() throws CriminalHasEscaped {
        attemptsToCatch++;

        logger.info("Trying to catch for {} times", attemptsToCatch);

        if (attemptsToCatch < timesToEscape) {
            throw new CriminalHasEscaped();
        }

        logger.info("Caught successfully!");

        return true;
    }

    public void setTimesToEscape(Integer timesToEscape) {
        this.timesToEscape = timesToEscape;
    }
}
