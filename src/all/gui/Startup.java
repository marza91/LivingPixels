package all.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Startup extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Board board=new Board();
	private Hud hud=new Hud();
	private Timer timer;
	
	public Startup()
	{
		setLayout(new BorderLayout());
		add(board,BorderLayout.CENTER);
		add(hud,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Living Pixels");
        setSize(800,600);
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(true);
        setVisible(true);
        
        timer = new Timer(25, this);
        timer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		switch(hud.getClicked())
		{
			case 0:
				board.addEater();
				break;
			case 1:
				board.removeEater();
				break;
			case 2:
				board.addFood();
				break;
			case 3:
				board.removeFood();
				break;
			case 4:
				board.killAll();
				break;
			case 5:System.exit(0);
		}
		hud.setClicked(-1);
		board.repaint();
		board.refresh();
	}
	
	public static void main(String[]args)
	{
		new Startup();
	}
}
