//import ConnectServer.ConnectServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login_Title extends JFrame
{
    private JButton signup;
    private JButton Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel Title_panel;
    private JLabel Notice;
    private JLabel Title_img;
    private JPanel Main_panel;
    private JPanel Logine_panel;
    private JScrollPane Notice_Scroll;

    public Login_Title()
    {
        setContentPane(Main_panel);
        setTitle("");
        setSize(1000,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Notice_Scroll.setViewportView(Notice);

        this.login_enter();
        this.loginbtnclicked();
        this.ReadFile();
        this.signupbtn();


    }

    private void loginbtnclicked()
    {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ID = textField1.getText();
                char[] PW = passwordField1.getPassword();

                int id = Integer.parseInt(ID);
                int pw = Integer.parseInt(new String(PW));

                ConnectServer cs = new ConnectServer();
                DATABASECLASS DB = new DATABASECLASS();
                DB.CurrentID = id;

                boolean flag = cs.FindID(id, pw);           //아이디만 맞아도 로그인되는 버그
                if(flag) {
                    System.out.println("아이디 존재.");
                    if(cs.GetUserinfo(id,2) == 0)
                    {
                        Main_Title_professor main = new Main_Title_professor();
                    }
                    else
                    {
                        Main_Title main = new Main_Title();
                    }
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "없는 회원정보입니다.", "회원 가입 필요", JOptionPane.DEFAULT_OPTION);
                }

                cs.DisconnectServer();
            }
        });
    }
    private void login_enter()
    {
        Action ok = new AbstractAction()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                String ID = textField1.getText();
                char[] PW = passwordField1.getPassword();

                int id = Integer.parseInt(ID);
                int pw = Integer.parseInt(new String(PW));

                DATABASECLASS DB = new DATABASECLASS();
                DB.CurrentID = id;

                ConnectServer cs = new ConnectServer();
                boolean flag = cs.FindID(id, pw);           //아이디만 맞아도 로그인되는 버그
                if(flag) {
                    System.out.println("아이디 존재.");
                    if(cs.GetUserinfo(id,2) == 0)
                    {
                        Main_Title_professor main = new Main_Title_professor();
                    }
                    else
                    {
                        Main_Title main = new Main_Title();
                    }
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "없는 회원정보입니다.", "회원 가입 필요", JOptionPane.DEFAULT_OPTION);
                }

                cs.DisconnectServer();
            }
        };
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        passwordField1.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "ENTER");
        passwordField1.getActionMap().put("ENTER", ok);
    }

    private void ReadFile()
    {
        StringBuffer strbuf_notice = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/PatchNote.txt"));
            String str;
            while ((str = reader.readLine()) != null)
            {
                strbuf_notice.append("<html>");
                strbuf_notice.append(str);
                strbuf_notice.append(" <br>");
            }
            reader.close();
            strbuf_notice.append("</html>");
            String str_notice = strbuf_notice.toString();
            this.Notice.setText(str_notice);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signupbtn()
    {
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String ID = textField1.getText();
                char[] PW = passwordField1.getPassword();

                int id = Integer.parseInt(ID);
                int pw = Integer.parseInt(new String(PW));

                ConnectServer cs = new ConnectServer();

                if(id != pw){ //불일치 시 DB저장 안되게하고 넘어가기
                    JOptionPane.showMessageDialog(null, "최초 회원가입시 아이디와 비밀번호는 학번으로 같아야 합니다.", "회원 가입 필요", JOptionPane.DEFAULT_OPTION);
                }
                else {
                    if (cs.InputData(id, pw)) {
                        JOptionPane.showMessageDialog(null, "이미 계정이 존재합니다.", "로그인 하세요", JOptionPane.DEFAULT_OPTION);
                    } else if (id == pw) { //회원가입이니까 둘이 일치하면 회원가입 완료로 로그인 개념으로 넘어감
                        User_Info info = new User_Info(); //수정
                    }
                }

                cs.DisconnectServer();

            }
        });
    }

}
