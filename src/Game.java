//Import Statements for Menu
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.applet.*;

public class Game
{
    //Instance Variables for Menus
    private ActionListener gameListener;
    private Timer gameRepeat;
    
    private MainMenu menu;
    private HelpMenu hMenu;
    private Win won;
    private Score board;
    
    //Instance Variables for Game
    private Grid grid;
    private int userRow;
    private int userCol;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;
    private boolean lost;
    
    private Enemy[][] enemies;
    private Plant[][] plants;
    private int sun;
    private int level;
    private int spawnTimer;
    private int finalWave;
    private boolean finale;
    private boolean firstWave;
    private boolean started;
    private boolean nextLevel;
    private Profile[][] infos;
    private Projectile[][] projs;
    
    private boolean projectile;
    private int pastUserRow;
    private boolean projectileSent;
    private boolean specialEnemy;
    
    private boolean win;
    
    private AudioClip introSong;
    
    public Game()
    {
        introSong = Applet.newAudioClip(this.getClass().getResource("music.wav"));
        introSong.play();

        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {

        }
        //Creating all the 
        menu = new MainMenu();
        hMenu = new HelpMenu();
        board = new Score();
        win = true;
        
        gameListener = new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(menu.gameStarted())
                {
                    introSong.stop();
                    gameRepeat.stop();
                    menu.hideGui();
                    init();
                    play();
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
            }
        };
        
