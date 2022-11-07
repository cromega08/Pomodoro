import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;

public class Main extends JPanel {

	private Session session;
	private Clock clock;
	private Controls controls;
	private Font startFont, newSmallFont, newBigFont;
	
	public Main(Resources resources) {

		//? Set: Local Font Resources

		startFont = getFont();
		newSmallFont = startFont.deriveFont((float) resources.windowHeight/40);
		newBigFont = startFont.deriveFont((float) resources.windowHeight/6);

		//? Create: Main Sections

		session = new Session(resources);
		clock = new Clock(resources);
		controls = new Controls(resources);

		//? Set: JPanel parameters

		this.setLayout(new BorderLayout());
		this.setOpaque(false);

		this.add(session, BorderLayout.NORTH);
		this.add(clock, BorderLayout.CENTER);
		this.add(controls, BorderLayout.SOUTH);
	}

	private class Clock extends JTextField {

		private JTextField period, time;
	
		Clock(Resources resources) {

			period = new JTextField();
			period.setEditable(false);
			period.setOpaque(false);
			period.setBorder(resources.emptyBorder);
			period.setText("#0");
			period.setFont(newSmallFont.deriveFont((float) newSmallFont.getSize() + 30));
			period.setHorizontalAlignment(JTextField.CENTER);
			period.setForeground(resources.workThird);
			
			time = new JTextField();
			time.setEditable(false);
			time.setOpaque(false);
			time.setBorder(resources.emptyBorder);
			time.setText("25:00");
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

		Controls(Resources resources) {

			start = new JButton();
			start.setBackground(resources.workContrast);
			start.setBorder(resources.paddingBorder);
			start.setText("Start");
			start.setFont(newSmallFont);
			start.setForeground(resources.workMain);

			end = new JButton();
			end.setBackground(resources.workSecond);
			end.setBorder(resources.paddingBorder);
			end.setText("End");
			end.setFont(newSmallFont);
			end.setForeground(resources.workMain);

			state = new JButton();
			state.setBackground(resources.workThird);
			state.setBorder(resources.paddingBorder);
			state.setText("Pause");
			state.setFont(newSmallFont);
			state.setForeground(resources.workSecond);

			this.setLayout(new FlowLayout(FlowLayout.CENTER));
			this.setOpaque(false);

			this.add(start);
		}
	}

	private class Session extends JPanel {

		private JTextField working, breakTime, restTime;
		private Border border;

		Session(Resources resources) {

			border = BorderFactory.createCompoundBorder(new LineBorder(resources.workSecond, 2), resources.paddingBorder);

			working = new JTextField();
			working.setEditable(false);
			working.setOpaque(false);
			working.setBorder(border);
			working.setText("Work");
			working.setFont(newSmallFont);
			working.setHorizontalAlignment(JTextField.CENTER);
			working.setForeground(resources.workSecond);

			breakTime = new JTextField();
			breakTime.setEditable(false);
			breakTime.setOpaque(false);
			breakTime.setBorder(border);
			breakTime.setText("Break");
			breakTime.setFont(newSmallFont);
			breakTime.setHorizontalAlignment(JTextField.CENTER);
			breakTime.setForeground(resources.workSecond);

			restTime = new JTextField();
			restTime.setEditable(false);
			restTime.setOpaque(false);
			restTime.setBorder(border);
			restTime.setText("Rest");
			restTime.setFont(newSmallFont);
			restTime.setHorizontalAlignment(JTextField.CENTER);
			restTime.setForeground(resources.workSecond);

			this.setLayout(new GridLayout(1,3, 10, 0));
			this.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
			this.setOpaque(false);
			
			this.add(working);
			this.add(breakTime);
			this.add(restTime);
		}
	}
}
