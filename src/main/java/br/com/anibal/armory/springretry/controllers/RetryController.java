package br.com.anibal.armory.springretry.controllers;

import br.com.anibal.armory.springretry.exceptions.DangerousCriminalException;
import br.com.anibal.armory.springretry.exceptions.UnhandledCriminalException;
import br.com.anibal.armory.springretry.models.Criminal;
import br.com.anibal.armory.springretry.services.CriminalCatcher;
import br.com.anibal.armory.springretry.services.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryController {

    @Autowired
    private RetryService service;

    @Autowired
    private CriminalCatcher criminalCatcher;

    @GetMapping("/simpleRetry")
    public ResponseEntity<?> getCriminalAnnotation() throws DangerousCriminalException, UnhandledCriminalException, Exception {
        return ResponseEntity.ok(service.captureCriminal());
    }

    @GetMapping("/templateRetry/{escapeTimes}")
    public ResponseEntity<?> getCriminalTemplate(@PathVariable Integer escapeTimes) throws Throwable {
        Criminal criminal = new Criminal();
        criminal.setTimesToEscape(escapeTimes);

        String caught = criminalCatcher.captureCriminal(criminal);

        return ResponseEntity.ok(caught);
    }

}
