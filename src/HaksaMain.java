import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HaksaMain extends JFrame{
	JPanel panel;  // 메뉴별 화면이 출력되는 패널
	int windowX=300,windowY=370;
	
	public HaksaMain() {
		this.setTitle("학사관리시스템");	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//x버튼 누르면 프로그램 종료
		
		JMenuBar bar=new JMenuBar();
		
		JMenu m_student=new JMenu("학생관리");//File메뉴
		bar.add(m_student);
		JMenu m_book=new JMenu("도서관리");//Edit메뉴
		bar.add(m_book);
		
		JMenuItem mi_list=new JMenuItem("학생정보");
		m_student.add(mi_list);
		
		mi_list.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); //모든컴포넌트 삭제
				panel.revalidate(); //다시 활성화
				panel.repaint();    //다시 그리기
				panel.add(new HaksaStudent()); //화면 생성.
				panel.setLayout(null);//레이아웃적용안함
				initWindow(300,370);
			}});
			
		JMenuItem mi_bookRent=new JMenuItem("대출현황");
		m_book.add(mi_bookRent);
		mi_bookRent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); //모든컴포넌트 삭제
				panel.revalidate(); //다시 활성화
				panel.repaint();    //다시 그리기
				panel.add(new BookRent()); //화면 생성.
				panel.setLayout(null);//레이아웃적용안함
				initWindow(500,400);
			}
			
		});
		
		JMenuItem mi_statistics=new JMenuItem("대출통계");
		m_book.add(mi_statistics);
		mi_statistics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); //모든컴포넌트 삭제
				panel.revalidate(); //다시 활성화
				panel.repaint();    //다시 그리기
				panel.add(new BookStatistics()); //화면 생성.
				panel.setLayout(null);//레이아웃적용안함
				initWindow(300,370);
			}
			
		});
		
		panel=new JPanel();//panel생성
		add(panel);//프레임에 패널 추가
		
		this.setJMenuBar(bar);
		initWindow(300,370);
		this.setResizable(true);//화면크기고정
		this.setVisible(true);
	}
	
	public void initWindow(int windowx,int windowy) {
		this.windowX=windowx;
		this.windowY=windowy;
		this.setSize(windowX,windowY);
	}
	
	public static void main(String[] args) {
		new HaksaMain();
	}

}
