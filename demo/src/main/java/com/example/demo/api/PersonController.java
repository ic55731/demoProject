package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Kafkaka;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

//Tells the computer where to look for the JSON file, mapping the path

//@ReequestMapping: this is the url that people use to add a person
//@Request COntroller: Tells the computer the method in the class will be altered by the API/Controller level
@RequestMapping("/") 
@RestController  
public class PersonController {
	//insert reference to the actual service
	@Autowired
	private final PersonService personservice;
	
	@Autowired  //Autowired injects PersonService into actual constructor
	public PersonController(PersonService personservice) {
		super();
		this.personservice = personservice;
	}
	
	//Get=retrieve data from server, post add resource to server, and put/delete change on server
	//addPerson is a Post Request method
	//posting a person from a client
	@PostMapping("person") 
	public String addPerson(@RequestBody Person person) {
		//Kafka object that runs constructor
		
		//return "Sent the thing";
		return personservice.addPerson(person);
	}
	
	//Get request tells the computer the person will get something from the database
	@GetMapping("getAll")
	public List<Person> getAllPeople(){
		return personservice.getAllPeople();
	}
	
	//Get job id from passed in argument id
	@GetMapping("getStatus/{personId}")
	public String getStatus(@PathVariable long personId) {
		System.out.println("Sample");
		return personservice.getStatus(personId);
	}
	
	@GetMapping("doWork/{personId}")
	public String doWork(@PathVariable long personId) {
		personservice.doWork(personId);
		return null;//personservice.getStatus(personId);
	}
	
}

