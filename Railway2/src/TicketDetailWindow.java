import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class TicketDetailWindow extends JFrame {

    public TicketDetailWindow(String name, String departureFrom, String departureTo, String seat, int totalCost, Date departureDate) {
        setTitle("Ticket Details");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 230, 230)); 
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        JLabel nameLabel = new JLabel("Name: " + name);
        nameLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDepartureDate = sdf.format(departureDate);
        JLabel departureDateLabel = new JLabel("Departure Date: " + formattedDepartureDate);
        departureDateLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(departureDateLabel, gbc);


        JLabel departureFromLabel = new JLabel("Departure From: " + departureFrom);
        departureFromLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(departureFromLabel, gbc);

        JLabel departureToLabel = new JLabel("Departure To: " + departureTo);
        departureToLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(departureToLabel, gbc);

        JLabel seatLabel = new JLabel("Seat: " + seat);
        seatLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(seatLabel, gbc);

        JLabel totalCostLabel = new JLabel("Total Cost: ₹" + totalCost);
        totalCostLabel.setForeground(Color.DARK_GRAY); 
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(totalCostLabel, gbc);

        JButton printButton = new JButton("Print Ticket");
        printButton.setBackground(Color.GREEN); 
        printButton.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(printButton, gbc);

        printButton.addActionListener(e -> {
            
            JOptionPane.showMessageDialog(this, "Ticket printed successfully!");
            
            updateDatabase(name, departureFrom, departureTo, seat, totalCost, departureDate);
            generatePDF(name, departureFrom, departureTo, seat, totalCost, departureDate);
        });

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void updateDatabase(String name, String departureFrom, String departureTo, String seat, int totalCost, Date departureDate) {
        String sql = "INSERT INTO tickets (name, departure_from, departure_to, seat_class, total_cost, departure_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, departureFrom);
            pstmt.setString(3, departureTo);
            pstmt.setString(4, seat);
            pstmt.setInt(5, totalCost);
            pstmt.setDate(6, new java.sql.Date(departureDate.getTime()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Ticket information saved to the database.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving ticket information: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void generatePDF(String name, String departureFrom, String departureTo, String seat, int totalCost, Date departureDate) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Ticket_" + name + ".pdf"));

//            String filePath = "D:\\Coding\\Java\\Railway2\\Tickets\\Ticket_" + name + ".pdf";
//            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
         
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Ticket Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

           
            
            PdfPTable table = new PdfPTable(2); 
            table.setWidthPercentage(100); 
            table.setWidths(new int[]{1, 3}); 
            table.setSpacingBefore(10); 
            table.setSpacingAfter(10); 

            table.addCell("Name:");
            table.addCell(name);
            table.addCell("Departure From:");
            table.addCell(departureFrom);
            table.addCell("Departure To:");
            table.addCell(departureTo);
            table.addCell("Seat:");
            table.addCell(seat);
            table.addCell("Total Cost:");
            table.addCell("₹Rs." + totalCost);
            table.addCell("Departure Date:");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDepartureDate = sdf.format(departureDate);
            table.addCell(formattedDepartureDate);

            document.add(table);
            
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Paragraph footer = new Paragraph("This is a digital ticket. Please present this PDF at the station.", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            
            document.close();
            JOptionPane.showMessageDialog(this, "Ticket PDF generated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
