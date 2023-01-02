package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.Kafkaka;
import com.example.demo.model.Person;

//Uses the Repository
@Service
public class PersonService {
    //how to insert a new person
	//NOT INSTANTIATED USE BEANS @ TO INSTANTIATE
	@Autowired
	private final PersonDao personDao;
	Kafkaka kafkaObj;
	
	//Injecting the @Service repository (personDao)
	@Autowired
	public PersonService(@Qualifier("fakeDao") PersonDao personDao) {
		//Qualifier distinguishes between the two types of PersonDao
		super();
		this.personDao = personDao;
	}


	public String addPerson(Person person) {
		String returnMsg = personDao.insertPerson(person);
		//Kafka object will send infromation and consume
		kafkaObj = new Kafkaka(person);
		//kafkaObj.kafkaConsumer();
		kafkaObj.kafkaProducer();
		return returnMsg;
	}
	
	public List<Person> getAllPeople(){
		return personDao.selectAllPeople();
	}


	public String getStatus(long id) {
		return personDao.returnStatus(id);
	}


	public void doWork(long personId) {
		
		personDao.updateStatus(personId, "Pending");
		System.out.println("Person ID:"+Long.toString(personId)+" status set to Pending");
		System.out.println("Going to pause to simulate work");
		kafkaObj.pauseThread();
		System.out.println("Resuming work");
		personDao.updateStatus(personId, "Done");
		System.out.println("Person ID:"+Long.toString(personId)+" status set to Done");
		
	}

}

