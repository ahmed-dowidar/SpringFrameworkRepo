package com.demo.school.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.demo.school.model.School;
import com.demo.school.service.SchoolService;

@ManagedBean(name = "SchoolMB")
@ViewScoped
public class SchoolMB extends SpringBeanAutowiringSupport {

	@Autowired
	private SchoolService schoolService;
	private boolean mainViewMode;
	private boolean addMode;
	private boolean editMode;
	private String editSchoolId;
	private String searchSchoolName;
	private Integer searchSchoolId;
	private String name;
	private String newName;
	private School selectedUpdatedSchool = new School();
	private List<School> schools = new ArrayList<School>();
	List<School> schoolsList = new ArrayList<School>();
	private School selectedSchool = new School();

	@PostConstruct
	public void init() {
		switchToMainViewMode();
		schoolListFromDb();
	}

	public void fillSchoolObjectToEdit(School school) {
		this.newName=school.getName();
		selectedUpdatedSchool = school;
	}

	public void saveSchool() {
		selectedSchool = new School();
		selectedSchool.setName(this.name);
		if (!checkIsNameDuplicated(this.name)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Entered Name is ( Duplicated ), Please Enter a nother one ! ", null));
		}else{
			performAddingOperation();
		}
			
	}

	private void performAddingOperation() {
		if(schoolService.create(selectedSchool) != null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"School with Name :  " + selectedSchool.getName() + " has been added  Succeessfully. ", null));
			this.schoolListFromDb();
			this.name = "";
			this.newName="";
			this.searchSchoolId=null;
			this.searchSchoolName="";
			initializeObjects();
			this.switchToMainViewMode();
			Collections.reverse(schools);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Adding School :  " + selectedSchool.getName() + "  Failed", null));
		}
	}

	private boolean checkIsNameDuplicated(String schoolName) {
		this.schoolListFromDb();
		for (School school : schools) {
			if (school != null && schoolName != null && school.getName() != null
					&& school.getName().equalsIgnoreCase(schoolName)) {
				return false;
			}
		}
		return true;
	}

	public void updateSchool(String schoolName) {
		if(!checkIsNameDuplicated(schoolName)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Entered Name is Duplicated, Please Enter a nother one ! ", null));
		}else{
			performUpdateOperation(schoolName);
		}
	}

	private void performUpdateOperation(String schoolName) {
		Integer schoolIdHolded=selectedUpdatedSchool.getId();
		selectedUpdatedSchool.setName(schoolName);
		if (schoolService.update(selectedUpdatedSchool) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"School with Id : "+ selectedUpdatedSchool.getId() +"  Updated With Name : ( "+ schoolName + " )", null));
			this.newName="";
			initializeObjects();
			if(this.searchSchoolId != null ){
				this.searchSchoolId =schoolIdHolded;
			}
			this.switchToMainViewMode();
			Collections.reverse(schools);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"School with Id : "+ selectedUpdatedSchool.getId() +"  Updated With Name : ( "+ schoolName + " ) ..  Failed , please Try again ! ", null));
		}
	}

	public void deleteSchool(School school) {
		try {
			schoolService.delete(school);
			search();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "School: " + school.getName() + " deleted", null));
			this.switchToMainViewMode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void clearSeachValues(){
		this.searchSchoolId=null;
		this.searchSchoolName="";
	}
	public void search() {
		schools.clear();
		schoolsList.clear();
		if(this.searchSchoolId != null && this.searchSchoolId > 0 && 
				this.searchSchoolName != null && !this.searchSchoolName.isEmpty()){
			findSchoolsByIdAndName();
		}else if (this.searchSchoolId != null && this.searchSchoolId > 0) {
			findSchoolById();
		} else if (this.searchSchoolName != null && !this.searchSchoolName.isEmpty()) {
			findSchoolByName();
		}else {
			this.schoolListFromDb();
		}
		if(this.searchSchoolId != null){
			if(this.searchSchoolId == 0 ){
				this.searchSchoolId=null;
			}
		}
	}

	private void findSchoolByName() {
		schoolsList = schoolService.findBySchoolName(this.searchSchoolName);
		for (School school : schoolsList) {
			schools.add(school);
		}
	}

	private void findSchoolById() {
		selectedUpdatedSchool=new School();
		selectedUpdatedSchool=schoolService.findById(this.searchSchoolId);
		if(selectedUpdatedSchool != null ){
			schools.add(selectedUpdatedSchool);
		}
	}

	private void findSchoolsByIdAndName() {
		schoolsList = schoolService.find(this.searchSchoolId,this.searchSchoolName);
		for (School school : schoolsList) {
			schools.add(school);
		}
	}

	public void switchToMainViewMode() {
		search();
		this.name="";
		this.mainViewMode = true;
		this.addMode = false;
		this.editMode = false;
	}

	public void switchToAddMode() {
		this.searchSchoolId=null;
		this.mainViewMode = false;
		this.addMode = true;
		this.editMode = false;
	}

	public void switchToEditMode() {
		this.mainViewMode = false;
		this.addMode = false;
		this.editMode = true;
	}

	public void schoolListFromDb() {
		schools = schoolService.findAll();
	}

	public void initializeObjects() {
		selectedSchool = new School();
		selectedUpdatedSchool = new School();
	}

	// Setters and Getters

	public List<School> getSchools() {
		return schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}

	public SchoolService getSchoolService() {
		return schoolService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public School getSelectedSchool() {
		return selectedSchool;
	}

	public void setSelectedSchool(School selectedSchool) {
		this.selectedSchool = selectedSchool;
	}

	public String getEditSchoolId() {
		return editSchoolId;
	}

	public void setEditSchoolId(String editSchoolId) {
		this.editSchoolId = editSchoolId;
	}

	public boolean isMainViewMode() {
		return mainViewMode;
	}

	public void setMainViewMode(boolean mainViewMode) {
		this.mainViewMode = mainViewMode;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public School getSelectedUpdatedSchool() {
		return selectedUpdatedSchool;
	}

	public void setSelectedUpdatedSchool(School selectedUpdatedSchool) {
		this.selectedUpdatedSchool = selectedUpdatedSchool;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getSearchSchoolName() {
		return searchSchoolName;
	}

	public void setSearchSchoolName(String searchSchoolName) {
		this.searchSchoolName = searchSchoolName;
	}

	public List<School> getSchoolsList() {
		return schoolsList;
	}

	public void setSchoolsList(List<School> schoolsList) {
		this.schoolsList = schoolsList;
	}

	public Integer getSearchSchoolId() {
		return searchSchoolId;
	}

	public void setSearchSchoolId(Integer searchSchoolId) {
		this.searchSchoolId = searchSchoolId;
	}
}
