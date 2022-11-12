import java.util.Timer;
import java.util.TimerTask;

public class Chrono {

	public Chrono() {
		Timer timer = new Timer();
		Test task = new Test();

		timer.schedule(task, 1000, 5000);
	}

	private class Test extends TimerTask {

		Test() {
			System.out.println("Here");
		}

		public void run() {
			System.out.println("running");
		}
	}
}