package com.liza.smart.diagnosis.controller;

import com.liza.smart.diagnosis.entity.Doctor;
import com.liza.smart.diagnosis.exception.ResourceNotFoundException;
import com.liza.smart.diagnosis.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class DoctorController {
   @Autowired
   private DoctorRepository doctorRepository;
   @GetMapping("/doctor")
    public List<Doctor>getAllDoctor(){
       return doctorRepository.findAll();
   }
   // create employee rest api
   @PostMapping("/doctors")
   public Doctor createDoctor(@RequestBody Doctor doctor) {
      return doctorRepository.save(doctor);
   }
   // get employee by id rest api
   @GetMapping("/doctors/{id}")
   public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
      Doctor doctor = doctorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
      return ResponseEntity.ok(doctor);
   }

   @PutMapping("/doctors/{id}")
   public ResponseEntity<Doctor> updateEmployee(@PathVariable Long id, @RequestBody Doctor doctorDetails){
      Doctor doctors = doctorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

      doctors.setFirstName(doctorDetails.getFirstName());
      doctors.setLastName(doctorDetails.getLastName());
      doctors.setEmail(doctorDetails.getEmail());
      doctors.setDescription(doctorDetails.getDescription());

      Doctor updatedEmployee = doctorRepository.save(doctors);
      return ResponseEntity.ok(updatedEmployee);
   }
   // delete employee rest api
   @DeleteMapping("/doctors/{id}")
   public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
      Doctor doctor = doctorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

      doctorRepository.delete(doctor);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);
      return ResponseEntity.ok(response);
   }

}
