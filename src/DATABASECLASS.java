import java.util.Vector;

public class DATABASECLASS
{
     static Vector<UserDB> Users = new Vector<>();
     static Vector<gesipanDB> Gesipan = new Vector<>();

     static Vector<maketeamDB> maketeam = new Vector<>();

    public void inputUsers(UserDB a)
    {
        Users.add(a);
    }

    public void inputGesipan(gesipanDB a)
    {
        Gesipan.add(a);
    }

    public boolean isEmpty()
    {
        if(Users == null)
            return true;
        else
            return false;
    }

    public void inputmaketeam(maketeamDB a)
    {
        maketeam.add(a);
    }
}
