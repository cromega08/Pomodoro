import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

public class Footer extends JPanel implements ActionListener {

	private UserOptions userOptions;
	private Credits credits;
	private JFrame pomodoroWindow;
	private Resources resources;

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

		private JButton help, settings;

		UserOptions(ActionListener listener) {

			//? Create: JButtons

			help = new JButton();
			help.setIcon(resources.helpIcon);
			help.setFocusable(false);
			help.setBorder(resources.paddingBorder);
			help.setBackground(resources.workThird);

			settings = new JButton();
			settings.setIcon(resources.settingsIcon);
			settings.setFocusable(false);
			settings.setBorder(resources.paddingBorder);
			settings.setBackground(resources.workThird);
			settings.addActionListener(listener);

			//? Set: JPanel Parameters

			this.setOpaque(false);
			this.setLayout(new FlowLayout(FlowLayout.CENTER));

			this.add(help);
			this.add(settings);
		}
	}

	private class Credits extends JLabel {

		private Font newFont;

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
