import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

public class MusicPlayerGUI extends JFrame {
    public static final Color FRAME_COLOR = Color.black;
    public static final Color TEXT_COLOR = Color.lightGray;

    private MusicPlayer musicPlayer;
    private JFileChooser jFileChooser;
    private JLabel songTitle, songArtist;
    private JPanel playbackbtns;
    private JSlider playbackslider;

    public MusicPlayerGUI(){

//            super(getTitle("music player"));

        setSize(400, 600);

        setTitle("Music Player");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(null);

        getContentPane().setBackground(FRAME_COLOR);

        musicPlayer = new MusicPlayer(this);
        jFileChooser= new JFileChooser();

        jFileChooser.setCurrentDirectory(new File("src/assist"));

        jFileChooser.setFileFilter(new FileNameExtensionFilter("FLAC","flac"));
        jFileChooser.setFileFilter(new FileNameExtensionFilter("WAV","wav"));
        jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));

        addGuiComponents();
    }

    private void addGuiComponents() {

        addtoolbar();
        JLabel songimage= new JLabel(loadImage("src/assist/record.png",210, 210));
        songimage.setBounds(0,30,getWidth()-20,225);
        add(songimage);


        songTitle = new JLabel("Song Title");
        songTitle.setBounds(0,270,getWidth()-10,30);
        songTitle.setFont(new Font("Dialog", Font.BOLD , 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);


        songArtist = new JLabel("Song Artist");
        songArtist.setBounds(0,300,getWidth()-10,30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN , 18));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);


        playbackslider = new JSlider(0 ,100,0);
        playbackslider.setBounds(getWidth()/2 -300/2,370,300,30);
        playbackslider.setBackground(null);
        playbackslider.setForeground(TEXT_COLOR);
        playbackslider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                musicPlayer.pauseSong();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JSlider source =(JSlider) e.getSource();

                int frame = source.getValue();



                musicPlayer.setCurrentFrame(frame);


                musicPlayer.setCurrentTimeInMilli((int)(frame / (2.08*musicPlayer.getCurentSong().getFrameRatePerMillisecond())));

                musicPlayer.playCurrentSong();

                enablePauseButtonDisablePlayButton();
            }
        });

        add(playbackslider);

        addPlaybackbtns();
    }

    private void addPlaybackbtns() {

        playbackbtns = new JPanel();
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
        Playbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enablePauseButtonDisablePlayButton();

                musicPlayer.playCurrentSong();
            }
        });
        playbackbtns.add(Playbtn);


        JButton Pausebtn =new JButton(loadImage("src/assist/pause.png",30,30));
//            Pausebtn.setBounds(0,0,50,50);
        Pausebtn.setBorderPainted(false);
        Pausebtn.setBackground(null);
        Pausebtn.setVisible(false);
        Pausebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enablePlayButtonDisablePauseButton();

                musicPlayer.pauseSong();
            }
        });
        playbackbtns.add(Pausebtn);

        JButton Nextbtn =new JButton(loadImage("src/assist/next.png",40,30));
//            Nextbtn.setBounds(0,0,50,50);
        Nextbtn.setBorderPainted(false);
        Nextbtn.setBackground(null);
        playbackbtns.add(Nextbtn);


    }

    public void setPlaybacksliderValue(int frame){
        playbackslider.setValue(frame);
    }

    private  void updatesongTitleArtist(Song song){
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    private void updateplaybackslider(Song song){
        playbackslider.setMaximum(song.getMp3File().getFrameCount());
        Hashtable<Integer,JLabel> LabelTable = new Hashtable<>();

        JLabel labelBegining = new JLabel("00:00");
        labelBegining.setFont(new Font("Dialog",Font.BOLD,16));
        labelBegining.setForeground(TEXT_COLOR);

        JLabel labelEnd = new JLabel(song.getSongLength());
        labelEnd.setFont(new Font("Dialog",Font.BOLD,16));
        labelEnd.setForeground(TEXT_COLOR);


        LabelTable.put(0,labelBegining);
        LabelTable.put(song.getMp3File().getFrameCount(),labelEnd);

        playbackslider.setLabelTable(LabelTable);
        playbackslider.setPaintLabels(true);


        playbackslider.setLabelTable(LabelTable);
        playbackslider.setPaintLabels(true);


    }

    private void addtoolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setBounds(0, 0 ,getWidth(), 30);
        toolbar.setFloatable(false);

        JMenuBar menubar = new JMenuBar();
        toolbar.add(menubar);

        JMenu songsMenu = new JMenu("Songs");
        menubar.add(songsMenu);


        JMenuItem loadsong = new JMenuItem("Load Song");
        loadsong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if (result == jFileChooser.APPROVE_OPTION && selectedFile!= null){
                    Song song = new Song(selectedFile.getPath());

                    musicPlayer.loadsong(song);

                    updatesongTitleArtist(song);

                    updateplaybackslider(song);
                    enablePauseButtonDisablePlayButton();

                }
            }
        });
        songsMenu.add(loadsong);

//        JMenuItem songsLoad = new JMenuItem("load songs");
//        songsMenu.add(songsLoad);

        JMenu songPlaylist = new JMenu("Playlist");
        menubar.add(songPlaylist);

        JMenuItem createPlaylist = new JMenuItem("Create Playlist");
        createPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        songPlaylist.add(createPlaylist);

        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        songPlaylist.add(loadPlaylist);



        add(toolbar);

    }

    private void enablePauseButtonDisablePlayButton() {
        JButton playButton = (JButton) playbackbtns.getComponent(1);
        JButton pauseButton = (JButton) playbackbtns.getComponent(2);


        playButton.setVisible(false);
        playButton.setEnabled(false);

        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }
    private void enablePlayButtonDisablePauseButton() {
        JButton playButton = (JButton) playbackbtns.getComponent(1);
        JButton pauseButton = (JButton) playbackbtns.getComponent(2);


        playButton.setVisible(true);
        playButton.setEnabled(true);

        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
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
