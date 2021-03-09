package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.Wizard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepo extends JpaRepository<Wizard, Long> {
    Wizard findByName(String wizard);


}
