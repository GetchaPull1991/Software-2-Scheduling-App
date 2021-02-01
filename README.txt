#Scheduling Application

Project descriptionThe scheduling application was the final project for my Software 2 course at Western Governors University.
The application was developed using JDK 11, JavaFX and MySQL.

Application Features:
- Login/Logout Functionality
- Appointment Management
- Customer Management
- Generate Reports
- Multi Language/Globalization (Implemented in French)

Login/Logout Functionality:
- Login dialog presented to the user upon launching application
- Login form displays current users country based on Local Machine information
- Does not allow Login attempts with empty fields
- Check credentials against database to grant access
- A Logout button in the Menu Pane will allow

Appointment Management:
- Add, Update and Remove Appointments
- View All Appointments, Current Week Appointments or Current Month Appointments
- Displays overdue appointments in red, Appointments within the next hour in yellow, and Appointments
within the current day in green.
- Does not allow the user to select a date or time before the current date and time
- Does not allow the user to schedule overlapping appointments
- Informational Dialog upon Login to indicate if the User has any appointments within the next 15 minutes
- Displays current users time zone, local time, and EST/Business time in the Menu pane for users who are
not local to the business.

Customer Management:
- Add, Update and Remove Customers
- View All Customers in a table

Generate Appointment Reports:
- Generate Report by type of Appointment and the Month it is scheduled for
- Generate Report by the Organization Contact the Appointment is scheduled with
- Generate report by the Customer ID associated with the Appointment
- Display Appointment Report results in a table

Multi-Language/Globalization (Implemented in French):
- If the users Locale uses the French language then all content including labels, buttons, information and
error messages will be translated to French on application start
