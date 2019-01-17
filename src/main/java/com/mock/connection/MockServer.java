package com.mock.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MockServer {
    
	public static String status = "debug";

	private static MockServer instance;
	
	private static List<String> sensor1 = new ArrayList<>();
	private static List<String> sensor2 = new ArrayList<>();

	
	public static ConcurrentLinkedQueue<String> sensor1DataForDb =  new ConcurrentLinkedQueue<>();
	public static ConcurrentLinkedQueue<String> sensor2DataForDb =  new ConcurrentLinkedQueue<>();
	
	public static ConcurrentLinkedQueue<Number> absAcc1Queue = new ConcurrentLinkedQueue<>();
	public static ConcurrentLinkedQueue<Number> absAcc2Queue = new ConcurrentLinkedQueue<>();
	public static ConcurrentLinkedQueue<Number> gyro1DataToDisplay = new ConcurrentLinkedQueue<>();
	public static ConcurrentLinkedQueue<Number> gyro2DataToDisplay = new ConcurrentLinkedQueue<>();

	private MockServer () {
    	
    }
	
	public static MockServer getInstance() {
		if (instance == null) {
			instance = new MockServer();
		}
		
		return instance;
	}
    
    public void activateButtons() {
    	
    }
    
    public void readData() {
    	//TODO: implement
    }

	public String getSensor1Data() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSensor2Data() {
		// TODO Auto-generated method stub
		return null;
	}
   
}
