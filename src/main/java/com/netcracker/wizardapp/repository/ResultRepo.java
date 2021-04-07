package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ResultRepo extends JpaRepository<Result, Long> {
    Optional<Result> findById(Long id);


}

