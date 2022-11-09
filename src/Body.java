import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class Body extends JFrame {

	private Header navBar;
	private Main main;
	private Footer footer;
	private Margins leftMargin, rightMargin;
	private Resources resources;

	public Body(Resources resource) {

		//? Set: Global Variables

		resources = resource;

		//? Create: UI Elements

		navBar = new Header(resources);
		main = new Main(resources);
		footer = new Footer(resources, this);
		leftMargin = new Margins();
		rightMargin = new Margins();

		//? Set: JFrame/Window Parameters

		this.setSize(resources.windowWidth, resources.windowHeight);
		this.setMinimumSize(resources.minimumDimension);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setTitle("Pomodoro");
		this.getContentPane().setBackground(resources.workMain);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(navBar, BorderLayout.NORTH);
		this.add(rightMargin, BorderLayout.EAST);
		this.add(footer, BorderLayout.SOUTH);
		this.add(leftMargin, BorderLayout.WEST);
		this.add(main, BorderLayout.CENTER);

		this.setVisible(true);
	}

	private class Margins extends JPanel {

		public Margins() {

			//? Set: JPanel Parameters

			this.setPreferredSize(resources.verticalMarginDimension);
			this.setOpaque(false);
		}
	}
	
}
