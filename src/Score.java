import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Score extends JComponent implements ActionListener
{
    private BufferedImage background;
    private JFrame gui;
    private ImageIcon image1;
    private JLabel jl;
    private JLabel back;
    private JTextArea ta;
    private JButton enter;
    private JLabel current;
    private int highScore;
    private int currentScore;
    private int previousScore;
    private JLabel currentS;
    private JLabel fill;
    private JLabel fill2;
    private ImageIcon fill2a;
    private JLabel highName;
    private JLabel highname;
    private JLabel high;
    private JLabel highscore;
    private String highN;
    
    private int sun;
    private String life;
    private String type;
    private String damage;
    
    //Put In
    private JLabel sunBox;
    private JLabel sunFill;

    private JLabel typeFill;

    private JLabel lifeFill;
    
    private JLabel dmgFill;
    
    
    public Score()
    {
        highScore = Integer.MIN_VALUE;
        previousScore = Integer.MIN_VALUE;
        background = loadImage("grass.png");
        setLayout(new FlowLayout());
        
        //GUI
        gui = new JFrame("Game");
        
        //SETS THE SIZE FOR THE JCOMPONENT
        setPreferredSize(new Dimension(400, 700));
        
        //MANDATORY TO EXIT OUT OF THE 
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //NEEDED
        gui.setFocusable(true);
        
        //NEEDED
        gui.getContentPane().add(this);

        //fill2a = new ImageIcon(getClass().getResource("title2.png"));
       // fill2 = new JLabel(fill2a);
       // add(fill2);
        
        jl = new JLabel("Score Board");
        
        jl.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        
        image1 = new ImageIcon(getClass().getResource("daveScore.png"));
        back = new JLabel(image1);
        
        add(jl);
        add(back);
        
        ta = new JTextArea(1, 8);
        add(ta);
        
        ta.setFont(new Font("Showcard Gothic", Font.BOLD, 25));
        ta.setBackground(java.awt.Color.orange);
        enter = new JButton("            Click To Enter Your Name          ");
        enter.setBackground(java.awt.Color.cyan);
        add(enter);
        
        enter.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        
        current = new JLabel("");
        current.setFont(new Font("Showcard Gothic", Font.BOLD, 25));
        add(current);
        
        //current.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        
        enter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                String text = ta.getText();
                current.setText("CURRENT PLAYER: " + text);
            }
            
        });
        
        /*
        //Score Viewing
        currentS = new JLabel("CURRENT SCORE: ");
        currentS.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(currentS);
        fill = new JLabel(currentScore+"    ");
        fill.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(fill);
        */
        
        //Select Viewing
        sunBox = new JLabel("          Sun:    ");
        sunFill = new JLabel(sun+"              ");
        sunBox.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
        sunFill.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
        add(sunBox);
        add(sunFill);
        
        typeFill = new JLabel(type+"       ");
        lifeFill = new JLabel(life+"       ");
        dmgFill = new JLabel(damage+"");
        typeFill.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
        lifeFill.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
        dmgFill.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
        add(typeFill);
        add(lifeFill);
        add(dmgFill);
        
        /*
        START
        THESE THREE ARE ALWAYS GONNA BE THE LAST THREE
        */
        //PACKS EVERYTHING (IF NOT INCLUDED SIZE OR COMPONENT) MAKES IT LOOK NICE
        gui.pack();
        
        //OPENS GUI IN THE CENTER! AFTER PACK - OPTIONAL
        //gui.setLocationRelativeTo(null); // OR JFrame.
        
        //AT THE LAST
        gui.setVisible(true);
        
       // END
        
    }
    
    public void sendScore(int score)
    {
        /*
        //previousScore = currentScore;
        currentScore = score;
        if(score>10)
        {
            gui.setVisible(false);
           // System.out.println("HI");
            highScore = currentScore;
            highN = current.getText();
            previousScore = currentScore;
        }
        */
    }
    
    public static void main(String[]args)
    {
        Score p = new Score();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void changeScore(int score)
    {
        fill.setText(score+"");
    }
    
    public int getHighScore()
    {
        return highScore;
    }
    
    public String getHighName()
    {
        return highN;
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
        g.drawImage(background, 0, 0, 400, 700, null);
    }
    
    public void changeSun(int n)
    {
        sunFill.setText(n+"");
    }
    
    public void changeLife(String l)
    {
        lifeFill.setText(l);
    }
    public void changeType(String t)
    {
        typeFill.setText(t);
    }
    public void changeDamage(String d)
    {
        dmgFill.setText(d);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //BAD
    
    
    public Score(int a)
    {      
        background = loadImage("grass.png");
        
     //   setLayout(new FlowLayout());
        
        //GUI
        gui = new JFrame("Game");
        
        //SETS THE SIZE FOR THE JCOMPONENT
        setPreferredSize(new Dimension(400, 700));
        
        //MANDATORY TO EXIT OUT OF THE 
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //NEEDED
        gui.setFocusable(true);
        
        //NEEDED
        gui.getContentPane().add(this);

        //fill2a = new ImageIcon(getClass().getResource("title2.png"));
       // fill2 = new JLabel(fill2a);
       // add(fill2);
        
        jl = new JLabel("Score Board");
        
        jl.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        
        image1 = new ImageIcon(getClass().getResource("daveScore.png"));
        back = new JLabel(image1);
        
        add(jl);
        add(back);
        
        ta = new JTextArea(2, 17);
        ta.setFont(new Font("Showcard Gothic", Font.BOLD, 25));
        add(ta);
        
        enter = new JButton("   Click To Enter Your Name   ");
        enter.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(enter);
        
        highName = new JLabel("HIGH SCORE CHAMPION: ");
        highName.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        //add(highName);
        highname = new JLabel(highN);
        highname.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        //add(highname);
        high = new JLabel("HIGH SCORE: ");
        high.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
       // add(high);
        highscore = new JLabel(highScore+"                  ");
        highscore.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        //add(highscore);
        
        
        current = new JLabel("");
        current.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(current);
        
        //current.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        
        enter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                String text = ta.getText();
                current.setText("CURRENT PLAYER: " + text);
            }
            
        });
        
        /*
        //Score Viewing
        currentS = new JLabel("CURRENT SCORE: ");
        currentS.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(currentS);
        fill = new JLabel(currentScore+"    ");
        fill.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(fill);
        */
        
        //Select Viewing
        sunBox = new JLabel("Sun:  ");
        sunFill = new JLabel(sun+"           ");
        sunBox.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        sunFill.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(sunBox);
        add(sunFill);
        
        typeFill = new JLabel(type+"             ");
        lifeFill = new JLabel(life+"              ");
        dmgFill = new JLabel(damage+"            ");
        typeFill.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        lifeFill.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        dmgFill.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        add(typeFill);
        add(lifeFill);
        add(dmgFill);
        
        /*
        START
        THESE THREE ARE ALWAYS GONNA BE THE LAST THREE
        */
        //PACKS EVERYTHING (IF NOT INCLUDED SIZE OR COMPONENT) MAKES IT LOOK NICE
        gui.pack();
        
        //OPENS GUI IN THE CENTER! AFTER PACK - OPTIONAL
        //gui.setLocationRelativeTo(null); // OR JFrame.
        
        //AT THE LAST
        gui.setVisible(true);
        /*
        END
        */
    }
}