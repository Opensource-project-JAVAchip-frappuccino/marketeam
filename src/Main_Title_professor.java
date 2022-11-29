import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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


    UserDB User = new UserDB();

    public Main_Title_professor(UserDB User)
    {
        setContentPane(panel1);
        setTitle("User_Info");
        setSize(1000,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

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
                User_Info update = new User_Info(User);
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
    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }

    private void User_info_mini_setting()
    {
        StringBuilder user_info_buf = new StringBuilder();



        user_info_buf.append("<html>");
        user_info_buf.append("이름 : " + User.name);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("학번 : " + Integer.toString(User.hakbun));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("학과 : " + User.major);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("학년 : 교수");
        user_info_buf.append(" <br>");

        user_info_buf.append("</html>");

        String user_info = new String(user_info_buf.toString());


        user_info_mini.setText(user_info);
    }

    public void setComboBox1()
    {
        DATABASECLASS DB = new DATABASECLASS();

        for(int i = 0; i < DB.Gesipan.size(); i++)
        {
            comboBox1.addItem(DB.Gesipan.get(i).subject_name + '/' + DB.Gesipan.get(i).professor);
        }

    }

    public void setSubject_info(int i)
    {
        StringBuilder user_info_buf = new StringBuilder();


        DATABASECLASS DB = new DATABASECLASS();
        user_info_buf.append("<html>");
        user_info_buf.append("과목명 : " + DB.Gesipan.get(i).subject_name);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("담당 교수 : " + DB.Gesipan.get(i).professor);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("수강 학년 : " + DB.Gesipan.get(i).grade);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("총 수강 인원 : " + DB.Gesipan.get(i).stdnum);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("설명 : " + DB.Gesipan.get(i).subject_info);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("선수과목 : " + DB.Gesipan.get(i).prerequisite);
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("팀당 인원 수 : " + DB.Gesipan.get(i).teamnum);
        user_info_buf.append(" <br>");
        user_info_buf.append("</html>");

        String user_info = new String(user_info_buf.toString());


        Subject_info.setText(user_info);
    }
}
