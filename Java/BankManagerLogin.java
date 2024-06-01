import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankManagerLogin extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public BankManagerLogin() {
        createGUI();
    }

    private void createGUI() {
        setTitle("Bank Management System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel paddingPanel = new JPanel();
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        paddingPanel.setLayout(new GridLayout(3, 2, 10, 10));

        paddingPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        paddingPanel.add(usernameField);

        paddingPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        paddingPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        paddingPanel.add(loginButton);

        add(paddingPanel, BorderLayout.CENTER);

        usernameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.equals("Manager") && password.equals("loginPass")) {
                setVisible(false); 
                new MyFrame();  
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankManagerLogin());
    }
}