        gameRepeat = new Timer(30, gameListener);
        gameRepeat.start();
    }
    
    public void init()
    {
        introSong.play();
        grid = new Grid(5, 9, "backgroundA.jpg"); //5,10
        userRow = 0;
        userCol = 0;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        enemies=new Enemy[grid.getNumRows()][grid.getNumCols()];
        plants=new Plant[grid.getNumRows()][grid.getNumCols()];
        updateTitle();
        //backgroundSound=Applet.newAudioClip(this.getClass().getResource("INSERT NAME .WAV"));
        lost=false;
        
        level=1;
        sun=10;
        finalWave=12;
        spawnTimer=5000;
        finale=false;
        started=true;
        nextLevel=true;
        firstWave=true;
        infos=new Profile[grid.getNumRows()][grid.getNumCols()];
        projs=new Projectile[grid.getNumRows()][grid.getNumCols()];
        for(int r=0;r<grid.getNumRows();r++)
            for(int c=0;c<grid.getNumCols();c++)
                infos[r][c]=new Profile(null,"BGSelect.png");
    }
    
    public void play()
    {
        gameListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                background();
                //Handles Levels 1-5 NOT INCL. BOSS LVL
                if(finalWave<=0 && !nextLevel){ //final wave ran out, FINALE starts
                    finale=true;
                    nextLevel=true;
                }
                else if(finalWave<=0){
                    nextLevel=false;
                    level++;
                    spawnTimer*=2; //Return to normal
                    finalWave=10;
                }

                if(finale){
                    spawnTimer/=2; //Spawn 2x as FAST
                    finalWave=10;
                    finale=false;
                }
                
                handleKeyPress();

                int sunSpawnChance=(int)(Math.random()*100)+1;
                if(msElapsed % 1000 == 0){
                    if(sunSpawnChance>=90){
                        if(sunSpawnChance>=95)
                            sun+=10;
                        else
                            sun+=5;
                    }
                }

                if(msElapsed % 10000 == 0 && msElapsed!=0) //produce sun every 10 sec
                    regulate(); //Produce sun
                if(msElapsed <= 20000){} //originally 25 sec
                else if(msElapsed % spawnTimer == 0)//spawnTimer=5000 (5 sec)
                    spawn(level);
                if(msElapsed % 3000 == 0)
                    detection();
                if(msElapsed % 1000 == 0) //move projectiles
                    moveProjectiles();
                if(msElapsed % 500 == 0)
                    attackingZombies();
                ZeroHPNPCs();
                if (msElapsed % 6000 == 0)
                {
                    scrollLeft();
                    //spawn();
                }

                if(isGameOver()&&win)
                {
                  //  endSong.play();
                    win = false;
                    grid.hideGui();
                    won = new Win(false);
                }
                
                updateTitle();
                msElapsed += 100;
            }
        };
        
        gameRepeat = new Timer(100, gameListener);
        gameRepeat.start();
    }
    
    public void handleKeyPress()
    {
        int key=grid.checkLastKeyPressed();
        //System.out.println(key);
        boolean moved=false;
        Profile prev=infos[userRow][userCol];
        Location prevLoc=new Location(userRow,userCol);
        
        if(key==38 && userRow>0){ //Up
            userRow--;
            moved=true;}
        else if(key==40 && userRow<grid.getNumRows()-1){ //Down
            userRow++;
            moved=true;}
        else if(key==37 && userCol>0){ //Left
            userCol--;
            moved=true;}
        else if(key==39 && userCol<grid.getNumCols()-1){ //Right
            userCol++;
            moved=true;}
        
        //Keyboard Controls
        if(key==32 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null && sun>=10) // Space
        {
            infos[userRow][userCol].setOg("Peashooter.png");
            infos[userRow][userCol].setSel("PeashooterSEL.png");
            plants[userRow][userCol]=new Plant("pea");
            sun-=10;
        }
        else if(key==49 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null && sun>=5) //1
        {
            infos[userRow][userCol].setOg("Sunflower.png");
            infos[userRow][userCol].setSel("SunflowerSEL.png");
            plants[userRow][userCol]=new Plant("flower");
            //Produces sun when spawned, counteracts sun cost
        }
        else if(key==50 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null && sun>=5) //2
        {
            infos[userRow][userCol].setOg("Walnut.png");
            infos[userRow][userCol].setSel("WalnutSEL.png");
            plants[userRow][userCol]=new Plant("walnut");
            sun-=5;
        }
        else if(key==82 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null) //Key "R" - REMOVES PLANT!
        {
            infos[userRow][userCol].setOg(null);
            infos[userRow][userCol].setSel("BGSelect.png");
            plants[userRow][userCol]=null;
        }
        
        boolean pause = true;
        if(key==80)
        {
            while(pause)
            {
                Grid.pause(1);
                int key2 = grid.checkLastKeyPressed();
                if(key2==80)
                    pause=false;
            }
        }
        
        Profile curr=infos[userRow][userCol];
        Location currLoc=new Location(userRow,userCol);
        if(moved)
        {
            grid.setImage(prevLoc, prev.getOg());
            grid.setImage(currLoc,curr.getSel());
        }
        else
        {
            grid.setImage(currLoc, curr.getSel());
        }
    }
    
    public void background()
    {
        for(int r=0;r<grid.getNumRows();r++)
        {
            for(int c=0;c<grid.getNumCols();c++)
            {
                if(grid.getImage(new Location(r,c))==null)
                    infos[r][c]=new Profile(null,"BGSelect.png");
            }
        }
    }
    public void ZeroHPNPCs()
    {
        for(int r=0;r<grid.getNumRows();r++)
        {
            for(int c=0;c<grid.getNumCols();c++){
                if(enemies[r][c]!=null && enemies[r][c].HP()==0) //Clean up dead enemies
                {
                    enemies[r][c]=null;
                    grid.setImage(new Location(r,c), null);
                    infos[r][c]=null;
                    finalWave--;
                }
                if(plants[r][c]!=null && plants[r][c].HP()==0) //Clean up dead plants
                {
                    plants[r][c]=null;
                    grid.setImage(new Location(r,c),null);
                    infos[r][c]=null;
                }
            }
        }
    }
    public void regulate()
    {
        for(int r=0;r<grid.getNumRows();r++)
            for(int c=0;c<grid.getNumCols();c++)
            {
                Location curr=new Location(r,c);
                if(plants[r][c]!=null && plants[r][c].type().equals("flower")) //producing sun
                    sun+=5;
                
            }
    }
    //LEVELS
    public void spawn(int tier) //populateRightEdge();
    {
        if(tier==1) //Regular
        {
            int chance=(int)(Math.random()*grid.getNumRows());
            if(chance==0 && firstWave){
                int row=(int)(Math.random()*grid.getNumRows());
                grid.setImage(new Location(row,grid.getNumCols()-1), "ZombieFlag.png");
                enemies[row][grid.getNumCols()-1]=new Enemy("flag");
                infos[row][grid.getNumCols()-1]=new Profile("ZombieFlag.png","ZombieFlagSEL.png");
                started=false;
            }
            
            for(int i=0;i<chance;i++)
            {
                int row=(int)(Math.random()*grid.getNumRows());
                int startingRow=0;
                if(started && i==0)
                {
                    grid.setImage(new Location(row,grid.getNumCols()-1), "ZombieFlag.png");
                    enemies[row][grid.getNumCols()-1]=new Enemy("flag");
                    infos[row][grid.getNumCols()-1]=new Profile("ZombieFlag.png","ZombieFlagSEL.png");
                    
                    startingRow=row;
                    started=false;
                }
                else
                {
                    while(row==startingRow && firstWave)
                        row=(int)(Math.random()*grid.getNumRows());
                    grid.setImage(new Location(row,grid.getNumCols()-1), "zombieReg.png");
                    enemies[row][grid.getNumCols()-1]=new Enemy("normal");
                    infos[row][grid.getNumCols()-1]=new Profile("zombieReg.png","zombieRegSEL.png");
                }
            }
            firstWave=false;
        }
        else if(tier==2)//Regular & Cone
        {
            int chance=(int)(Math.random()*grid.getNumRows());
            //int[] pastSpawn=new int[chance];
            if(chance==0 && firstWave){
                int row=(int)(Math.random()*grid.getNumRows());
                grid.setImage(new Location(row,grid.getNumCols()-1), "ZombieFlag.png");
                enemies[row][grid.getNumCols()-1]=new Enemy("flag");
                infos[row][grid.getNumCols()-1]=new Profile("ZombieFlag.png","ZombieFlagSEL.png");
                started=false;
            }
            
            for(int i=0;i<chance;i++)
            {
                int row=(int)(Math.random()*grid.getNumRows());
                int startingRow=0;
                if(started && i==0)
                {
                    grid.setImage(new Location(row,grid.getNumCols()-1), "ZombieFlag.png");
                    enemies[row][grid.getNumCols()-1]=new Enemy("flag");
                    infos[row][grid.getNumCols()-1]=new Profile("ZombieFlag.png","ZombieFlagSEL.png");
                    
                    startingRow=row;
                    started=false;
                }
                else
                {
                    while(row==startingRow && firstWave)
                        row=(int)(Math.random()*grid.getNumRows());
                    int spawn=(int)(Math.random()*10)+1;
                    if(spawn>=7){
                        grid.setImage(new Location(row,grid.getNumCols()-1), "zombieReg.png");
                        enemies[row][grid.getNumCols()-1]=new Enemy("normal");
                        infos[row][grid.getNumCols()-1]=new Profile("zombieReg.png","zombieRegSEL.png");
                    }
                    else{
                        grid.setImage(new Location(row,grid.getNumCols()-1), "zombieCone.png");
                        enemies[row][grid.getNumCols()-1]=new Enemy("armored");
                        infos[row][grid.getNumCols()-1]=new Profile("zombieCone.png","zombieConeSEL.png");
                    }
                }
            }
            firstWave=false;
        }
        else if(tier==3)//Bucket & Football
        {
            
        }
        else if(tier==4)//Bucket & Football
        {
            
        }
        else if(tier==5)//Yeti & Gargantuar
        {
            
        }
        else//Zomboss & Gargantuar(s)
        {
            
        }
    }
    
    public void moveProjectiles()
    {
        for(int r=0;r<grid.getNumRows();r++)
        {
            for(int c=0;c<grid.getNumCols();c++)
            {
                Location curr=new Location(r,c);
                if(projs[r][c]!=null && !projs[r][c].isMoved())
                {
                    if(c==grid.getNumCols()-1) //Last row = disappear
                    {
                        infos[r][c]=null;
                        projs[r][c]=null;
                        grid.setImage(curr,null);
                    }
                    else if(enemies[r][c+1]!=null) //Enemy in front
                    {
                        enemies[r][c+1].reduceHP(projs[r][c].dmg());
                        projs[r][c]=null;
                        infos[r][c]=null;
                        grid.setImage(curr,null);
                    }
                    else if(plants[r][c+1]!=null) //Plant in front
                    {
                        infos[r][c]=null;
                        grid.setImage(curr,null);
                        projs[r][c+1]=projs[r][c];
                        projs[r][c+1].setMove(true);
                        projs[r][c]=null;
                    }
                    else //Nothing in front
                    {
                        
                        projs[r][c+1]=projs[r][c];
                        projs[r][c+1].setMove(true);
                        projs[r][c]=null;
                        if(plants[r][c-1]==null){
                            infos[r][c+1]=infos[r][c];
                            infos[r][c]=null;
                        }
                        else
                            infos[r][c+1]=new Profile("pea.png","peaSEL.png");
                        grid.setImage(curr, null);
                        if(enemies[r][c+1]==null && plants[r][c+1]==null)
                            grid.setImage(new Location(r,c+1), infos[r][c+1].getOg());
                    }
                }
            }
        }
        for(int r=0;r<grid.getNumRows();r++)
            for(int c=0;c<grid.getNumCols();c++)
                if(projs[r][c]!=null)
                    projs[r][c].setMove(false);
    }
    //Projectile Method Varying Intervals
    public void detection()
    {
        for(int row=0;row<grid.getNumRows();row++)
        {
            boolean peaPresent=false;
            boolean enemyPresent=false;
            for(int c=0;c<grid.getNumCols();c++){
                if(plants[row][c]!=null)
                    peaPresent=true;
                else if(enemies[row][c]!=null)
                    enemyPresent=true;
            }
            if(peaPresent && enemyPresent)
                shootProj(row);
        }
    }
    public void shootProj(int row)
    {
            for(int c=0;c<grid.getNumCols();c++)
            {
                Location curr=new Location(row,c);
                if(plants[row][c]!=null && plants[row][c].type().equals("pea")) //Shooting (placing a pea down)
                {
                    boolean canPlace=false;
                    int col=c+1;
                    int combinedPeas=1;
                    for(int i=c+1;i<grid.getNumCols();i++)
                    {
                        if(plants[row][i]!=null && plants[row][i].type().equals("pea") && plants[row][i-1].type().equals("pea"))
                            combinedPeas++;
                        if(enemies[row][i]!=null){
                            enemies[row][i].reduceHP(plants[row][c].dmg());
                            i=grid.getNumCols();
                        }
                        else if(enemies[row][i]==null && plants[row][i]==null)
                        {
                            canPlace=true;
                            col=i;
                            i=grid.getNumCols();
                        }
                    }
                    if(canPlace)
                    {
                        infos[row][col]=new Profile("pea.png","peaSEL.png");
                        grid.setImage(new Location(row,col), "pea.png");
                        projs[row][col]=new Projectile(plants[row][c].dmg(),plants[row][c].power());
                        projs[row][col].combineDmg(combinedPeas);
                    }
                }
            }
    }
    //HELPER METHOD
    public void scrollRow(int row)
    {
        for(int c=0;c<grid.getNumCols();c++)
        {
            Location curr=new Location(row,c);
            Enemy foe=enemies[row][c];
            if(foe!=null) //Enemy present (only thing that needs to move
            {
                if(c==0) //At end
                {
                    grid.setImage(curr, null);
                    lost=true;
                    c=grid.getNumCols();
                }
                else if(plants[row][c-1]!=null) //Plant in spot
                {
                    Plant victim=plants[row][c-1];
                    if(victim.HP()==0)
                    {
                        //Updating
                        plants[row][c-1]=null;
                        enemies[row][c-1]=foe;
                        enemies[row][c]=null;
                        infos[row][c-1]=new Profile(foe.ogPic(),foe.selPic());
                        infos[row][c]=null;
                        
                        grid.setImage(curr, null);
                        grid.setImage(new Location(row,c-1), foe.ogPic());
                    }
                }
                else if(enemies[row][c-1]!=null) //Enemy in spot
                {
                    Enemy foe2=enemies[row][c-1];
                    if(foe2.HP()>foe.HP()){//DOESN'T COVER ULTRAS
                        grid.setImage(new Location(row,c-1), foe2.ogPic());
                        grid.setImage(curr,null);
                        
                        enemies[row][c]=null;
                        infos[row][c]=null;
                        foe2.buffHP(foe.HP());
                        foe2.buffAtk(foe.dmg());
                    }
                    else{
                        grid.setImage(new Location(row,c-1),foe.ogPic());
                        grid.setImage(curr, null);
                        
                        enemies[row][c]=null;
                        enemies[row][c-1]=foe;
                        infos[row][c]=null;
                        infos[row][c-1]=new Profile(foe.ogPic(),foe.selPic());
                        foe.buffHP(foe2.HP());
                        foe.buffAtk(foe2.dmg());
                    }
                }
                else //Nothing in spot
                {
                    enemies[row][c-1]=foe;
                    enemies[row][c]=null;
                    infos[row][c-1]=new Profile(foe.ogPic(),foe.selPic());
                    infos[row][c]=null;
                    grid.setImage(curr,null);
                    grid.setImage(new Location(row,c-1),foe.ogPic());
                }
            }
        }
    }
    public void scrollLeft()
    {
        for(int r=0;r<grid.getNumRows();r++)
            scrollRow(r);
    }
    
    public void attackingZombies()
    {
        for(int r=0;r<grid.getNumRows();r++)
            for(int c=0;c<grid.getNumCols();c++){
                if(c!=0 && plants[r][c-1]!=null && enemies[r][c]!=null)
                {
                    Plant victim=plants[r][c-1];
                    Enemy attacker=enemies[r][c];
                    victim.reduceHP(attacker.dmg());
                }
            }
    }
    
    public int getScore()
    {
        int score=timesGet-timesAvoid;
        if(score<0)
            score=0;
        
        return score;
        //int score=msElapsed/1000;
    }
    
    public void updateTitle()
    {
        String selectLife="";
        String selectType="";
        String selectDmg="";
        if(enemies[userRow][userCol]!=null) 
        {
            selectLife=""+enemies[userRow][userCol].HP();
            selectType=enemies[userRow][userCol].name()+" HP:";
            selectDmg="        Damage:"+enemies[userRow][userCol].dmg();
        }
        else if(plants[userRow][userCol]!=null)
        {
            selectLife=""+plants[userRow][userCol].HP();
            selectType=plants[userRow][userCol].name()+" HP:";
            selectDmg="        Damage:"+plants[userRow][userCol].dmg();
        }
        
        board.changeSun(sun);
        board.changeLife(selectLife);
        board.changeType(selectType);
        board.changeDamage(selectDmg);
        
        grid.setTitle("Sun: "+sun+"        "+selectType+selectLife+selectDmg);
        //"Score:  " + getScore()+ "    "+
    }
    
    public boolean isGameOver()
    {
        return lost;
    }
    
    public static void main(String [] args)
    {
        Game game = new Game();
    }
}
