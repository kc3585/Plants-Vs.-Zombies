import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class OptionMenu extends JComponent implements ActionListener
{
    private JFrame gui;
    private JButton game1;
    private JButton game2;
    private JButton game3;
    private boolean gameA;
    private boolean gameB;
    private boolean gameC;
    private BufferedImage backgroundImg;
    
    public OptionMenu()
    {
        gameA = false;
        gameB = false;
        gameC = false;
        //GUI
        gui = new JFrame("Game");
        
        backgroundImg = loadImage("optionScreen.jpg");
        
        //SETS THE SIZE FOR THE JCOMPONENT
        setPreferredSize(new Dimension(1000, 700));
        
        //MANDATORY TO EXIT OUT OF THE 
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //NEEDED
        gui.setFocusable(true);
        
        //NEEDED
        gui.getContentPane().add(this);

        //ADD BUTTON
        game1 = new JButton("Basic");
        //SIZE
        game1.setSize(200, 100);
        //Location
        game1.setLocation(400, 100);
        //Event Handling
        game1.addActionListener(this);
        add(game1);
        
        game2 = new JButton("Super Power");
        //SIZE
        game2.setSize(200, 100);
        //Location
        game2.setLocation(400, 250);
        //Event Handling
        game2.addActionListener(this);
        add(game2);
        
        game3 = new JButton("Ice");
        //SIZE
        game3.setSize(200, 100);
        //Location
        game3.setLocation(400, 400);
        //Event Handling
        game3.addActionListener(this);
        add(game3);
        
        game1.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        game2.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        game3.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        
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
        OptionMenu p = new OptionMenu();
    }

    //IMPLEMENT FOR THE SECOND WAY
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(game1)){
            gameA = true;
        }
        if(ae.getSource().equals(game2)){
            gameB = true;
        }
        if(ae.getSource().equals(game3)){
            gameC = true;
        }
    }
    
    public void paintComponent(Graphics g)
    {
        g.drawImage(backgroundImg, 0, 0, 1000, 700, null);
    }
    
    public boolean clicked()
    {
        return gameA||gameB||gameC;
    }
    
    public boolean clickedA()
    {
        return gameA;
    }
    public boolean clickedB()
    {
        return gameB;
    }
    public boolean clickedC()
    {
        return gameC;
    }
    
    public void see()
    {
        gui.setVisible(true);
    }
    
    public void hide()
    {
        gui.setVisible(false);
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