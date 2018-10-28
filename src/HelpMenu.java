import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class HelpMenu extends JComponent implements ActionListener
{
    private JFrame gui;
    private JButton exit;
    private boolean exitClicked;
    private BufferedImage backgroundImg;
    
    public HelpMenu()
    {
        //GUI
        gui = new JFrame("Help Menu");
        exit = new JButton("Exit");
        exitClicked = false;
        backgroundImg = loadImage("help.png");
        
        //SETS THE SIZE FOR THE JCOMPONENT
        setPreferredSize(new Dimension(1000, 700));
        
        //MANDATORY TO EXIT OUT OF THE 
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //NEEDED
        gui.setFocusable(true);
        
        //NEEDED
        gui.getContentPane().add(this);
        
        //SECOND WAY
        //ADD BUTTON
        exit = new JButton("Back");
        //SIZE
        exit.setSize(100, 50);
        //Location
        exit.setLocation(900, 650);
        //FONT
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        //Event Handling
        exit.addActionListener(this);
        //ADDS TO COMPONENT
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
        gui.setVisible(false);
        /*
        END
        */
    }
    
    public static void main(String[]args)
    {
        HelpMenu p = new HelpMenu();
    }
    
    public void paintComponent(Graphics g)
    {
        g.drawImage(backgroundImg, 0, 0, 1000, 700, null);
    }
    
    //IMPLEMENT FOR THE SECOND WAY
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(exit)){
            exitClicked = true;
        }
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
     
    public void hideGui()
    {
        gui.setVisible(false);
    }
    
    public void showGui()
    {
        gui.setVisible(true);
    }
    
    public boolean exitClicked()
    {
        return exitClicked;
    }
    
    public void setExitF()
    {
        exitClicked = false;
    }
}