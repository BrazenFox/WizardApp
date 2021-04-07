package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.Page;
import com.netcracker.wizardapp.domain.Wizard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepo extends JpaRepository<Page, Long> {
    Optional<Page> findById(Long id);
    Optional<Page> findByNameAndWizard(String name, Wizard wizard);
    Boolean existsByNameAndWizard(String page, Wizard wizard);
    List<Page> findByWizard(Wizard wizard);


}
