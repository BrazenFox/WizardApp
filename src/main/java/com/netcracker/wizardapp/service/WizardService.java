package com.netcracker.wizardapp.service;

import com.netcracker.wizardapp.domain.Wizard;
import org.springframework.http.ResponseEntity;

public interface WizardService {
    ResponseEntity<?> createWizard(Wizard wizardView);
    ResponseEntity<?> updateWizard(Wizard wizardView,Long id);
    ResponseEntity<?> deleteWizard(Long id);
    ResponseEntity<Wizard> findWizard(Long id);
    Iterable<Wizard> findWizardAll();

}
