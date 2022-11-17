import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

/* TODO:
*  - Make the controller for disable or enable elements
* */

public class Body extends JFrame implements PaletteSetters {

	private final Header header;
	private final Main main;
	private final Footer footer;
	private final Margins leftMargin;
	private final Margins rightMargin;
	private final Resources resources;

	public Body(Resources resourcesInstance) {

		//? Set: Global Variables

		resources = resourcesInstance;

		//? Create: UI Elements

		header = new Header(resources);
		main = new Main(resources, this);
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

		this.add(header, BorderLayout.NORTH);
		this.add(rightMargin, BorderLayout.EAST);
		this.add(footer, BorderLayout.SOUTH);
		this.add(leftMargin, BorderLayout.WEST);
		this.add(main, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void enableInput() {
		main.enableInput();
		footer.enableInput();
	}

	public void disableInput() {
		main.disableInput();
		footer.disableInput();
	}

	public void disableMainWindow() {this.setEnabled(false);}

	public void enableMainWindow() {this.setEnabled(true);}

	@Override
	public void setWorkPalette() {
		this.getContentPane().setBackground(resources.colors.workMain);
		header.setWorkPalette();
		main.setWorkPalette();
		footer.setWorkPalette();
	}

	@Override
	public void setRestPalette() {
		this.getContentPane().setBackground(resources.colors.restMain);
		header.setRestPalette();
		main.setRestPalette();
		footer.setRestPalette();
	}

	private class Margins extends JPanel {

		public Margins() {

			//? Set: JPanel Parameters

			this.setPreferredSize(resources.projectDimensions.verticalMarginDimension);
			this.setOpaque(false);
		}
	}
	
}
