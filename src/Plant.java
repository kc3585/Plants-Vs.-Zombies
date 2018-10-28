public class Plant
{
    private String type;
    private String power;
    private int health;
    private int damage;
    private int cost;
    public Plant(String spawn)
    {
        type=spawn;
        if(spawn.equals("flower"))
        {
            power="sun";
            health=30; //6 bites
            damage=0;
            cost=5;
        }
        else if(spawn.equals("pea"))
        {
            power="none";
            health=30; //6 bites
            damage=2; //
            cost=10;
        }
        else if(spawn.equals("repeater"))
        {
            power="none";
            health=30; //6 bites
            damage=4;
            cost=20;
        }
        else if(spawn.equals("gatling"))
        {
            power="dmg+";
            health=30; //6 bites
            damage=8;
            cost=20;   //CAN ONLY BE PLACED ON REPEATER! (UPGRADE)
        }
        else if(spawn.equals("walnut"))
        {
            power="none";
            health=360; //72 bites
            damage=0;
            cost=5;
        }
        else if(spawn.equals("tallnut"))
        {
            power="block+";
            health=720; //144 bites
            damage=0;
            cost=25;
        }
    }
    
    public String type()
    {
        return type;
    }
    public String power()
    {
        return power;
    }
    public int HP()
    {
        return health;
    }
    public int dmg()
    {
        return damage;
    }
    public int cost()
    {
        return cost;
    }
    public void reduceHP(int inflicted)
    {
        if(inflicted>health)
            health=0;
        else
            health-=inflicted;
    }
    public String name()
    {
        if(type.equals("flower"))
            return "Sunflower";
        else if(type.equals("pea"))
            return "Pea Shooter";
        else if(type.equals("repeater"))
            return "Repeater";
        else if(type.equals("gatling"))
            return "Gatling Pea";
        else if(type.equals("walnut"))
            return "Walnut";
        else if(type.equals("tallnut"))
            return "Tallnut";
        return "";
    }
}