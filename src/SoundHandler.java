import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.util.Objects;

/*
    <Pomodoro:  An application to track productive time.>
    Copyright (C) <2022>  <Cromega>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
* */

public class SoundHandler {

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
                try {runChange();}
                catch (JavaLayerException e) {throw new RuntimeException(e);}
            }
            else {
                try {runEnd();}
                catch (JavaLayerException e) {throw new RuntimeException(e);}
            }
        }

        private void runChange() throws JavaLayerException {
            setChangePlayer(); changeSound.play(); changeSound.close();
        }

        private void runEnd() throws JavaLayerException {
            setEndPlayer(); endSound.play(); endSound.close();
        }
    }
}
