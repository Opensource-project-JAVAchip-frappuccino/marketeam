import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Subject extends JFrame
{
    private JButton cancelButton;
    private JButton doneButton;
    private JTextField subject_text;
    private JTextField sub_info_text;
    private JRadioButton a2RadioButton;
    private JRadioButton a3RadioButton;
    private JRadioButton a4RadioButton;
    private JRadioButton a5RadioButton;
    private JPanel mainpanel;
    private JTextField presub_text;
    private JTextField grade_text;
    private JTextField professor_text;
    private JTextField stdnum_text;

    DATABASECLASS DB = new DATABASECLASS();

    public Subject(UserDB User)
    {
        setContentPane(mainpanel);
        setTitle("User_Info");
        setSize(500,250);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                Main_Title_professor update = new Main_Title_professor();
            }
        });
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                input_data();
                dispose();
                Main_Title_professor update = new Main_Title_professor();
            }
        });
    }

    public void input_data()
    {
        String [] str = new String[5];
        str[0] = subject_text.getText();
        str[1] = professor_text.getText();
        str[2] = sub_info_text.getText();
        str[3] = presub_text.getText();
        str[4] = grade_text.getText();

        int stdnum = Integer.parseInt(stdnum_text.getText());
        int teamnum = rbutn();

        ConnectServer cs = new ConnectServer();

        cs.SetCourse(str,stdnum,teamnum);



    }

    public int rbutn()
    {

        if (a2RadioButton.isSelected())
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
}
