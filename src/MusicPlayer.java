import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer extends PlaybackListener {

    private static final Object playSignal = new Object();

    //reference
    private MusicPlayerGUI musicPlayerGUI;


    private Song curentSong;
    public Song getCurentSong(){
        return curentSong;
    }

    private AdvancedPlayer advancedPlayer;

    private boolean isPaused;

    private int currentFrame;
    public void setCurrentFrame(int frame){
        currentFrame= frame;
    }


    private int currentTimeInMilli;
    public void setCurrentTimeInMilli(int timeInMilli){
        currentTimeInMilli=timeInMilli;
    }

    private String mp3File;

    private double frameRatePerMilliSeconds;

    public MusicPlayer(MusicPlayerGUI musicPlayerGUI){
        this.musicPlayerGUI=musicPlayerGUI;

    }

    public void loadsong(Song song){
        curentSong=song;


        if (curentSong != null){


            currentFrame=0;
            currentTimeInMilli = 0;

            musicPlayerGUI.setPlaybacksliderValue(0);
            playCurrentSong();
        }
    }

    public void pauseSong(){
        if(advancedPlayer != null){
            isPaused = true;
            stopSong();
        }
    }
    public void stopSong(){
        if (advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    public void playCurrentSong() {
        if (curentSong == null) return;
        try{
            FileInputStream fileInputStream = new FileInputStream(curentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            advancedPlayer  = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);

            startMusicThread();

            startPlaybackSliderThread();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startMusicThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isPaused){

                        synchronized (playSignal){
                            isPaused=false;

                            playSignal.notify();
                        }

                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);

                    }else {

                        advancedPlayer.play();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private  void startPlaybackSliderThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isPaused){
                    try{
                        synchronized (playSignal){
                            playSignal.wait();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                System.out.println("isPaused :"+ isPaused);

                while(!isPaused){

                    try {
                        currentTimeInMilli++;
//                        System.out.println(currentTimeInMilli);

                        int calculatedFrom= (int)((double) currentTimeInMilli*2.08*curentSong.getFrameRatePerMillisecond());

                        musicPlayerGUI.setPlaybacksliderValue(calculatedFrom);
                        Thread.sleep(1);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        System.out.println("Playback Started");


    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        System.out.println("Playback Finished");
        System.out.println("Actual Stops:" + evt.getFrame());


        if (isPaused){
            currentFrame += (int) ((double) evt.getFrame()*curentSong.getFrameRatePerMillisecond());
//            System.out.println("Stopped @"+ currentFrame);
        }
    }
}
