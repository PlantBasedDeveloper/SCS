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
		} catch (Exception e) {
			System.out.println("Something goes wrong! Thread hasn't started!!!");
		}

	}

	public static void getInstance(MockServer server) {
		if (mathems == null) {
			synchronized (Mathems.class) {
				if (mathems == null) {
					mathems = new Mathems(server);
				}
			}
		}
	}

	public static void main() {		
		MockServer mockServer = MockServer.getInstance();
		Mathems mathems = new Mathems(mockServer);
		
		mathems.add_measurments();
	}

	public void isFall() {
		// function to add new values with changes
		try {
			this.setName("Math_Thread");
			this.start();
		} catch (Exception e) {
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
		while (true) {
			if (this.count_pass_measur <= ConfigurationStorage.getSKIP_MEASURE()) { // increasing of count_pass_measur
																					// in .add method
				 //System.out.print("fkygwyc");

				add_measurments(); // was added to check ""

			} else {

				this.count_pass_measur = 0;

				// if(aclr_1.bufSize() < ConfigurationStorage.getCOUNT_SEC()) //if amount of
				// measurments is less than it is need tocover one second
				// return;

				// System.out.println("IsAclFall");

				this.aclr_1.isAclrFall(this.gyro_1);
				this.aclr_2.isAclrFall(this.gyro_2);

				if (this.isAclrFall && this.isGyroFall) {
					System.out.println("Notify about a Fall!!");
					new Thread(new Runnable() {

						@Override
						public void run() {
							FallNotificationService.notifyFall();
						}
					}).start();

				}

				this.isAclrFall = false;
				this.isGyroFall = false;
			}
		}

	}

	public boolean add_measurments() { //was added to check String str

		long tmptime = System.currentTimeMillis();
		//for the first sensor
		
		String[] measureStrArr = server.getSensorData();
		
		if (measureStrArr == null) return false;

		double Ax = Double.parseDouble(measureStrArr[0]);
		double Ay = Double.parseDouble(measureStrArr[1]);
		double Az = Double.parseDouble(measureStrArr[2]);

		double Gx = Double.parseDouble(measureStrArr[3]);
		double Gy = Double.parseDouble(measureStrArr[4]);
		double Gz = Double.parseDouble(measureStrArr[5]);
		
		double tmp_aclr;
		double tmp_gyro;
		
		int count_sec = ConfigurationStorage.getCOUNT_SEC();


		//aclr
		this.count_pass_measur++;

		 Ax = correctMeasurments(Ax); // Az  1234 5678 9112 3456 7892 1234
		 Ay = correctMeasurments(Ay); // Ay  2108 00AE 011E 0089 00C6 FEB8
		 Az = correctMeasurments(Az); // Ax

		//gyro
		 Gx = correctMeasurments(Gx); // Az
		 Gy = correctMeasurments(Gy); // Ay
		 Gz = correctMeasurments(Gz); // Ax

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

		//there we can send data to the Graph!!!!!
		Ax = Double.parseDouble(measureStrArr[6]);
		Ay = Double.parseDouble(measureStrArr[7]);
		Az = Double.parseDouble(measureStrArr[8]);

		//aclr
		Ax = correctMeasurments(Ax); // Az
		Ay = correctMeasurments(Ay); // Ay
		Az = correctMeasurments(Az); // Ax

		Ax = convertG(Ax);
		Ay = convertG(Ay);
		Az = convertG(Az);

		//TODO: calculate Absolute value and add it to the Queue
		tmp_aclr = Math.sqrt(sqr(Ax) + sqr(Ay) + sqr(Az));
		//System.out.println("ACC2: "+ tmp_aclr);
		MockServer.absAcc2Queue.add((Number)tmp_aclr);

	  //TODO: here I add values to accelerometer graph for gyroscope but now I add the same value that we calculate for Accelerometer this must change
		tmp_gyro = 0;
		if(Math.abs(Gx) > 5 && Math.abs(Gy) > 5)
		tmp_gyro = Math.abs(Gx * (double)(1.0 / count_sec)) +
						Math.abs(Gy * (double)(1.0 / count_sec));

		MockServer.gyro2DataToDisplay.add((Number)tmp_gyro);
		this.aclr_2.add_aclr(Ax, Ay, Az);
		this.gyro_2.add_gyro(Gx, Gy, Gz);
		//there we can send data to the Graph!!!!!

		return true;
	}

	private double correctMeasurments(double tmp) {
		if(tmp > 32768) {
			tmp -= 65536;
		}
		return tmp;
	}
	private double sqr(double i) {
		return i * i;
	}

	//													RANGE / RESOLUTION
	private double convertG(double number) {//WE NORMALIZE THE VALUE AS IT IS TOO SMALL
		return (double) Math.round(((number * 350.0) / (32768 / ConfigurationStorage.getG_SCALE()))) / 100;
	}

	private double convertAngSp(double number) {//ANGULAR SPEED
		return (double) Math.round(((number * 1200.0) / (65536 / 500))) / 100;
	}

	public Gyroskope getGyro() {
		return gyro_1;
	}

	public Gyroskope getGyro_2() {
		return gyro_2;
	}

}
