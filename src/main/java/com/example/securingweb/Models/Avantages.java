package com.example.securingweb.Models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.AvantagesDTO;
import com.example.securingweb.Enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Avantages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String type_avantage;
	
	@ManyToMany(mappedBy = "avantages")
	@Builder.Default
	private Collection<Fonction>fonction = new ArrayList<>();
	
	public Avantages(String type_avantage) {
		super();
		this.type_avantage = type_avantage;
	}


	public Avantages() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AvantagesDTO toAvantagesDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AvantagesDTO.class);
    }
	
	
	
}
