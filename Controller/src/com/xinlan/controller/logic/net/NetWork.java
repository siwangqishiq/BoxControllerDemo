package com.xinlan.controller.logic.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;

public class NetWork {
	private Context mContext;
	private Queue<String> messageQueue;
	private boolean isRun=true;
	private OutputStream outStream;
	private Socket mSocket;
	
	public NetWork(Context context){
		mContext = context;
		messageQueue = new LinkedList<String>();
	}
	
	public void connectServer(String serverHost){
		new MainServiceThread(serverHost).start();
	}
	
	private final class MainServiceThread extends Thread{
		private String server;
		public MainServiceThread(String serverHost){
			this.server = serverHost;
		}
		@Override
		public void run() {
			try {
				mSocket = new Socket(server,8888);
				int temp=0;
				if(mSocket.isConnected()|| temp==0){
					temp=1;
					System.out.println("与服务端建立连接....");
					outStream= mSocket.getOutputStream();
					while(isRun){
						String msg = messageQueue.poll();
						if(msg!=null){
							System.out.println("客户端发送数据包--->"+msg);
							outStream.write(msg.getBytes());
						}
					}//end while
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}//end inner class
	
	public synchronized void sendMessage(String msg){
		messageQueue.add(msg);
	}
	
	public void clear(){
		isRun = false;
		if(outStream!=null){
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(mSocket!=null){
			try {
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}//end class
