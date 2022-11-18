import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.util.Objects;

public class SoundHandler {

    /* TODO:
    * - Fix the Thread to check if another thread it's running
    * */

    BufferedInputStream changeInputStream, endInputStream;
    private Player changeSound, endSound;
    private SoundPlayer soundPlayer;
    private Thread lastThread;

    SoundHandler() {lastThread = new Thread();}

    private void setChangePlayer() throws JavaLayerException {
        String changeSoundPath = "sounds/change_sound.mp3";
        changeInputStream = new BufferedInputStream(
                Objects.requireNonNull(
                        this.getClass().getClassLoader().getResourceAsStream(changeSoundPath)));
        changeSound = new Player(changeInputStream);
    }

    private void setEndPlayer() throws JavaLayerException {
        String endSoundPath = "sounds/end_sound.mp3";
        endInputStream = new BufferedInputStream(
                Objects.requireNonNull(
                        this.getClass().getClassLoader().getResourceAsStream(endSoundPath)));
        endSound = new Player(endInputStream);
    }

    public void playChangeSound() {
        lastThread = soundPlayer != null? soundPlayer:lastThread;
        soundPlayer = new SoundPlayer("change");
        soundPlayer.start();
    }

    public void playEndSound() {
        lastThread = soundPlayer != null? soundPlayer:lastThread;
        soundPlayer = new SoundPlayer("end");
        soundPlayer.start();
    }

    private class SoundPlayer extends Thread {

        private final String play;

        SoundPlayer(String toPlay) {play = toPlay;}

        public void run() {

            if (lastThread.isAlive()) return;

            if (play.equals("change")) {
                try {setChangePlayer(); changeSound.play(); changeSound.close();}
                catch (JavaLayerException e) {throw new RuntimeException(e);}
            }
            else {
                try {setEndPlayer(); endSound.play(); endSound.close();}
                catch (JavaLayerException e) {throw new RuntimeException(e);}
            }
        }
    }
}
