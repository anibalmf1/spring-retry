package br.com.anibal.armory.springretry.services;

import br.com.anibal.armory.springretry.exceptions.CriminalHasEscaped;
import br.com.anibal.armory.springretry.models.Criminal;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

public class CriminalCatcherCallback implements RetryCallback<Boolean, CriminalHasEscaped> {

    private Criminal criminal;

    @Override
    public Boolean doWithRetry(RetryContext context) throws CriminalHasEscaped {
        return criminal.catchCriminal();
    }

    public void setCriminal(Criminal criminal) {
        this.criminal = criminal;
    }
}
