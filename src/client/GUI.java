//Chuanyuan Liu (884140)
package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JComboBox queryComboBox;
    private JTextField meaningText;
    private JTextArea dialogueText;
    private JPanel mainPanel;
    private JLabel queryLabel;
    private JLabel wordLabel;
    private JLabel meaningLabel;
    private JTextField wordText;
    private JScrollPane dialoguePane;
    private JButton sendButton;
    private JLabel displayLabel;

    private void createUIComponents() {
        // Combo Box
        String[] options = {"ADD", "QUERY", "DELETE", "INDEX"};
        queryComboBox = new JComboBox(options);
        // Dialogue
        dialogueText = new JTextArea();
        dialogueText.setFont(new Font("monospaced", Font.PLAIN, 12));
        dialogueText.setEditable(false);
    }

    public GUI(Client client) {

        // Display GUI
        JFrame frame = new JFrame("English Dictionary");

        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(300,300));
        frame.setVisible(true);

        // Binds the GUI to a client
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the client
                String query = queryComboBox.getSelectedItem().toString();
                String word = wordText.getText();
                String meaning = meaningText.getText();
                client.Update(query, word, meaning);
            }
        });
    }

    public void appendDialogue(String string) {
        dialogueText.append(string);
    }

}
