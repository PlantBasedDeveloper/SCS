package com.mock.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SensorDataReader extends Thread {
	
	public ArrayList<String[]> data;
	
	public SensorDataReader() {
		data = new ArrayList<String[]>();
	}
	
	public ArrayList<File> readFilesFromFolder(File folder) {
		ArrayList<File> files = new ArrayList<File>();
		
		for (final File file : folder.listFiles()) {
	        if (file.isDirectory()) {
	        	files.addAll(readFilesFromFolder(file));
	        	
	        } else {
	            files.add(file);
	        }
	    }
		
		return files;
	}
	
	public void readFile(File file) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					String[] arrLine = line.split(",");
					data.add(arrLine);
					Thread.sleep(5);
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
