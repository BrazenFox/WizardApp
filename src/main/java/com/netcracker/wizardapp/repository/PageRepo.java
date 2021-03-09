package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.Page;
import com.netcracker.wizardapp.domain.Wizard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PageRepo extends JpaRepository<Page, Long> {
    Page findByNameAndWizard(String page, Wizard wizard);
    List<Page> findByWizard(Wizard wizard);


}
