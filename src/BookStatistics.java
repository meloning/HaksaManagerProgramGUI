import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DAO.BookRentDAO;

public class BookStatistics extends JPanel {
	// ������Ʈ �׷�
	private Color[] color = { Color.red, Color.black, Color.green, Color.blue };
	PI_ChartPanel chartPanel;

	// DAO
	BookRentDAO brDAO = new BookRentDAO();
	HashMap<String, String> Temp;// DAO ��ȯ�ӽú���

	boolean isYearShowList;// �⵵�� �б� ������ Flag����
	int[] value = new int[4];// �б⺰ �� ���� ����

	int quarterValueIndex = 0;
	int[] quarterValue = new int[4];
	
	//Animation Speed Controller
	Thread timer;

	public BookStatistics() {
		this.setLayout(null);

		JLabel l_dept = new JLabel("�������");
		l_dept.setBounds(110, 10, 60, 20);
		this.add(l_dept);

		String[] year = { "ALL", "2016", "2017", "2018" };
		JComboBox cb_year = new JComboBox(year);
		cb_year.setBounds(180, 10, 70, 20);
		this.add(cb_year);

		cb_year.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				int deptIndex = cb.getSelectedIndex();
				
				for (int i = 0; i < chartPanel.isPrintShow.length; i++) {
					chartPanel.isPrintShow[i] = 0;
				}
				
				chartPanel.preangle = 0;

				// 0. ALL 1. 2016 2. 2017 3. 2018
				// return 0-> null | else-> not null
				Temp = brDAO.ShowStatistics(deptIndex);
				
				//comboSelect 1,2,3 clicked
				if (Temp != null) {
					timer = new Thread(chartPanel);
					timer.start();
					isYearShowList = true;
				} else {
					isYearShowList = false;
				}
				chartPanel.repaint();
			}

		});

		chartPanel = new PI_ChartPanel();
		chartPanel.setBounds(10, 30, 700, 450);
		this.add(chartPanel);

		this.setSize(700, 450);
		this.setVisible(true);
	}

	public class PI_ChartPanel extends JPanel implements Runnable {
		//���� ���۸�(thread ������� �ߺ� repaint()ȣ�� ������)
		Image bi;
		Graphics offG;
		
		// animation Control ����
		int preangle;
		int isPrintShow[] = new int[4];

		public void paintComponent(Graphics g) {
			bi = this.createImage(700, 450);
			offG = bi.getGraphics();
			offG.clearRect(0, 0, 700, 450);

			int sum = 0;
			
			// �⵵���� �����ٶ�(ComboBox => ALL)
			if (!isYearShowList) {
				Temp = brDAO.ShowStatistics();
				for (int i = 0; i < value.length - 1; i++) {
					value[i] = Integer.parseInt(Temp.get("201" + String.valueOf(i + 6)));
					sum += value[i];
					// System.out.println(value[i]);
				}

				// ������Ʈ�׸� ����
				quarterValue[0] = (int) ((value[0] * 360 / sum));// 2016
				quarterValue[1] = (int) ((value[1] * 360 / sum));// 2017
				quarterValue[2] = (int) ((value[2] * 360 / sum));// 2018

				// ������Ʈ �׸���
				//----------------------------------------------------------------------------------------------------
				//2016
				offG.setColor(color[0]);
				offG.fillArc(30, 30, 200, 200, quarterValue[0] + quarterValue[1] + quarterValue[2], quarterValue[0]);
				
				offG.fillArc(30+40, 30+200+5, 10, 10, 0, 360);
				offG.setColor(Color.black);
				offG.drawString("2016", 30+40+10, 30+200+10+5);
				//----------------------------------------------------------------------------------------------------
				//2017
				offG.setColor(color[1]);
				offG.fillArc(30, 30, 200, 200, quarterValue[0], quarterValue[1]);
				
				offG.fillArc(30+40+40, 30+200+5, 10, 10, 0, 360);
				offG.setColor(Color.black);
				offG.drawString("2017", 30+40+10+40, 30+200+10+5);
				//----------------------------------------------------------------------------------------------------
				//2018
				offG.setColor(color[2]);
				offG.fillArc(30, 30, 200, 200, quarterValue[0] + quarterValue[1], quarterValue[2]);
				
				offG.fillArc(30+40+40+40, 30+200+5, 10, 10, 0, 360);
				offG.setColor(Color.black);
				offG.drawString("2018", 30+40+10+40+40, 30+200+10+5);
				//----------------------------------------------------------------------------------------------------
			}
			// �⵵�� �б⸦ ������ ��
			else {
				for (int i = 0; i < value.length; i++) {
					// System.out.println("Temp.get()=>"+Temp.get(String.valueOf(i+1)));
					value[i] = Integer.parseInt(Temp.get(String.valueOf(i + 1)));
					sum += value[i];
					// System.out.println((i + 1) + "�б�:" + value[i]);
				}

				// ������Ʈ�׸� ����
				quarterValue[0] = (int) ((value[0] * 360 / sum));// 1�б�
				quarterValue[1] = (int) ((value[1] * 360 / sum));// 2�б�
				quarterValue[2] = (int) ((value[2] * 360 / sum));// 3�б�
				quarterValue[3] = (int) ((value[3] * 360 / sum));// 4�б�

				// ���� �α�
				for (int i = 0; i < quarterValue.length; i++) {
					// System.out.println((i + 1) + "�б⺰����:" + quarterValue[i]);
					System.out.print(quarterValue[i] + ((i != 3) ? ", " : "\n"));
				}

				// ������Ʈ �׸���
				//----------------------------------------------------------------------------------------------------
				// 1�б�
				offG.setColor(color[0]);
				offG.fillArc(30, 30, 200, 200, 0, (quarterValueIndex == 0) ? preangle : quarterValue[0]);
				
				offG.fillArc(20+5, 30+200+5, 10, 10, 0, 360);
				offG.setColor(Color.black);
				offG.drawString("1/4�б�", 20+5+10, 30+200+10+5);
				//----------------------------------------------------------------------------------------------------
				// 2�б�
				if (isPrintShow[1] == 1) {
					offG.setColor(color[1]);
					offG.fillArc(30, 30, 200, 200, quarterValue[0],
							(quarterValueIndex == 1) ? preangle : quarterValue[1]);
					
					offG.fillArc(20+5+50, 30+200+5, 10, 10, 0, 360);
					offG.setColor(Color.black);
					offG.drawString("2/4�б�", 20+5+10+50, 30+200+10+5);
				}
				//----------------------------------------------------------------------------------------------------
				// 3�б�
				if (isPrintShow[2] == 2) {
					offG.setColor(color[2]);
					offG.fillArc(30, 30, 200, 200, quarterValue[0] + quarterValue[1],
							(quarterValueIndex == 2) ? preangle : quarterValue[2]);
					
					offG.fillArc(20+5+50+50, 30+200+5, 10, 10, 0, 360);
					offG.setColor(Color.black);
					offG.drawString("3/4�б�", 20+5+10+50+50, 30+200+10+5);
				}
				//----------------------------------------------------------------------------------------------------
				// 4�б�
				if (isPrintShow[3] == 3) {
					offG.setColor(color[3]);
					offG.fillArc(30, 30, 200, 200, quarterValue[0] + quarterValue[1] + quarterValue[2],
							(quarterValueIndex == 3) ? preangle : quarterValue[3]);
					
					offG.fillArc(20+5+50+50+50, 30+200+5, 10, 10, 0, 360);
					offG.setColor(Color.black);
					offG.drawString("4/4�б�", 20+5+10+50+50+50, 30+200+10+5);
				}
				//----------------------------------------------------------------------------------------------------
			}
			update(g);
		}
		
		public void update(Graphics g) {
			g.drawImage(bi, 0, 0, this);
		}
		
		@Override
		public void run() {

			while (true) {
				try {
					
					//�ʱ�ȭ �� thread ����
					if (quarterValueIndex == quarterValue.length) {
						quarterValueIndex = 0;
						return;
					}

					if (preangle <= quarterValue[quarterValueIndex]) {
						preangle++;
						
						if (preangle > quarterValue[quarterValueIndex]) {
							quarterValueIndex++;
							
							//�ʱ�ȭ �� thread ����
							if (quarterValueIndex == quarterValue.length) {
								quarterValueIndex = 0;
								return;
							}
							
							isPrintShow[quarterValueIndex] = quarterValueIndex;

							preangle = 0;
						}
					}

					System.out.println("quarterValueIndex:" + quarterValueIndex);
					System.out.println("preangle:" + preangle);
					System.out.println("isPrintShow:" + isPrintShow[quarterValueIndex]);

					chartPanel.repaint();

					Thread.sleep(20);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
