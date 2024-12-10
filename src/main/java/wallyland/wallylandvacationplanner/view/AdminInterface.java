package wallyland.wallylandvacationplanner.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInterface extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public AdminInterface() {
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("WallyLand Admin Interface");
        setSize(800, 600);
        setLocationRelativeTo(null);  // Center window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        // Initialize Admin Dashboard
        displayAdminDashboard();
        
        // Setup Menu Bar
        setupMenuBar();
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu dashboardMenu = new JMenu("Dashboard");
        JMenuItem viewDashboardItem = new JMenuItem("View Dashboard");
        viewDashboardItem.addActionListener(e -> displayAdminDashboard());
        dashboardMenu.add(viewDashboardItem);

        JMenu reportsMenu = new JMenu("Reports");
        JMenuItem viewReportsItem = new JMenuItem("View Reports");
        viewReportsItem.addActionListener(e -> showReports());
        reportsMenu.add(viewReportsItem);

        JMenu resourceMenu = new JMenu("Resources");
        JMenuItem resourceMetricsItem = new JMenuItem("View Resource Metrics");
        resourceMetricsItem.addActionListener(e -> displayResourceMetrics());
        resourceMenu.add(resourceMetricsItem);

        JMenu feedbackMenu = new JMenu("Feedback");
        JMenuItem feedbackItem = new JMenuItem("View Feedback");
        feedbackItem.addActionListener(e -> showFeedbackPanel());
        feedbackMenu.add(feedbackItem);

        menuBar.add(dashboardMenu);
        menuBar.add(reportsMenu);
        menuBar.add(resourceMenu);
        menuBar.add(feedbackMenu);

        setJMenuBar(menuBar);
    }

    public void displayAdminDashboard() {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dashboardPanel.add(titleLabel, BorderLayout.NORTH);

        // Add some sample statistics to the dashboard
        JTextArea statsTextArea = new JTextArea(10, 30);
        statsTextArea.setEditable(false);
        statsTextArea.setText("Total Visitors: 5000\nTotal Sales: $100,000\nActive Activities: 25\nTotal Feedback: 200");
        dashboardPanel.add(new JScrollPane(statsTextArea), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Stats");
        refreshButton.addActionListener(e -> refreshDashboardStats(statsTextArea));
        dashboardPanel.add(refreshButton, BorderLayout.SOUTH);

        mainPanel.add(dashboardPanel, "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    private void refreshDashboardStats(JTextArea statsTextArea) {
        // Simulate refreshing of statistics (In reality, these should be fetched from the system)
        statsTextArea.setText("Total Visitors: 5200\nTotal Sales: $105,000\nActive Activities: 27\nTotal Feedback: 220");
    }

    public void showReports() {
        JPanel reportsPanel = new JPanel();
        reportsPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        reportsPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea reportsTextArea = new JTextArea(10, 30);
        reportsTextArea.setEditable(false);
        reportsTextArea.setText("Report 1: Activity Stats\nReport 2: User Purchases\nReport 3: Feedback Summary");
        reportsPanel.add(new JScrollPane(reportsTextArea), BorderLayout.CENTER);

        mainPanel.add(reportsPanel, "REPORTS");
        cardLayout.show(mainPanel, "REPORTS");
    }

    public void displayResourceMetrics() {
        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Resource Metrics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resourcePanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea resourceTextArea = new JTextArea(10, 30);
        resourceTextArea.setEditable(false);
        resourceTextArea.setText("Active Users: 250\nServer Load: 65%\nDatabase Connections: 12");
        resourcePanel.add(new JScrollPane(resourceTextArea), BorderLayout.CENTER);

        mainPanel.add(resourcePanel, "RESOURCES");
        cardLayout.show(mainPanel, "RESOURCES");
    }

    public void showFeedbackPanel() {
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("User Feedback", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        feedbackPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea feedbackTextArea = new JTextArea(10, 30);
        feedbackTextArea.setEditable(false);
        feedbackTextArea.setText("Feedback 1: Great experience!\nFeedback 2: Needs improvement.\nFeedback 3: Excellent service.");
        feedbackPanel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);

        JButton respondButton = new JButton("Respond to Feedback");
        respondButton.addActionListener(e -> respondToFeedback());
        feedbackPanel.add(respondButton, BorderLayout.SOUTH);

        mainPanel.add(feedbackPanel, "FEEDBACK");
        cardLayout.show(mainPanel, "FEEDBACK");
    }

    private void respondToFeedback() {
        String response = JOptionPane.showInputDialog(this, "Enter your response to the feedback:");
        if (response != null && !response.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Response sent to user.");
        }
    }
}
