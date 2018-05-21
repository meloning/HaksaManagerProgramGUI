import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.StudentDAO;
import DTO.StudentDTO;

public class HaksaStudent extends JPanel{
	//������Ʈ �׷�.
	JTextField nameInput;
	JTextField hakgwaInput;
	JTextField addressInput;
	JTextField hakbunInput;
	
	JButton insertBtn;
	JButton selectBtn;
	JButton updateBtn;
	JButton deleteBtn;
	JButton searchBtn;
	
	DefaultTableModel model;
	JTable table;
	
	//DAO ���������� ��ü
	StudentDAO stdDAO = new StudentDAO();
	
	public HaksaStudent() {
		this.setLayout(new FlowLayout());
		
		hakbunInput = new JTextField(14);
		nameInput = new JTextField(20);
		hakgwaInput = new JTextField("��ǻ�Ͱ��а�",20);
		addressInput = new JTextField("�����...",20);
		
		insertBtn = new JButton("���");
		selectBtn = new JButton("���");
		updateBtn = new JButton("����");
		deleteBtn = new JButton("����");
		searchBtn = new JButton("�˻�");
		
		//�Է� �� �߰�.
		this.add(new JLabel("�й� "));this.add(hakbunInput);this.add(searchBtn);//�˻��߰�(Layout�Ŵ�������Ǿ��⿡..)
		this.add(new JLabel("�̸� "));this.add(nameInput);
		this.add(new JLabel("�а� "));this.add(hakgwaInput);
		this.add(new JLabel("�ּ� "));this.add(addressInput);
		
		//��� �� �߰�(Table����)
		String colName[]={"�й�","�̸�","�а�","�ּ�"};//Table �ʵ��
		model=new DefaultTableModel(colName,0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(270,120));
		this.add(table);
		this.add(new JScrollPane(table));
		
		//��ư �߰�.
		this.add(insertBtn);this.add(selectBtn);this.add(updateBtn);this.add(deleteBtn);
		
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StudentDTO stdTemp = null;
				
				stdTemp=stdDAO.SelectById(hakbunInput.getText());
				
				hakbunInput.setText(stdTemp.getId());
				nameInput.setText(stdTemp.getName());
				hakgwaInput.setText(stdTemp.getDepartmentId());
				addressInput.setText(stdTemp.getAddress());
				
			}
			
		});
		
		insertBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//�ʼ��Է�üũ
				if(nameInput.getText().length()==0||hakbunInput.getText().length()==0||
						hakgwaInput.getText().length()==0||addressInput.getText().length()==0) {
					
					JOptionPane.showMessageDialog(null, "��ĭ�� �ֽ��ϴ�. �����Է����ּ���.","Message"
							,JOptionPane.ERROR_MESSAGE);
				
				}else {
					
					StudentDTO stdTemp = new StudentDTO();
					stdTemp.setId(hakbunInput.getText());
					stdTemp.setName(nameInput.getText());
					stdTemp.setDepartmentId(hakgwaInput.getText());
					stdTemp.setAddress(addressInput.getText());
					
					int result = stdDAO.Insert(stdTemp);
					
					if(result>0) {
						JOptionPane.showMessageDialog(null, "�Է¼���.","Success",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "�Է½���.","Fail",JOptionPane.ERROR_MESSAGE);
					}
					//��� �޼ҵ� ȣ��
					ShowList();
				}
				
			}
			
		});
		
		selectBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//��� �޼ҵ� ȣ��
				ShowList();
			}
			
		});
		
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id="";
				StudentDTO stdTemp = null;
				
				//������ ����� ����ϴ� �κ�.
				/*if(IsUpdate==false) {
					id=JOptionPane.showInputDialog("������ id �Է�(7����):");
					
					//����Ѱ��,
					if(id==null) return;
					
					stdTemp=dbm.SelectById(id);
					
					if(stdTemp==null) {
						JOptionPane.showMessageDialog(null, "�������� �ʴ� id�Դϴ�."+id,"Message",JOptionPane.ERROR_MESSAGE);
					}
					
					hakbunInput.setText(stdTemp.getId());
					nameInput.setText(stdTemp.getName());
					hakgwaInput.setText(stdTemp.getDept());
					addressInput.setText(stdTemp.getAddress());
					
					updateBtn.setText("�Ϸ�");
					IsUpdate=true;
					
				}else {*/
				
					//�������� ������ �����ϴ� �κ�.
					stdTemp=new StudentDTO();
					stdTemp.setId(hakbunInput.getText());
					stdTemp.setName(nameInput.getText());
					stdTemp.setDepartmentId(hakgwaInput.getText());
					stdTemp.setAddress(addressInput.getText());
					
					int result = stdDAO.Update(stdTemp);
					
					if(result>0) {
						JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.","Success",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "�������� ���߽��ϴ�.:"+id,"Fail",JOptionPane.ERROR_MESSAGE);
					}
					
					ShowList();
					
					//updateBtn.setText("����");
					//IsUpdate=false;
				//}
				
			}
			
		});
		
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "�����Ұ��Դϱ�?","Confirm",JOptionPane.YES_NO_OPTION);
				//YES�̸�,
				if(result==JOptionPane.YES_OPTION) {
					
					//String id=JOptionPane.showInputDialog("������ id �Է�(7����):");
					
					//����Ѱ��,
					//if(id==null) return;
					
					if(hakbunInput.getText()==null) {
						JOptionPane.showMessageDialog(null, "������ �й��� �Է����ּ���.","Success",JOptionPane.INFORMATION_MESSAGE);
					}
					
					int result2 = stdDAO.Delete(hakbunInput.getText());
					
					if(result2>0) {
						JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.","Success",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "�������� ���߽��ϴ�.:","Fail",JOptionPane.ERROR_MESSAGE);
					}
					//��� �޼ҵ� ȣ��
					ShowList();
				}
				//NO��� ����.
			}
			
		});
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				table=(JTable)e.getComponent();//Ŭ���� ���̺� ���ϱ�
				model=(DefaultTableModel)table.getModel();
				String id = (String) model.getValueAt(table.getSelectedRow(), 0);
				hakbunInput.setText(id);
				String name = (String) model.getValueAt(table.getSelectedRow(), 1);
				nameInput.setText(name);
				String dept = (String) model.getValueAt(table.getSelectedRow(), 2);
				hakgwaInput.setText(dept);
				String address = (String) model.getValueAt(table.getSelectedRow(), 3);
				addressInput.setText(address);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		
		//this.setResizable(false);
		this.setSize(290, 350);
		this.setVisible(true);
	}
	
	//���(Table) �޼ҵ�
	public void ShowList() {
		//�Է��� �ʱ�ȭ
		hakbunInput.setText("");
		nameInput.setText("");
		hakgwaInput.setText("");
		addressInput.setText("");
		
		//Table �ʱ�ȭ
		model.setNumRows(0);
		
		ArrayList<StudentDTO> arrStd = null;
		
		arrStd=stdDAO.SelectAll();
		
		if(arrStd==null) {
			JOptionPane.showMessageDialog(null, "��½���.","Fail",JOptionPane.ERROR_MESSAGE);
		}
		//�÷��� ����=> 4
		String[] row=new String[4];
		
		for(StudentDTO stdTemp:arrStd) {
			//jta.append(stdTemp+"\n");
			
		    row[0]=stdTemp.getId();
		    row[1]=stdTemp.getName();
		    row[2]=stdTemp.getDepartmentId();
		    row[3]=stdTemp.getAddress();
		    model.addRow(row);
		}
	}
	
	/*public static void main(String[] args) {
		new NewHaksa();
	}*/
}
