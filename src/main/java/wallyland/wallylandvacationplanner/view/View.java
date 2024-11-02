/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.view;

/**
 *
 * @author chris
 */

public class View {
    private UserInterface userInterface;
    private AdminInterface adminInterface;
    
    public View() {
        userInterface = new UserInterface();
        adminInterface = new AdminInterface();
    }
    
    public UserInterface getUserInterface() {
        return userInterface;
    }
    
    public AdminInterface getAdminInterface() {
        return adminInterface;
    }
}