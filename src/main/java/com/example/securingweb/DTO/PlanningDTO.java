package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Planning;

import lombok.Data;

@Data
public class PlanningDTO {
	

	
	private Date date_creation;
	private Date date_debut;
	private Date date_fin;
	
	public Planning toPlanning() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Planning.class);
    }

}
