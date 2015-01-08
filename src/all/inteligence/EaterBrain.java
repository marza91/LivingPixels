package all.inteligence;

public class EaterBrain
{
	private int hunger;
	
	public EaterBrain()
	{
		setHunger(100);
	}
	
	public void setHunger(int hunger)
	{
		this.hunger=hunger;
		if(this.hunger>100)
			{setHunger(100);}
	}
	
	public int getHunger()
	{
		return hunger;
	}
}
