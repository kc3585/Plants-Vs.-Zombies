//Francis Sy
import java.applet.*;
import java.util.ArrayList;
public class Game_1
{
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
    private boolean started;
    private boolean nextLevel;
    private Profile[][] infos;
    private Projectile[][] projs;
    
    private boolean projectile;
    private int pastUserRow;
    private boolean projectileSent;
    private boolean specialEnemy;
    
    public Game_1()
    {
        grid = new Grid(6, 9,"background2.jpg"); //5,10
        /*
        for(int r=0;r<grid.getNumRows();r++)
        {
            for(int c=0;c<grid.getNumCols();c++)
            {
                if((r%2==0 && c%2!=0)||(r%2!=0 && c%2==0))
                    grid.setImage(new Location(r,c), "Grass2.png");
                else
                    grid.setImage(new Location(r,c),"Grass.png");
            }
        }
        */
        userRow = 0;
        userCol = 0;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        enemies=new Enemy[grid.getNumRows()][grid.getNumCols()];
        plants=new Plant[grid.getNumRows()][grid.getNumCols()];
        updateTitle();
        lost=false;
        
        level=1;
        sun=25;
        finalWave=12;
        spawnTimer=5000;
        finale=false;
        started=true;
        nextLevel=true;
        infos=new Profile[grid.getNumRows()][grid.getNumCols()];
        projs=new Projectile[grid.getNumRows()][grid.getNumCols()];
        for(int r=0;r<grid.getNumRows();r++)
        {
            for(int c=0;c<grid.getNumCols();c++)
            {
                if((r%2==0 && c%2!=0)||(r%2!=0 && c%2==0))
                    infos[r][c]=new Profile("Grass2.png","Grass2SEL.png");
                else
                    infos[r][c]=new Profile("Grass.png","grassSEL.png");
            }
        }
    }
    
