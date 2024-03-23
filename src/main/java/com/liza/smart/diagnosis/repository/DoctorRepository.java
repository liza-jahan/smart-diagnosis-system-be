package com.liza.smart.diagnosis.repository;

import com.liza.smart.diagnosis.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    }


