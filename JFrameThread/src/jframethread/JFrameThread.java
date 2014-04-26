package jframethread;

import javax.swing.JFrame;


public class JFrameThread {
	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.add(new MakeThread());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setVisible(true);

	}

}
