import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Footer extends JPanel implements ActionListener {

	private final UserOptions userOptions;
	private final Credits credits;
	private final JFrame pomodoroWindow;
	private final Resources resources;

	public Footer(Resources resource, JFrame window) {

		//? Set: Global Variables

		resources = resource;
		pomodoroWindow = window;

		//? Create: UI Elements

		userOptions = new UserOptions(this);
		credits = new Credits();

		//? Set: JPanel Parameters

		this.setPreferredSize(resources.horizontalMarginDimension);
		this.setLayout(new GridLayout(2, 1));
		this.setOpaque(false);

		this.add(userOptions);
		this.add(credits);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == userOptions.settings) {
			new Settings(resources, pomodoroWindow);
		}
		
		if (event.getSource() == userOptions.help) {

		}
	}

	private class UserOptions extends JPanel {

		private final JButton help;
		private final JButton settings;
		private final ActionListener listener;

		UserOptions(ActionListener listen) {

			//? Set: Global Variables

			listener = listen;

			//? Create: JButtons

			help = new Option(resources.helpIcon);
			settings = new Option(resources.settingsIcon);

			//? Set: JPanel Parameters

			this.setOpaque(false);
			this.setLayout(new FlowLayout(FlowLayout.CENTER));

			this.add(help);
			this.add(settings);
		}

		private class Option extends JButton {

			Option(ImageIcon icon) {
				this.setIcon(icon);
				this.setFocusable(false);
				this.setBorder(resources.paddingBorder);
				this.setBackground(resources.workThird);
				this.addActionListener(listener);
			}
		}
	}

	private class Credits extends JLabel {

		private final Font newFont;

		Credits() {

			//? Set: Local Font

			newFont = getFont().deriveFont((float) resources.windowHeight/50);

			//? Set: JLabel Parameters

			this.setOpaque(false);
			this.setText("Made by");
			this.setFont(newFont);
			this.setForeground(resources.workSecond);
			this.setIcon(resources.logoIcon);
			this.setHorizontalTextPosition(JLabel.LEFT);
			this.setVerticalAlignment(JLabel.CENTER);
			this.setHorizontalAlignment(JLabel.CENTER);
		}
	}
}
