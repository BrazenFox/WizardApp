package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.Button;
import com.netcracker.wizardapp.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ButtonRepo extends JpaRepository<Button, Long> {
    Button findByNameAndPage(String button, Page page);
    List<Button> findByPage(Page page);
}
