import org.jdom2.JDOMException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;

public class Footer extends JPanel implements ActionListener, PaletteSetters {

	private final UserOptions userOptions;
	private final Credits credits;
	private final Body pomodoroWindow;
	private final Resources resources;

	public Footer(Resources resourcesInstance, Body window) {

		//? Set: Global Variables

		resources = resourcesInstance;
		pomodoroWindow = window;

		//? Create: UI Elements

		userOptions = new UserOptions(this);
		credits = new Credits();

		//? Set: JPanel Parameters

		this.setPreferredSize(resources.projectDimensions.horizontalMarginDimension);
		this.setLayout(new GridLayout(2, 1));
		this.setOpaque(false);

		this.add(userOptions);
		this.add(credits);
	}

	public void enableInput() {userOptions.enableInput();}
	public void disableInput() {
		userOptions.disableInput();
	}

	@Override
	public void setWorkPalette() {
		userOptions.setWorkPalette();
		credits.setWorkPalette();
	}

	@Override
	public void setRestPalette() {
		userOptions.setRestPalette();
		credits.setRestPalette();
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
	}

	private class UserOptions extends JPanel implements PaletteSetters {

		private final Option settings;
		private final ActionListener listener;

		UserOptions(ActionListener listen) {

			//? Set: Global Variables

			listener = listen;

			//? Create: JButtons

			settings = new Option(resources.icons.settingsIcon);

			//? Set: JPanel Parameters

			this.setOpaque(false);
			this.setLayout(new FlowLayout(FlowLayout.CENTER));

			this.add(settings);
		}

		@Override
		public void setWorkPalette() {
			settings.setWorkPalette();
		}

		@Override
		public void setRestPalette() {
			settings.setRestPalette();
		}

		public void enableInput() {
			settings.setEnabled(true);
			this.setEnabled(true);
		}

		public void disableInput() {
			settings.setEnabled(false);
			this.setEnabled(false);
		}

		private class Option extends JButton implements PaletteSetters {

			Option(ImageIcon icon) {
				this.setIcon(icon);
				this.setFocusable(false);
				this.setBorder(resources.borders.paddingBorder);
				this.setBackground(resources.colors.workThird);
				this.addActionListener(listener);
			}

			@Override
			public void setWorkPalette() {
				this.setBackground(resources.colors.workThird);
			}

			@Override
			public void setRestPalette() {
				this.setBackground(resources.colors.restThird);
			}
		}
	}

	private class Credits extends JLabel implements PaletteSetters {

		Credits() {

			//? Set: Local Font

			Font newFont = getFont().deriveFont((float) resources.projectDimensions.windowHeight / 50);

			//? Set: JLabel Parameters

			this.setOpaque(false);
			this.setText("Made by");
			this.setFont(newFont);
			this.setForeground(resources.colors.workSecond);
			this.setIcon(resources.icons.logoIcon);
			this.setHorizontalTextPosition(JLabel.LEFT);
			this.setVerticalAlignment(JLabel.CENTER);
			this.setHorizontalAlignment(JLabel.CENTER);

			this.addMouseListener(new SelectedListener());
		}

		@Override
		public void setWorkPalette() {
			this.setForeground(resources.colors.workSecond);
		}

		@Override
		public void setRestPalette() {
			this.setForeground(resources.colors.restSecond);
		}

		private class SelectedListener extends MouseAdapter {

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
