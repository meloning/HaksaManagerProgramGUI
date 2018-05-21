import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.BookRentDAO;
import DTO.BookRentDTO;
import DTO.StudentDTO;

public class BookRent extends JPanel {
	DefaultTableModel model;
	JTable table;
	
	ArrayList<BookRentDTO> arrBR= null;
	ArrayList<StudentDTO> arrStd=null;
	
	BookRentDAO brDAO= new BookRentDAO();
	boolean isDeptShowList;
	
	public BookRent() {
		this.setLayout(null);
		JLabel l_dept = new JLabel("�а�");
		l_dept.setBounds(10, 10, 30, 20);
		this.add(l_dept);

		String[] dept = { "��ü", "���ڰ��а�", "��ǻ�Ͱ��а�", "�����а�" };
		JComboBox cb_dept = new JComboBox(dept);
		cb_dept.setBounds(45, 10, 100, 20);
		this.add(cb_dept);

		cb_dept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				System.out.println(cb.getSelectedIndex());
				int deptIndex = cb.getSelectedIndex();

				//0. ��ü 1. ���ڰ��а� 2. ��ǻ�Ͱ��а� 3. �����а�
				arrBR=brDAO.SelectAll(deptIndex);
				
				if(arrBR!=null) {
					isDeptShowList=true;
				}else {
					isDeptShowList=false;
				}
				ShowList();
			}
		});

		String colName[] = { "�й�", "�̸�", "������", "������" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(270,120));
		this.add(table);
		
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(10, 40, 460, 250);
		this.add(sp);

		JButton ListButton = new JButton("��ȸ");
		ListButton.setBounds(10, 300, 70, 20);
		this.add(ListButton);

		// ��Ϲ�ư �̺�Ʈ ó��
		ListButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isDeptShowList=false;
				ShowList();// ������
			}
		});

		this.setSize(700, 450);
		this.setVisible(true);
	}
	
	public void ShowList() {
		//Table �ʱ�ȭ
		model.setNumRows(0);
		
		if(!isDeptShowList)
			arrBR=brDAO.SelectAll();
		
		if(arrBR==null) {
			JOptionPane.showMessageDialog(null, "��½���.","Fail",JOptionPane.ERROR_MESSAGE);
		}
		
		//�÷��� ����=> 4
		String[] row=new String[4];
		
		for(BookRentDTO brTemp:arrBR) {
			//jta.append(stdTemp+"\n");
			
		    row[0]=brTemp.getStdDto().getId();
		    row[1]=brTemp.getStdDto().getName();
		    row[2]=brTemp.getBookno();
		    row[3]=brTemp.getrDate();
		    model.addRow(row);
		}
	}
	
}
