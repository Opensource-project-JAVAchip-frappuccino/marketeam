import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable status_table;
    private JTable request_table;
    private JPanel status_panel;
    private JScrollPane status_scroll;
    private JScrollPane request_scroll;
    private JLabel status_label;

    boolean a = true;
    boolean status = false;

    UserDB User = new UserDB();
    DATABASECLASS DB = new DATABASECLASS();

    public Main_Title()
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
        setStatus_table();

        status_scroll.setVisible(!status);
        request_scroll.setVisible(status);
        status_label.setText("나의 팀 정보");



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
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSubject_info(comboBox1.getSelectedIndex());
                setMatching_table(comboBox1.getSelectedIndex());
            }
        });
        MAKETEAMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Make_Team make = new Make_Team(comboBox1.getSelectedIndex());
                dispose();
            }
        });
        MATCHINGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectServer cs = new ConnectServer();
                DATABASECLASS DB = new DATABASECLASS();
                cs.RewriteInfo(DB.CurrentID,1,1);
                int val = calscore();
                cs.RewriteInfo(DB.CurrentID,4, val);
                JOptionPane.showMessageDialog(null, "매칭 신청이 완료되었습니다.", "매칭", JOptionPane.DEFAULT_OPTION);
            }
        });
        APPLYSTATUSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConnectServer cs = new ConnectServer();
                DATABASECLASS DB = new DATABASECLASS();

                if(cs.GetUserinfo(DB.CurrentID,12) == 0)
                {
                    JOptionPane.showMessageDialog(null, "작성한 팀 게시글이 없습니다.", "조회 불가!", JOptionPane.DEFAULT_OPTION);
                    return;
                }

                if(a)
                {
                    status_scroll.setVisible(status);
                    request_scroll.setVisible(!status);
                    status_label.setText("팀 신청자 목록");
                    setRequest_table();
                    a = false;
                    APPLYSTATUSButton.setText("Current Team");
                }
                else
                {
                    status_scroll.setVisible(!status);
                    request_scroll.setVisible(status);
                    status_label.setText("나의 팀 정보");
                    setStatus_table();
                    a = true;
                    APPLYSTATUSButton.setText("APPLY STATUS");
                }

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

        ConnectServer cs = new ConnectServer();

        String subject = new String("subject");
        String name = new String("name");
        for(int i = 1; i <= cs.subject_row_size(); i++)
        {
            comboBox1.addItem(cs.GetCourse(i,subject) + '/' + cs.GetCourse(i,name));
        }

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



        public void setMatching_table(int x)
        {
            DATABASECLASS DB = new DATABASECLASS();
            ConnectServer cs = new ConnectServer();

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
                    if (e.getClickCount() > 1)
                    {
                        int row = Matching_table.getSelectedRow();
                        ConnectServer cs = new ConnectServer();
                        if(cs.GetCourse(comboBox1.getSelectedIndex()+1,3) == cs.getTeam(comboBox1.getSelectedIndex()+1,row+1,8))
                        {
                            JOptionPane.showMessageDialog(null, "팀이 가득 찼습니다.", "신청 불가", JOptionPane.DEFAULT_OPTION);
                        }
                        else
                        {
                            Team_Request request = new Team_Request(row+1 , comboBox1.getSelectedIndex()+1);
                        }
                    }

                }
            });
            Matching_table.addMouseListener(new MouseAdapter()
            {
            });

            DefaultTableModel model = (DefaultTableModel) Matching_table.getModel();
            model.removeRow(0);
            for (int i = 0; i < cs.teamtitlecount(x+1); i++)
            {
                String str  = cs.GetTitle(x+1, i+1,"id");
                model.addRow(new Object[]{i+1, cs.GetTitle(x+1, i +1,"title"), cs.GetUserinfo(Integer.parseInt(str),"name"), cs.getTeam(x+1,i+1,8) + "/" + cs.GetCourse(x+1,3)});
            }

            Matching_table.getColumnModel().getColumn(0).setPreferredWidth(10);
            Matching_table.getColumnModel().getColumn(1).setPreferredWidth(100);
            Matching_table.getColumnModel().getColumn(2).setPreferredWidth(30);
            Matching_table.getColumnModel().getColumn(3).setPreferredWidth(30);

        }

