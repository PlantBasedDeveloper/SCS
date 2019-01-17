package com.application.util;

import com.application.mainController;

/**
 * This class handles the notifications triggered by the detection of a fall.
 * On start it idles while waiting for a trigger. When the fall detection
 * algorithm thinks the user has fallen, it alerts this class which turns on and off
 * the buzzer on the Sensortags, the flags in the GUI and sends email accordingly.
 *
 * The class is runnable and one instance is expected to always be available at runtime.
 * To insure that, at class load time an instance of the class is created and started.
 * This instance can then be reached by means of static methods. This allows callers
 * to reach instance methods without actually needing a reference to the instance itself.
 *
 * @author Raul Bertone
 */
public class FallNotificationService implements Runnable{

	private static FallNotificationService instance;
	private static boolean fallDetected = false; // true if a fall has been detected but help not yet requested.
	private boolean run = true; // set to false to stop the thread
	private static boolean falseAlarm = false; // true if the user signaled a false alarm by pressing a button on the Sensortag
	private static mainController controller; // reference to the GUI controller of the main screen
	private boolean fallHappened = false;
	private static boolean waitingForFeedback = false;

	/*
	 * At class load time, an instance of this class is created and started.
	 */
	static{
		instance = new FallNotificationService();
		Thread trd = new Thread(instance);
		trd.setName("FallNotification");
		trd.start();

    }

	/*
	 * Static methods
	 */

	// call this method when a fall is detected
	public static void notifyFall() {
		if (waitingForFeedback) {
			return;
		}
		
		fallDetected = true;
		instance.wakeUp();
	}

	// call this method to stop the service. It is meant to be called just before turning the application off.
	public static void stop() {
		instance.run = false;
	}

	// call this method to signal a false alarm (when the user presses a button on the Sensortag)
	public static void notifyFalseAlarm() {
		falseAlarm = true;
		controller.setLblFallDetColor("#000000");
		if (waitingForFeedback) {
			return;
		}
		
		instance.wakeUp();
	}

	// call this method at startup to provide a reference to the GUI controller
	public static void setMain(mainController controller) {
		FallNotificationService.controller = controller;
	}

	/*
	 * Instance methods
	 */
	@Override
	public void run() {

		// main thread loop
		while(run) {
			waitForFall();
		}
	}

	private synchronized void waitForFall() {

		// wait on this until either notifyFall() or notifyFalseAlarm() methods are called
		while (!fallDetected && !falseAlarm) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if(falseAlarm) {
			// TODO turn off the buzzer
			instance.controller.setLblFallDetColor("#000000"); // lower Fall flag in the GUI
			instance.controller.setLblHelpReqColor("#000000"); // lower Help flag in the GUI

			if (fallHappened) {
				String message = " says it was a false alarm, everything's fine";
				String subject = "False Alarm";
				sendMail(message, subject);
				fallHappened = false;
			}
			
			falseAlarm = false;
			return;
		}

		// to prevent random button presses to incorrectly "pre-declare" a fall as a false alarm
		falseAlarm = false;

		// TODO turn on the buzzer
		instance.controller.setLblFallDetColor("#a20000"); // raise flag in the GUI

		// wait to see if the user signals a false alarm
		waitingForFeedback = true;
		try {
			Thread.sleep(ConfigurationStorage.getHELP_REQUEST_DELAY());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (falseAlarm) {
			// TODO turn off the buzzer
			instance.controller.setLblFallDetColor("#000000"); // lower the flag in the GUI
			falseAlarm = false;
			waitingForFeedback = false;
			fallDetected = false;
		} else {
			fallHappened = true;

			String message = " has fallen and requires assistance";
			String subject = "Help! I have fallen!";

			sendMail(message, subject); // send email
			instance.controller.setLblHelpReqColor("#a20000"); // raise flag in the GUI

			fallDetected = false;
			waitingForFeedback = false;
		}
	}

	private synchronized void wakeUp() {
		notify();
	}

	// helper method that prepares the email and sends it
	private void sendMail(String message, String subject) {
		String messageBody = ConfigurationStorage.getFIRST_NAME() + " " + ConfigurationStorage.getLAST_NAME() + " living at " + ConfigurationStorage.getADDRESS() + message;
		String toAddress = ConfigurationStorage.getCONTACT_PERSON_EMAIL();

		try {
			SendMail.send(messageBody, subject, toAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
