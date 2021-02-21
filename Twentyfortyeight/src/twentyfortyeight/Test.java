package twentyfortyeight;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JPanel {

	public Test() {
		this.add(new Grid());
		this.setVisible(true);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new Test());
		frame.setVisible(true);
	}
}
