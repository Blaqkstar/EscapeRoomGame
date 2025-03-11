package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class BackgroundMusic implements Runnable{
    private String filePath = "UNDEFINED BG MUSIC FILEPATH";
    private Clip audioClip; // references the current audio clip
    private volatile boolean isRunning = true; // this flag controls the music thread

    // default constructor
    public BackgroundMusic(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public void run() {
        try {
            File audioFile = new File(filePath);
            //System.out.println("Audio filepath: " + audioFile.getAbsolutePath());
            // ensures file exists
            if (!audioFile.exists()) {
                System.err.println("Audio file not found!");
                return;
            }
            // defines an audio stream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); // loops music
            audioClip.start();
            // keeps thread alive while music is playing
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000); // sleeps to avoid waiting
            }
            audioClip.stop();
            audioClip.close();
        } catch (UnsupportedAudioFileException ex){
            System.err.println("Unsupported audio file format: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error reading the audio file: " + ex.getMessage());
        } catch (LineUnavailableException ex) {
            System.err.println("Audio line unavailable: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println("Music playback interrupted: " + ex.getMessage());
        }
    }

    // stop method
    public void stop() {
        isRunning = false; // stops the loop
        if (audioClip != null && audioClip.isRunning()){
            audioClip.stop(); // stops music
            audioClip.close(); // releases resources
        }
    }
    // change music method
    public void changeMusic(String newFilePath) {
        stop(); // stops current music
        this.filePath = newFilePath; // updates filepath
        isRunning = true; // resets running flag
        new Thread(this).start(); // begins new thread w/ updated music
    }
}
