import org.jdom2.JDOMException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings extends JFrame implements ActionListener, WindowListener {

	//- Value if Download new Icons fos Check 

	private final Resources resources;
	private final Body pomodoroWindow;
	private final Boolean restPalette;
	private final Check checkStartWork, checkStartBreak, checkStartRest;
	private final Input inputNumberSessions, inputTimeWork, inputTimeBreak, inputTimeRest, inputIntervalRest;
	private final Input[] inputs;
	private final Setting settingNumberSessions, settingStartWork, settingStartBreak, settingStartRest,
			settingTimeWork, settingTimeBreak, settingTimeRest,
				settingIntervalRest;
	private final Apply applyButton;
	private final String[] settingsInputValues, settingsCheckValues;

	/*TODO:
	* - Set an attribute to each input element to automatize the setting
	* - Set the values specified in the XML to each Setting
	* */

	public Settings(Resources resource, Body mainWindow) throws IOException, JDOMException {

		//? Set: Global Variables

		resources = resource;
		settingsInputValues = resources.fileHandler.getTimeValues();
		settingsCheckValues = resources.fileHandler.getStartValues();
		pomodoroWindow = mainWindow;
		restPalette = pomodoroWindow.getContentPane().getBackground() == resources.restMain;

		//? Create: JCheckBoxes Customized Elements (Check)

		checkStartWork = new Check(this, 0);
		checkStartBreak = new Check(this, 1);
		checkStartRest = new Check(this, 2);

		//? Create: JTextField Customized Elements (Input)

		inputNumberSessions = new Input(this, 0);
		inputTimeWork = new Input(this, 1);
		inputTimeBreak = new Input(this, 2);
		inputTimeRest = new Input(this, 3);
		inputIntervalRest = new Input(this, 4);

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

		this.setSize(resources.windowWidth/3, resources.windowHeight/2);
		this.setResizable(false);
		this.setLocationRelativeTo(pomodoroWindow);
		this.setLayout(new GridLayout(9, 1));
		this.setTitle("Pomodoro - Settings");
		this.getContentPane().setBackground(pomodoroWindow.getContentPane().getBackground());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
			String[] inputs = {inputNumberSessions.inputField.getText(),
					inputTimeWork.inputField.getText(),
					inputTimeBreak.inputField.getText(),
					inputTimeRest.inputField.getText(),
					inputIntervalRest.inputField.getText()};

			try {
				resources.fileHandler.setElementValues(checks, inputs);
			} catch (IOException | JDOMException e) {
				throw new RuntimeException(e);
			}

			this.dispose();
		}
	}

	@Override
	public void windowOpened(WindowEvent windowEvent) {
		pomodoroWindow.setEnabled(false);
	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {pomodoroWindow.setEnabled(true);}

	@Override
	public void windowClosing(WindowEvent windowEvent) {}
	@Override
	public void windowIconified(WindowEvent windowEvent) {}
	@Override
	public void windowDeiconified(WindowEvent windowEvent) {}
	@Override
	public void windowActivated(WindowEvent windowEvent) {}
	@Override
	public void windowDeactivated(WindowEvent windowEvent) {}

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

		Check(ActionListener listener, int indexValue) {

			//? Set: JCheckBox Parameters

			this.setOpaque(false);
			this.setBorder(resources.paddingBorder);
			this.setIcon(resources.uncheckIcon);
			this.setSelectedIcon(resources.checkIcon);
			this.addActionListener(listener);

			this.setSelected(Boolean.parseBoolean(settingsCheckValues[indexValue]));
		}
	}

	private class Input extends JPanel {

		private final InputField inputField;
		private final int indexValue;

		Input(ActionListener listener, int index) {

			//? Create: Input Field

			indexValue = index;
			inputField = new InputField(listener);

			//? Set: JPanel Parameters

			this.setOpaque(false);
			this.add(inputField);
		}

		private class InputField extends JTextField implements KeyListener {

			InputField(ActionListener listener) {

				this.setOpaque(false);
				this.setColumns(3);
				this.setBorder(restPalette? resources.inputRestDarkBorder:resources.inputWorkDarkBorder);
				this.setCaretColor(restPalette? resources.restThird:resources.workThird);
				this.setText(String.format("%d", Integer.parseInt(settingsInputValues[indexValue])));
				this.setHorizontalAlignment(JTextField.TRAILING);
				this.setForeground(restPalette? resources.restContrast:resources.workContrast);
				this.addActionListener(listener);
				this.addKeyListener(this);
			}

			private void checkInputs() {
				boolean activate = Arrays.stream(inputs).noneMatch(
						input -> input.inputField.getText().length() < 1);

				if (activate) {applyButton.enableButton();}
				else {applyButton.disableButton();}
			}

			@Override
			public void keyTyped(KeyEvent keyEvent) {

				char keyChar = keyEvent.getKeyChar();
				boolean isDelete = checkDelete(keyChar);
				Pattern pattern = Pattern.compile("[^0-9]", Pattern.UNICODE_CASE);
				Matcher matcher = pattern.matcher(String.format("%s", keyChar));

				if ((this.getText().length() >= 2 && !isDelete) || matcher.find()) {
					if (!isDelete) resources.tools.beep();
					keyEvent.consume();
				}
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {}

			@Override
			public void keyReleased(KeyEvent keyEvent) {checkInputs();}
		}

		private boolean checkDelete(char keyChar) {
			return (keyChar == KeyEvent.VK_DELETE || keyChar == KeyEvent.VK_BACK_SPACE);
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

		private void disableButton() {
			this.setEnabled(false);
			this.setBackground(restPalette? resources.restMain:resources.workMain);
			this.setForeground(restPalette? resources.restContrast:resources.workContrast);
		}

		private void enableButton() {
			this.setEnabled(true);
			this.setBackground(restPalette? resources.restContrast:resources.workContrast);
			this.setForeground(restPalette? resources.restMain:resources.workMain);
		}
	}
}
