import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Main_Title_professor extends JFrame
{

    private JPanel panel1;
    private JButton MYINFOButton;
    private JButton LOGOUTButton;

    private JLabel user_info_mini;
    private JComboBox comboBox1;
    private JTable Matching_table;
    private JButton matching_DoneButton;
    private JButton settingButton;
    private JButton applyStatusButton;
    private JLabel Subject_info;
    private javax.swing.JScrollPane JScrollPane;


    UserDB User = new UserDB();

    public Main_Title_professor()
    {
        setContentPane(panel1);
        setTitle("Who_is_Leader?");
        setSize(1000,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        JScrollPane.setViewportView(Matching_table);

        this.User = User;

        User_info_mini_setting();
        setComboBox1();

        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                Login_Title new_login = new Login_Title();
            }
        });
        MYINFOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                User_Info update = new User_Info();
            }
        });
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Subject subject = new Subject(User);
                dispose();
            }
        });


        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSubject_info(comboBox1.getSelectedIndex());
            }
        });
        matching_DoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                matching(comboBox1.getSelectedIndex());
                JOptionPane.showMessageDialog(null, "팀 선정이 완료되었습니다.", "매칭", JOptionPane.DEFAULT_OPTION);

            }
        });
    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }

    private void User_info_mini_setting()
    {
        StringBuilder user_info_buf = new StringBuilder();
        ConnectServer cs = new ConnectServer();
        DATABASECLASS DB = new DATABASECLASS();

        user_info_buf.append("<html>");
        String name = new String("name");
        user_info_buf.append("이름 : " + cs.GetUserinfo(DB.CurrentID,name));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("학번 : " + Integer.toString(DB.CurrentID));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        String major = new String("major");
        user_info_buf.append("학과 : " + cs.GetUserinfo(DB.CurrentID,major));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("학년 : " + Integer.toString(cs.GetUserinfo(DB.CurrentID,2)));
        user_info_buf.append(" <br>");

        user_info_buf.append("</html>");

        String user_info = new String(user_info_buf.toString());


        user_info_mini.setText(user_info);
    }

    public void setComboBox1()
    {
        DATABASECLASS DB = new DATABASECLASS();

        ConnectServer cs = new ConnectServer();

        String subject = new String("subject");
        String name = new String("name");
        for(int i = 1; i <= cs.subject_row_size(); i++)
        {
            comboBox1.addItem(cs.GetCourse(i,subject) + '/' + cs.GetCourse(i,name));
        }

    }

    public void setSubject_info(int i)
    {
        StringBuilder user_info_buf = new StringBuilder();


        DATABASECLASS DB = new DATABASECLASS();
        ConnectServer cs = new ConnectServer();
        user_info_buf.append("<html>");
        user_info_buf.append("과목명 : " + cs.GetCourse(i+1,"subject"));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("담당 교수 : " + cs.GetCourse(i+1,"name"));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("수강 학년 : " + cs.GetCourse(i+1,1));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("총 수강 인원 : " + cs.GetCourse(i+1,2));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("사용 언어 : " + cs.GetCourse(i+1,"language"));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("선수과목 : " + cs.GetCourse(i+1,"precourse"));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("팀당 인원 수 : " + cs.GetCourse(i+1,3));
        user_info_buf.append(" <br>");
        user_info_buf.append("</html>");

        String user_info = new String(user_info_buf.toString());


        Subject_info.setText(user_info);
    }

    public void matching(int x)
    {
        ConnectServer cs = new ConnectServer();


        for(int i = 0; i < cs.teamtitlecount(x+1); i++)
        {
            cs.deleteteam(x+1,cs.GetCourse(x+1,3),Integer.parseInt(cs.GetTitle(x+1,i+1,"id")));
        }
        cs.matching(x+1);

    }

}
