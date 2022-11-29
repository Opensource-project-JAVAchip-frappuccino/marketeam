import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User_Info extends JFrame
{
    private JPanel user_panel;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JRadioButton a1RadioButton;
    private JRadioButton a2RadioButton;
    private JRadioButton a3RadioButton;
    private JRadioButton a5RadioButton;
    private JRadioButton a4RadioButton;
    private JRadioButton a1RadioButton1;
    private JRadioButton a2RadioButton1;
    private JRadioButton a3RadioButton1;
    private JRadioButton a4RadioButton1;
    private JRadioButton a5RadioButton1;
    private JRadioButton a1RadioButton2;
    private JRadioButton a2RadioButton2;
    private JRadioButton a3RadioButton2;
    private JRadioButton a4RadioButton2;
    private JRadioButton a5RadioButton2;
    private JRadioButton a1RadioButton3;
    private JRadioButton a1RadioButton4;
    private JRadioButton a2RadioButton3;
    private JRadioButton a2RadioButton4;
    private JRadioButton a3RadioButton3;
    private JRadioButton a3RadioButton4;
    private JRadioButton a4RadioButton3;
    private JRadioButton a4RadioButton4;
    private JRadioButton a5RadioButton3;
    private JRadioButton a5RadioButton4;
    private JRadioButton a1RadioButton5;
    private JRadioButton a2RadioButton5;
    private JRadioButton a3RadioButton5;
    private JRadioButton a4RadioButton5;
    private JRadioButton a5RadioButton5;
    private JButton okbtn;
    private JRadioButton teamaterbtn1;
    private JRadioButton teamaterbtn2;
    private JTextField textField3;

    UserDB User = new UserDB();


    public User_Info(UserDB User)
    {
        setContentPane(user_panel);
        setTitle("User_Info");
        setSize(1000,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        this.User = User;

        okbtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                input_data();
                boolean complete = false;
                for(int i = 0; i < 7; i++)
                {
                    if(User.match_var[i] == 0)
                    {
                        complete = true;
                    }
                    else complete = false;
                }
                if(User.match_var[6] == 3 || complete)
                {
                    JOptionPane.showMessageDialog(null, "정보를 모두 입력하세요", "내 정보 입력", JOptionPane.DEFAULT_OPTION);
                    return;
                }
                input_data();
                dispose();
                User.signup = true;
                if(User.grade == 0)
                {
                    Main_Title_professor main = new Main_Title_professor(User);
                }
                else
                {
                    Main_Title main = new Main_Title(User);
                }

            }
        });
    }

    public void input_data()
    {
        User.name = textField3.getText();
        User.major = textField1.getText();
        User.hakbun = Integer.parseInt(textField2.getText());
        User.grade = combobtn_1();
        User.match_var[0] = rbutn_1();
        User.match_var[1] = rbutn_2();
        User.match_var[2] = rbutn_3();
        User.match_var[3] = rbutn_4();
        User.match_var[4] = rbutn_5();
        User.match_var[5] = rbutn_6();
        User.match_var[6] = rbutn_7();
    }

    public int combobtn_1()
    {
        return comboBox1.getSelectedIndex();
    }

    public int rbutn_1()
    {
        if(a1RadioButton.isSelected())
            return 1;
        else if (a2RadioButton.isSelected())
            return 2;
        else if (a3RadioButton.isSelected())
            return 3;
        else if (a4RadioButton.isSelected())
            return 4;
        else if (a5RadioButton.isSelected())
            return 5;
        else
            return 0;
    }
    public int rbutn_2()
    {
        if(a1RadioButton1.isSelected())
            return 1;
        else if (a2RadioButton1.isSelected())
            return 2;
        else if (a3RadioButton1.isSelected())
            return 3;
        else if (a4RadioButton1.isSelected())
            return 4;
        else if (a5RadioButton1.isSelected())
            return 5;
        else
            return 0;
    }

    public int rbutn_3()
    {
        if(a1RadioButton2.isSelected())
            return 1;
        else if (a2RadioButton2.isSelected())
            return 2;
        else if (a3RadioButton2.isSelected())
            return 3;
        else if (a4RadioButton2.isSelected())
            return 4;
        else if (a5RadioButton2.isSelected())
            return 5;
        else
            return 0;
    }

    public int rbutn_4()
    {
        if(a1RadioButton3.isSelected())
            return 1;
        else if (a2RadioButton3.isSelected())
            return 2;
        else if (a3RadioButton3.isSelected())
            return 3;
        else if (a4RadioButton3.isSelected())
            return 4;
        else if (a5RadioButton3.isSelected())
            return 5;
        else
            return 0;
    }

    public int rbutn_5()
    {
        if(a1RadioButton4.isSelected())
            return 1;
        else if (a2RadioButton4.isSelected())
            return 2;
        else if (a3RadioButton4.isSelected())
            return 3;
        else if (a4RadioButton4.isSelected())
            return 4;
        else if (a5RadioButton4.isSelected())
            return 5;
        else
            return 0;
    }

    public int rbutn_6()
    {
        if(a1RadioButton5.isSelected())
            return 1;
        else if (a2RadioButton5.isSelected())
            return 2;
        else if (a3RadioButton5.isSelected())
            return 3;
        else if (a4RadioButton5.isSelected())
            return 4;
        else if (a5RadioButton5.isSelected())
            return 5;
        else
            return 0;
    }
    public int rbutn_7()
    {
        if(teamaterbtn1.isSelected())
            return 1;
        else if(teamaterbtn2.isSelected())
            return 0;
        else
            return 3;
    }

}
