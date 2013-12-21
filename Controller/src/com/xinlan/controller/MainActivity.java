package com.xinlan.controller;

import com.xinlan.controller.logic.IActionDo;
import com.xinlan.controller.logic.net.NetWork;
import com.xinlan.controller.ui.ControllerView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
	private ControllerView touchPad;
	private NetWork mNetWork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		touchPad = (ControllerView)findViewById(R.id.touchPad);
		mNetWork = new NetWork(this);
		mNetWork.connectServer("192.168.0.100");
		
		touchPad.setActionDo(new TouchAction());
		// new MyThread().start();
	}
	
	private final class TouchAction implements IActionDo{
		@Override
		public void send(int direction) {
			mNetWork.sendMessage(direction+"客户端来的-");
		}
	}//end inner class

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mNetWork != null) {
			mNetWork.clear();
		}
	}

	// private class MyThread extends Thread{
	// @Override
	// public void run() {
	// try {
	// Socket socket = new Socket("192.168.0.100",8888);
	// OutputStream out = socket.getOutputStream();
	// out.write("客户端发来的信息哦!><!".getBytes());
	// out.close();
	// socket.close();
	// } catch (UnknownHostException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }//end inner class
}// end class
