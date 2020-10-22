
Project Title: 
Software 2 Scheduling App

Purpose:
The purpose of the application is to allow the user to manage customers and appointments, and generate reports based on different criteria.
The user will be able to add, update delete customers and appointments. The application will provide reminders to the user of upcoming appointments
upon login and will verify all deletions including customers and appointments.

Author: 
Joseph Smith

Contact Information: 
jsmit468@wgu.edu

Application Version: 
1.0.0

Date: 
10/21/2020

IDE: 
IntelliJ Community 2019.3.5

JDK Version: 
JDK 11.0.5

JavaFX Version: 
JavaFX-SDK-11.0.2

How To Run:

Additional Report: 
My chosen additional report was to return a schedule of the current appointments for each Customer ID.
This selection is the third of 3 radio buttons on the Report View screen and has a ComboBox that is populated from
the database with the available Customer ID's. The Generate Report button is then clicked and the appointments that
match the Customer ID are populated below the form in a table.


Requirements Notes:

A-2: Deleting a customer requires all appointments to be deleted first:
    - I implemented a secondary confirmation alert that displays the information about the
      customers appointments and allows the user to decide to delete them along with the customer.

A-3-a:  Write code that enables the user to add, update, and delete appointments:
    - I disabled the Customer ID field along with the Appointment ID field
        so that appointments cannot be made for non-existing customers.

A-3-d:  scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends:
    - I implemented datepickers and combo boxes for dates and times. I also added cell factories to prevent the
        start time from being before the current day during business hours and the end date from being before the current day.
        The start times combo box is populated with times that are within business hours and after the current local time plus 15 minutes.
        The end times combo box is populated with times that are within business hours and after the start time.

    scheduling overlapping appointments for customers:
    I did not allow appointments to overlap but appointments can start at the time that another ends and vice versa.
    If end time for appointment 1 is 10:15, appointment 2 is allowed to start at 10:15

B:  Write at least two different lambda expressions to improve your code:
    - I used lambdas for all cell factories, action events and dialog responses throughout the program.

C: Write code that provides the ability to track user activity by recording all user log-in attempts, dates, and
    time stamps and whether each attempt was successful in a file named login_activity.txt:
    - login_activity.txt is located in the root of the application folder.

D:  Provide descriptive Javadoc comments for at least 70 percent of the classes and their members throughout the code, and create an index.html
    file of your comments to include with your submission based on Oracle’s guidelines for the Javadoc tool best practices:
    - The JavaDoc folder which contains all the files including index.html is in the root of the application folder.