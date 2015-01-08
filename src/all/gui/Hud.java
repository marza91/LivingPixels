package all.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Hud extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private String[] buttonNames={"New Eater","Remove Eater","New Food","Remove Food","Remove All","exit"};
	private BtnListen listener=new BtnListen();
	private int clicked=-1;
	
	public Hud()
	{
		buttons=new JButton[buttonNames.length];
		for(int i=0;i<buttons.length;i++)
		{
			buttons[i]=new JButton(buttonNames[i]);
			buttons[i].addActionListener(listener);
			add(buttons[i]);
		}
	}
	
	public void setClicked(int clicked)
		{this.clicked = clicked;}

	public int getClicked()
		{return clicked;}

	private class BtnListen implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton pressed = (JButton) e.getSource();
			for(int i=0;i<buttons.length;i++)
			{
				if (buttons[i]==pressed)
				{
					setClicked(i);
				}
			}
		}
		
	}
}
