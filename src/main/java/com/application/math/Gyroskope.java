package com.application.math;

import java.util.ArrayList;

import com.application.util.ConfigurationStorage;


public class Gyroskope{

	private ArrayList<Double> buf_x;
	private ArrayList<Double> buf_y;
	private ArrayList<Double> buf_z;
	private Mathems math;
	private int count_sec;
	private double FALL_ANGLE;

	public Gyroskope(Mathems math)  {
		buf_x = new ArrayList<Double>();
		buf_y = new ArrayList<Double>();
		buf_z = new ArrayList<Double>();
		this.math = math;
		this.count_sec = 0;
		this.FALL_ANGLE = 0;
	}
	
	public Gyroskope (Gyroskope copy)
	{
		this.buf_x=new ArrayList<Double>(copy.buf_x);
		this.buf_y=new ArrayList<Double>(copy.buf_y);
		this.buf_z=new ArrayList<Double>(copy.buf_z);
		this.math=copy.math;
		
	}

	public void isGyroFall(int fallStart, int fallStop) {

		this.count_sec = ConfigurationStorage.getCOUNT_SEC();
		this.FALL_ANGLE = ConfigurationStorage.getFALL_ANGLE();

		if(fallStart == -1) {

			//System.out.println("Error!!! Incorrect FallStart value!!! " + fallStart);
			return;
		}


		double angle = isFall(fallStart, fallStop);

		if( angle >= -this.FALL_ANGLE && angle <= this.FALL_ANGLE) {
			math.isGyroFall = true;
			System.out.println("Gyro detected a Fall!");
			return;
		}

	}

	private double isFall(int fallStart, int fallStop) {
		double xAngle = 0;
		double yAngle = 0;
		
		//System.out.println("Im in Gyro, start:" + fallStart + " stop: " + fallStop);
		
		

		for(int i = fallStart; i < fallStop; i++ ) {
			if(Math.abs(this.buf_x.get(i)) > 5) {
				xAngle += Math.abs(this.buf_x.get(i) * (double)(1.0 / count_sec));
				//System.out.println("New angle x: " + xAngle + " i:" + i);	
			}

			if(Math.abs(this.buf_y.get(i)) > 5) {
				yAngle += Math.abs(this.buf_y.get(i) * (double)(1.0 / count_sec));
				//System.out.println("New angle y: " + yAngle + " i:" + i);	
			}
		}

		System.out.println((xAngle + " " + yAngle));

		return (xAngle + yAngle) % 180 - 90;
	}

	public void add_gyro(double x, double y, double z) {
		if(!this.buf_x.isEmpty()) {
			if( this.buf_x.size() > ConfigurationStorage.getSKIP_MEASURE() + 10) {
				this.buf_x.remove(0);
				this.buf_y.remove(0);
				this.buf_z.remove(0);
			}
		}

		this.buf_x.add(x);
		this.buf_y.add(y);
		this.buf_z.add(z);

	}

}
