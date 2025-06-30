package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundUrl = new URL[30]; // Used to store the file path of the sounds file
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {
        soundUrl[0] = getClass().getResource("/sounds/littlerootTown.wav");
        //soundUrl[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundUrl[1] = getClass().getResource("/sounds/coin.wav");
        soundUrl[2] = getClass().getResource("/sounds/powerUp.wav");
        soundUrl[3] = getClass().getResource("/sounds/door.wav");
        soundUrl[4] = getClass().getResource("/sounds/gameEnd.wav");
        soundUrl[5] = getClass().getResource("/sounds/hitmonster.wav");
        soundUrl[6] = getClass().getResource("/sounds/receivedamage.wav");
        soundUrl[7] = getClass().getResource("/sounds/swordswing.wav");
        soundUrl[8] = getClass().getResource("/sounds/levelup.wav");
        soundUrl[9] = getClass().getResource("/sounds/levelup2.wav");
        soundUrl[10] = getClass().getResource("/sounds/cursor.wav");
        soundUrl[11] = getClass().getResource("/sounds/fireball.wav");
        soundUrl[12] = getClass().getResource("/sounds/cuttree.wav");
        soundUrl[13] = getClass().getResource("/sounds/gameOver.wav");
        soundUrl[14] = getClass().getResource("/sounds/stairs.wav");




    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception ignored) {
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        fc.setValue(volume);
    }

}
