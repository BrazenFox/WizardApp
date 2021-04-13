package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.*;
import com.netcracker.wizardapp.exceptions.ResourceNotFoundException;
import com.netcracker.wizardapp.payload.response.MessageResponse;
import com.netcracker.wizardapp.repository.ButtonRepo;
import com.netcracker.wizardapp.repository.PageRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import com.netcracker.wizardapp.repository.WizardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createWizard(@RequestBody Wizard wizardView) {
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
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Page name is already taken!"));
                }
                System.out.println(pageView.getType());
                System.out.println(pageView.getType().name());
                //Page page = new Page(pageView.getName(), wizard, pageView.getContent());

                Page page = new Page(pageView.getName(), wizard, pageView.getContent(), PageTypes.valueOf(pageView.getType().name()));
                pageRepo.save(page);

            }
            System.out.println(2);
            for (Page pageView : wizardView.getPages()) {
                Page page = pageRepo.findByNameAndWizard(pageView.getName(), wizard).orElseThrow(ResourceNotFoundException::new); /*new Page(pageView.getName(), wizard, pageView.getNumber(), pageView.getContent());*/
                if ((pageView.getButtons() != null) && (!pageView.getButtons().isEmpty())) {
                    for (Button buttonView : pageView.getButtons()) {
                        if (buttonRepo.existsByNameAndPage(buttonView.getName(), page)) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: Button name is already taken!"));
                        }
                        Page toPage = pageRepo.findByNameAndWizard(buttonView.getToPage().getName(), wizard).orElseThrow(ResourceNotFoundException::new);
                        Button button = new Button(buttonView.getName(), page, toPage);
                        buttonRepo.save(button);

                    }
                }

            }
        }
        return ResponseEntity.ok(wizardView);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWizard(@RequestBody Wizard wizardView, @PathVariable(value = "id") Long id) {
        Wizard wizard = wizardRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
        for (Page page:wizard.getPages()){
            page.getButtons().removeAll(page.getButtons());
            buttonRepo.deleteAll(page.getButtons());
            pageRepo.save(page);
        }
        wizard.getPages().removeAll(wizard.getPages());
        pageRepo.deleteAll(wizard.getPages());
        wizard.setName(null);
        wizardRepo.save(wizard);
        if (wizardRepo.existsByName(wizardView.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Wizards name is already taken!"));
        }
        User creator = userRepo.findById(wizardView.getCreator().getId()).orElseThrow(() -> new RuntimeException("User Not Found with id: " + wizardView.getCreator().getId()));
        wizard.setName(wizardView.getName());
        wizard.setCreator(creator);

        wizardRepo.save(wizard);
        System.out.println(1);
        if (!wizardView.getPages().isEmpty()) {
            for (Page pageView : wizardView.getPages()) {
                if (pageRepo.existsByNameAndWizard(pageView.getName(), wizard)) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Page name is already taken!"));
                }
                Page page = new Page(pageView.getName(), wizard, pageView.getContent(), PageTypes.valueOf(pageView.getType().name()));
                pageRepo.save(page);
                System.out.println(page.getName());

            }
            for (Page pageView : wizardView.getPages()) {
                Page page = pageRepo.findByNameAndWizard(pageView.getName(), wizard).orElseThrow(ResourceNotFoundException::new); /*new Page(pageView.getName(), wizard, pageView.getNumber(), pageView.getContent());*/
                System.out.println(page.getName());
                if ((pageView.getButtons() != null) && (!pageView.getButtons().isEmpty())) {
                    for (Button buttonView : pageView.getButtons()) {
                        if (buttonRepo.existsByNameAndPage(buttonView.getName(), page)) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: Button name is already taken!"));
                        }
                        System.out.println(buttonView.getToPage().getName());
                        Page toPage = pageRepo.findByNameAndWizard(buttonView.getToPage().getName(), wizard).orElseThrow(ResourceNotFoundException::new);
                        Button button = new Button(buttonView.getName(), page, toPage);
                        buttonRepo.save(button);

                    }
                }

            }
        }
        return ResponseEntity.ok(wizardView);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Wizard> deleteWizard(@PathVariable(value = "id") Long id) {
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
