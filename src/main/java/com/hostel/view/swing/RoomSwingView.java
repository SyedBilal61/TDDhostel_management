package com.hostel.view.swing;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.hostel.view.RoomView;

public class RoomSwingView extends JFrame implements RoomView {

	private JPanel contentPane;
	private JTextField txtRoomi;
	private JTextField txtNametextbox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomSwingView frame = new RoomSwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RoomSwingView() {
		setTitle("Hostel View ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("ROOM ID");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txtRoomi = new JTextField();
		txtRoomi.setText("roomIdTextBox");
		GridBagConstraints gbc_txtRoomi = new GridBagConstraints();
		gbc_txtRoomi.insets = new Insets(0, 0, 5, 0);
		gbc_txtRoomi.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomi.gridx = 1;
		gbc_txtRoomi.gridy = 0;
		contentPane.add(txtRoomi, gbc_txtRoomi);
		txtRoomi.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("TenantName");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtNametextbox = new JTextField();
		txtNametextbox.setText("nameTextBox");
		GridBagConstraints gbc_txtNametextbox = new GridBagConstraints();
		gbc_txtNametextbox.insets = new Insets(0, 0, 5, 0);
		gbc_txtNametextbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNametextbox.gridx = 1;
		gbc_txtNametextbox.gridy = 1;
		contentPane.add(txtNametextbox, gbc_txtNametextbox);
		txtNametextbox.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setEnabled(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setName("RoomList");
		
		JButton btnNewButton_1 = new JButton("Delete Selected");
		btnNewButton_1.setEnabled(false);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridwidth = 2;
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 4;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setName("errorMessageLabel");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 5;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
	}

}
