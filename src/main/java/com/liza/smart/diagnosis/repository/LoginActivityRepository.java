package com.liza.smart.diagnosis.repository;

import com.liza.smart.diagnosis.entity.LoginActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {
}
