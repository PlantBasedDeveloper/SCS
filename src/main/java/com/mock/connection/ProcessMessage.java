package com.mock.connection;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang3.ArrayUtils;

import com.application.mainController;
import com.application.math.Mathems;
import com.application.util.FallNotificationService;
import com.google.common.io.BaseEncoding;

public class ProcessMessage extends Thread {
	
	Boolean alive;
	MockServer server = MockServer.getInstance();
	private static int dataLength=0;
	private  int countAcc1=0;
	private  int countAcc2=0;
	private mainController controller ;
	private long timestamp=0;

	public ProcessMessage(boolean value) {
		this.alive=value;
		this.setName("Msg_Thread");
		this.start();
	}
	
	public ProcessMessage(Boolean value,  mainController controller)
	{
		this.controller=controller;
		this.alive=value;
		this.setName("Msg_Thread");
		this.start();
	}
	
	public void run() {
		
		while(alive)
		{
			if(!MockServer.msg.isEmpty())
			{
				getMsg();
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	 public  void getMsg()
		{
			try {
				
				String type= String.format("%02X", MockServer.msg.remove());
				
				
				//check if it  is an event 
				if(type.equals("04"))
				{
					String opCode = String.format("%02X", MockServer.msg.remove());
					// this is always with an event not yet what this is 
					//TODO: maybe we need to define other opcodes 
					if(opCode.equals("FF"))
					{
						dataLength= MockServer.msg.remove();
						String data = getData(dataLength);
						Message msg = new Message(type,opCode,String.format("%02X", dataLength) ,data);
						if(data.startsWith("1B05")) //&&data.length()==26
						{
							if(data.length()<20)
							{
								System.out.println("False Alarm");
								
								FallNotificationService.notifyFalseAlarm();
								
							}
							else
							{
								addToQueue(data);
							}
							
							
						} else {						
							
							System.out.println(msg.toString());
						}
						
						
					}
					else if(opCode.equals("0E"))
					{
						dataLength=MockServer.msg.remove();
						String data = getData(dataLength);
						
						if(data.equals(Commands.RESET.val())) {
							System.out.println(new Message(type,opCode,String.format("%02X", dataLength) ,data).toString());
							System.out.println("Reset complete!!");
							MockServer.STATUS="Ready";
							//mainController.StatusChanger();
							this.alive=false;
						}
					}
				}
				else if(type=="01")
				{					
					//this means is a command not events
				}
								
			}
			catch(Exception x)
			{
				System.out.println(x.getMessage());
			}
		}

	private void addToQueue(String data) {
		Mathems.getInstance(server);	
		
		String realValues = data.substring(16, 40);
		if(data.startsWith("1B050000"))
		{		
			
			this.countAcc1++;
			//Server.acc1.add(Utils.reverseHexString(realValues));
			server.addToSensor1(Utils.reverseHexString(realValues));
			
			//
		}
		else if(data.startsWith("1B050001"))
		{
			this.countAcc2++;
			
			//Server.acc2.add(Utils.reverseHexString(realValues));
			server.addToSensor2(Utils.reverseHexString(realValues));	
			//System.out.println("S2: "+realValues);
		}
		
	}

	private String getData(int length) {
		ByteArrayOutputStream message = new ByteArrayOutputStream();
		while (length>0)
		{
			
			try {				
				message.write(MockServer.msg.remove());
			}
			catch(Exception x)
			{
				System.out.println(x.getStackTrace());
			}
		length--;
		}
		
		return BaseEncoding.base16().encode(message.toByteArray());
	}


}
