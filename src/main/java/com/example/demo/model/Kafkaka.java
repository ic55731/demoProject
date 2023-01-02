package com.example.demo.model;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;


public class Kafkaka {
	
	String personString;
	Thread consumerThread = new Thread(new ConsumerRunnable(), "consumerThread");
	
	public Kafkaka(Person person) {
		this.personString = person.toString();
		consumerThread.start();
	}
	
	public void kafkaProducer() {
		//Create properties object and configure
		Properties props = new Properties();
		String personString = this.personString;
		
		props.put("bootstrap.servers", "localhost:9092");//9092, 2181
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("transactional.id", "my-transactional-id");
		KafkaProducer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
		//KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		System.out.println("Created producer in Kafka");
		producer.initTransactions();
		
		try {
		    producer.beginTransaction();
		    System.out.println("Begin Transaction");
		    ProducerRecord<String, String> sentMessage = new ProducerRecord<>("person-data", "person", personString );
			producer.send( sentMessage );
			System.err.println("Message sent:"+personString+" and commiting transaction");
		    producer.commitTransaction();
		 } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
			 System.err.println("Error");
		     // We can't recover from these exceptions, so our only option is to close the producer and exit.
		     producer.close();
		 } catch (KafkaException e) {
			 System.err.println("Kafka Exception");
		     // For all other exceptions, just abort the transaction and try again.
		     producer.abortTransaction();
		 } 
		//close producer
		producer.close();
		System.out.println("Closed producer");
		
		
	}

	public void pauseThread() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
