import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;

public class Footer extends JPanel {

	private UserOptions userOptions;
	private Credits credits;

	public Footer(Resources resources) {

		userOptions = new UserOptions(resources);
		credits = new Credits(resources);

		//? Set: JPanel Parameters

		this.setPreferredSize(resources.horizontalMarginDimension);
		this.setLayout(new GridLayout(2, 1));
		this.setOpaque(false);

		this.add(userOptions);
		this.add(credits);
	}

	private class UserOptions extends JPanel {

		private JButton help, settings;

		UserOptions(Resources resources) {

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

			this.setOpaque(false);
			this.setLayout(new FlowLayout(FlowLayout.CENTER));

			this.add(help);
			this.add(settings);
		}
	}

	private class Credits extends JLabel {

		private Font newFont;

		Credits(Resources resources) {

			newFont = getFont().deriveFont((float) resources.windowHeight/50);

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
