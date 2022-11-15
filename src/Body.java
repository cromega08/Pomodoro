import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

/* TODO:
*  - Implements the change of color for each period and state
* */

public class Body extends JFrame {

	private final Header navBar;
	private final Main main;
	private final Footer footer;
	private final Margins leftMargin;
	private final Margins rightMargin;
	private final Resources resources;

	public Body(Resources resourcesInstance) {

		//? Set: Global Variables

		resources = resourcesInstance;

		//? Create: UI Elements

		navBar = new Header(resources);
		main = new Main(resources);
		footer = new Footer(resources, this);
		leftMargin = new Margins();
		rightMargin = new Margins();

		//? Set: JFrame/Window Parameters

		this.setSize(resources.projectDimensions.windowWidth, resources.projectDimensions.windowHeight);
		this.setMinimumSize(resources.projectDimensions.minimumDimension);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setTitle("Pomodoro");
		this.getContentPane().setBackground(resources.colors.workMain);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(navBar, BorderLayout.NORTH);
		this.add(rightMargin, BorderLayout.EAST);
		this.add(footer, BorderLayout.SOUTH);
		this.add(leftMargin, BorderLayout.WEST);
		this.add(main, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void disableMainWindow() {this.setEnabled(false);}

	public void enableMainWindow() {this.setEnabled(true);}

	private class Margins extends JPanel {

		public Margins() {

			//? Set: JPanel Parameters

			this.setPreferredSize(resources.projectDimensions.verticalMarginDimension);
			this.setOpaque(false);
		}
	}
	
}
