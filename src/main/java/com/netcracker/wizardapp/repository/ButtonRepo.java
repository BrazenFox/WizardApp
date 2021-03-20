package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.Button;
import com.netcracker.wizardapp.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ButtonRepo extends JpaRepository<Button, Long> {
    Optional<Button> findById(Integer id);
    Boolean existsByNameAndPage(String button, Page page);
    List<Button> findByPage(Page page);
}
