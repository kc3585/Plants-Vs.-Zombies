import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Win extends JComponent implements ActionListener
{
    private JFrame gui;
    private JButton clickMe;
    private JButton quit;
    private ImageIcon pic1;
    private JLabel pic;
    private BufferedImage background;
    private JLabel statusA;
    private ImageIcon statusB;
    private JLabel fill;
    private ImageIcon fillPic;
    
    public Win(boolean status)
    {
        setLayout(new FlowLayout());
        background = loadImage("daveWin.png");
        //GUI
        gui = new JFrame("Game");
        clickMe = new JButton("Button");
        quit = new JButton("Quit");
        
        //SETS THE SIZE FOR THE JCOMPONENT
        setPreferredSize(new Dimension(1000, 700));
        
        //MANDATORY TO EXIT OUT OF THE 
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //NEEDED
        gui.setFocusable(false);
        
        //NEEDED
        gui.getContentPane().add(this);
        
        if(status == true)
        {  
            statusB = new ImageIcon(getClass().getResource("win.png"));
            statusA = new JLabel(statusB);
        }
        else if(status == false)
        {
            statusB = new ImageIcon(getClass().getResource("lose.png"));
            statusA = new JLabel(statusB);
        }
        
        add(statusA);
        
        fillPic = new ImageIcon(getClass().getResource("fill.png"));
        fill = new JLabel(fillPic);
        add(fill);
        
        //SECOND WAY
        //ADD BUTTON
        clickMe = new JButton("Play Again");
        //SIZE
        clickMe.setSize(200, 200);
        clickMe.setFont(new Font("Showcard Gothic", Font.BOLD, 80));
        clickMe.setBackground(java.awt.Color.yellow);
        //Location
        clickMe.setLocation(400, 300);
        //Event Handling
        clickMe.addActionListener(this);

        add(clickMe);
        
        //SECOND WAY
        //ADD BUTTON
        quit = new JButton("Quit");
        //SIZE
        quit.setSize(200, 200);
        quit.setFont(new Font("Showcard Gothic", Font.BOLD, 80));
        quit.setBackground(java.awt.Color.red);
        //Location
        quit.setLocation(700, 300);
        //Event Handling
        quit.addActionListener(this);

        add(quit);
        
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
        Win p = new Win(false);
    }

    //IMPLEMENT FOR THE SECOND WAY
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(clickMe)){
            //Play Again
            gui.setVisible(false);
            Game g = new Game();
        }
        if(ae.getSource().equals(quit)){
            System.exit(0);
        }
    }
    
    public void showGui()
    {
        gui.setVisible(true);
    }
    public void hideGui()
    {
        gui.setVisible(false);
    }
    
    public boolean againClicked()
    {
        return againClicked();
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
    public void paintComponent(Graphics g)
    {
        g.drawImage(background, 0, 0, 1000, 700, null);
    }
}