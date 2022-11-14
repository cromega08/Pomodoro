import org.jdom2.JDOMException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;

public class Footer extends JPanel implements ActionListener {

	private final UserOptions userOptions;
	private final Credits credits;
	private final Body pomodoroWindow;
	private final Resources resources;

	public Footer(Resources resource, Body window) {

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
			try {
				new Settings(resources, pomodoroWindow);
			} catch (IOException | JDOMException e) {
				throw new RuntimeException(e);
			}
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

		/* TODO:
		* - Implement the Redirect to my github
		* */

		Credits() {

			//? Set: Local Font

			Font newFont = getFont().deriveFont((float) resources.windowHeight / 50);

			//? Set: JLabel Parameters

			this.setOpaque(false);
			this.setText("Made by");
			this.setFont(newFont);
			this.setForeground(resources.workSecond);
			this.setIcon(resources.logoIcon);
			this.setHorizontalTextPosition(JLabel.LEFT);
			this.setVerticalAlignment(JLabel.CENTER);
			this.setHorizontalAlignment(JLabel.CENTER);

			this.addMouseListener(new MouseListen());
		}

		private class MouseListen extends MouseAdapter {

			public void mousePressed(MouseEvent event) {
				try {
					openWebPage();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			private void openWebPage() throws IOException {

				URI uri = URI.create("https://github.com/Cromega08");
				Desktop desktop = Desktop.isDesktopSupported()? Desktop.getDesktop():null;

				System.out.println(uri);

				if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {desktop.browse(uri);}
				else {
					JOptionPane.showMessageDialog(
							pomodoroWindow,
							String.format("Error opening author Github: \"%s\"", uri),
							"Error Opening Link",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
