package com.application.math;

import java.rmi.server.ServerCloneException;
import java.text.DecimalFormat;
import java.util.Date;

import com.application.util.ConfigurationStorage;
import com.application.util.FallNotificationService;
import com.mock.connection.MockServer;

public class Mathems extends Thread {


	private int count_pass_measur; // counter to skip 10 measurments to do not overload CPU

	boolean isAclrFall;
	boolean isGyroFall;
	boolean isNotified;

	private Gyroskope gyro_1;
	private Accelerometer aclr_1;

	private Gyroskope gyro_2;
	private Accelerometer aclr_2;

	private MockServer server;
	

	public static Mathems mathems;

	private Mathems(MockServer server) {
		this.isAclrFall = false;
		this.isGyroFall = false;
		this.isNotified = false;

		this.count_pass_measur = 0;

		this.server = server;

		gyro_1 = new Gyroskope(this);
		aclr_1 = new Accelerometer(this);

		gyro_2 = new Gyroskope(this);
		aclr_2 = new Accelerometer(this);

		try {
			this.setName("Math_Thread");
			this.start();
		}catch(Exception e) {
			System.out.println("Something goes wrong! Thread hasn't started!!!");
		}

	}

	public static void getInstance(MockServer server){
		if(mathems==null)
		{
			synchronized(Mathems.class)
			{
				if(mathems == null)
				{
					mathems = new Mathems(server);
				}
			}
		}
	}

	public static void main() {
		//Server server = Server.getInstance();
	//	Mathems math = new Mathems(server);

		//math.add_measurments("F06800A2003FFEC000AD008E");

		//System.out.println("Hi!! I'm here using WhatsApp!!");

	}

	public void isFall() {
		// function to add new values with changes
		try {
			this.setName("Math_Thread");
			this.start();
		}catch(Exception e) {
			System.out.println("Something goes wrong! Thread hasn't started!!!");
		}
	}

