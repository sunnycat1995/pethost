package com.project.pethost.dto;

import java.util.ArrayList;
import java.util.List;

public class PetCreationDto {
    private List<PetDto> pets;

    public PetCreationDto() {
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
