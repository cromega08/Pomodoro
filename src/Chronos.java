import javazoom.jl.decoder.JavaLayerException;
import org.jdom2.IllegalDataException;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Chronos {

	/* TODO:
	* - Handle the problem with buttons
	* - Handle the disable of inputs
	* - handle the pauses
	* */

	private final Resources resources;
	private final Main mainPanel;
	private final short DELAY, SECOND;

	private Timer timer;
	private Chronometer chronometer;
	private int totalSessions, restingSessions;
	private boolean paused, startWork, startBreak, startRest;

	public Chronos(Resources resourcesInstance, Main content) {

		resources = resourcesInstance;
		mainPanel = content;
		DELAY = 50;
		SECOND = 1000;
		paused = false;
		startWork = false;
		startBreak = false;
		startRest = false;
	}

	public void startChronometer(String numberOfSessions) {
		timer = new Timer();
		totalSessions = Byte.parseByte(numberOfSessions);
		restingSessions = totalSessions;
		chronometer = new Chronometer();
		chronometer.startTimer();
	}

	public void pauseChronometer() {paused = true;}

	public void restartChronometer() {paused = false;}

	public void stopChronometer() throws LineUnavailableException, IOException, JavaLayerException {chronometer.stopTimer();}

	public void nextSession() throws LineUnavailableException, IOException, JavaLayerException {chronometer.changeSession();}

	private class Chronometer extends TimerTask {

		private byte totalTime, secondsInMinute, untilRest;
		private final byte restInterval;
		private final long timeOfExec;
		private LocalTime sessionTime;
		private String currentSession;

		Chronometer() {
			restInterval = Byte.parseByte(resources.handlerTools.fileHandler.getElementValue("time-interval"));
			untilRest = restInterval;
			secondsInMinute = 2;
			timeOfExec = calculateExec();
		}

		public void run() {
			if (paused) return;
			sessionTime = sessionTime.minusSeconds(1);
			--secondsInMinute;
			if (secondsInMinute <= 0) {--totalTime; secondsInMinute = 60;}
			if (totalTime < 0) {
				try {changeSession();}
				catch (LineUnavailableException | IOException | JavaLayerException e) {throw new RuntimeException(e);}
				return;
			}
			mainPanel.newTime(totalTime, (byte) (secondsInMinute - 1));
		}

		private long calculateExec() {
			Instant execStart, execEnd;
			sessionTime = LocalTime.now();
			currentSession = "break";
			totalTime = 3;

			execStart = Instant.now();
			run();
			execEnd = Instant.now();

			return Duration.between(execStart, execEnd).toMillis();
		}

		private void changeSession() throws LineUnavailableException, IOException, JavaLayerException {

			if (restingSessions <= 0) {stopTimer(); return;}

			secondsInMinute = 1;

			switch (currentSession) {
				case "work" -> {
					if (untilRest <= 0) {setRest(); untilRest = restInterval;}
					else {setBreak();}
				}
				case "break", "rest" -> setWork();
				default -> throw new IllegalDataException(String.format("\"currentSession\" variable have an illegal value %s", currentSession));
			}

			mainPanel.setCurrentSessionText((byte) (totalSessions - restingSessions));

			resources.handlerTools.soundHandler.playChangeSound();
		}

		private void setWork() {
			--restingSessions; --untilRest;
			currentSession = "work";
			totalTime = Byte.parseByte(resources.handlerTools.fileHandler.getElementValue("time-work"));
			mainPanel.setWorkSession();
			startWork = Boolean.parseBoolean(resources.handlerTools.fileHandler.getElementValue("start-work"));

			if (startWork) {mainPanel.pauseButton();}
			else {mainPanel.continueButton();}
		}

		private void setBreak() {
			currentSession = "break";
			totalTime = Byte.parseByte(resources.handlerTools.fileHandler.getElementValue("time-break"));
			mainPanel.setBreakSession();
			startBreak = Boolean.parseBoolean(resources.handlerTools.fileHandler.getElementValue("start-break"));

			if (startBreak) {mainPanel.pauseButton();}
			else {mainPanel.continueButton();}
		}

		private void setRest() {
			currentSession = "rest";
			totalTime = Byte.parseByte(resources.handlerTools.fileHandler.getElementValue("time-rest"));
			mainPanel.setRestSession();
			startRest = Boolean.parseBoolean(resources.handlerTools.fileHandler.getElementValue("start-rest"));
			if (startRest) {mainPanel.pauseButton();}
			else {mainPanel.continueButton();}
		}

		public void startTimer() {
			mainPanel.callDisableInput();
			mainPanel.endButtons();
			sessionTime = LocalTime.now(); setWork();
			paused = false;
			timer.schedule(this, DELAY, SECOND - timeOfExec);
		}

		public void stopTimer() {
			timer.cancel(); timer.purge();
			mainPanel.callEnableInput();
			mainPanel.startButtons();
			mainPanel.restartTime();
			resources.handlerTools.soundHandler.playEndSound();
			paused = false;
		}
	}
}
