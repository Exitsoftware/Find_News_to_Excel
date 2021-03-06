import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;

public class Manager extends JFrame{

	public static JFileChooser fileChooser = new JFileChooser();

	Manager(){
		JLabel title = new JLabel("검색어를 입력해 주세요.",JLabel.CENTER);
		JLabel save_title = new JLabel("결과물 몇 백개를 뽑을까요?",JLabel.CENTER);
		
		JButton btn_apply = new JButton("확인");
		JButton btn_cancle = new JButton("취소");
		JTextField input_search = new JTextField();
		JTextField input_savename = new JTextField();
		
		setTitle("Search Program");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		JPanel btnGroup = new JPanel(new GridLayout(1, 2));
		JPanel pan_north = new JPanel(new GridLayout(2, 2));

		btnGroup.add(btn_apply);
		btnGroup.add(btn_cancle);
		
		pan_north.add(title);
		pan_north.add(input_search);
		pan_north.add(save_title);
		pan_north.add(input_savename);
		
		add(pan_north, "Center");
		add(btnGroup, "South");
		
		Main m = new Main();
		
		btn_apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				fileChooser.setDialogTitle("Save file");
				fileChooser.addChoosableFileFilter(new MyFilter("xlsx", "Excel File"));
				int userSelection = fileChooser.showSaveDialog(null);
				if(userSelection == JFileChooser.APPROVE_OPTION) {
					String query = input_search.getText();
					String count = input_savename.getText();
					m.naverStart(query, count, fileChooser);
				}

			}
		});
		
		
		setSize(500,200);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Manager manager = new Manager();
		
	}

}
