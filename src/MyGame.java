//REAL DEAL BASIC GAME
//import java.applet.*;
/*
import MenuTest.Grid;
import MenuTest.HelpMenu;
import MenuTest.Location;
import MenuTest.MainMenu;
import MenuTest.Win;
import MenuTest.Score;
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.applet.*;

public class MyGame
{
    private Grid grid;
    private int userRow;
    private int userCol;
    private int rows;
    private int cols;
    private String player;
    private String background;
    private String playerBack;
    private int backNum;
    private String get;
    private String avoid;
    private String projectile;
    
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;

    private ActionListener gameListener;
    private Timer gameRepeat;
    
    private MainMenu menu;
    private HelpMenu hMenu;
    private Win won;
    private Score board;
    private OptionMenu option;
    
    private AudioClip introSong;
    private AudioClip hit;
    private AudioClip got;
    private AudioClip wIn;
    private AudioClip lose;
    private AudioClip playAgain;
    
    private AudioClip playA;
    private AudioClip playB;
    private AudioClip playC;
    
    private boolean win;
    
    private HighScore hScore;
    
    //THERE ARE TWO GAME CONSTRUCTORS
    public MyGame()
    {
        try
        {
            Thread.sleep(4500);
        }
        catch(Exception e)
        {

        }
        /*
        introSong = Applet.newAudioClip(this.getClass().getResource("ding.wav"));
        hit = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        got = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        wIn = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        lose = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        playAgain = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        */
        //introSong.play();
        
        win = true;
        menu = new MainMenu();
        hMenu = new HelpMenu();
        board = new Score();
        option = new OptionMenu(); 
        hScore = new HighScore();
        
        gameListener = new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(menu.gameStarted())
                {
                    option.see();
                }
                if(menu.helpClicked())
                {
                    hMenu.showGui();
                    menu.setHelpF();
                }
                if(hMenu.exitClicked())
                {
                    hMenu.hideGui();
                    hMenu.setExitF();
                }
                if(option.clicked())
                {
                    gameRepeat.stop();
                    option.hide();
                    menu.hideGui();
                    
                    //System.out.println("here");
                    if(option.clickedA())
                    {
                      //  introSong.stop();
                        background = "backgroundA.jpg";
                        player = "plantA.png";
                        avoid = "zombieA.png";
                        get = "getA.png";
                        projectile = "projectA.png";
                    }
                    else if(option.clickedB())
                    {
                     //   introSong.stop();
                        background = "backgroundB.png";
                        player = "plantB.png";
                        avoid = "zombieB.png";
                        get = "getB.png";
                        projectile = "projectB.png";
                    }
                    else if(option.clickedC())
                    {
                      //  introSong.stop();
                        background = "backgroundC.jpg";
                        player = "plantC.png";
                        avoid = "zombieC.png";
                        get = "getC.png";
                        projectile = "projectC.png";
                    }
                    
                    init();
                    play();
                }
            }
        };
        
        gameRepeat = new Timer(30, gameListener);
        gameRepeat.start();
    }
    
    public MyGame(int fakeParam)
    {
        /*
        introSong = Applet.newAudioClip(this.getClass().getResource("ding.wav"));
        hit = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        got = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        wIn = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        lose = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        playAgain = Applet.newAudioClip(this.getClass().getResource("ding.wav"));;
        
        introSong.play();
        */
        win = true;
        menu = new MainMenu();
        hMenu = new HelpMenu();
        board = new Score();
        option = new OptionMenu();
        
        gameListener = new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(menu.gameStarted())
                {
                    option.see();
                }
                if(menu.helpClicked())
                {
                    hMenu.showGui();
                }
                if(hMenu.exitClicked())
                {
                    hMenu.hideGui();
                    hMenu.setExitF();
                }
                if(option.clicked())
                {
                    gameRepeat.stop();
                    menu.hideGui();
                    option.hide();
                    
                    //System.out.println("here");
                    
                    if(option.clickedA())
                    {
                       // introSong.stop();
                        background = "backgroundA.jpg";
                        player = "plantA.png";
                        avoid = "zombieA.png";
                        get = "getA.png";
                        projectile = "projectA.png";
                    }
                    else if(option.clickedB())
                    {
                       // introSong.stop();
                        background = "backgroundB.png";
                        player = "plantB.png";
                        avoid = "zombieB.png";
                        get = "getB.png";
                        projectile = "projectB.png";
                    }
                    else if(option.clickedC())
                    {
                       //introSong.stop();
                        background = "backgroundC.jpg";
                        player = "plantC.png";
                        avoid = "zombieC.png";
                        get = "getC.png";
                        projectile = "projectC.png";
                    }
                    
                    init();
                    play();
                }
            }
        };
        
        gameRepeat = new Timer(30, gameListener);
        gameRepeat.start();
    }
        
    private void init()
    {
        rows = 6;
        cols = 14;
        //background="plantsBackground.jpg";
        grid = new Grid(rows, cols, background);
        //player="peashooter.png";
        //playerBack = "plant45.png";
        //backNum = 45;
        //projectile="pea.png";
        userRow = 3;
        userCol = 2;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, userCol), player);
        //introSong = Applet.newAudioClip(this.getClass().getResource("Song.wav"));
        //play();
    }
    
    public void play()
    {
        //Makes a action that holds instructions
        gameListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                handleKeyPress();

                if (msElapsed % 300 == 0) //CHANGE HERE TO CHANGE THE SPEED OF THE SCROLLING
                {
                    populateRightEdge();
                    scrollLeft();
                    //addBackground();
                }
                if(getScore()>10&&win)
                {
                   // introSong.stop();
                    win=false;
                    grid.hideGui();
                    won = new Win(true);
                    board.sendScore(getScore());
                }
                if(getScore()<-5&&win){
                    win=false;
                    grid.hideGui();
                    won = new Win(false);
                    board.sendScore(getScore());
                }
                updateTitle();
                msElapsed += 100;
            }
            
        };
        
        //Makes a timer that repeats the action every 100 miliseconds
        gameRepeat = new Timer(100,gameListener);
        
        //starts the tiemr
        gameRepeat.start();
        
        /*introSong.play();
        while (!isGameOver())
        {
            grid.pause(100);
            handleKeyPress();
            if (msElapsed % 300 == 0) //CHANGE HERE TO CHANGE THE SPEED OF THE SCROLLING
            {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
        *///introSong.stop();
    }

    public void handleKeyPress()
    {
        int key = grid.checkLastKeyPressed();
        int pastUserRow = userRow;
        int pastUserCol = userCol;
        int pastBackNum = backNum;
        if(key==38&&userRow>0){
            userRow--;
            backNum -=14;
           // playerBack = "plant"+backNum+".png";
        }
        if(key==40&&userRow<rows-1){
            userRow++;
            backNum +=14;
            //playerBack = "plant"+backNum+".png";
        }
        if(key==37&&userCol>0)
            userCol--;
        if(key==39&&userCol<cols-1)
            userCol++;
        if(key==32) //Projectile
            project(userCol);
        
        boolean pause = true;
        if(key==80){
            while(pause){
                Grid.pause(1);
                int key2 = grid.checkLastKeyPressed();
                if(key2==80)
                    pause=false;
            }
            pause=true;
        }
        
        handleCollision(new Location(userRow, userCol));
 
        grid.setImage(new Location(pastUserRow, pastUserCol), null);
        grid.setImage(new Location(userRow, userCol), player);
    }

    public void populateRightEdge()
    {
        //Random row to place popups
        int row = (int)(Math.random()*(grid.getNumRows())); //0 to 4
        //int row1 = (int) (Math.random() * (grid.getNumRows())); //0 to 4

        /*
        //Quantity ALOT
        //POP UP
        for(int i = 0; i < grid.getNumRows(); i++){
            int gal = (int)(Math.random()*3);
            if(gal==0)
            grid.setImage(new Location(i, grid.getNumCols()-1), "get.gif");
            else if(gal==1)
            grid.setImage(new Location(i, grid.getNumCols()-1), "avoid.gif");
            else
            grid.setImage(new Location(i, grid.getNumCols()-1), null);
        }
        */
        /*
        //Quantity MEDIUM
        //POP UP
        for (int i = 0; i < 2; i++) {
            int ga = (int) (Math.random() * 3);
            if (ga == 0) {
                grid.setImage(new Location(row, grid.getNumCols() - 1), "get.gif");
                grid.setImage(new Location(row1, grid.getNumCols() - 1), "get.gif");
            } else if (ga == 1) {
                grid.setImage(new Location(row, grid.getNumCols() - 1), "avoid.gif");
                grid.setImage(new Location(row1, grid.getNumCols() - 1), "get.gif");
            } else {
                grid.setImage(new Location(row, grid.getNumCols() - 1), null);
                grid.setImage(new Location(row1, grid.getNumCols() - 1), "get.gif");
            }
        }
        */
        //Quantity Small
        //POP UP
        int ga = (int)(Math.random()*3);
        if(ga==0)
            grid.setImage(new Location(row, grid.getNumCols()-1), get);
        else if(ga==1)
            grid.setImage(new Location(row, grid.getNumCols()-1), avoid);
        else
            grid.setImage(new Location(row, grid.getNumCols()-1), null);
    }

    public void scrollRow(int row){
        for(int i = 0; i<grid.getNumCols(); i++){
            String imageNow = grid.getImage(new Location(row, i));
            if((imageNow!=null)&&(imageNow.equals(player)||imageNow.equals(get)||imageNow.equals(avoid))){
                if((imageNow!=null)&&(imageNow.equals(projectile)||imageNow.equals(player))){
                    
                }
                else if(i==0){
                    grid.setImage(new Location(row, 0), null);
                }
                else if(grid.getImage(new Location(row, i))!=null){
                    grid.setImage(new Location(row, i), null);
                    grid.setImage(new Location(row, i-1), imageNow);
                }
            }
        }
    }
    public void scrollLeft()
    {
        for(int r = 0; r<grid.getNumRows(); r++){
            //int pastUserRow = userRow;
            scrollRow(r);
            handleCollision(new Location(userRow, userCol));
        }
    }

    public void handleCollision(Location loc)
    {

        Location toBeCollided = loc;
        String image = grid.getImage(loc);
        boolean isNull = image==null;
        boolean isGet;
        boolean isAvoid;
        
        if(!(isNull)){
            if(image.equals(get)){
                timesGet++;
                grid.setImage(loc, null);
                grid.setImage(new Location(userRow, userCol), player);
            }
            if(image.equals(avoid)){
                timesAvoid++;
                grid.setImage(loc, null);
                grid.setImage(new Location(userRow, userCol), player);
            }
        }
    }
    
    public int getScore()
    {
        int total = timesGet-timesAvoid;
        return total;
    }

    public void updateTitle()
    {
        board.changeScore(getScore());
        grid.setTitle("Game: " + getScore() + " | Time: "+msElapsed/100);
    }

    /*
    public boolean isGameOver()
    {
        if(getScore()>2)
        {
            grid.hideGui();
            return true;
        }
        else
            return false;
    }
    */
    
    /*
    public void addBackground()
    {
        int count = 1;
        for(int r = 0; r<grid.getNumRows(); r++)
        {
            for(int c = 0; c<grid.getNumCols(); c++)
            {
                if(grid.getImage(new Location(r, c))==null)
                {
                    grid.setImage(new Location(r, c), count+".png");
                }
                System.out.println(count);
                count++;
            }
        }
    }
    */
    
    /*
    public String pickRanEnemy()
    {
        int ran = (int)(Math.random()*5+1);
        return "zombie"+ran+".png";
    }
    
    public String pickRanPowerup()
    {
        int ran = (int)(Math.random()*2+1);
        return "p"+ran+".png";
    }
    */
    
    public void project(int col)
    {
        
        //grid.setImage(new Location(userRow, userCol+1), projectile);
       // Grid.pause(100);
       // grid.setImage(new Location(userRow, userCol+1), null);
        for(int i = userCol+1; i<cols-1; i++)
        {
            if(i==cols-2)
                grid.setImage(new Location(userRow, i), null);
            else if(grid.getImage(new Location(userRow, i+1))!=null){
                grid.setImage(new Location(userRow, userCol+1), null);
                grid.setImage(new Location(userRow, i+1), null);
                timesGet++;
                return;
            }
            else{
                grid.setImage(new Location(userRow, i), null);
                Grid.pause(10);
                grid.setImage(new Location(userRow, i+1), projectile);
            }
        }
        
        /*
        if(col == cols-1 || grid.getImage(new Location(userRow, col+1))!=null)
        {
            grid.setImage(new Location(userRow, col), null);
        }
        else
        {
            project(col+1);
        }
                */
    }


    public static void main(String[] args)
    {
        MyGame g = new MyGame();
    }
}