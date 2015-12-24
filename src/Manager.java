import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Manager extends JFrame{
	Manager(){
		JButton btn_apply = new JButton("확인");
		JButton btn_cancle = new JButton("취소");
		JTextField input_search = new JTextField();
		
		setTitle("Search Program");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(1, 2));
		btnGroup.add(btn_apply);
		btnGroup.add(btn_cancle);
		
		
		add(input_search, "Center");
		add(btnGroup, "South");
		
		
		
		
		
		setSize(500,500);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Manager manager = new Manager();
		
	}

}
