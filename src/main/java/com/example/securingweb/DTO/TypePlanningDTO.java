package com.example.securingweb.DTO;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.TypePlanning;

import lombok.Data;

@Data
public class TypePlanningDTO {
	
	private String nom_type;
	
	public TypePlanning toTypePlanning() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(this, TypePlanning.class);
	}

}
