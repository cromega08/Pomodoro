import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener {

	private final Session session;
	private final Clock clock;
	private final Controls controls;
	private final Font startFont;
	private final Font newSmallFont;
	private final Font newBigFont;
	private final Resources resources;

	public Main(Resources resource) {

		//? Set: Global Variables

		resources = resource;

		//? Set: Local Font

		startFont = getFont();
		newSmallFont = startFont.deriveFont((float) resources.windowHeight/40);
		newBigFont = startFont.deriveFont((float) resources.windowHeight/6);

		//? Create: UI Elements

		session = new Session();
		clock = new Clock();
		controls = new Controls(this);

		//? Set: JPanel parameters

		this.setLayout(new BorderLayout());
		this.setOpaque(false);

		this.add(session, BorderLayout.NORTH);
		this.add(clock, BorderLayout.CENTER);
		this.add(controls, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == controls.start) {

		}

		if (event.getSource() == controls.end) {

		}

		if (event.getSource() == controls.state) {

		}
	}

	private class Session extends JPanel {

		private final SessionPanel working;
		private final SessionPanel breakTime;
		private final SessionPanel restTime;

		Session() {

			//? Create: JTextField Elements (State of the Session)

			working = new SessionPanel("Work");
			breakTime = new SessionPanel("Break");
			restTime = new SessionPanel("Rest");

			//? Set: JPanel Parameters

			this.setLayout(new GridLayout(1,3, 10, 0));
			this.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
			this.setOpaque(false);

			this.add(working);
			this.add(breakTime);
			this.add(restTime);
		}

		private class SessionPanel extends JTextField {

			SessionPanel(String text) {
				this.setEditable(false);
				this.setOpaque(false);
				this.setBorder(resources.lineWorkDarkBorder);
				this.setText(text);
				this.setFont(newSmallFont);
				this.setHorizontalAlignment(JTextField.CENTER);
				this.setForeground(resources.workSecond);
			}
		}
	}

	private class Clock extends JTextField {

		private final ClockText period;
		private final ClockText time;

		/* TODO:
		* - Value the possibility of making an Text Field for the periods count and the user
		* 	change the number of pomodoro through the interface
		* */

		Clock() {

			//? Create: JTextField Elements (Info About Current Session)

			period = new ClockText("0/0",
					newSmallFont.deriveFont((float) newSmallFont.getSize() + 30),
					resources.workThird);
			time = new ClockText("00:00", newBigFont, resources.workContrast);

			//? Set: JPanel Parameters

			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setBorder(resources.emptyBorder);

			this.add(period, BorderLayout.NORTH);
			this.add(time, BorderLayout.CENTER);
		}

		private class ClockText extends JTextField {

			ClockText(String text, Font newFont, Color foreground) {

				this.setEditable(false);
				this.setOpaque(false);
				this.setBorder(resources.emptyBorder);
				this.setText(text);
				this.setFont(newFont);
				this.setHorizontalAlignment(JTextField.CENTER);
				this.setForeground(foreground);
			}
		}
	}

	private class Controls extends JPanel {

		private final ControlsButton start;
		private final ControlsButton end;
		private final ControlsButton state;
		private final ActionListener listener;

		Controls(ActionListener listen) {

			//? Set: Global Variables

			listener = listen;

			//? Create: JButton Elements (Actions Related to Session)

			start = new ControlsButton("Start");
			end = new ControlsButton("End");
			state = new ControlsButton("Pause");

			//? Set: JPanel Parameters

			this.setLayout(new FlowLayout(FlowLayout.CENTER));
			this.setOpaque(false);

			this.add(start);
		}

		private class ControlsButton extends JButton {

			ControlsButton(String text) {
				this.setBackground(resources.workContrast);
				this.setBorder(resources.paddingBorder);
				this.setFocusable(false);
				this.setText(text);
				this.setFont(newSmallFont);
				this.setForeground(resources.workMain);
				this.addActionListener(listener);
			}
		}
	}
}
