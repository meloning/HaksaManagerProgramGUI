import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HaksaMain extends JFrame{
	JPanel panel;  // �޴��� ȭ���� ��µǴ� �г�
	int windowX=300,windowY=370;
	
	public HaksaMain() {
		this.setTitle("�л�����ý���");	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//x��ư ������ ���α׷� ����
		
		JMenuBar bar=new JMenuBar();
		
		JMenu m_student=new JMenu("�л�����");//File�޴�
		bar.add(m_student);
		JMenu m_book=new JMenu("��������");//Edit�޴�
		bar.add(m_book);
		
		JMenuItem mi_list=new JMenuItem("�л�����");
		m_student.add(mi_list);
		
		mi_list.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); //���������Ʈ ����
				panel.revalidate(); //�ٽ� Ȱ��ȭ
				panel.repaint();    //�ٽ� �׸���
				panel.add(new HaksaStudent()); //ȭ�� ����.
				panel.setLayout(null);//���̾ƿ��������
				initWindow(300,370);
			}});
			
		JMenuItem mi_bookRent=new JMenuItem("������Ȳ");
		m_book.add(mi_bookRent);
		mi_bookRent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); //���������Ʈ ����
				panel.revalidate(); //�ٽ� Ȱ��ȭ
				panel.repaint();    //�ٽ� �׸���
				panel.add(new BookRent()); //ȭ�� ����.
				panel.setLayout(null);//���̾ƿ��������
				initWindow(500,400);
			}
			
		});
		
		JMenuItem mi_statistics=new JMenuItem("�������");
		m_book.add(mi_statistics);
		mi_statistics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); //���������Ʈ ����
				panel.revalidate(); //�ٽ� Ȱ��ȭ
				panel.repaint();    //�ٽ� �׸���
				panel.add(new BookStatistics()); //ȭ�� ����.
				panel.setLayout(null);//���̾ƿ��������
				initWindow(300,370);
			}
			
		});
		
		panel=new JPanel();//panel����
		add(panel);//�����ӿ� �г� �߰�
		
		this.setJMenuBar(bar);
		initWindow(300,370);
		this.setResizable(true);//ȭ��ũ�����
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
