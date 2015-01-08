package all.instance;

import java.awt.Image;

public class Food extends Thing
{
	private int amount;
	public boolean sporesReleased = false;
	
	public Food(int x, int y, Image sprite)
	{
		this(x,y,random(100)+1,sprite);
	}
	public Food(int x, int y, int amount, Image sprite)
	{
		super(x,y,0,sprite);
		setAmount(amount);
	}
	
	public void setAmount(int amount)
		{this.amount = amount;}

	public int getAmount()
		{return amount;}
	
	
}
