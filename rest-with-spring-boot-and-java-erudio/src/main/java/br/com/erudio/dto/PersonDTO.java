package br.com.erudio.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
