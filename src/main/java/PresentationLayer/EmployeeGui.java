package PresentationLayer;

import BussinessLayer.Observable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class EmployeeGui extends Observable {

    private JFrame frame;


    public EmployeeGui() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 626, 365);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel newOrderLabel = new JLabel("New Order");
        newOrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 44));
        newOrderLabel.setBounds(196, 51, 287, 197);
        frame.getContentPane().add(newOrderLabel);
        frame.setVisible(true);
    }
}