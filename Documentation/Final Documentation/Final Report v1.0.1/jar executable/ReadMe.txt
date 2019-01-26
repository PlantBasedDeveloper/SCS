

		*************************************
		  Fall Recognition Application v1.0
		*************************************

INTRODUCTION

The Fall Recognition Application v1.0 was made by M.Sc. High  Integrity  Systems students for the
Smart  Sensor  Network Systems course, lead by Prof. Dr.Matthias F.  Wagner  
and  his  associates  Luigi  La  Blunda, Olaf Reich and Kristiyan Balabanov.
Summer Semester 2018

REQUIREMENTS

* The Application requires the LaunchPad to be connected on the "COM7"
* Both SensorTags should be turned on 
* Please, use Command Line to run and shut down the Application.

STEPS TO START

1) Run Project_Group_1.jar throught the Command Line with the command
"java -jar Project_Group_1.jar";
Tipp: use the command "cd /root_to_the_jar_file/".
2) Press button "Scan". Wait until the SensorTags are found;
3) Choose one of them in the droplist "Select a Sensor" and press "Connect". Wait till 
services will be discovered;
4) Choose another one from the list and press "Connect". Wait till 
services will be discovered;
5) Press button "Start"
6) To activate "False Alarm Feature" press "IO Service On"

COMMON ERRORS

* "can not open the port" - on Command Line. There are two
possibilities why it can happen. First, that the LaunchPad is connected
to wrong port. Second, that the LaunchPad is using at the moment. 
* The Application cannot find SensorTags. Make sure that they are turned
on and they are disconnected from other devices.
* The Application draws a lot of data at the begining of work. It means
that previous connection to the Sensors was lost and some data was stacked
on the LaunchPad.  
 


