package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.service.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wizard")
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WizardController {
    @Autowired
    WizardService wizardService;

    @Transactional
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<?> createWizard(@RequestBody Wizard wizardView) {
        return wizardService.createWizard(wizardView);
    }

    @Transactional
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<?> updateWizard(@RequestBody Wizard wizardView, @PathVariable(value = "id") Long id) {
        return wizardService.updateWizard(wizardView, id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<?> deleteWizard(@PathVariable(value = "id") Long id) {
        return wizardService.deleteWizard(id);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Wizard> findWizard(@PathVariable(value = "id") Long id) {
        return wizardService.findWizard(id);
    }

    @GetMapping("/find")
    public Iterable<Wizard> findWizardAll() {
        return wizardService.findWizardAll();
    }

}
