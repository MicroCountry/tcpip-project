package com.tcp.framer;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DelimFramer implements Framer {

	private InputStream in;
	private static final byte DELIMITER = '\n';
	@Override
	public void frameMsg(byte[] message, OutputStream out) throws IOException {
		for(byte b  :message){
			if(b==DELIMITER){
				throw new IOException("Message contains delimiter");
			}
		}
		out.write(message);
		out.write(DELIMITER);
		out.flush();
	}

	@Override
	public byte[] nextMsg() throws IOException {
		ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
		int nextByte;
		while((nextByte=in.read())!=DELIMITER){
			if(nextByte==-1){
				if(messageBuffer.size()==0){
					return null;
				}else{
					throw new EOFException("Non-empty");
				}
			}
			messageBuffer.write(nextByte);
		}
		return messageBuffer.toByteArray();
	}

}
