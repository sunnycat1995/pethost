package com.project.pethost.form;

import com.project.pethost.dto.PetDto;

import java.util.ArrayList;
import java.util.List;

public class PetCreationForm {
    private List<PetDto> pets;

    public PetCreationForm() {
        pets = new ArrayList<>();
    }

    public List<PetDto> getPets() {
        return pets;
    }

    public void setPets(final List<PetDto> pets) {
        this.pets = pets;
    }

    public void addPet(final PetDto pet) {
        this.pets.add(pet);
    }
}
