package com.hostel.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.hostel.view.RoomView;

public class RoomSwingView extends JFrame implements RoomView {
	private JTextField txtRoomidtextbox;
	public RoomSwingView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("RoomId");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		txtRoomidtextbox = new JTextField();
		txtRoomidtextbox.setName("roomIdTextBox");
		txtRoomidtextbox.setText("roomIdTextBox");
		GridBagConstraints gbc_txtRoomidtextbox = new GridBagConstraints();
		gbc_txtRoomidtextbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomidtextbox.gridx = 1;
		gbc_txtRoomidtextbox.gridy = 0;
		getContentPane().add(txtRoomidtextbox, gbc_txtRoomidtextbox);
		txtRoomidtextbox.setColumns(10);
	}

}
