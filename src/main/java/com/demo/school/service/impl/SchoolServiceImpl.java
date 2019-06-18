package com.demo.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.school.model.School;
import com.demo.school.repository.SchoolRepository;
import com.demo.school.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService{

	@Autowired
	private SchoolRepository schoolRepository;
	
	/**
	 * create new student in db
	 * @param STUDENT
	 * @return Student with id
	 */
	@Override
	@Transactional
	public School create(School school) {
		return this.schoolRepository.create(school);
	}

	/**
	 * delete exist student
	 * @param student with id
	 * @exception if student id not found
	 */
	@Override
	@Transactional
	public void delete(School school) throws Exception {
		
		if (String.valueOf(school.getId()) == null)
			throw new Exception("Student id is null");
		
		this.schoolRepository.delete(school);
	}

	/**
	 * return all student in db
	 * @return List<Student>
	 */
	@Override
	@Transactional(readOnly = true)
	public List<School> findAll() {
		return schoolRepository.findAll();
	}

	/**
	 * find specific student by id
	 * @param id
	 * @return STUDENT
	 */
	@Override
	@Transactional(readOnly = true)
	public School findById(int id) {
		return this.schoolRepository.findById(id);
	}

	/**
	 * modify student in db
	 * @param new student
	 * @return saved student
	 */
	@Override
	@Transactional
	public School update(School school) {
		return this.schoolRepository.update(school);
	}

	@Override
	public List<School> findBySchoolName(String name) {
		return this.schoolRepository.findBySchoolName(name);
	}
	@Override
	public List<School> find(int schoolId,String name) {
		return this.schoolRepository.find(schoolId,name);
	}
	
}