    public void play()
    {
        while (!isGameOver())
        {
            //introSong.loop();
            //background();
            /*
            if(finalWave<=0 && !nextLevel){ //final wave ran out, FINALE starts
                finale=true;
                nextLevel=true;
            }
            else if(finalWave<=0){
                finale=false;
                nextLevel=false;
                level++;
            }
            
            if(finale){
                spawnTimer/=2;
                finalWave=10;
            }
            */
            
            grid.pause(100);
            handleKeyPress();
            
            int sunSpawnChance=(int)(Math.random()*100)+1;
            if(msElapsed % 1000 == 0){
                if(sunSpawnChance>=80){
                    if(sunSpawnChance>=95)
                        sun+=10;
                    else sun+=5;
                }
            }
            
            if(msElapsed % 3000 == 0 && msElapsed!=0)
                regulate(); //Produce sun and places pea down
            if(msElapsed % spawnTimer == 0)//spawnTimer=5000 (5 sec)
                spawn(level);
            if(msElapsed % 1000 == 0) //move projectiles
                moveProjectiles();
            ZeroHPEnemies();
            if (msElapsed % 3000 == 0)
            {
                scrollLeft();
                //spawn();
            }
            updateTitle();
            msElapsed += 100;
        }
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
        if(key==32 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null && sun>=15) // Space
        {
            infos[userRow][userCol].setOg("Peashooter.png");
            infos[userRow][userCol].setSel("PeashooterSEL.png");
            plants[userRow][userCol]=new Plant("pea");
            sun-=15;
        }
        else if(key==49 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null && sun>=20) //1
        {
            infos[userRow][userCol].setOg("Sunflower.png");
            infos[userRow][userCol].setSel("SunflowerSEL.png");
            plants[userRow][userCol]=new Plant("flower");
            sun-=20;
        }
        else if(key==50 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null && sun>=40) //2
        {
            infos[userRow][userCol].setOg("Walnut.png");
            infos[userRow][userCol].setSel("WalnutSEL.png");
            plants[userRow][userCol]=new Plant("walnut");
            sun-=40;
        }
        else if(key==82 && enemies[userRow][userCol]==null && projs[userRow][userCol]==null) //Key "R" - REMOVES PLANT!
        {
            if((userRow%2==0 && userCol%2!=0)||(userRow%2!=0 && userCol%2==0))
            {
                infos[userRow][userCol].setOg("Grass2.png");
                infos[userRow][userCol].setSel("Grass2SEL.png");
            }
            else
            {
                infos[userRow][userCol].setOg("Grass.png");
                infos[userRow][userCol].setSel("grassSEL.png");
            }
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
                {
                    if((r%2==0 && c%2!=0)||(r%2!=0 && c%2==0)){
                        grid.setImage(new Location(r,c), "Grass2.png");
                        infos[r][c]=new Profile("Grass2.png","Grass2SEL.png");
                    }
                    else{
                        grid.setImage(new Location(r,c),"Grass.png");
                        infos[r][c]=new Profile("Grass.png","grassSEL.png");
                    }
                }
            }
        }
    }
    public void ZeroHPEnemies()
    {
        for(int r=0;r<grid.getNumRows();r++)
        {
            for(int c=0;c<grid.getNumCols();c++)
                if(enemies[r][c]!=null && enemies[r][c].HP()==0)
                {
                    enemies[r][c]=null;
                    grid.setImage(new Location(r,c), null);
                    infos[r][c]=null;
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
                    sun+=4;
                if(plants[r][c]!=null && plants[r][c].type().equals("pea")) //Shooting (placing a pea down)
                {
                    boolean canPlace=false;
                    int col=c+1;
                    for(int i=c+1;i<grid.getNumCols();i++)
                    {
                        if(enemies[r][i]!=null){
                            enemies[r][i].reduceHP(plants[r][c].dmg());
                            i=grid.getNumCols();
                        }
                        else if(enemies[r][i]==null && plants[r][i]==null)
                        {
                            canPlace=true;
                            col=i;
                            i=grid.getNumCols();
                        }
                    }
                    if(canPlace)
                    {
                        infos[r][col]=new Profile("pea.png","peaSEL.png");
                        grid.setImage(new Location(r,col), "pea.png");
                        projs[r][col]=new Projectile(plants[r][c].dmg(),"none");
                    }
                }
            }
    }
    public void spawn(int tier) //populateRightEdge();
    {
        if(tier==1) //Regular
        {
            int chance=(int)(Math.random()*grid.getNumRows());
            //int[] pastSpawn=new int[chance];
            for(int i=0;i<chance;i++)
            {
                int row=(int)(Math.random()*grid.getNumRows());
                if(started && i==0)
                {
                    grid.setImage(new Location(row,grid.getNumCols()-1), "ZombieFlag.png");
                    enemies[row][grid.getNumCols()-1]=new Enemy("normal");
                    infos[row][grid.getNumCols()-1]=new Profile("ZombieFlag.png","ZombieFlagSEL.png");
                    
                    started=false;
                }
                else
                {
                    grid.setImage(new Location(row,grid.getNumCols()-1), "zombieReg.png");
                    enemies[row][grid.getNumCols()-1]=new Enemy("normal");
                    infos[row][grid.getNumCols()-1]=new Profile("zombieReg.png","zombieRegSEL.png");
                }
                finalWave--;
            }
        }
        else if(tier==2)//Regular & Cone
        {
            
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
                    if(c==grid.getNumCols()-1)
                    {
                        infos[r][c]=null;
                        projs[r][c]=null;
                        grid.setImage(curr,null);
                    }
                    else if(enemies[r][c+1]!=null)
                    {
                        enemies[r][c+1].reduceHP(projs[r][c].dmg());
                        projs[r][c]=null;
                        infos[r][c]=null;
                        grid.setImage(curr,null);
                    }
                    else if(plants[r][c+1]!=null)
                    {
                        infos[r][c]=null;
                        grid.setImage(curr,null);
                        projs[r][c+1]=projs[r][c];
                        projs[r][c+1].setMove(true);
                        projs[r][c]=null;
                    }
                    else
                    {
                        projs[r][c+1]=projs[r][c];
                        projs[r][c+1].setMove(true);
                        projs[r][c]=null;
                        infos[r][c+1]=infos[r][c];
                        infos[r][c]=null;
                        grid.setImage(curr, null);
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
                    if(victim.HP()!=0)
                        victim.reduceHP(foe.dmg());
                    else
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
    
    public void handleCollision(Location loc)
    {
        String image=grid.getImage(loc);
        if(image.equals("healthPack.png"))
            timesGet++;
        else if(image.equals(""))
            timesAvoid++;
        grid.setImage(loc, null);
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
        
        grid.setTitle("Sun: "+sun+"        "+selectType+selectLife+selectDmg);
        //"Score:  " + getScore()+ "    "+
    }
    
    public boolean isGameOver()
    {
        return lost;
    }
    
    public static void test()
    {
        Game_1 game = new Game_1();
        game.play();
    }
    
    public static void main(String[] args)
    {
        test();
    }
}