# WallyLandVacationPlanner

## Team 8
- Skyler Ames
- Keith Christensen
- Yanxi Wang

## Explanation
> The app's main features will involve buying tickets, scheduling activities receiving updates on park status and ordering food options that cater to different guest preferences while also offering useful insights, for park administrators. In terms of operations, the app should connect with the park systems, for ticketing, point of sale transactions, and managing attractions. Moreover, it should offer analytic tools to help park administrators allocate resources efficiently and enhance the overall effectiveness of the park experience.  

USE CASE 1: Buy tickets, food, and/or drink

Our implemented use case allows users to purchase park tickets and concessions through the WallyLand Vacation Planner application. Users can select admission tickets, food items, and drinks from available options, add them to a cart, and complete the purchase using their preferred payment method. The application provides real-time inventory updates and order confirmation.

Main Class: wallyland.wallylandvacationplanner.WallylandVacationPlanner
Location: src/wallyland/wallylandvacationplanner/WallylandVacationPlanner.java

Current functionality includes:
- User interface for viewing available tickets and concessions
- Shopping cart functionality
- Basic payment processing
- Order confirmation display

Note: Other features such as ride scheduling, real-time updates, and park management are currently stubbed for future implementation.

#Explanation Use Case 2: Scheduling Activities

Our second implemented use case focuses on enabling users to schedule activities
at WallyLand.
We cleaned up the shopping cart functionality and created example activities 
that guests could schedule(add, remove, and check) during their visit.
These activities would be at set times and guests could mark their attendence.
Users can also see the max capacity of each event for planning needs.
Upon scheduling or canceling, the system provides confirmation or an 
appropriate error message.

Main Class: wallyland.wallylandvacationplanner.view.ActivityPanel
Location: src/wallyland/wallylandvacationplanner/view/ActivityPanel.java

Current Functionalities:
- Purchase funtionality(tickets, food, or drinks)
- Browsing list of available activities with details of time and capacity
- Schedule activities
- Activity canceling functionality with real-time updates

Future implementations will include an admin interface, ability for guests to 
provide feedback and resource management tools.

#Explanation: Use Case 3: Providing Feedback and Fixes

Our third and final use case focuses on the feedback system and fully implementing the admin screen.
We added a recipt class for users to recieve a reciept after transaction.
Updated purchase service for better cart functionality.
We implemented the system for users to schedule their rides and also provide feedback.
Admin now have a few tabs to help monitor their park. 
Admin can now generate reports, respond to feedback from users, and monitor resources.

Current Functionalities:
- Purchase funtionality(tickets, food, or drinks)
- Browsing list of available activities with details of time and capacity
- Schedule activities
- Activity canceling functionality with real-time updates
- Providing feedback including a 1-5 rating and comment
- Admin can monitor resources
- Admin can generate reports
- Admin can respond to real time feedback
