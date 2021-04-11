package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.Result;
import com.netcracker.wizardapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResultRepo extends JpaRepository<Result, Long> {
    Optional<Result> findById(Long id);

    @Query("from Result r inner join Wizard w on r.wizard = w.id where w.creator = :user")
    List<Result> findByCreator(@Param("user") User user);

    List<Result> findAllByUser(User user);


}

