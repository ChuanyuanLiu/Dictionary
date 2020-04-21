package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JComboBox queryComboBox;
    private JTextField meaningText;
    private JTextArea dialgoueText;
    private JPanel mainPanel;
    private JLabel queryLabel;
    private JLabel wordLabel;
    private JLabel meaningLabel;
    private JTextField wordText;
    private JScrollPane dialoguePane;
    private JButton sendButton;
    private JLabel displayLabel;

    private void createUIComponents() {
        String[] options = {"ADD", "QUERY", "DELETE", "INDEX"};
        queryComboBox = new JComboBox(options);
        dialgoueText = new JTextArea();
        dialgoueText.setFont(new Font("monospaced", Font.PLAIN, 12));

    }

    public GUI(Client client) {

        // Display GUI
        JFrame frame = new JFrame("App");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // Binds the GUI to a client
        sendButton.addActionListener(new ActionListener() {
            // Update the client
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = queryComboBox.getSelectedItem().toString();
                String word = wordText.getText();
                String meaning = meaningText.getText();
                client.Update(query, word, meaning);
            }
        });
    }

    public void appendDialogue(String string) {
        dialgoueText.append(string);
    }

}
