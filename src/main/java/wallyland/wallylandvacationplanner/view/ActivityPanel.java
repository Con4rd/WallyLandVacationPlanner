package wallyland.wallylandvacationplanner.view;

import wallyland.wallylandvacationplanner.model.Activity;
import wallyland.wallylandvacationplanner.controller.ActivityManager;

import javax.swing.*;
import java.awt.*;

public class ActivityPanel extends JPanel {
    private ActivityManager activityManager;
    private JList<Activity> activityList;
    private DefaultListModel<Activity> listModel;
    private JButton scheduleButton;
    private JButton cancelButton;
    private JComboBox<String> timeSlotCombo;
    private JTextArea descriptionArea;
    private JLabel waitTimeLabel;
    private Timer waitTimeUpdateTimer;

    public ActivityPanel(ActivityManager activityManager) {
        this.activityManager = activityManager;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Create list model and JList
        listModel = new DefaultListModel<>();
        activityList = new JList<>(listModel);
        activityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Prevent multiple selections
        updateActivityList();

        // Create description panel
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionArea = new JTextArea(3, 30);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionPanel.add(new JLabel("Description:"), BorderLayout.NORTH);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        // Create wait time label
        waitTimeLabel = new JLabel("Wait Time: 0 min");
        descriptionPanel.add(waitTimeLabel, BorderLayout.SOUTH);

        // Create time slot selector
        String[] timeSlots = {
                "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM",
                "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM",
                "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM"
        };
        timeSlotCombo = new JComboBox<>(timeSlots);

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(activityList), BorderLayout.CENTER);
        mainPanel.add(descriptionPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        // Add selection listener
        activityList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Activity selected = activityList.getSelectedValue();
                updateControlsState(selected);
                updateDescriptionArea(selected);
            }
        });

        // Initialize wait time update timer
        initializeWaitTimeTimer();
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(2, 1));

        // Time slot panel
        JPanel timeSlotPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timeSlotPanel.add(new JLabel("Select Time:"));
        timeSlotPanel.add(timeSlotCombo);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scheduleButton = new JButton("Schedule");
        cancelButton = new JButton("Cancel");

        scheduleButton.addActionListener(e -> scheduleSelectedActivity());
        cancelButton.addActionListener(e -> cancelSelectedActivity());

        // Initially disable buttons
        scheduleButton.setEnabled(false);
        cancelButton.setEnabled(false);

        buttonPanel.add(scheduleButton);
        buttonPanel.add(cancelButton);

        controlPanel.add(timeSlotPanel);
        controlPanel.add(buttonPanel);

        return controlPanel;
    }

    private void updateActivityList() {
        listModel.clear();
        for (Activity activity : activityManager.getAllActivities()) {
            listModel.addElement(activity);
        }
    }

    private void scheduleSelectedActivity() {
        Activity selected = activityList.getSelectedValue();
        if (selected != null) {
            String selectedTime = (String)timeSlotCombo.getSelectedItem();
            if (activityManager.scheduleActivity(selected.getActivityId())) {
                JOptionPane.showMessageDialog(this,
                        "Activity scheduled successfully for " + selectedTime + "!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                updateActivityList();
                updateControlsState(selected);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Unable to schedule activity. It may be at capacity or closed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelSelectedActivity() {
        Activity selected = activityList.getSelectedValue();
        if (selected != null) {
            activityManager.cancelActivity(selected.getActivityId());
            JOptionPane.showMessageDialog(this,
                    "Activity cancelled successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            updateActivityList();
            updateControlsState(selected);
        }
    }

    private void updateDescriptionArea(Activity selected) {
        if (selected != null) {
            descriptionArea.setText(selected.getDescription());
            updateWaitTimeLabel(selected);
        } else {
            descriptionArea.setText("");
            waitTimeLabel.setText("Wait Time: 0 min");
        }
    }

    private void updateWaitTimeLabel(Activity activity) {
        if (activity != null) {
            waitTimeLabel.setText("Wait Time: " + activity.getWaitTime() + " min");
        }
    }

    private void initializeWaitTimeTimer() {
        waitTimeUpdateTimer = new Timer(5000, e -> {
            Activity selected = activityList.getSelectedValue();
            if (selected != null) {
                updateWaitTimeLabel(selected);
                updateControlsState(selected);
                activityList.repaint(); // Refresh the display
            }
        });
        waitTimeUpdateTimer.start();
    }

    private void updateControlsState(Activity selected) {
        boolean hasSelection = selected != null;
        boolean canSchedule = hasSelection &&
                selected.getDailyScheduled() < selected.getDailyCapacity() &&
                selected.isOperational();
        boolean canCancel = hasSelection && selected.getDailyScheduled() > 0;

        scheduleButton.setEnabled(canSchedule);
        cancelButton.setEnabled(canCancel);
        timeSlotCombo.setEnabled(canSchedule);
    }

    // Clean up resources
    public void cleanup() {
        if (waitTimeUpdateTimer != null) {
            waitTimeUpdateTimer.stop();
        }
    }





}
