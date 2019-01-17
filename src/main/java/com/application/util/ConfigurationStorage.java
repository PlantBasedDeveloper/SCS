package com.application.util;

/**
 * The class provides static access to configuration and user data.
 *
 * @author Raul Bertone
 */
public abstract class ConfigurationStorage {
	// user's data
	private static String FIRST_NAME = "John";
	private static String LAST_NAME = "Doh";
	private static String BIRTH_DATE = "03/03/1978";
	private static String GENDER = "M";
	private static String ADDRESS = "Some Street 5, Onecity";
	private static String PHONE_NUMBER = "555-123456789";
	private static String BLOOD_TYPE = "AB-";

	// helper's data
	private static String CONTACT_PERSON_NAME = "Good Samaritan";
	private static String CONTACT_PERSON_EMAIL = "bert1.raul@gmail.com";

	// fall detection algorithm's settings
	private static double IMPACT_POW = 2; // Value of Impact(g)
	private static int IMPACT_PASS = 2; // Measurements after Impact(count)
	private static double LAYING_POW = 0.8; // double, Laying Acceleration(g)
	private static int SKIP_MEASURE = 20; // Skipped Measurements(count)
	private static double FALL_ANGLE = 30.0; // Fall angle(degrees)
	private static int COUNT_SEC = 20; // amount of measurements per second
	private static double FORCE_IMPACT_D = 0.8; // lower and upper limit for force of laying
	private static double FORCE_IMPACT_U = 1.3; //
	private static double G_SCALE = 4; // scale of the Accelerometer

	// help request settings
	private static int HELP_REQUEST_DELAY = 10 * 1000;

	
	public static String getBIRTH_DATE() {
		return BIRTH_DATE;
	}

	public static void setBIRTH_DATE(String bIRTH_DATE) {
		BIRTH_DATE = bIRTH_DATE;
	}
	
	public static String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public static void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public static String getLAST_NAME() {
		return LAST_NAME;
	}

	public static void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public static String getGENDER() {
		return GENDER;
	}

	public static void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public static String getADDRESS() {
		return ADDRESS;
	}

	public static void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public static String getPHONE_NUMBER() {
		return PHONE_NUMBER;
	}

	public static void setPHONE_NUMBER(String pHONE_NUMBER) {
		PHONE_NUMBER = pHONE_NUMBER;
	}

	public static String getBLOOD_TYPE() {
		return BLOOD_TYPE;
	}

	public static void setBLOOD_TYPE(String bLOOD_TYPE) {
		BLOOD_TYPE = bLOOD_TYPE;
	}

	public static String getCONTACT_PERSON_NAME() {
		return CONTACT_PERSON_NAME;
	}

	public static void setCONTACT_PERSON_NAME(String cONTACT_PERSON_NAME) {
		CONTACT_PERSON_NAME = cONTACT_PERSON_NAME;
	}

	public static String getCONTACT_PERSON_EMAIL() {
		return CONTACT_PERSON_EMAIL;
	}

	public static void setCONTACT_PERSON_EMAIL(String cONTACT_PERSON_EMAIL) {
		CONTACT_PERSON_EMAIL = cONTACT_PERSON_EMAIL;
	}

	public static double getIMPACT_POW() {
		return IMPACT_POW;
	}

	public static void setIMPACT_POW(double iMPACT_POW) {
		IMPACT_POW = iMPACT_POW;
	}

	public static int getIMPACT_PASS() {
		return IMPACT_PASS;
	}

	public static void setIMPACT_PASS(int iMPACT_PASS) {
		IMPACT_PASS = iMPACT_PASS;
	}

	public static double getLAYING_POW() {
		return LAYING_POW;
	}

	public static void setLAYING_POW(double lAYING_POW) {
		LAYING_POW = lAYING_POW;
	}

	public static int getSKIP_MEASURE() {
		return SKIP_MEASURE;
	}

	public static void setSKIP_MEASURE(int sKIP_MEASURE) {
		SKIP_MEASURE = sKIP_MEASURE;
	}

	public static double getFALL_ANGLE() {
		return FALL_ANGLE;
	}

	public static void setFALL_ANGLE(double fALL_ANGLE) {
		FALL_ANGLE = fALL_ANGLE;
	}

	public static int getCOUNT_SEC() {
		return COUNT_SEC;
	}

	public static void setCOUNT_SEC(int cOUNT_SEC) {
		COUNT_SEC = cOUNT_SEC;
	}

	public static double getFORCE_IMPACT_D() {
		return FORCE_IMPACT_D;
	}

	public static void setFORCE_IMPACT_D(double fORCE_IMPACT_D) {
		FORCE_IMPACT_D = fORCE_IMPACT_D;
	}

	public static double getFORCE_IMPACT_U() {
		return FORCE_IMPACT_U;
	}

	public static void setFORCE_IMPACT_U(double fORCE_IMPACT_U) {
		FORCE_IMPACT_U = fORCE_IMPACT_U;
	}

	//G_SCALE

	public static double getG_SCALE() {
		return G_SCALE;
	}

	public static void setG_SCALE(double g_SCALE) {
		G_SCALE = g_SCALE;
	}


	public static int getHELP_REQUEST_DELAY() {
		return HELP_REQUEST_DELAY;
	}

	public static void setHELP_REQUEST_DELAY(int hELP_REQUEST_DELAY) {
		HELP_REQUEST_DELAY = hELP_REQUEST_DELAY;
	}

}