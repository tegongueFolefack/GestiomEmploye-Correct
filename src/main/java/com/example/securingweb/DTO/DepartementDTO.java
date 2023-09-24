package com.example.securingweb.DTO;



import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Departement;

import lombok.Data;

@Data
public class DepartementDTO {
	
	private String nom_departement;
	
	
	
	public Departement toDepartement() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Departement.class);
    }


}
