import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeWindow extends JFrame {

    public HomeWindow() {
        setTitle("Welcome to Railway Reservation System");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 230, 230)); 
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN); 
        loginButton.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(loginButton, gbc);

        JButton signupButton = new JButton("Signup");
        signupButton.setBackground(Color.BLUE); 
        signupButton.setForeground(Color.WHITE); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(signupButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginWindow().setVisible(true);
                dispose(); 
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignupWindow().setVisible(true);
                dispose(); 
            }
        });

        //setLocationRelativeTo(null); 
        setVisible(true);
    }
}
