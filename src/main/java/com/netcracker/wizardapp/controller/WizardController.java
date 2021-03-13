package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Button;
import com.netcracker.wizardapp.domain.Page;
import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.exceptions.NotFoundException;
import com.netcracker.wizardapp.repository.ButtonRepo;
import com.netcracker.wizardapp.repository.PageRepo;
import com.netcracker.wizardapp.repository.WizardRepo;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("/create")
    public Wizard addWizard(@RequestBody Wizard wizardView) {
        if (wizardRepo.findByName(wizardView.getName()) == null) {
            Wizard wizard = new Wizard(wizardView.getName());
            wizardRepo.save(wizard);
            if (!wizardView.getPages().isEmpty()) {
                for (Page pageView : wizardView.getPages()) {
                    if (pageRepo.findByNameAndWizard(pageView.getName(), wizard) == null) {
                        Page page = new Page(pageView.getName(), wizard);
                        pageRepo.save(page);
                        if (!pageView.getButtons().isEmpty()) {
                            for (Button buttonView : pageView.getButtons()) {
                                if (buttonRepo.findByNameAndPage(buttonView.getName(), page) == null) {
                                    Button button = new Button(buttonView.getName(), page);
                                    buttonRepo.save(button);
                                }
                            }
                        }
                    }
                }
            }
            return wizard;
        }
        return wizardView;
    }

    @DeleteMapping("/delete/{wizard}")
    public Wizard deleteWaizard(@PathVariable(value = "wizard") String wizardName) {
        Wizard wizard = wizardRepo.findByName(wizardName);
        if (wizard == null) throw new NotFoundException();
        wizardRepo.delete(wizard);
        return wizard;
    }


    @GetMapping("/find/{wizard}")
    public Wizard findWizard(@PathVariable(value = "wizard") String wizardName) {
        Wizard wizard = wizardRepo.findByName(wizardName);
        if (wizard == null) throw new NotFoundException();
        return wizard;
    }

    @GetMapping("/find")
    public Iterable<Wizard> findWizardAll() {
        return wizardRepo.findAll();
    }

}
