package com.example.securingweb.DTO;

import java.util.ArrayList;
import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.securingweb.Models.Comptable;






public class ComptableDTO extends UserDTO {
	
	
	
	public Comptable toComptable() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Comptable.class);
    }
}
