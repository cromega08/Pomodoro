import org.jdom2.JDOMException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;

public class Settings extends JFrame implements ActionListener {

	private final Resources resources;
	private final Body pomodoroWindow;
	private final Check checkStartWork, checkStartBreak, checkStartRest;
	private final Input inputNumberSessions, inputTimeWork, inputTimeBreak, inputTimeRest, inputIntervalRest;
	private final Input[] inputs;
	private final Setting settingNumberSessions, settingStartWork, settingStartBreak, settingStartRest,
			settingTimeWork, settingTimeBreak, settingTimeRest,
				settingIntervalRest;
	private final Apply applyButton;
	private final String[] settingsInputValues, settingsCheckValues;


	public Settings(Resources resourcesInstance, Body mainWindow) throws IOException, JDOMException {

		//? Set: Global Variables

		resources = resourcesInstance;
		settingsInputValues = resources.handlerTools.fileHandler.getTimeValues();
		settingsCheckValues = resources.handlerTools.fileHandler.getStartValues();
		pomodoroWindow = mainWindow;

		//? Create: JCheckBoxes Customized Elements (Check)

		checkStartWork = new Check(this, 0);
		checkStartBreak = new Check(this, 1);
		checkStartRest = new Check(this, 2);

		//? Create: JTextField Customized Elements (Input)

		inputNumberSessions = new Input(0);
		inputTimeWork = new Input(1);
		inputTimeBreak = new Input(2);
		inputTimeRest = new Input(3);
		inputIntervalRest = new Input(4);

		inputs = new Input[]{inputNumberSessions, inputTimeWork, inputTimeBreak, inputTimeRest, inputIntervalRest};

		//? Create: JPanels for Each Setting (Setting)

		//* Time Settings

		settingNumberSessions = new Setting("Number of sessions", inputNumberSessions);
		settingTimeWork = new Setting("Work Time (minutes)", inputTimeWork);
		settingTimeBreak = new Setting("Break Time (minutes)", inputTimeBreak);
		settingTimeRest = new Setting("Rest Time (minutes)", inputTimeRest);

		//* Auto Start Settings

		settingStartWork = new Setting("Auto Start Work?", checkStartWork);
		settingStartBreak = new Setting("Auto Start Break?", checkStartBreak);
		settingStartRest = new Setting("Auto Start Rest?", checkStartRest);

		//* Interval Settings

		settingIntervalRest = new Setting("Rest Interval", inputIntervalRest);

		//* Apply Button

		applyButton = new Apply(this);

		//? Set: JFrame/Window Parameters

		this.setSize(resources.projectDimensions.windowWidth/3, resources.projectDimensions.windowHeight/2);
		this.setResizable(false);
		this.setLocationRelativeTo(pomodoroWindow);
		this.setLayout(new GridLayout(9, 1));
		this.setTitle("Pomodoro - Settings");
		this.getContentPane().setBackground(pomodoroWindow.getContentPane().getBackground());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new SettingsListener());

		this.add(settingNumberSessions);
		this.add(settingTimeWork);
		this.add(settingTimeBreak);
		this.add(settingTimeRest);

		this.add(settingStartWork);
		this.add(settingStartBreak);
		this.add(settingStartRest);

		this.add(settingIntervalRest);

		this.add(applyButton);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == applyButton) {
			boolean[] checks = {checkStartWork.isSelected(),
					checkStartBreak.isSelected(),
					checkStartRest.isSelected()};
			String[] inputs = {inputNumberSessions.localInputField.getText(),
					inputTimeWork.localInputField.getText(),
					inputTimeBreak.localInputField.getText(),
					inputTimeRest.localInputField.getText(),
					inputIntervalRest.localInputField.getText()};

			try {
				resources.handlerTools.fileHandler.setElementValues(checks, inputs);
			} catch (IOException | JDOMException e) {
				throw new RuntimeException(e);
			}

			this.dispose();
		}
	}

	private class SettingsListener extends WindowAdapter {

		public void windowOpened(WindowEvent e) {pomodoroWindow.disableMainWindow();}

		public void windowClosed(WindowEvent e) {pomodoroWindow.enableMainWindow();}
	}

	private class Setting extends JPanel {

		Setting(String label, Component component) {

			//? Set: JPanel parameters

			this.setOpaque(false);
			this.setBorder(resources.colors.restPalette? resources.borders.underlineRestBorder :resources.borders.underlineWorkBorder);
			this.setLayout(new BorderLayout(10, 10));

			this.add(new Label(label), BorderLayout.CENTER);
			this.add(component, BorderLayout.EAST);

			this.setVisible(true);
		}

		private class Label extends JLabel {

			Label(String label) {

				//? Set: JLabel Parameters

				this.setOpaque(false);
				this.setBorder(resources.borders.paddingBorder);
				this.setText(label);
				this.setHorizontalTextPosition(JLabel.LEADING);
				this.setForeground(resources.colors.restPalette? resources.colors.restContrast:resources.colors.workThird);
			}
		}
	}

	private class Check extends JCheckBox {

		Check(ActionListener listener, int indexValue) {

			//? Set: JCheckBox Parameters

			this.setOpaque(false);
			this.setBorder(resources.borders.paddingBorder);
			this.setIcon(resources.icons.uncheckIcon);
			this.setSelectedIcon(resources.icons.checkIcon);
			this.addActionListener(listener);

			this.setSelected(Boolean.parseBoolean(settingsCheckValues[indexValue]));
		}
	}

	private class Input extends JPanel {

		private final LocalInputField localInputField;
		private final int indexValue;

		Input(int index) {

			//? Create: Input Field

			indexValue = index;
			localInputField = new LocalInputField(resources, indexValue, settingsInputValues);

			//? Set: JPanel Parameters

			this.setOpaque(false);
			this.setAlignmentY(JPanel.CENTER_ALIGNMENT);
			this.add(localInputField);
		}

		private class LocalInputField extends InputField {

			LocalInputField(Resources resourcesInstance, int indexValue, String... pairElements) {
				super(resourcesInstance, indexValue, pairElements);
			}

			private void checkInputs() {
				byte valueInput = this.getText().isBlank()? -1:Byte.parseByte(this.getText());
				boolean activate = Arrays.stream(inputs).noneMatch(
						input -> input.localInputField.getText().length() < 1),
						higherThanZero = valueInput > 0;

				if (activate && higherThanZero) {applyButton.enableButton();}
				else {applyButton.disableButton();}
			}

			public void keyReleased(KeyEvent keyEvent) {checkInputs();}
		}
	}

	private class Apply extends JButton {

		Apply(ActionListener listener) {
			this.setFocusable(false);
			this.setBackground(resources.colors.restPalette? resources.colors.restContrast:resources.colors.workContrast);
			this.setBorder(resources.borders.paddingBorder);
			this.setText("Apply Changes");
			this.setForeground(resources.colors.restPalette? resources.colors.restMain:resources.colors.workMain);
			this.addActionListener(listener);
		}

		private void disableButton() {
			this.setEnabled(false);
			this.setBackground(resources.colors.restPalette? resources.colors.restMain:resources.colors.workMain);
			this.setForeground(resources.colors.restPalette? resources.colors.restContrast:resources.colors.workContrast);
		}

		private void enableButton() {
			this.setEnabled(true);
			this.setBackground(resources.colors.restPalette? resources.colors.restContrast:resources.colors.workContrast);
			this.setForeground(resources.colors.restPalette? resources.colors.restMain:resources.colors.workMain);
		}
	}
}
