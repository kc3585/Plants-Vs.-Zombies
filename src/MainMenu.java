import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import java.applet.*;

public class MainMenu extends JComponent implements ActionListener
{
    private JFrame gui;
    private JButton start;
    private JButton help;
    private JButton exit;
    private JButton back;
    private boolean startClicked;
    private boolean helpClicked;
    private BufferedImage backgroundImg;
    private ImageIcon image1;
    private JLabel label1;
    private ImageIcon image2;
    private JLabel label2;
    
    private AudioClip introSong;
    
    public MainMenu()
    {
        //introSong.play();
        startClicked = false;
        helpClicked = false;
        backgroundImg = loadImage("mainBackground.jpg");
        
        //GUI
        gui = new JFrame("Start Menu");
        
        //SETS THE SIZE FOR THE JCOMPONENT
        setPreferredSize(new Dimension(1000, 700));
        
        //MANDATORY TO EXIT OUT OF THE 
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //NEEDED
        gui.setFocusable(true);
        
        //NEEDED
        gui.getContentPane().add(this);
        
        //Title
        setLayout(new FlowLayout());
        image1 = new ImageIcon(getClass().getResource("title.png"));
        label1 = new JLabel(image1);
        image2 = new ImageIcon(getClass().getResource("title2.png"));
        label2 = new JLabel(image2);
        add(label1);
        add(label2);
        
        //BUTTONS
        
        //FIRST BUTTON
        //ADD BUTTON
        start = new JButton("START");
        //SIZE
        start.setSize(300, 100);
        //Location
        start.setLocation(400, 100);
        //Event Handling
        start.addActionListener(this);
        start.setBackground(java.awt.Color.cyan);
        //ADDS TO COMPONENT
        add(start);
        
        
        //SECOND BUTTON
        //ADD BUTTON
        help = new JButton("HELP");
        //SIZE
        help.setSize(300, 100);
        //Location
        help.setLocation(200, 300);
        //Event Handling
        help.addActionListener(this);
        help.setBackground(java.awt.Color.yellow);
        add(help);
        
        //THIRD BUTTON
        //ADD BUTTON
        exit = new JButton("EXIT");
        //SIZE
        exit.setSize(300, 100);
        //Location
        exit.setLocation(200, 500);
        //FONT
        start.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        help.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        exit.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        //Event Handling
        exit.addActionListener(this);
        exit.setBackground(java.awt.Color.orange);
        add(exit);
        
        /*
        START
        THESE THREE ARE ALWAYS GONNA BE THE LAST THREE
        */
        //PACKS EVERYTHING (IF NOT INCLUDED SIZE OR COMPONENT) MAKES IT LOOK NICE
        gui.pack();
        
        //OPENS GUI IN THE CENTER! AFTER PACK - OPTIONAL
        gui.setLocationRelativeTo(null); // OR JFrame.
        
        //AT THE LAST
        gui.setVisible(true);
        /*
        END
        */
    }
    
    public static void main(String[]args)
    {
        MainMenu p = new MainMenu();
    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(backgroundImg, 0, 0, 1000, 700, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(start)){
            startClicked = true;
        }
        if(ae.getSource().equals(help)){
            helpClicked = true;
        }
        if(ae.getSource().equals(exit)){
            System.exit(0);
        }
    }
    
    public boolean gameStarted()
    {
        return startClicked;
    }
    
    public void hideGui()
    {
        gui.setVisible(false);
        //introSong.stop();
    }
    
    public void showGui()
    {
        gui.setVisible(true);
    }
    
    public boolean helpClicked()
    {
        return helpClicked;
    }
    
    public void setHelpF()
    {
        helpClicked = false;
    }
    public void setHelpR()
    {
        helpClicked = true;
    }
    
    private BufferedImage loadImage(String imageFileName)
    {
        URL url = getClass().getResource(imageFileName);
        if (url == null)
          throw new RuntimeException("cannot find file:  " + imageFileName);
        try
        {
          return ImageIO.read(url);
        }
        catch(IOException e)
        {
          throw new RuntimeException("unable to read from file:  " + imageFileName);
        }
    }
}