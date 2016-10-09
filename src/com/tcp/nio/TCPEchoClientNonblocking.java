package com.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonblocking {
	public static void main(String[] args) throws IOException {
		args = new String[]{"127.0.0.1","heiheiheihei"};
		if((args.length<2)||(args.length>3)){
			throw new IllegalArgumentException("args is invalid");
		}
		String server = args[0];
		byte[] argument = args[1].getBytes();
		int servPort =(args.length == 3) ? Integer.parseInt(args[2]):7;
		SocketChannel clntChannel = SocketChannel.open();
		clntChannel.configureBlocking(false);
		if(!clntChannel.connect(new InetSocketAddress(server,servPort))){
			while(!clntChannel.finishConnect()){
				System.out.print(".");
			}
		}
		System.out.print("\n");
		ByteBuffer writeBuf = ByteBuffer.wrap(argument);
		ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
		int totalByteRcvd =0 ;
		int bytesRvd;
		while(totalByteRcvd<argument.length){
			if(writeBuf.hasRemaining()){
				clntChannel.write(writeBuf);
			}
			if((bytesRvd=clntChannel.read(readBuf))==-1){
				throw new SocketException("Connection closed prematurely");
			}
			totalByteRcvd += bytesRvd;
			System.out.print(".");
		}
		System.out.println("Receivd: "+new String (readBuf.array(),0,totalByteRcvd));
		clntChannel.close();
	}
}
