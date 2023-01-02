package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDao extends JpaRepository<Person, Long> {
	
	//Insert a person into the database, return string if succesful
	
	default String insertPerson(Person person) {
		save(person);
		//System.out.println("Saved the thing");
		return "PersonId:"+person.getPersonId()+" JobId:"+person.getJobId();
	}
	
	default List<Person> selectAllPeople(){
		return null;
	}

	default String returnStatus(long id) {
		Optional<Person> foundPerson = findById(id);
		try {
			Person person = foundPerson.get();
			return person.getName()+" with jobId:"+person.getJobId()+" has status:"+person.getJobStatus();
		}
		catch(Exception e) {
			return "No person found with given ID";
		}
	}

	default void updateStatus(long personId, String status) {
		Optional<Person> foundPerson = findById(personId);
		try {
			Person person = foundPerson.get();
			person.updateStatus(status);
			save(person);
		}
		catch(Exception e) {
			System.err.println("Error");
		}
	}
}
