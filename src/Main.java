import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener {

	private Session session;
	private Clock clock;
	private Controls controls;
	private Font startFont, newSmallFont, newBigFont;
	private Resources resources;

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

		private JTextField working, breakTime, restTime;

		Session() {

			//? Create: JTextField Elements (State of the Session)

			working = new JTextField();
			working.setEditable(false);
			working.setOpaque(false);
			working.setBorder(resources.lineWorkDarkBorder);
			working.setText("Work");
			working.setFont(newSmallFont);
			working.setHorizontalAlignment(JTextField.CENTER);
			working.setForeground(resources.workSecond);

			breakTime = new JTextField();
			breakTime.setEditable(false);
			breakTime.setOpaque(false);
			breakTime.setBorder(resources.lineWorkDarkBorder);
			breakTime.setText("Break");
			breakTime.setFont(newSmallFont);
			breakTime.setHorizontalAlignment(JTextField.CENTER);
			breakTime.setForeground(resources.workSecond);

			restTime = new JTextField();
			restTime.setEditable(false);
			restTime.setOpaque(false);
			restTime.setBorder(resources.lineWorkDarkBorder);
			restTime.setText("Rest");
			restTime.setFont(newSmallFont);
			restTime.setHorizontalAlignment(JTextField.CENTER);
			restTime.setForeground(resources.workSecond);

			//? Set: JPanel Parameters

			this.setLayout(new GridLayout(1,3, 10, 0));
			this.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
			this.setOpaque(false);

			this.add(working);
			this.add(breakTime);
			this.add(restTime);
		}
	}

	private class Clock extends JTextField {

		private JTextField period, time;

		Clock() {

			//? Create: JTextField Elements (Info About Current Session)

			period = new JTextField();
			period.setEditable(false);
			period.setOpaque(false);
			period.setBorder(resources.emptyBorder);
			period.setText("0/0");
			period.setFont(newSmallFont.deriveFont((float) newSmallFont.getSize() + 30));
			period.setHorizontalAlignment(JTextField.CENTER);
			period.setForeground(resources.workThird);

			time = new JTextField();
			time.setEditable(false);
			time.setOpaque(false);
			time.setBorder(resources.emptyBorder);
			time.setText("00:00");
			time.setFont(newBigFont);
			time.setHorizontalAlignment(JTextField.CENTER);
			time.setForeground(resources.workContrast);

			//? Set: JPanel Parameters

			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setBorder(resources.emptyBorder);

			this.add(period, BorderLayout.NORTH);
			this.add(time, BorderLayout.CENTER);
		}
	}

	private class Controls extends JPanel {

		private JButton start, end, state;

		Controls(ActionListener listener) {

			//? Create: JButton Elements (Actions Related to Session)

			start = new JButton();
			start.setBackground(resources.workContrast);
			start.setBorder(resources.paddingBorder);
			start.setText("Start");
			start.setFont(newSmallFont);
			start.setForeground(resources.workMain);
			start.addActionListener(listener);

			end = new JButton();
			end.setBackground(resources.workSecond);
			end.setBorder(resources.paddingBorder);
			end.setText("End");
			end.setFont(newSmallFont);
			end.setForeground(resources.workMain);
			end.addActionListener(listener);

			state = new JButton();
			state.setBackground(resources.workThird);
			state.setBorder(resources.paddingBorder);
			state.setText("Pause");
			state.setFont(newSmallFont);
			state.setForeground(resources.workSecond);
			state.addActionListener(listener);

			//? Set: JPanel Parameters

			this.setLayout(new FlowLayout(FlowLayout.CENTER));
			this.setOpaque(false);

			this.add(start);
		}
	}
}
