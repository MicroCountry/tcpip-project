package com.tcp.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
	public static void main(String[] args) throws IOException {
		String strSend="Hello UDPClient";
		byte[] buf = new byte[1024];
		DatagramSocket ds = new DatagramSocket(3000);
		DatagramPacket dpReceive = new DatagramPacket(buf,1014);
		boolean f = true;  
		while(f){
			ds.receive(dpReceive);
			System.out.println("server received data from client：");  
            String str_receive = new String(dpReceive.getData(),0,dpReceive.getLength()) +   
                    " from " + dpReceive.getAddress().getHostAddress() + ":" + dpReceive.getPort();  
            System.out.println(str_receive);  
            //数据发动到客户端的3000端口  
            DatagramPacket dp_send= new DatagramPacket(strSend.getBytes(),strSend.length(),dpReceive.getAddress(),9000);  
            ds.send(dp_send);  
            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，  
            //所以这里要将dp_receive的内部消息长度重新置为1024  
            dpReceive.setLength(1024);  
        }  
		ds.close();
	}
}
