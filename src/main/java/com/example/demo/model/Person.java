package com.example.demo.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "people")
public class Person {
	//Create entity for the table
	@Id
    @Column(name="personId", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long personId;
	private final String name;
	private final String jobId;
	private String jobStatus;
	private final String jobTitle;
	private final java.sql.Timestamp updatedTime;
	
	public Person() {
		this.name 		 = "name";
		this.jobId		 = "jobId";
		this.jobStatus	 = "jobStatus";
		this.jobTitle	 = "jobTitle";
		java.util.Date date = new Date();
		this.updatedTime = new java.sql.Timestamp(date.getTime());
	}
	
	//Tell computer to use the data from Json
	public Person(@JsonProperty("name") String name, @JsonProperty("jobId") String jobId, @JsonProperty("jobStatus") String jobStatus, @JsonProperty("jobTitle") String jobTitle) {
		super();
		this.name 		 = name;
		this.jobId		 = jobId;
		this.jobStatus	 = jobStatus;
		this.jobTitle	 = jobTitle;
		java.util.Date date = new Date();
		this.updatedTime = new java.sql.Timestamp(date.getTime());
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public String getName() {
		return name;
	}

	public String getJobId() {
		return jobId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public String getJobTitle() {
		return jobTitle;
	}
	
	public java.sql.Timestamp getUpdateTime() {
		return updatedTime;
	}
	
	public void updateStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name + ", jobId=" + jobId + ", jobStatus=" + jobStatus
				+ ", jobTitle=" + jobTitle + ", updatedTime=" + updatedTime + "]";
	}
	
}
