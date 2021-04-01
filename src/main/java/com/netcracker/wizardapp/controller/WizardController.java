package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Button;
import com.netcracker.wizardapp.domain.Page;
import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.exceptions.ResourceNotFoundException;
import com.netcracker.wizardapp.payload.response.MessageResponse;
import com.netcracker.wizardapp.repository.ButtonRepo;
import com.netcracker.wizardapp.repository.PageRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import com.netcracker.wizardapp.repository.WizardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/wizard")
public class WizardController {
    @Autowired
    private WizardRepo wizardRepo;
    @Autowired
    private PageRepo pageRepo;
    @Autowired
    private ButtonRepo buttonRepo;

    @Autowired
    private UserRepo userRepo;


    @PostMapping("/create")
    public ResponseEntity<?> addWizard(@RequestBody Wizard wizardView) {
        System.out.println(wizardView);
        if (wizardRepo.existsByName(wizardView.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Wizards name is already taken!"));
        }

        Wizard wizard = new Wizard(wizardView.getName());
        wizardRepo.save(wizard);
        if (!wizardView.getPages().isEmpty()) {
            for (Page pageView : wizardView.getPages()) {
                if (pageRepo.existsByNameAndWizard(pageView.getName(), wizard)) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Page name is already taken!"));
                }
                Page page = new Page(pageView.getName(), wizard);
                pageRepo.save(page);
                if (!pageView.getButtons().isEmpty()) {
                    for (Button buttonView : pageView.getButtons()) {
                        if (buttonRepo.existsByNameAndPage(buttonView.getName(), page)) {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: Button name is already taken!"));
                        }
                        Button button = new Button(buttonView.getName(), page);
                        buttonRepo.save(button);

                    }
                }

            }
        }
        return ResponseEntity.ok(wizardView);
    }

    @PostMapping("/create1")
    public ResponseEntity<?> addWizard1(@RequestBody Wizard wizardView) {
        System.out.println(wizardView);
        if (wizardRepo.existsByName(wizardView.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Wizards name is already taken!"));
        }

        User creator = userRepo.findById(wizardView.getCreator().getId()).orElseThrow(() -> new RuntimeException("User Not Found with id: " + wizardView.getCreator().getId()));
        Wizard wizard = new Wizard(wizardView.getName(), creator);
        wizardRepo.save(wizard);

        if (!wizardView.getPages().isEmpty()) {
            for (Page pageView : wizardView.getPages()) {
                if (pageRepo.existsByNameAndWizard(pageView.getName(), wizard)) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Page name is already taken!"));
                }
                Page page = new Page(pageView.getName(), wizard, pageView.getNumber(), pageView.getContent());
                pageRepo.save(page);
                if (!pageView.getButtons().isEmpty()) {
                    for (Button buttonView : pageView.getButtons()) {
                        if (buttonRepo.existsByNameAndPage(buttonView.getName(), page)) {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: Button name is already taken!"));
                        }
                        Button button = new Button(buttonView.getName(), page, buttonView.getToPage());
                        buttonRepo.save(button);

                    }
                }

            }
        }
        return ResponseEntity.ok(wizardView);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Wizard> deleteWaizard(@PathVariable(value = "id") Long id) {
        Wizard wizard = wizardRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
        wizardRepo.delete(wizard);
        return ResponseEntity.ok(wizard);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Wizard> findWizard(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(wizardRepo.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @GetMapping("/find")
    public Iterable<Wizard> findWizardAll() {
        return wizardRepo.findAll();
    }

}
