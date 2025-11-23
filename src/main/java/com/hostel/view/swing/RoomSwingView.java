package com.hostel.view.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.hostel.controller.RoomController;
import com.hostel.view.RoomView;

import hostel_management.Room;


public class RoomSwingView extends JFrame implements RoomView {

	
	private JTextField txtId;
	private JTextField txtName;
	private DefaultListModel<Room> roomListModel; // model storing the rooms
	private JList<Room> roomList;                 // the visible list
	private JButton btnDeleteSelected;
	private JLabel lblNewLabel_2;
	private RoomController roomController;

	DefaultListModel<Room> getRoomListModel() {
		return roomListModel;
	}
	
	private void resetErrorLabel() {
	    lblNewLabel_2.setText(" "); // clear the error label
	}
	
	//setter for roomController 
	
	public void setRoomController(RoomController controller) {
		this.roomController = controller;
		
	}
	
	
	
	
	public RoomSwingView() {
		setTitle("HostelView");
		setSize(600, 400); // make the frame visible for e2e
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close on exit
	    
	    
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		
		
		JLabel lblNewLabel = new JLabel("RoomId");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		txtId = new JTextField();
		txtId.setName("roomIdTextBox");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		getContentPane().add(txtId, gbc_textField);
		txtId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tenant Name");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtName = new JTextField();
		txtName.setName("nameTextBox");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		getContentPane().add(txtName, gbc_textField_1);
		txtName.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setEnabled(false);
		btnNewButton.setName("addButton");
		btnNewButton.addActionListener(e -> {
		    if (roomController != null) {
		        roomController.assignTenant(txtId.getText().trim(), txtName.getText().trim());
		    }
		});

		
		
		
		
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		
		//Key lister for enabling add button when the Fiield are non empty 
		
		KeyAdapter btnAddEnabler = new KeyAdapter() {
		  @Override 
		  public void keyReleased(KeyEvent e) {
			  btnNewButton.setEnabled(
			     !txtId.getText().trim().isEmpty() &&
			     !txtName.getText().trim().isEmpty()
			     );
					  
			 
		  }
		};
		txtId.addKeyListener(btnAddEnabler);
		txtName.addKeyListener(btnAddEnabler);
		
		
		
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		
		//This Selection is for the checking of deleteSelection button for checking enabling/Disabling
		
		
		// 1. Create the model (storage for room IDs)
		roomListModel = new DefaultListModel<>();
		
		
		// 2. Create the JList and connect it to the model
		roomList = new JList<>(roomListModel);
		
		roomList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				btnDeleteSelected.setEnabled(roomList.getSelectedIndex() != -1);
			}
		});
		roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomList.setName("roomList"); // same name as before

		
		
		
		
		
		
		
		// 3. put/show the JList to the scroll pane
		scrollPane.setViewportView(roomList);
		
		
		
		
		btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.setName("deleteButton");
	    btnDeleteSelected.setEnabled(false); // ✅ initially disabled
		btnDeleteSelected.addActionListener(e -> {
		    if (roomController != null && roomList.getSelectedValue() != null) {
		        roomController.vacateRoom(roomList.getSelectedValue().getRoomNumber());
		    }
		});

		
		
		
		btnDeleteSelected.setEnabled(false);
		GridBagConstraints gbc_btnDeleteSelected= new GridBagConstraints();
		gbc_btnDeleteSelected.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteSelected.gridwidth = 2;
		gbc_btnDeleteSelected.gridx = 0;
		gbc_btnDeleteSelected.gridy = 4;
		getContentPane().add(btnDeleteSelected, gbc_btnDeleteSelected);
		
		lblNewLabel_2 = new JLabel(" ");
		lblNewLabel_2.setName("errorMessageLabel");
		lblNewLabel_2.setForeground(Color.RED);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 5;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
	}




	@Override
	public void showAllRooms(List<Room> rooms) {
	    roomListModel.clear();            // clear old entries
	    rooms.forEach(roomListModel::addElement);  // add new rooms
	    resetErrorLabel();                // clear error label
	}




	@Override
	public void showError(String message, Room room) {
		lblNewLabel_2.setText(message+ ": " + room);
		lblNewLabel_2.setForeground(Color.RED); // make the error message red
		
	}

	
	
	//fixes for UI ,Frame Logic , earliar the list was disappeared automatucally 
	//and the newly add tenant showed so this update the list ,
	
	
	@Override 
	public void tenantAssigned(Room room, String tenantName) {
		//check if the room already exist in the list 
		boolean exists = false;
		for (int i=0;  i<roomListModel.size(); i++) {
			if (roomListModel.get(i).getRoomNumber().equals(room.getRoomNumber())) {
				//update the list 
			roomListModel.set(i,room);
			exists = true;
			break;
			}
			
		}
		
		if(!exists) {
			roomListModel.addElement(room);
		}
		
		resetErrorLabel();
		
	}


	@Override
	public void roomVacated(Room room) {
		
		
	// Remove the room from the list by room number 
		
	    for	(int i= 0; i < roomListModel.size(); i++) {
			if (roomListModel.get(i).getRoomNumber().equals(room.getRoomNumber())) {
				roomListModel.remove(i);
				break;
			}
	    }
		resetErrorLabel();
	}




	@Override
	public void showRoom(Room room) {
		roomListModel.addElement(room);
		resetErrorLabel();
		
	}
	
}
