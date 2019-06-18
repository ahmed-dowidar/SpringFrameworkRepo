package com.demo.school.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.demo.school.model.School;
import com.demo.school.repository.SchoolRepository;

@Repository()
public class SchoolRepositoryImpl implements SchoolRepository{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public School create(School school) {
		if(school.getId() == null || school.getId() == 0)
			this.em.persist(school);
		else
			return null;
		
		return school;
	}

	@Override
	public void delete(School school) {
		if(!this.em.contains(school))
			school = this.em.merge(school);
		
		this.em.remove(school);
	}

	@Override
	public List<School> findAll() {
		return this.em.createQuery("from School", School.class).getResultList();
	}

	@Override
	public School update(School school) {
		School entity = this.em.find(School.class, school.getId());
		if(entity != null) {
			entity.setId(school.getId());
			entity.setName(school.getName());
			this.em.flush();
		}
		
		return entity;
	}

	@Override
	public School findById(int id) {
		return this.em.find(School.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<School> findBySchoolName(String name) {
		if(name != null && !name.isEmpty()) {
			List<School> schoolsList=new ArrayList<School>();
			Query nativeQuery = this.em.createNativeQuery("SELECT * FROM SCHOOL a where a.NAME like concat('%',:name,'%')   ", School.class);
			nativeQuery.setParameter("name", name);
			schoolsList = nativeQuery.getResultList();
			return schoolsList;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<School> find(int schoolId,String name){
		if( schoolId > 0 && name != null && !name.isEmpty()) {
			List<School> schoolsList=new ArrayList<School>();
			Query nativeQuery = this.em.createNativeQuery("SELECT * FROM SCHOOL a where a.ID = :id AND a.NAME like concat('%',:name,'%')   ", School.class);
			nativeQuery.setParameter("id", schoolId);
			nativeQuery.setParameter("name", name);
			schoolsList = nativeQuery.getResultList();
			return schoolsList;
		}
		
		return null;
	}
}
