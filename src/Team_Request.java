import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Team_Request extends JFrame
{
    private JPanel mainpanel;
    private JButton 취소Button;
    private JButton 신청Button;
    private JLabel writer_info;
    private JLabel writer_content;
    private JLabel writer_content_title;

    int row;
    int subnum;

    public Team_Request(int row, int subnum)
    {
        setContentPane(mainpanel);
        setTitle("User_Info");
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        this.row = row;
        this.subnum = subnum;

        setWriter_info();
        setWriter_content();


        취소Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        신청Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DATABASECLASS DB = new DATABASECLASS();
                ConnectServer cs = new ConnectServer();
                //cs.RewriteInfo(DB.CurrentID,1,0);
                int [] a = new int [3];
                a[0] = subnum;
                a[1] = Integer.parseInt(cs.GetTitle(subnum,row,"id"));
                a[2] = DB.CurrentID;
                cs.SetRequest(a);
                JOptionPane.showMessageDialog(null, "신청 완료!", "팀 신청", JOptionPane.DEFAULT_OPTION);
                dispose();
            }
        });
    }

    public void setWriter_info()
    {
        StringBuilder user_info_buf = new StringBuilder();
        ConnectServer cs = new ConnectServer();
        DATABASECLASS DB = new DATABASECLASS();

        int Leader_id = Integer.parseInt(cs.GetTitle(subnum,row,"id"));

        user_info_buf.append("<html>");
        String name = new String("name");
        user_info_buf.append("이름 : " + cs.GetUserinfo(Leader_id,name));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("학번 : " + Integer.toString(Leader_id));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        String major = new String("major");
        user_info_buf.append("학과 : " + cs.GetUserinfo(Leader_id,major));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("학년 : " + Integer.toString(cs.GetUserinfo(Leader_id,2)));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("발표 실력 : " + Integer.toString(cs.GetUserinfo(Leader_id,4)));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("문서 작성 능력 : " + Integer.toString(cs.GetUserinfo(Leader_id,6)));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("코딩 실력 : " + Integer.toString(cs.GetUserinfo(Leader_id,8)));
        user_info_buf.append(" <br>");
        user_info_buf.append("<html>");
        user_info_buf.append("프론트/백 : " + Integer.toString(cs.GetUserinfo(Leader_id,7)));
        user_info_buf.append(" <br>");

        user_info_buf.append("</html>");

        String user_info = new String(user_info_buf.toString());


        writer_info.setText(user_info);
    }
    public void setWriter_content()
    {
        DATABASECLASS DB = new DATABASECLASS();
        ConnectServer cs = new ConnectServer();
        writer_content.setText(cs.GetTitle(subnum, row,"content"));
        writer_content_title.setText(cs.GetTitle(subnum, row,"title"));
    }

}
