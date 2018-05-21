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
	//컴포넌트 그룹.
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
	
	//DAO 데이터접근 객체
	StudentDAO stdDAO = new StudentDAO();
	
	public HaksaStudent() {
		this.setLayout(new FlowLayout());
		
		hakbunInput = new JTextField(14);
		nameInput = new JTextField(20);
		hakgwaInput = new JTextField("컴퓨터공학과",20);
		addressInput = new JTextField("서울시...",20);
		
		insertBtn = new JButton("등록");
		selectBtn = new JButton("목록");
		updateBtn = new JButton("수정");
		deleteBtn = new JButton("삭제");
		searchBtn = new JButton("검색");
		
		//입력 폼 추가.
		this.add(new JLabel("학번 "));this.add(hakbunInput);this.add(searchBtn);//검색추가(Layout매니저적용되었기에..)
		this.add(new JLabel("이름 "));this.add(nameInput);
		this.add(new JLabel("학과 "));this.add(hakgwaInput);
		this.add(new JLabel("주소 "));this.add(addressInput);
		
		//출력 폼 추가(Table형태)
		String colName[]={"학번","이름","학과","주소"};//Table 필드명
		model=new DefaultTableModel(colName,0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(270,120));
		this.add(table);
		this.add(new JScrollPane(table));
		
		//버튼 추가.
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
				//필수입력체크
				if(nameInput.getText().length()==0||hakbunInput.getText().length()==0||
						hakgwaInput.getText().length()==0||addressInput.getText().length()==0) {
					
					JOptionPane.showMessageDialog(null, "빈칸이 있습니다. 전부입력해주세요.","Message"
							,JOptionPane.ERROR_MESSAGE);
				
				}else {
					
					StudentDTO stdTemp = new StudentDTO();
					stdTemp.setId(hakbunInput.getText());
					stdTemp.setName(nameInput.getText());
					stdTemp.setDepartmentId(hakgwaInput.getText());
					stdTemp.setAddress(addressInput.getText());
					
					int result = stdDAO.Insert(stdTemp);
					
					if(result>0) {
						JOptionPane.showMessageDialog(null, "입력성공.","Success",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "입력실패.","Fail",JOptionPane.ERROR_MESSAGE);
					}
					//출력 메소드 호출
					ShowList();
				}
				
			}
			
		});
		
		selectBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//출력 메소드 호출
				ShowList();
			}
			
		});
		
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id="";
				StudentDTO stdTemp = null;
				
				//수정할 목록을 출력하는 부분.
				/*if(IsUpdate==false) {
					id=JOptionPane.showInputDialog("변경할 id 입력(7글자):");
					
					//취소한경우,
					if(id==null) return;
					
					stdTemp=dbm.SelectById(id);
					
					if(stdTemp==null) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 id입니다."+id,"Message",JOptionPane.ERROR_MESSAGE);
					}
					
					hakbunInput.setText(stdTemp.getId());
					nameInput.setText(stdTemp.getName());
					hakgwaInput.setText(stdTemp.getDept());
					addressInput.setText(stdTemp.getAddress());
					
					updateBtn.setText("완료");
					IsUpdate=true;
					
				}else {*/
				
					//실질적인 수정을 적용하는 부분.
					stdTemp=new StudentDTO();
					stdTemp.setId(hakbunInput.getText());
					stdTemp.setName(nameInput.getText());
					stdTemp.setDepartmentId(hakgwaInput.getText());
					stdTemp.setAddress(addressInput.getText());
					
					int result = stdDAO.Update(stdTemp);
					
					if(result>0) {
						JOptionPane.showMessageDialog(null, "수정되었습니다.","Success",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "수정하지 못했습니다.:"+id,"Fail",JOptionPane.ERROR_MESSAGE);
					}
					
					ShowList();
					
					//updateBtn.setText("수정");
					//IsUpdate=false;
				//}
				
			}
			
		});
		
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "삭제할것입니까?","Confirm",JOptionPane.YES_NO_OPTION);
				//YES이면,
				if(result==JOptionPane.YES_OPTION) {
					
					//String id=JOptionPane.showInputDialog("삭제할 id 입력(7글자):");
					
					//취소한경우,
					//if(id==null) return;
					
					if(hakbunInput.getText()==null) {
						JOptionPane.showMessageDialog(null, "삭제할 학번을 입력해주세요.","Success",JOptionPane.INFORMATION_MESSAGE);
					}
					
					int result2 = stdDAO.Delete(hakbunInput.getText());
					
					if(result2>0) {
						JOptionPane.showMessageDialog(null, "삭제되었습니다.","Success",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "삭제하지 못했습니다.:","Fail",JOptionPane.ERROR_MESSAGE);
					}
					//출력 메소드 호출
					ShowList();
				}
				//NO라면 없음.
			}
			
		});
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				table=(JTable)e.getComponent();//클릭한 테이블 구하기
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
	
	//출력(Table) 메소드
	public void ShowList() {
		//입력폼 초기화
		hakbunInput.setText("");
		nameInput.setText("");
		hakgwaInput.setText("");
		addressInput.setText("");
		
		//Table 초기화
		model.setNumRows(0);
		
		ArrayList<StudentDTO> arrStd = null;
		
		arrStd=stdDAO.SelectAll();
		
		if(arrStd==null) {
			JOptionPane.showMessageDialog(null, "출력실패.","Fail",JOptionPane.ERROR_MESSAGE);
		}
		//컬럼의 갯수=> 4
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
