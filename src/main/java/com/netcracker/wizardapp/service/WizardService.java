package com.netcracker.wizardapp.service;

import com.netcracker.wizardapp.domain.Wizard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface WizardService {
    ResponseEntity<?> createWizard(Wizard wizardView);
    ResponseEntity<?> updateWizard(Wizard wizardView,Long id);
    ResponseEntity<?> deleteWizard(Long id);
    ResponseEntity<Wizard> findWizard(Long id);
    Iterable<Wizard> findWizardAll();

}
