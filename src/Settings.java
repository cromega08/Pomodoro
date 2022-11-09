import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;

public class Settings extends JFrame implements ActionListener {

	//- Value if Download new Icons fos Check 

	private Resources resources;
	private Boolean restPalette;
	private Check checkStartPomodoro, checkStartBreak, checkStartRest;
	private Input inputTimePomodoro, inputTimeBreak, inputTimeRest, inputIntervalRest;
	private Setting settingStartPomodoro, settingStartBreak, settingStartRest,
				settingTimePomodoro, settingTimeBreak, settingTimeRest,
				settingIntervalRest;

	public Settings(Resources resource, JFrame pomodoroWindow) {

		//? Set: Global Variables

		resources = resource;
		restPalette = pomodoroWindow.getContentPane().getBackground() == resources.restMain? true:false;

		//? Create: JCheckBoxes Customized Elements (Check)

		checkStartPomodoro = new Check(this);
		checkStartBreak = new Check(this);
		checkStartRest = new Check(this);

		//? Create: JTextField Customized Elements (Input)

		inputTimePomodoro = new Input(this);
		inputTimeBreak = new Input(this);
		inputTimeRest = new Input(this);
		inputIntervalRest = new Input(this);

		//? Create: JPanels for Each Setting (Setting)

		//* Time Settings

		settingTimePomodoro = new Setting("Pomodoro Time (minutes)", inputTimePomodoro);
		settingTimeBreak = new Setting("Break Time (minutes)", inputTimeBreak);
		settingTimeRest = new Setting("Rest Time (minutes)", inputTimeRest);

		//* Auto Start Settings

		settingStartPomodoro = new Setting("Auto Start Pomodoro?", checkStartPomodoro);
		settingStartBreak = new Setting("Auto Start Break?", checkStartBreak);
		settingStartRest = new Setting("Auto Start Rest?", checkStartRest);

		//* Interval Settings

		settingIntervalRest = new Setting("Rest Interval", inputIntervalRest);

		//? Set: JFrame/Window Parameters

		this.setSize(resources.windowWidth/3, resources.windowHeight/2);
		this.setResizable(false);
		this.setLocationRelativeTo(pomodoroWindow);
		this.setLayout(new GridLayout(8, 1));
		this.setTitle("Pomodoro - Settings");
		this.getContentPane().setBackground(pomodoroWindow.getContentPane().getBackground());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.add(settingTimePomodoro);
		this.add(settingTimeBreak);
		this.add(settingTimeRest);

		this.add(settingStartPomodoro);
		this.add(settingStartBreak);
		this.add(settingStartRest);

		this.add(settingIntervalRest);

		this.add(new Apply(this));

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	private class Setting extends JPanel {

		Setting(String label, Component component) {

			//? Set: JPanel parameters

			this.setOpaque(false);
			this.setBorder(restPalette? resources.underlineRestLightBorder:resources.underlineWorkLightBorder);
			this.setLayout(new BorderLayout(10, 10));

			this.add(new Label(label), BorderLayout.CENTER);
			this.add(component, BorderLayout.EAST);

			this.setVisible(true);
		}

		private class Label extends JLabel {

			Label(String label) {

				//? Set: JLabel Parameters

				this.setOpaque(false);
				this.setBorder(resources.paddingBorder);
				this.setText(label);
				this.setHorizontalTextPosition(JLabel.LEADING);
				this.setForeground(restPalette? resources.restContrast:resources.workThird);
			}
		}
	}

	private class Check extends JCheckBox {

		Check(ActionListener listener) {

			//? Set: JCheckBox Parameters

			this.setOpaque(false);
			this.setBorder(resources.paddingBorder);
			this.setIcon(resources.uncheckIcon);
			this.setSelectedIcon(resources.checkIcon);
			this.addActionListener(listener);

			this.setSelected(true);
		}
	}

	private class Input extends JPanel {

		Input(ActionListener listener) {

			//? Set: JPanel Parameters

			this.setOpaque(false);
			this.add(new InputField(listener));
		}

		private class InputField extends JTextField {

			InputField(ActionListener listener) {

				this.setOpaque(false);
				this.setBorder(restPalette? resources.inputRestDarkBorder:resources.inputWorkDarkBorder);
				this.setCaretColor(restPalette? resources.restThird:resources.workThird);
				this.setText("test");
				this.setForeground(restPalette? resources.restContrast:resources.workContrast);
				this.addActionListener(listener);
			}
		}
	}

	private class Apply extends JButton {

		Apply(ActionListener listener) {
			this.setFocusable(false);
			this.setBackground(restPalette? resources.restContrast:resources.workContrast);
			this.setBorder(resources.paddingBorder);
			this.setText("Apply Changes");
			this.setForeground(restPalette? resources.restMain:resources.workMain);
			this.addActionListener(listener);
		}
	}
}
