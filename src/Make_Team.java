import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Make_Team extends JFrame
{

    private JTextField textField1;
    private JTextArea textArea1;
    private JButton 등록Button;
    private JButton 취소Button;
    private JPanel Main;

    DATABASECLASS DB = new DATABASECLASS();
    UserDB User = new UserDB();

    public Make_Team(UserDB User)
    {
        setContentPane(Main);
        setTitle("User_Info");
        setSize(500,250);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);


        this.User = User;

        취소Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        등록Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputdata();
                dispose();
                Main_Title update = new Main_Title(User);
            }
        });
    }

    public void inputdata()
    {
        maketeamDB mtb = new maketeamDB();

        mtb.Title = textField1.getText();
        mtb.Content = textArea1.getText();
        mtb.name = User.name;
        mtb.count++;

        DB.inputmaketeam(mtb);
    }
}