public int calscore()
        {
            ConnectServer cs = new ConnectServer();
            DATABASECLASS DB = new DATABASECLASS();
            int sum = 0;

            for(int i = 3; i < 8; i++)
            {
                if(i == 7) continue;
                sum = sum + cs.GetUserinfo(DB.CurrentID, i);
            }
            return sum;

        }

        public void setStatus_table()
        {
            DATABASECLASS DB = new DATABASECLASS();
            ConnectServer cs = new ConnectServer();

            String[] colnames = new String[]{"과목", "팀 번호", "팀장", "팀원 1", "팀원 2", "팀원 3", "팀원 4"};
            Object[][] contents = new Object[1][7];


            contents[0][0] = "";
            contents[0][1] = "";
            contents[0][2] = "";
            contents[0][3] = "";
            contents[0][4] = "";
            contents[0][5] = "";
            contents[0][6] = "";


            status_table.setModel(new DefaultTableModel(contents, colnames) {
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }

            });

            status_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
            });
            status_table.addMouseListener(new MouseAdapter()
            {
            });

            DefaultTableModel model = (DefaultTableModel) status_table.getModel();
            model.removeRow(0);
            for (int i = 1; i < cs.teamcount_id(DB.CurrentID)+1; i++)
            {
                int subnum = cs.getTeam_subteam(DB.CurrentID,1,i);
                int teamnum = cs.getTeam_subteam(DB.CurrentID,1,i);
                if(subnum == 0 || teamnum == 0) break;
                model.addRow(new Object[]{cs.GetCourse(subnum,"subject"), teamnum,cs.GetUserinfo(cs.getTeam(subnum,teamnum,3),"name"),cs.GetUserinfo(cs.getTeam(subnum,teamnum,4),"name"),cs.GetUserinfo(cs.getTeam(subnum,teamnum,5),"name"),cs.GetUserinfo(cs.getTeam(subnum,teamnum,6),"name"),cs.GetUserinfo(cs.getTeam(subnum,teamnum,7),"name")});
            }

            status_table.getColumnModel().getColumn(0).setPreferredWidth(120);
            status_table.getColumnModel().getColumn(1).setPreferredWidth(30);
            status_table.getColumnModel().getColumn(2).setPreferredWidth(50);
            status_table.getColumnModel().getColumn(3).setPreferredWidth(50);
            status_table.getColumnModel().getColumn(4).setPreferredWidth(50);
            status_table.getColumnModel().getColumn(5).setPreferredWidth(50);
            status_table.getColumnModel().getColumn(6).setPreferredWidth(50);

        }

    public void setRequest_table()
    {
        DATABASECLASS DB = new DATABASECLASS();
        ConnectServer cs = new ConnectServer();

        String[] colnames = new String[]{ "과목번호","이름","학번","학년","코딩실력","프론트/백"};
        Object[][] contents = new Object[1][6];


        contents[0][0] = "";
        contents[0][1] = "";
        contents[0][2] = "";
        contents[0][3] = "";
        contents[0][4] = "";
        contents[0][5] = "";


        request_table.setModel(new DefaultTableModel(contents, colnames) {
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

        });

        request_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() > 1)
                {
                    JOptionPaneEx();
                }

            }
        });
        request_table.addMouseListener(new MouseAdapter()
        {
        });

        DefaultTableModel model = (DefaultTableModel) request_table.getModel();
        model.removeRow(0);


        for (int i = 1; i < cs.requestcount(DB.CurrentID)+1; i++)
        {
            int id = cs.GetRequest(DB.CurrentID,2,i);
            model.addRow(new Object[]{cs.GetRequest(DB.CurrentID,1,i), cs.GetUserinfo(id,"name"), id,cs.GetUserinfo(id,2),cs.GetUserinfo(id,8),cs.GetUserinfo(id,7) });
        }

        request_table.getColumnModel().getColumn(0).setPreferredWidth(20);
        request_table.getColumnModel().getColumn(1).setPreferredWidth(30);
        request_table.getColumnModel().getColumn(2).setPreferredWidth(50);
        request_table.getColumnModel().getColumn(3).setPreferredWidth(20);
        request_table.getColumnModel().getColumn(4).setPreferredWidth(30);
        request_table.getColumnModel().getColumn(5).setPreferredWidth(30);

    }

    public void JOptionPaneEx(){
        int answer = JOptionPane.showConfirmDialog(this, "수락할까요?", "confirm",JOptionPane.YES_NO_OPTION );
        if(answer==JOptionPane.YES_OPTION)
        {
            ConnectServer cs = new ConnectServer();
            DATABASECLASS DB = new DATABASECLASS();
            int row = request_table.getSelectedRow();

            int subnum = cs.getTeam_subteam(DB.CurrentID,1,row+1);
            int teamnum = cs.getTeam_subteam(DB.CurrentID,2,row+1);
            int request_id = cs.GetRequest(DB.CurrentID,2,row+1);

            if(cs.getTeam(subnum,teamnum,8) == cs.GetCourse(subnum,3))
            {
                JOptionPane.showMessageDialog(null, "팀이 가득 찼습니다.", "수락 불가", JOptionPane.DEFAULT_OPTION);
                return;
            }

            cs.Rewriteteam(subnum,teamnum,cs.getTeam(subnum,teamnum,8),request_id);
            cs.RewriteInfo(request_id,2,1);
            cs.Rewriteteam(subnum,teamnum,5,1);

            DefaultTableModel model = (DefaultTableModel) request_table.getModel();
            model.removeRow(row);
            cs.deleterequest(request_id);

        }
        else
        {

        }
    }

}