	public void run() {
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			if(this.count_pass_measur <= ConfigurationStorage.getSKIP_MEASURE()) { // increasing of count_pass_measur in .add method
				//System.out.print("fkygwyc");
				
					add_measurments(); // was added to check ""
				
			}else {
				
				
				
				this.count_pass_measur = 0;

				//if(aclr_1.bufSize() < ConfigurationStorage.getCOUNT_SEC()) //if amount of measurments is less than it is need tocover one second
					//return;

				//System.out.println("IsAclFall");

				this.aclr_1.isAclrFall(this.gyro_1);
				this.aclr_2.isAclrFall(this.gyro_2);

				if(this.isAclrFall && this.isGyroFall) {
					System.out.println("Notify about a Fall!!");
					new Thread(new Runnable() {

						@Override
						public void run() {
							FallNotificationService.notifyFall();
						}}).start();
					
				
				}
				
				this.isAclrFall = false;
				this.isGyroFall = false;
			}
		}

	}


	public void add_measurments() { //was added to check String str

		long tmptime = System.currentTimeMillis();
		//for the first sensor
		String measure_str = server.getSensor1Data();

		double Ax;
		double Ay;
		double Az;

		double Gx;
		double Gy;
		double Gz;
		
		double tmp_aclr;
		double tmp_gyro;
		
		int count_sec = ConfigurationStorage.getCOUNT_SEC();

		if(measure_str==null) { 
			System.out.println("string null");
			}
		if( !measure_str.equals("-1")) {

		//aclr
			this.count_pass_measur++;

			 Ax = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(0, 4), 16)); // Az  1234 5678 9112 3456 7892 1234
			 Ay = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(4, 8), 16)); // Ay  2108 00AE 011E 0089 00C6 FEB8
			 Az = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(8, 12), 16)); // Ax

			//gyro
			 Gx = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(12, 16), 16)); // Az
			 Gy = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(16, 20), 16)); // Ay
			 Gz = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(20, 24), 16)); // Ax

			Ax = convertG(Ax);
			Ay = convertG(Ay);
			Az = convertG(Az);
			
			Gx = convertAngSp(Gx);
			Gy = convertAngSp(Gy);
			Gz = convertAngSp(Gz);
			 
			//System.out.println("Accelerometer: "+convertG(Ax) + " " + convertG(Ay) + " " + convertG(Az));
			//System.out.println("Gyroscope: "+convertAngSp(Gx) + " " + convertAngSp(Gy) + " " + convertAngSp(Gz));
			//TODO: calculate Absolute value and add it to the Queue
			 
			
			 
			tmp_aclr = Math.sqrt(sqr(Ax) + sqr(Ay) + sqr(Az));
			
			MockServer.absAcc1Queue.add((Number)tmp_aclr);
			//TODO: here I add values to accelerometer graph for gyroscope but now I add the same value that we calculate for Accelerometer this must change
			tmp_gyro = 0;
		    if(Math.abs(Gx) > 5 && Math.abs(Gy) > 5)
			tmp_gyro = Math.abs(Gx * (double)(1.0 / count_sec)) +
    						Math.abs(Gy * (double)(1.0 / count_sec));
		    //System.out.println("Gyro1: "+ tmp_gyro);
		    MockServer.gyro1DataToDisplay.add((Number)tmp_gyro);
			this.aclr_1.add_aclr(Ax, Ay, Az);
			this.gyro_1.add_gyro(Gx, Gy, Gz);
			//System.out.println("Time addMsr: " +(System.currentTimeMillis()-tmptime));
		}

		//there we can send data to the Graph!!!!!

		//for the second sensor
		measure_str ="";
		measure_str = server.getSensor2Data();

		if(!measure_str.equals("-1")) {

			//aclr
			Ax = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(0, 4), 16)); // Az
			Ay = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(4, 8), 16)); // Ay
			Az = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(8, 12), 16)); // Ax

			//gyro
			Gx = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(12, 16), 16)); // Az
			Gy = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(16, 20), 16)); // Ay
			Gz = correctMeasurments(
					Integer.parseInt(
							measure_str.substring(20, 24), 16)); // Ax
			
			Ax = convertG(Ax);
			Ay = convertG(Ay);
			Az = convertG(Az);
			
			Gx = convertAngSp(Gx);
			Gy = convertAngSp(Gy);
			Gz = convertAngSp(Gz);

			//TODO: calculate Absolute value and add it to the Queue
			tmp_aclr = Math.sqrt(sqr(Ax) + sqr(Ay) + sqr(Az));
			//System.out.println("ACC2: "+ tmp_aclr);
			MockServer.absAcc2Queue.add((Number)tmp_aclr);
		    
		  //TODO: here I add values to accelerometer graph for gyroscope but now I add the same value that we calculate for Accelerometer this must change
			tmp_gyro = 0;
		    if(Math.abs(Gx) > 5 && Math.abs(Gy) > 5)
		    tmp_gyro = Math.abs(Gx * (double)(1.0 / count_sec)) +
		    				Math.abs(Gy * (double)(1.0 / count_sec));
		    //System.out.println("Gyro2: "+ tmp_gyro);
		    MockServer.gyro2DataToDisplay.add((Number)tmp_gyro);
			this.aclr_2.add_aclr(Ax, Ay, Az);
			this.gyro_2.add_gyro(Gx, Gy, Gz);
		}

		//there we can send data to the Graph!!!!!
	}
	
	private double sqr(double i) {
		return i*i;
	}

	private double correctMeasurments(double tmp) {
		if(tmp > 32768) {
			tmp -= 65536;
		}
		return tmp;
	}

	private double convertG(double number) {
		return (double) Math.round(((number * 100.0) / (32768/ConfigurationStorage.getG_SCALE()))) / 100;
	}

	private double convertAngSp(double number) {
		return (double) Math.round(((number * 100.0) / (65536 / 500))) / 100;
	}

	public Gyroskope getGyro() {
		return gyro_1;
	}

	public Gyroskope getGyro_2() {
		return gyro_2;
	}

}
