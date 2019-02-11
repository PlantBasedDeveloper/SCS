package com.mock.connection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.application.mainController;

public class SensorDataReader {

	MockServer server = MockServer.getInstance();
	public FileReader fileReader;
	public BufferedReader bufferedReader;
	public ConcurrentLinkedQueue<String[]> lines = new ConcurrentLinkedQueue<String[]>();
	mainController mController;

	public SensorDataReader() {
		try {
			fileReader = new FileReader(mainController.fileToRead);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String[] readLineFromFile() {
		String[] line = lines.poll();
		return line;
	}
	
	public void addLineFromFile(String[] arrLine) {
		lines.add(arrLine);
	}

	Thread readFileThread = null;
    Runnable readTask = new Runnable() {    	
		@Override
		public void run() {
			mController = new mainController();
			try {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					try {
						line = line.replace(";", "");
						String[] arrLine = line.split(",");
//						System.out.println(arrLine[0] + " " + arrLine[0] + " " + arrLine[1] + " " + arrLine[2] + " "
//								+ arrLine[3] + " " + arrLine[4] + " " + arrLine[5] + " " + arrLine[6] + " " + arrLine[7]
//								+ " " + arrLine[8]);
						addLineFromFile(arrLine);
						Thread.sleep(5);

					} catch (InterruptedException e) {
						break;
					}
				}
				bufferedReader.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	};
	
	public void readFile() {
		readFileThread = new Thread(readTask);
		readFileThread.start();
	}

	public void stopReading() throws IOException {
		if (readFileThread.isAlive()) readFileThread.interrupt();
	}
}
