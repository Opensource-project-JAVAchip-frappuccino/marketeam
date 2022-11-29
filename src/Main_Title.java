import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main_Title extends JFrame
{

    private JPanel panel1;
    private JButton MYINFOButton;
    private JButton LOGOUTButton;
    private JButton APPLYSTATUSButton;
    private JButton MATCHINGButton;
    private JLabel user_info_mini;
    private JComboBox comboBox1;
    private JTable Matching_table;
    private JButton MAKETEAMButton;
    private JLabel Subject_info;
    private JPanel Datapanel;
    private JScrollPane JScrollPane;

    UserDB User = new UserDB();
    DATABASECLASS DB = new DATABASECLASS();

    public Main_Title(UserDB User)
    {
        setContentPane(panel1);
        setTitle("User_Info");
        setSize(1000,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        JScrollPane.setViewportView(Matching_table);

        this.User = User;

        User_info_mini_setting();
        setMatching_table();
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
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSubject_info(comboBox1.getSelectedIndex());
            }
        });
        MAKETEAMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Make_Team make = new Make_Team(User);
                dispose();
            }
        });
    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }

    public void setComboBox1()
    {
        DATABASECLASS DB = new DATABASECLASS();

        for(int i = 0; i < DB.Gesipan.size(); i++)
        {
            comboBox1.addItem(DB.Gesipan.get(i).subject_name + '/' + DB.Gesipan.get(i).professor);
        }

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
        user_info_buf.append("학년 : " + Integer.toString(User.grade));
        user_info_buf.append(" <br>");

        user_info_buf.append("</html>");

        String user_info = new String(user_info_buf.toString());


        user_info_mini.setText(user_info);
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



        public void setMatching_table()
        {
            DATABASECLASS DB = new DATABASECLASS();

            String[] colnames = new String[]{"번호", "제목", "작성자", "현재 팀원 수"};
            Object[][] contents = new Object[1][4];


            contents[0][0] = "";
            contents[0][1] = "";
            contents[0][2] = "";
            contents[0][3] = "";


            Matching_table.setModel(new DefaultTableModel(contents, colnames) {
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }

            });

            Matching_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 1) {
                        super.mouseClicked(e);
                        int row = Matching_table.getSelectedRow();


                    }

                }
            });
            Matching_table.addMouseListener(new MouseAdapter() {
            });



            DefaultTableModel model = (DefaultTableModel) Matching_table.getModel();
            model.removeRow(0);
            for (int i = 0; i < DB.maketeam.size(); i++)
            {
                model.addRow(new Object[]{DB.maketeam.get(i).count, DB.maketeam.get(i).Title, DB.maketeam.get(i).name, DB.maketeam.get(i).teamnum});
            }

        }

    }
