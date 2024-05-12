import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import com.toedter.calendar.JCalendar;
import java.util.Date;

public class RailwayReservationSystem extends JFrame{
	
	 public RailwayReservationSystem() {
	        setTitle("Railway Reservation System");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400, 500);

	        JPanel panel = new JPanel(new GridBagLayout());
	        panel.setBackground(new Color(230, 230, 230)); 
	        add(panel);
	        setLocationRelativeTo(null);
	        placeComponents(panel);

	        setVisible(true);
	    }

    private static HashMap<String, String> reservations = new HashMap<>();
    
    public static void main(String[] args) {
        new HomeWindow();
    }

    private static void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        JLabel userLabel = new JLabel("Name");
        userLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JTextField userText = new JTextField(20);
        userText.setBackground(Color.WHITE); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userText, gbc);
        
        JLabel departureDateLabel = new JLabel("Departure Date");
        departureDateLabel.setForeground(Color.DARK_GRAY); 
        departureDateLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(departureDateLabel, gbc);


        JCalendar departureDateCalendar = new JCalendar();
        departureDateCalendar.setFont(new Font("Arial", Font.PLAIN, 12)); 
        departureDateCalendar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(departureDateCalendar, gbc);


        JLabel departureFromLabel = new JLabel("Departure From");
        departureFromLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(departureFromLabel, gbc);

        String[] departureFromOptions = {"Vashi", "CBD", "Kharghar"};
        JComboBox<String> departureFromComboBox = new JComboBox<>(departureFromOptions);
        departureFromComboBox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(departureFromComboBox, gbc);

        JLabel departureToLabel = new JLabel("Departure To");
        departureToLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(departureToLabel, gbc);

        String[] departureToOptions = {"Vashi", "CBD", "Kharghar"};
        JComboBox<String> departureToComboBox = new JComboBox<>(departureToOptions);
        departureToComboBox.setBackground(Color.WHITE); 
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(departureToComboBox, gbc);

        JLabel ticketsLabel = new JLabel("Number of Tickets");
        ticketsLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(ticketsLabel, gbc);

        JTextField ticketsText = new JTextField(20);
        ticketsText.setBackground(Color.WHITE); 
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(ticketsText, gbc);

        JLabel seatLabel = new JLabel("Seat Class");
        seatLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(seatLabel, gbc);

        String[] seatClasses = {"First Class", "Second Class"};
        JComboBox<String> seatClassComboBox = new JComboBox<>(seatClasses);
        seatClassComboBox.setBackground(Color.WHITE); 
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(seatClassComboBox, gbc);

        JLabel totalCostLabel = new JLabel("Total Cost: $0");
        totalCostLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(totalCostLabel, gbc);

        JButton reserveButton = new JButton("Reserve");
        reserveButton.setBackground(Color.GREEN); 
        reserveButton.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; 
        panel.add(reserveButton, gbc);

        reserveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = userText.getText();
                String departureFrom = (String) departureFromComboBox.getSelectedItem();
                String departureTo = (String) departureToComboBox.getSelectedItem();
                String tickets = ticketsText.getText();
                String seatClass = (String) seatClassComboBox.getSelectedItem();
                Date departureDate = departureDateCalendar.getDate();

                int ticketCost = seatClass.equals("First Class") ? 100 : 20; 
                int totalCost = Integer.parseInt(tickets) * ticketCost;
                totalCostLabel.setText("Total Cost: ₹" + totalCost);

               
                reservations.put(name + "-" + departureFrom + "-" + departureTo, seatClass + " - " + tickets + " tickets");

          
                new TicketDetailWindow(name, departureFrom, departureTo, seatClass, totalCost, departureDate);
            }
        });
    }
}
