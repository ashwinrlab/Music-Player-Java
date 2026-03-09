import javax.swing.*;

public class App {
    static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new  MusicPlayerGUI().setVisible(true);


//                Song song = new Song("src/assist/Vaada-Bin-Laada.mp3");
//                System.out.println(song.getSongTitle());
//                System.out.println(song.getSongArtist());

            }
        });
    }
}
