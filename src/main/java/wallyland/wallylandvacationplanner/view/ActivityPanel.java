package wallyland.wallylandvacationplanner.view;


import wallyland.wallylandvacationplanner.model.Activity;
import wallyland.wallylandvacationplanner.controller.ActivityManager;

import javax.swing.*;
import java.awt.*;

public class ActivityPanel extends JPanel {
    private ActivityManager activityManager;
    private JList<Activity> activityList;
    private DefaultListModel<Activity> listModel;

    public ActivityPanel(ActivityManager activityManager) {
        this.activityManager = activityManager;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Create list model and JList
        listModel = new DefaultListModel<>();
        activityList = new JList<>(listModel);
        updateActivityList();

        // Add components
        add(new JScrollPane(activityList), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton scheduleButton = new JButton("Schedule");
        JButton cancelButton = new JButton("Cancel");

        scheduleButton.addActionListener(e -> scheduleSelectedActivity());
        cancelButton.addActionListener(e -> cancelSelectedActivity());

        buttonPanel.add(scheduleButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
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
            if (activityManager.scheduleActivity(selected.getActivityId())) {
                JOptionPane.showMessageDialog(this,
                        "Activity scheduled successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Unable to schedule activity. It may be at capacity or closed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            updateActivityList();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select an activity to schedule.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select an activity to cancel.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
