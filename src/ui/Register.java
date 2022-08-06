package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame{
    private JButton Test;
    private JPanel Main;
    private JTextField REGISTERTextField;
    private JTextField txt_username;
    private JTextField input_username;
    private JTextField txt_password;
    private JTextField txt_repeatPass;
    private JPasswordField input_repeatPass;
    private JPasswordField input_password;
    //hello

    public Register() {
        Test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args){
        Register r = new Register();
        r.setContentPane(new Register().Main);
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        r.setVisible(true);
        r.pack();
    }
}

