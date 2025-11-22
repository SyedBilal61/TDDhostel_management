Feature: HostelRoomApplication
  Specifications of the behavior of the Hostel Room application

Scenario: The initial state of the Room View
  Given the database contains a room with number "A1" and tenant "Ali"
  When the Hostel Room View is shown
  Then the list contains an element with room number "A1" and tenant "Ali"
