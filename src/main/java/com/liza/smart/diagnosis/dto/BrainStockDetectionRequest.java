package com.liza.smart.diagnosis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrainStockDetectionRequest {

        private String firstname;
        private String lastname;
        private String ever_married;
        private int age;
        private double avg_glucose_level;
        private double bmi;
        private String smoking_status;
        private String hypertension;
        private String heart_disease;
        private String work_type;
        private String residence_type;


}
