public class Ultra extends Enemy
{
    private String power;
    public Ultra(String spawn, String pow)
    {
        super(spawn);
        buffHP(20);
        buffAtk(10);
        power=pow;
        /*
        Giga Football:  150 HP
        Yeti: 46
        Gargantuar: 150 HP/ INSTANT destruction
        //Zomboss: 1583 HP/ Instant
        */
    }
    
    public void usePower()
    {
        if(power.equals("shield"))//Football+
        {
            buffHP(15);    
            buffAtk(-5);
        }
        if(power.equals("energize"))//Yeti
        {
            buffAtk(15);
            buffHP(-5);
        }
        if(power.equals("powered")) //Gargantuar
        {
            buffHP(15);
            buffAtk(15);
        }
        if(power.equals("finale"))//Zomboss
        {
            buffAtk(30);
            buffHP(30);
        }
    }
    
    public String pow()
    {
        return power;
    }
}