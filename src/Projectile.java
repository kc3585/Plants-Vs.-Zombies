public class Projectile
{
    private int damage;
    private String effect;
    private boolean moved;
    public Projectile(int dmg,String eff)
    {
        damage=dmg;
        effect=eff;
        moved=false;
    }
    public int dmg()
    {
        return damage;
    }
    public String effect()
    {
        return effect;
    }
    public boolean isMoved()
    {
        return moved;
    }
    public void setMove(boolean b)
    {
        moved=b;
    }
    public void combineDmg(int multipliedBy)
    {
        damage*=multipliedBy;
    }
}