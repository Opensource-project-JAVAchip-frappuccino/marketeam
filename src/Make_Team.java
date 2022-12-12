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
        // TODO Auto-generated method stub
        System.out.println("[GUI (awt/swing) - JFrame 프레임 창 크기 고정 수행 실시]");


        //TODO 부모 프레임 크기 설정 (가로, 세로)
        setSize(450, 300);

        //TODO 부모 프레임을 화면 가운데에 배치
        setLocationRelativeTo(null);

        //TODO 부모 프레임 창 크기 고정 실시
        setResizable(false);

        //TODO 부모 레이아웃 설정
        getContentPane().setLayout(null);

        //TODO 부모 프레임이 보이도록 설정
        setVisible(true);

        setContentPane(Main);
        setTitle("User_Info");
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
