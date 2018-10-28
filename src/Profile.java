public class Profile
{
    private String originalPic;
    private String selectedPic;
    public Profile(String original, String selected)
    {
        originalPic=original;
        selectedPic=selected;
    }
    public String getOg()
    {
        return originalPic;
    }
    public String getSel()
    {
        return selectedPic;
    }
    public void setOg(String original)
    {
        originalPic=original;
    }
    public void setSel(String selected)
    {
        selectedPic=selected;
    }
}