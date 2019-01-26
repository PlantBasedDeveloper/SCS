package com.mock.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MockServer {
    
	public static String STATUS = "debug";

	private static MockServer instance;
	
	private static List<String> sensor1 = new ArrayList<>();
	private static List<String> sensor2 = new ArrayList<>();
	private static SensorDataReader[] mockSensors;
	public static MsgQueue msg = new MsgQueue(5000);

	
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
			synchronized (MockServer.class) {
				if (instance == null) {
					instance = new MockServer();
				}
			}
		}
		return instance;
	}
    
    public void activateButtons() {
    	//TODO: implement
    }
    
    public void readData() {
    	new ProcessMessage(true);
		for (SensorDataReader s : mockSensors) {
			s.readMovementService();
			
		}
    }

	public String getSensor1Data() {
		if(sensor1.size()!=0 && sensor1.get(0)!=null)
		{	
				return sensor1.remove(0);
				
		}
		else
		{
			return "-1";
		}
	}

	public String getSensor2Data() {
		if(sensor2.size()!=0 && sensor2.get(0)!=null)
		{	
				return sensor2.remove(0);
				
		}
		else
		{
			return "-1";
		}
	}
	
	public void addToabsAcc1Queue(double val)
	{
		instance.absAcc1Queue.add((Number)val);
	}
	
	public void addToSensor1(String data) {
		instance.sensor1.add(data);
	}
	
	public void addToSensor2(String data) {
		instance.sensor2.add(data);
	}
		
}
