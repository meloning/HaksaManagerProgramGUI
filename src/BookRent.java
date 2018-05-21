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
		JLabel l_dept = new JLabel("학과");
		l_dept.setBounds(10, 10, 30, 20);
		this.add(l_dept);

		String[] dept = { "전체", "전자공학과", "컴퓨터공학과", "보안학과" };
		JComboBox cb_dept = new JComboBox(dept);
		cb_dept.setBounds(45, 10, 100, 20);
		this.add(cb_dept);

		cb_dept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				System.out.println(cb.getSelectedIndex());
				int deptIndex = cb.getSelectedIndex();

				//0. 전체 1. 전자공학과 2. 컴퓨터공학과 3. 보안학과
				arrBR=brDAO.SelectAll(deptIndex);
				
				if(arrBR!=null) {
					isDeptShowList=true;
				}else {
					isDeptShowList=false;
				}
				ShowList();
			}
		});

		String colName[] = { "학번", "이름", "도서명", "대출일" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(270,120));
		this.add(table);
		
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(10, 40, 460, 250);
		this.add(sp);

		JButton ListButton = new JButton("조회");
		ListButton.setBounds(10, 300, 70, 20);
		this.add(ListButton);

		// 목록버튼 이벤트 처리
		ListButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isDeptShowList=false;
				ShowList();// 목록출력
			}
		});

		this.setSize(700, 450);
		this.setVisible(true);
	}
	
	public void ShowList() {
		//Table 초기화
		model.setNumRows(0);
		
		if(!isDeptShowList)
			arrBR=brDAO.SelectAll();
		
		if(arrBR==null) {
			JOptionPane.showMessageDialog(null, "출력실패.","Fail",JOptionPane.ERROR_MESSAGE);
		}
		
		//컬럼의 갯수=> 4
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
