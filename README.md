# Innovaccer_Assignment
Innovaccer's SummerGeeks 2020 Assignment

## Description
This is an entry management software as a part of Innocaccer's SummerGeeks 2020. Multiple clients can use this application with each client having exclusive access to only his data.
Here, you can maintain the list of host/employees and manage visitor entry/exit system. You can the past and presently running entries in the app itself.

<b>Compatible Platforms</b> - Android OS

<b>Backend</b> - Firebase NoSQL

<b>Other APIs</b> - JAVAMail (for auto mailing using JAVA), FirebaseAuthentication (for Email & Password) based authentication 
## Approach
This is an entry management software requiring basic initial configurations. 
<li>First inference taken out of the assignment is to make the data independent for multiple clients. That is suppose Innovaccer and some other company is using this app. Then innovacer can only view its data and other company can only view its data.</li>
<li>Second inference taken out of the problem statement is that adding hosts will be a part of initial configuration. Visitor only need to choose the host rather than entering the host details.
<li>Third inference is that this will be a public register meaning each visitor won't have to authenticate itself.</li>

## Application Flow
<ol>
<li> Application checks if user already logged in or not 
<ul>
  <li> If not logged in, ask admin to login</li>
  <li> Otherwise, direct to MainActivity</li>
</ul>
</li>
<li> <b> To add a host</b>
<ul>
  <li>Access AddHost section by clicking on Floating Action Button (+) on MainActivity</li>
  <li>Fill in valid details</li>
  <li>Click on Submit Button and the host will be added.
</ul>
</li>
<li> <b> To Check-In</b>
<ul>
  <li>Access MakeEntry section on MainActivity</li>
  <li>Fill in valid details</li>
  <li>Click on Submit Button and you'll be asked to select a host.</li>
  <li>Select a host and check-in is done. Host will recieve an SMS and an Email.</li> 
</ul>
</li>
<li> <b> Running Entry</b>
<ul>
  <li>An entry once made and not checked out can be viewed in Running Entry Section of MainActivity</li>
</ul>
</li>
<li> <b> To Check-Out</b>
<ul>
  <li>Access Running Entry Section of MainActivity</li>
  <li>Click on checkout and entry will be checked out. Guest will recieve a mail regarding the meeting details.
  <li>You may also checkout from Entry Details screen which can be accessed by clicking on an entry in Running Entry Section of MainActivity.</li>
</ul>
</li>
<li> <b> Past Entry</b>
<ul>
  <li>An entry once checked out can be viewed in Past Entry Section of MainActivity.</li>
</ul>
</li>
<li> <b> Log Out</b>
<ul>
  <li>You can Logout by clicking on power button on Toolbar on MainActivity.</li>
</ul>
</li>
<li> <b> Log In</b>
<ul>
  <li>If you're logging in for the first time then enter email & password, click on sign up, and with same credentials click on Login.</li>
  <li>Otherwise enter credentials and click on Login straightaway.</li>
</ul>
</li>
</ol>

## Application Installation
Clone the repo in your <b>Android Studio</b> and run it on an emulator or on your android device. You can also generate an APK from <b>Android Studio</b> and install it on any Android phone you want.
