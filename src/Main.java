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

	public Main(Resources resourcesInstance) {

		//? Set: Global Variables

		resources = resourcesInstance;

		//? Set: Local Font

		startFont = getFont();
		newSmallFont = startFont.deriveFont((float) resources.projectDimensions.windowHeight/40);
		newBigFont = startFont.deriveFont((float) resources.projectDimensions.windowHeight/6);

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
			this.setAlignmentY(JPanel.CENTER_ALIGNMENT);
			this.setOpaque(false);

			this.add(working);
			this.add(breakTime);
			this.add(restTime);
		}

		private class SessionPanel extends JTextField {

			SessionPanel(String text) {
				this.setEditable(false);
				this.setOpaque(false);
				this.setBorder(resources.borders.lineWorkBorder);
				this.setText(text);
				this.setFont(newSmallFont);
				this.setHorizontalAlignment(JTextField.CENTER);
				this.setForeground(resources.colors.workSecond);
			}
		}
	}

	private class Clock extends JTextField {

		private final ClockSessions sessions;
		private final ClockText time;

		Clock() {

			//? Create: JTextField Elements (Info About Current Session)

			sessions = new ClockSessions();
			time = new ClockText();

			//? Set: JPanel Parameters

			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setBorder(resources.borders.emptyBorder);

			this.add(sessions, BorderLayout.NORTH);
			this.add(time, BorderLayout.CENTER);
		}

		private class ClockText extends JTextField {

			ClockText() {

				//? Set: JTextField parameters

				this.setEditable(false);
				this.setOpaque(false);
				this.setBorder(resources.borders.emptyBorder);
				this.setFont(newBigFont);
				this.setHorizontalAlignment(JTextField.CENTER);
				this.setForeground(resources.colors.workContrast);

				newTime(resources.handlerTools.fileHandler.getElementValue("time-work"));
			}

			private void newTime(String min) {
				this.setText(String.format("%s:00", min));
			}

			private void newTime(String min, String sec) {
				this.setText(String.format("%1$s:%2$s", min, sec));
			}
		}

		private class ClockSessions extends JPanel {

			private final Font localFont;
			private final SessionsText sessionsText, separator;
			private final InputField totalSessions;

			ClockSessions() {

				//? Set: Global Variables of this class

				localFont = newSmallFont.deriveFont((float) newSmallFont.getSize() + 30);

				//? Create: JTextField Elements

				sessionsText = new SessionsText("0");
				separator = new SessionsText("/");
				totalSessions = new InputField(resources, "time-sessions", localFont);

				//? Set: JPanel Parameters

				this.setLayout(new FlowLayout(FlowLayout.TRAILING));
				this.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
				this.setOpaque(false);

				this.add(sessionsText);
				this.add(separator);
				this.add(totalSessions);
			}

			private class SessionsText extends JTextField {

				SessionsText(String text) {
					this.setEditable(false);
					this.setOpaque(false);
					this.setBorder(resources.borders.emptyBorder);
					this.setText(text);
					this.setFont(localFont);
					this.setHorizontalAlignment(JTextField.CENTER);
					this.setForeground(resources.colors.workThird);
				}
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
				this.setBackground(resources.colors.workContrast);
				this.setBorder(resources.borders.paddingBorder);
				this.setFocusable(false);
				this.setText(text);
				this.setFont(newSmallFont);
				this.setForeground(resources.colors.workMain);
				this.addActionListener(listener);
			}
		}
	}
}
