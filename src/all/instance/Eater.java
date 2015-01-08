package all.instance;

import java.awt.Image;

public class Eater extends Thing
{
	private int hunger;
	private Food chasing;
	
	public Eater(int x, int y, Image sprite)
	{
		super(x,y,3,sprite);
		setHunger(random(100)+1);
	}

	public void setHunger(int hunger)
		{this.hunger=hunger;}
	public void setChasing(Food chasing)
		{this.chasing = chasing;}

	public int getHunger()
		{return hunger;}
	public Food getChasing()
		{return chasing;}

	/*public void setEaterBrain(EaterBrain eaterBrain)
		{this.eaterBrain = eaterBrain;}

	public EaterBrain getEaterBrain()
		{return eaterBrain;}*/
}
