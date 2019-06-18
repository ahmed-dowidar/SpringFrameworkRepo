package com.demo.school.repository;

import java.util.List;

import com.demo.school.model.School;

public interface SchoolRepository {
	public School create(School school);
	public void delete(School school);
	public School update(School school);
	public List<School> findAll();
	public School findById(int id);
	public List<School> findBySchoolName(String name);
	public List<School> find(int schoolId,String name);
}
