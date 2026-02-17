import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MusicPlayerGUI extends JFrame {
    public static final Color FRAME_COLOR = Color.black;

    public static final Color TEXT_COLOR = Color.lightGray;
        public MusicPlayerGUI(){

//            super(getTitle("music player"));

            setSize(400, 600);

            setTitle("Music Player");

            setDefaultCloseOperation(EXIT_ON_CLOSE);

            setLocationRelativeTo(null);

            setResizable(false);

            setLayout(null);

            getContentPane().setBackground(FRAME_COLOR);

            addGuiComponents();
        }

    private void addGuiComponents() {

            addtoolbar();
        JLabel songimage= new JLabel(loadImage("src/assist/record.png",210, 210));
        songimage.setBounds(0,30,getWidth()-20,225);
            add(songimage);


            JLabel songTitle = new JLabel("Song Title");
            songTitle.setBounds(0,270,getWidth()-10,30);
            songTitle.setFont(new Font("Dialog", Font.BOLD , 24));
            songTitle.setForeground(TEXT_COLOR);
            songTitle.setHorizontalAlignment(SwingConstants.CENTER);
            add(songTitle);


            JLabel songArtist = new JLabel("Song Artist");
            songArtist.setBounds(0,300,getWidth()-10,30);
            songArtist.setFont(new Font("Dialog", Font.PLAIN , 18));
            songArtist.setForeground(TEXT_COLOR);
            songArtist.setHorizontalAlignment(SwingConstants.CENTER);
            add(songArtist);


            JSlider playbackslider = new JSlider(0 ,100,0);
            playbackslider.setBounds(getWidth()/2 -300/2,370,300,30);
            playbackslider.setBackground(null);
            playbackslider.setForeground(TEXT_COLOR);
            add(playbackslider);

            addPlaybackbtns();
    }

    private void addPlaybackbtns() {

            JPanel playbackbtns = new JPanel();
            playbackbtns.setBounds(0,420,getWidth()-10,100);
            playbackbtns.setBackground(null);
            add(playbackbtns);

            //previous
            JButton Previousbtn =new JButton(loadImage("src/assist/previous.png" ,40,30));
//            Previousbtn.setBounds("20");
            Previousbtn.setBorderPainted(false);
            Previousbtn.setBackground(null);
            playbackbtns.add(Previousbtn);



            JButton Playbtn =new JButton(loadImage("src/assist/play.png",30,30));
//            Playbtn.setBounds(0,0,20,20);
            Playbtn.setBorderPainted(false);
            Playbtn.setBackground(null);
            playbackbtns.add(Playbtn);


            JButton Pausebtn =new JButton(loadImage("src/assist/pause.png",30,30));
//            Pausebtn.setBounds(0,0,50,50);
            Pausebtn.setBorderPainted(false);
            Pausebtn.setBackground(null);
            Pausebtn.setVisible(false);
            playbackbtns.add(Pausebtn);

            JButton Nextbtn =new JButton(loadImage("src/assist/next.png",40,30));
//            Nextbtn.setBounds(0,0,50,50);
            Nextbtn.setBorderPainted(false);
            Nextbtn.setBackground(null);
            playbackbtns.add(Nextbtn);




    }

    private void addtoolbar() {
            JToolBar toolbar = new JToolBar();
            toolbar.setBounds(0, 0 ,getWidth(), 20);
            toolbar.setFloatable(false);

            JMenuBar menubar = new JMenuBar();
            toolbar.add(menubar);

            JMenu songsMenu = new JMenu("Songs");
            menubar.add(songsMenu);

            JMenuItem songsLoad = new JMenuItem("load songs");
            songsMenu.add(songsLoad);

            JMenu songPlaylist = new JMenu("Playlist");
            menubar.add(songPlaylist);

            JMenuItem createPlaylist = new JMenuItem("Create Playlist");
            songPlaylist.add(createPlaylist);

            JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
            songPlaylist.add(loadPlaylist);



            add(toolbar);

    }

    private ImageIcon loadImage(String imagepath, int width, int height){
            try{
                BufferedImage image = ImageIO.read(new File(imagepath));
                Image scaledImage = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);


            } catch (Exception e) {

                System.out.println(e);
            }
            return  null;
    }


}
