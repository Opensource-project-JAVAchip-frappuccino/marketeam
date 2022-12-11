public class maketeamDB
{
    String Title = new String();

    String Content = new String();

    String name = new String();

    UserDB User = new UserDB();

    int teamnum = 1;

    public maketeamDB()
    {

    }
    public maketeamDB(UserDB User)
    {
        this.User = User;
    }
}
