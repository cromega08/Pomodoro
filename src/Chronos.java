import org.jdom2.IllegalDataException;

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
	private int totalSessions;
	private boolean paused;

	public Chronos(Resources resourcesInstance, Main content) {

		resources = resourcesInstance;
		mainPanel = content;
		DELAY = 50;
		SECOND = 1000;
		paused = false;
	}

	public void startChronometer(String numberOfSessions) {
		timer = new Timer();
		totalSessions = Byte.parseByte(numberOfSessions);
		chronometer = new Chronometer();
		chronometer.startTimer();
	}

	public void pauseChronometer() {paused = true;}

	public void restartChronometer() {paused = false;}

	public void stopChronometer() {chronometer.stopTimer();}

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
			if (totalTime < 0) {changeSession(); return;}
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

		private void changeSession() {

			if (totalSessions <= 0) {stopTimer(); return;}

			secondsInMinute = 1;

			switch (currentSession) {
				case "work" -> {
					if (untilRest <= 0) {setRest(); untilRest = restInterval;}
					else {setBreak();}
				}
				case "break", "rest" -> setWork();
				default -> throw new IllegalDataException(String.format("\"currentSession\" variable have an illegal value %s", currentSession));
			}
		}

		private void setWork() {
			--totalSessions; --untilRest;
			currentSession = "work";
			totalTime = Byte.parseByte(resources.handlerTools.fileHandler.getElementValue("time-work"));
			mainPanel.callWorkPalette();
		}

		private void setBreak() {
			currentSession = "break";
			totalTime = Byte.parseByte(resources.handlerTools.fileHandler.getElementValue("time-break"));
			mainPanel.callRestPalette();
		}

		private void setRest() {
			currentSession = "rest";
			totalTime = Byte.parseByte(resources.handlerTools.fileHandler.getElementValue("time-rest"));
			mainPanel.callRestPalette();
		}

		public void startTimer() {
			mainPanel.callDisableInput();
			mainPanel.endButtons();
			sessionTime = LocalTime.now(); setWork();
			timer.schedule(this, DELAY, SECOND - timeOfExec);
		}

		public void stopTimer() {
			timer.cancel(); timer.purge();
			mainPanel.callEnableInput();
			mainPanel.startButtons();
			mainPanel.restartTime();
			paused = false;
		}
	}
}
