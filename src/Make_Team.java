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


    public Make_Team(int x)
    {
        setContentPane(Main);
        setTitle("User_Info");
        setSize(500,250);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);


        취소Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main_Title update = new Main_Title();
            }
        });
        등록Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputdata(x+1);
                teambuild(x+1);
                dispose();
                Main_Title update = new Main_Title();
            }
        });
    }

    public void inputdata(int x)
    {
        DATABASECLASS DB = new DATABASECLASS();
        ConnectServer cs = new ConnectServer();


        cs.SetTitle(DB.CurrentID,textField1.getText(),textArea1.getText(), x);
    }

    public void teambuild(int x)
    {
        DATABASECLASS DB = new DATABASECLASS();
        ConnectServer cs = new ConnectServer();

        int [] a = new int[7];

        a[0] = cs.teamcount(x) + 1;
        a[1] = DB.CurrentID;
        a[2] = 0;
        a[3] = 0;
        a[4] = 0;
        a[5] = 0;
        a[6] = 1;


        cs.SetTeam(x,a);
        cs.RewriteInfo(DB.CurrentID,2,1);
    }
}
