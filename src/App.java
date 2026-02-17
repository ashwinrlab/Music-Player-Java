import javax.swing.*;

public class App {
    static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new  MusicPlayerGUI().setVisible(true);


                Song song = new Song("");
            }
        });
    }
}
