package com.tcp.socket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
	private static final int TIMEOUT = 5000;
	private static final int MAXNUM =5;
	public static void main(String[] args) throws IOException {
		String strSend = "Hello World!";
		byte[] buf = new byte[1024];
		DatagramSocket ds = new DatagramSocket(9000);
		InetAddress loc = InetAddress.getLocalHost();
		DatagramPacket dpSend = new DatagramPacket(strSend.getBytes(),strSend.length(),loc,3000);
		DatagramPacket dpReceive = new DatagramPacket(buf,1014);
		ds.setSoTimeout(TIMEOUT);
		int tries = 0;
		boolean receivedResponse =false;
		while(!receivedResponse&&tries<MAXNUM){
			ds.send(dpSend);
			try {
				ds.receive(dpReceive);
				if(!dpReceive.getAddress().equals(loc)){
					throw new IOException("Received packet from an umkonwn source");
				}
				receivedResponse = true;
			} catch (InterruptedIOException  e) {
				tries +=1;
				System.out.println("Time out," + (MAXNUM - tries) + " more tries...");
			}
		}
		
		if(receivedResponse){
			System.out.println("client received data from server：");  
			String strReceive = new String(dpReceive.getData(),0,dpReceive.getLength()) +   
            " from " + dpReceive.getAddress().getHostAddress() + ":" + dpReceive.getPort();  
			 System.out.println(strReceive);  
	            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，  
	            //所以这里要将dp_receive的内部消息长度重新置为1024  
	            dpReceive.setLength(1024);  
		}else{
			System.out.println("No response -- give up.");  
        }  
        ds.close();  
	}
}
