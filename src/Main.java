import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener, PaletteSetters {

	/* TODO:
	 * - Handle the problem with buttons
	 * - Handle disable of inputs
	 * - handle the pauses
	 * */

	private final Resources resources;
	private final Chronos chronos;
	private final Body pomodoroWindow;
	private final Session session;
	private final Clock clock;
	private final Controls controls;
	private final Font startFont, newSmallFont, newBigFont;

	public Main(Resources resourcesInstance, Body window) {

		//? Set: Global Variables

		resources = resourcesInstance;
		chronos = new Chronos(resources, this);
		pomodoroWindow = window;

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
			chronos.startChronometer(clock.getTotalSessions());
		}

		if (event.getSource() == controls.end) {
			chronos.stopChronometer();
			restartTime();
		}

		if (event.getSource() == controls.state) {
			String currentState = controls.state.getText();
			if (currentState.equals("PAUSE")) {
				controls.state.setText("CONTINUE");
				chronos.pauseChronometer();
			}
			else {
				controls.state.setText("PAUSE");
				chronos.restartChronometer();
			}
		}
	}

	public void restartTime() {clock.setDefaultTime();}

	public void newTime(byte min, byte sec) {clock.newTime(min, sec);}

	private void enableStart() {controls.enableStart();}

	private void disableStart() {controls.disableStart();}

	public void endButtons() {controls.endButtons();}

	public void startButtons() {controls.startButtons();}

	public void callWorkPalette() {pomodoroWindow.setWorkPalette();}

	public void callRestPalette() {pomodoroWindow.setRestPalette();}

	public void callEnableInput() {pomodoroWindow.enableInput();}

	public void callDisableInput() {pomodoroWindow.disableInput();}

	@Override
	public void setWorkPalette() {
		session.setWorkPalette();
		clock.setWorkPalette();
		controls.setWorkPalette();
	}

	@Override
	public void setRestPalette() {
		session.setRestPalette();
		clock.setRestPalette();
		controls.setRestPalette();
	}

	public void enableInput() {
		clock.enableInput();
	}

	public void disableInput() {
		clock.disableInput();
	}

	private class Session extends JPanel implements PaletteSetters {

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

		@Override
		public void setWorkPalette() {
			working.setBorder(resources.borders.lineWorkBorder);
			working.setForeground(resources.colors.workSecond);

			breakTime.setBorder(resources.borders.lineWorkBorder);
			breakTime.setForeground(resources.colors.workSecond);

			restTime.setBorder(resources.borders.lineWorkBorder);
			restTime.setForeground(resources.colors.workSecond);
		}

		@Override
		public void setRestPalette() {
			working.setBorder(resources.borders.lineRestBorder);
			working.setForeground(resources.colors.restSecond);

			breakTime.setBorder(resources.borders.lineRestBorder);
			breakTime.setForeground(resources.colors.restSecond);

			restTime.setBorder(resources.borders.lineRestBorder);
			restTime.setForeground(resources.colors.restSecond);
		}

		private class SessionPanel extends JTextField implements PaletteSetters {

			SessionPanel(String text) {
				this.setEditable(false);
				this.setOpaque(false);
				this.setBorder(resources.borders.lineWorkBorder);
				this.setText(text);
				this.setFont(newSmallFont);
				this.setHorizontalAlignment(JTextField.CENTER);
				this.setForeground(resources.colors.workSecond);
			}

			@Override
			public void setWorkPalette() {
				this.setBorder(resources.borders.lineWorkBorder);
				this.setForeground(resources.colors.workSecond);
			}

			@Override
			public void setRestPalette() {
				this.setBorder(resources.borders.lineRestBorder);
				this.setForeground(resources.colors.restSecond);
			}
		}
	}

	private class Clock extends JTextField implements PaletteSetters {

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

		@Override
		public void setWorkPalette() {
			sessions.setWorkPalette();
			time.setWorkPalette();
		}

		@Override
		public void setRestPalette() {
			sessions.setRestPalette();
			time.setRestPalette();
		}

		public String getTotalSessions() {
			return sessions.getSessions();
		}

		public void setDefaultTime() {time.setDefaultTime();}

		public void newTime(byte min, byte sec) {
			time.newTime(min, sec);
		}

		public void enableInput() {
			sessions.enableInput();
		}

		public void disableInput() {
			sessions.disableInput();
		}

		private class ClockText extends JTextField implements PaletteSetters {

			ClockText() {

				//? Set: JTextField parameters

				this.setEditable(false);
				this.setOpaque(false);
				this.setBorder(resources.borders.emptyBorder);
				this.setFont(newBigFont);
				this.setHorizontalAlignment(JTextField.CENTER);
				this.setForeground(resources.colors.workContrast);

				setDefaultTime();
			}

			@Override
			public void setWorkPalette() {
				this.setForeground(resources.colors.workContrast);
			}

			@Override
			public void setRestPalette() {
				this.setForeground(resources.colors.restContrast);
			}

			public void setDefaultTime() {newTime(resources.handlerTools.fileHandler.getElementValue("time-work"));}

			public void newTime(String min) {
				this.setText(String.format("%02d:00", Integer.parseInt(min)));
			}

			public void newTime(byte min, byte sec) {
				this.setText(String.format("%1$02d:%2$02d", min, sec));
			}
		}

		private class ClockSessions extends JPanel implements PaletteSetters {

			private final Font localFont;
			private final CurrentSessionText currentSessionText, separator;
			private final TotalSessionsText totalSessions;

			ClockSessions() {

				//? Set: Global Variables of this class

				localFont = newSmallFont.deriveFont((float) newSmallFont.getSize() + 30);

				//? Create: JTextField Elements

				currentSessionText = new CurrentSessionText("0");
				separator = new CurrentSessionText("/");
				totalSessions = new TotalSessionsText(resources, "time-sessions", localFont);

				//? Set: JPanel Parameters

				this.setLayout(new FlowLayout(FlowLayout.CENTER));
				this.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
				this.setOpaque(false);

				this.add(currentSessionText);
				this.add(separator);
				this.add(totalSessions);
			}

			@Override
			public void setWorkPalette() {
				currentSessionText.setWorkPalette();
				separator.setWorkPalette();
				totalSessions.setWorkPalette();
			}

			@Override
			public void setRestPalette() {
				currentSessionText.setRestPalette();
				separator.setRestPalette();
				totalSessions.setRestPalette();
			}

			public String getSessions() {
				return totalSessions.getText();
			}

			public void enableInput() {
				totalSessions.enableInput();
			}

			public void disableInput() {
				totalSessions.disableInput();
			}

			private class CurrentSessionText extends JTextField implements PaletteSetters {

				CurrentSessionText(String text) {
					this.setEditable(false);
					this.setOpaque(false);
					this.setBorder(resources.borders.emptyBorder);
					this.setText(text);
					this.setFont(localFont);
					this.setHorizontalAlignment(JTextField.CENTER);
					this.setForeground(resources.colors.workThird);
				}

				@Override
				public void setWorkPalette() {
					this.setForeground(resources.colors.workThird);
				}

				@Override
				public void setRestPalette() {
					this.setForeground(resources.colors.restThird);
				}
			}

			private class TotalSessionsText extends InputField implements PaletteSetters {

				TotalSessionsText(Resources resources, String element, Font font) {
					super(resources, element, font);
				}

				private void checkInput() {
					byte valueInput = this.getText().isBlank()? -1:Byte.valueOf(this.getText());
					boolean higherThanZero = valueInput > 0;

					if (higherThanZero) {enableStart();}
					else {disableStart();}
				}

				@Override
				public void keyReleased(KeyEvent keyEvent) {checkInput();}
			}
		}
	}

	private class Controls extends JPanel implements PaletteSetters {

		private final ControlsButton start;
		private final ControlsButton end;
		private final ControlsButton state;
		private final ActionListener listener;

		Controls(ActionListener listen) {

			//? Set: Global Variables

			listener = listen;

			//? Create: JButton Elements (Actions Related to Session)

			start = new ControlsButton("START");
			end = new ControlsButton("END");
			state = new ControlsButton("PAUSE");

			//? Set: JPanel Parameters

			this.setLayout(new FlowLayout(FlowLayout.CENTER));
			this.setOpaque(false);

			this.add(start);
		}

		@Override
		public void setWorkPalette() {
			start.setWorkPalette();
			end.setWorkPalette();
			state.setWorkPalette();
		}

		@Override
		public void setRestPalette() {
			start.setRestPalette();
			end.setRestPalette();
			state.setRestPalette();
		}

		public void enableStart() {
			start.setEnabled(true);
			this.setEnabled(true);
		}

		public void disableStart() {
			start.setEnabled(false);
			this.setEnabled(false);
		}

		public void startButtons() {
			this.remove(state);
			this.remove(end);
			this.add(start);

			state.setText("PAUSE");

			this.repaint();
		}

		public void endButtons() {
			this.remove(start);
			this.add(state);
			this.add(end);

			state.setText("PAUSE");

			this.repaint();
		}

		private class ControlsButton extends JButton implements PaletteSetters {

			ControlsButton(String text) {
				this.setBackground(resources.colors.workContrast);
				this.setBorder(resources.borders.paddingBorder);
				this.setFocusable(false);
				this.setText(text);
				this.setFont(newSmallFont);
				this.setForeground(resources.colors.workMain);
				this.addActionListener(listener);
			}

			@Override
			public void setWorkPalette() {
				this.setBackground(resources.colors.workContrast);
				this.setForeground(resources.colors.workMain);
			}

			@Override
			public void setRestPalette() {
				this.setBackground(resources.colors.restContrast);
				this.setForeground(resources.colors.restMain);
			}
		}
	}
}
