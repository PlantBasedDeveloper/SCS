package com.application.math;

import java.util.ArrayList;

import com.application.util.ConfigurationStorage;

public class Accelerometer {

	private ArrayList<Double> buf_x;
	private ArrayList<Double> buf_y;
	private ArrayList<Double> buf_z;
	private Mathems math;
	private double IMPACT_POW;
	private int IMPACT_PASS;
	private double LAYING_POW;
	private double FORCE_IMPACT_D;
	private double FORCE_IMPACT_U;

	public Accelerometer(Mathems math)  {
		buf_x = new ArrayList<Double>();
		buf_y = new ArrayList<Double>();
		buf_z = new ArrayList<Double>();
		this.math = math;
		this.IMPACT_PASS = 0;
		this.IMPACT_POW = 0;
		this.LAYING_POW = 0;
		this.FORCE_IMPACT_D = 0;
		this.FORCE_IMPACT_U = 0;
	}
	
	public Accelerometer(Accelerometer copy)
	{
		this.buf_x=new ArrayList<Double>(copy.buf_x);
		this.buf_y=new ArrayList<Double>(copy.buf_y);
		this.buf_z=new ArrayList<Double>(copy.buf_z);
		this.math=copy.math;
		
	}

	public void isAclrFall(Gyroskope gyro)  {

		Accelerometer tmp_aclr = new Accelerometer(this);
		Gyroskope tmp_gyro = new Gyroskope(gyro);
		//System.out.println("IsAclFall");

		this.IMPACT_PASS = ConfigurationStorage.getIMPACT_PASS();
		this.IMPACT_POW = ConfigurationStorage.getIMPACT_POW();
		this.LAYING_POW = ConfigurationStorage.getLAYING_POW();

		for(int i = 10; i < tmp_aclr.buf_x.size(); i++) { //first loop goes through all numbers in array

			double impact = Math.sqrt( sqr(tmp_aclr.buf_x.get(i))
									 + sqr(tmp_aclr.buf_y.get(i))
									 + sqr(tmp_aclr.buf_z.get(i))); // got an impact from fall need to be sure

			//System.out.println("In loop" + i + "impact = " + impact);

			if(impact > IMPACT_POW) {
				//System.out.println("Impact");
				if(isLaying(i, tmp_aclr)){
					tmp_gyro.isGyroFall(getFallStart(i, tmp_aclr), i);// double check for fall from Gyro
				}
			}
		}
	}

	public int getFallStart(int impact, Accelerometer tmp_aclr) {

		//System.out.println("getFallStart impact  = " + impact);
		
		

		this.FORCE_IMPACT_D = ConfigurationStorage.getFORCE_IMPACT_D();
		this.FORCE_IMPACT_U = ConfigurationStorage.getFORCE_IMPACT_U();

		for(int i = impact - 5; i >= 0; i--) {

			//System.out.println("getFallStart loop i = " + i);

			double force_impact = Math.abs(tmp_aclr.buf_z.get(i));

			//System.out.println("force_impact = " + force_impact + " original: " + tmp_aclr.buf_z.get(i));
			
			while(force_impact > 0.8 && force_impact < 1.3)
			{
				return i;
			}
			
//			if(force_impact < FORCE_IMPACT_U && force_impact > FORCE_IMPACT_D) {
//				//System.out.println("Return i");
//				return i;
//			}
		}
	 //System.out.println("getFallStart is not correct");
		return 0;
	}

	public void add_aclr(double x, double y, double z) {

		if(!this.buf_x.isEmpty()) {
			if (this.buf_x.size() > ConfigurationStorage.getSKIP_MEASURE() + 10) {
				this.buf_x.remove(0);
				this.buf_y.remove(0);
				this.buf_z.remove(0);
			}
		}

		this.buf_x.add(x);
		this.buf_y.add(y);
		this.buf_z.add(z);

	}

	public int bufSize() {
		return this.buf_x.size();
	}

	private boolean isLaying(int i, Accelerometer tmp_aclr) {

		//System.out.println("IsLLaying, i = " + i);
		boolean fall = false;
		for(int j = i + IMPACT_PASS; j < tmp_aclr.buf_x.size(); j++) { // to be sure that there is a fall we pass half of fall time

			//System.out.println("isLaying loop j = " + j);
			double laying = Math.sqrt( sqr(tmp_aclr.buf_x.get(j)) + sqr(tmp_aclr.buf_y.get(j)));

			if(laying > LAYING_POW) {
				math.isAclrFall = true;
				System.out.println("Aclr detected a Fall!!!");
				return true;
			}
		}
		
		return false;
	}

	private double sqr(double i) {
		return i*i;
	}
}
