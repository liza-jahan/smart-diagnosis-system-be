package com.liza.smart.diagnosis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="first_name")
    private  String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="description")
    private String description;
    @Column(name="email")
    private  String email;

    public  Doctor(String firstName,String lastName,String description,String email){
        super();
        this.firstName=firstName;
        this.lastName=lastName;
        this.description=description ;
        this.email=email;
    }

    public Doctor() {

    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
