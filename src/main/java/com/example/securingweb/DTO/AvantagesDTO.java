package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Enums.Role;
import com.example.securingweb.Models.Avantages;
import com.example.securingweb.Request.RegisterRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvantagesDTO {
	
	private String type_avantage;
	
	public Avantages toAvantages() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(this, Avantages.class);
	}
}
