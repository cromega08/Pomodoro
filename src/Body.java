import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class Body extends JFrame {

	private Header navBar;
	private Main main;
	private Footer footer;
	private JPanel west, east;
	
	public Body(Resources resources) {

		navBar = new Header(resources);
		main = new Main(resources);
		footer = new Footer(resources);

		east = new JPanel();
		east.setPreferredSize(resources.verticalMarginDimension);
		east.setBackground(resources.workMain);
		west = new JPanel();
		west.setPreferredSize(resources.verticalMarginDimension);
		west.setBackground(resources.workMain);

		this.setSize(resources.windowWidth, resources.windowHeight);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setTitle("Pomodoro");
		this.getContentPane().setBackground(resources.workMain);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(navBar, BorderLayout.NORTH);
		this.add(east, BorderLayout.EAST);
		this.add(footer, BorderLayout.SOUTH);
		this.add(west, BorderLayout.WEST);
		this.add(main, BorderLayout.CENTER);

		this.setVisible(true);
	}
}
