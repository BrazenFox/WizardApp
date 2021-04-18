package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.*;
import com.netcracker.wizardapp.exceptions.PageNotFoundException;
import com.netcracker.wizardapp.exceptions.UserNotFoundException;
import com.netcracker.wizardapp.exceptions.WizardNotFoundException;
import com.netcracker.wizardapp.payload.response.MessageResponse;
import com.netcracker.wizardapp.repository.ButtonRepo;
import com.netcracker.wizardapp.repository.PageRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import com.netcracker.wizardapp.repository.WizardRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import com.netcracker.wizardapp.domain.Roles;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wizard")
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WizardController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private WizardRepo wizardRepo;

    @Autowired
    private PageRepo pageRepo;

    @Autowired
    private ButtonRepo buttonRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<?> createWizard(@RequestBody Wizard wizardView) {
        if (wizardRepo.existsByName(wizardView.getName())) {
            logger.error("Wizard name: \"" + wizardView.getName() + "\" is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Wizards name is already taken!"));
        }
        User creator = userRepo.findById(wizardView.getCreator().getId()).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + wizardView.getCreator().getId()));
        Wizard wizard = new Wizard(wizardView.getName(), creator);
        wizardRepo.save(wizard);
        if (!wizardView.getPages().isEmpty()) {
            for (Page pageView : wizardView.getPages()) {
                if (pageRepo.existsByNameAndWizard(pageView.getName(), wizard)) {
                    logger.error("Page name: \"" + pageView.getName() + "\" is already taken!");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Page name is already taken!"));
                }
                Page page = new Page(pageView.getName(), wizard, pageView.getContent(), PageTypes.valueOf(pageView.getType().name()));
                pageRepo.save(page);

            }
            for (Page pageView : wizardView.getPages()) {
                Page page = pageRepo.findByNameAndWizard(pageView.getName(), wizard).orElseThrow(() -> new PageNotFoundException("The page with the name: " + pageView.getName() + " was not found for the wizard with id: " + wizard.getId()));
                if ((pageView.getButtons() != null) && (!pageView.getButtons().isEmpty())) {
                    for (Button buttonView : pageView.getButtons()) {
                        if (buttonRepo.existsByNameAndPage(buttonView.getName(), page)) {
                            logger.error("Button name: \"" + buttonView.getName() + "\" is already taken!");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: Button name is already taken!"));
                        }
                        Page toPage = pageRepo.findByNameAndWizard(buttonView.getToPage().getName(), wizard).orElseThrow(() -> new PageNotFoundException("The page with the name: " + buttonView.getToPage().getName() + " was not found for the wizard with id: " + wizard.getId()));
                        Button button = new Button(buttonView.getName(), page, toPage);
                        buttonRepo.save(button);

                    }
                }

            }
        }
        return ResponseEntity.ok(new MessageResponse("Wizard created successfully!"));
    }

    @Transactional
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<?> updateWizard(@RequestBody Wizard wizardView, @PathVariable(value = "id") Long id) {
        Wizard wizard = wizardRepo.findById(id).orElseThrow(() -> new WizardNotFoundException("No wizard was found with id = " + id));
        for (Page page : wizard.getPages()) {
            page.getButtons().removeAll(page.getButtons());
            buttonRepo.deleteAll(page.getButtons());
            pageRepo.save(page);
        }
        wizard.getPages().removeAll(wizard.getPages());
        pageRepo.deleteAll(wizard.getPages());
        if (wizardRepo.existsByNameAndIdNot(wizardView.getName(), wizard.getId())) {
            logger.error("Wizard name: \"" + wizardView.getName() + "\" is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Wizards name is already taken!"));
        }
        User creator = userRepo.findById(wizardView.getCreator().getId()).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + wizardView.getCreator().getId()));
        wizard.setName(wizardView.getName());
        wizard.setCreator(creator);
        wizardRepo.save(wizard);
        if (!wizardView.getPages().isEmpty()) {
            for (Page pageView : wizardView.getPages()) {
                if (pageRepo.existsByNameAndWizard(pageView.getName(), wizard)) {
                    logger.error("Page name: \"" + pageView.getName() + "\" is already taken!");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Page name is already taken!"));
                }
                Page page = new Page(pageView.getName(), wizard, pageView.getContent(), PageTypes.valueOf(pageView.getType().name()));
                pageRepo.save(page);

            }
            for (Page pageView : wizardView.getPages()) {
                Page page = pageRepo.findByNameAndWizard(pageView.getName(), wizard).orElseThrow(() -> new PageNotFoundException("The page with the name: " + pageView.getName() + " was not found for the wizard with id: " + wizard.getId())); /*new Page(pageView.getName(), wizard, pageView.getNumber(), pageView.getContent());*/
                if ((pageView.getButtons() != null) && (!pageView.getButtons().isEmpty())) {
                    for (Button buttonView : pageView.getButtons()) {
                        if (buttonRepo.existsByNameAndPage(buttonView.getName(), page)) {
                            logger.error("Button name: \"" + buttonView.getName() + "\" is already taken!");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: Button name is already taken!"));
                        }
                        Page toPage = pageRepo.findByNameAndWizard(buttonView.getToPage().getName(), wizard).orElseThrow(() -> new PageNotFoundException("The page with the name: " + buttonView.getToPage().getName() + " was not found for the wizard with id: " + wizard.getId()));
                        Button button = new Button(buttonView.getName(), page, toPage);
                        buttonRepo.save(button);

                    }
                }

            }
        }
        return ResponseEntity.ok(new MessageResponse("Wizard created successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<?> deleteWizard(@PathVariable(value = "id") Long id) {
        Wizard wizard = wizardRepo.findById(id).orElseThrow(() -> new WizardNotFoundException("No wizard was found with id = " + id));
        wizardRepo.delete(wizard);
        logger.info("Wizard " + wizard.getName() + " has been removed");
        return ResponseEntity.ok(new MessageResponse("Wizard deleted successfully!"));
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Wizard> findWizard(@PathVariable(value = "id") Long id) {
        Wizard wizard = wizardRepo.findById(id).orElseThrow(() -> new WizardNotFoundException("No wizard was found with id = " + id));
        logger.info("Wizard was found with id="+wizard.getId());
        return ResponseEntity.ok(wizard);
    }

    @GetMapping("/find")
    public Iterable<Wizard> findWizardAll() {
        return wizardRepo.findAll();
    }

}
