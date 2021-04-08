package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.domain.Wizard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WizardRepo extends JpaRepository<Wizard, Long> {
    Optional<Wizard> findByName(String wizard);
    List<Wizard> findAllByCreator(User user);
    Optional<Wizard> findById(Long id);
    Boolean existsByName(String name);


}
