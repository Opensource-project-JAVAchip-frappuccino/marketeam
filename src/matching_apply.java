import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// matching 버튼 누르면 뜨는 창
public class matching_apply extends JFrame
{
    private JPanel mainpanel;
    private JButton APPLYButton;
    private JButton BACKButton;

    UserDB User = new UserDB();
    public matching_apply(){
        // TODO Auto-generated method stub
        System.out.println("[GUI (awt/swing) - JFrame 프레임 창 크기 고정 수행 실시]");


        //TODO 부모 프레임 크기 설정 (가로, 세로)
        setSize(380, 200);

        //TODO 부모 프레임을 화면 가운데에 배치
        setLocationRelativeTo(null);

        //TODO 부모 프레임 창 크기 고정 실시
        setResizable(false);

        //TODO 부모 레이아웃 설정
        getContentPane().setLayout(null);

        //TODO 부모 프레임이 보이도록 설정
        setVisible(true);

        setContentPane(mainpanel);
        setTitle("");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main_Title title = new Main_Title();
            }
        });

        APPLYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main_Title title = new Main_Title();
            }
        });
    }
}
