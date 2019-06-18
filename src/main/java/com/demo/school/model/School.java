package com.demo.school.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class School {
	@Id
    @GeneratedValue
    private Integer id; 
    private String name;
 
    public School() { }
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
}
