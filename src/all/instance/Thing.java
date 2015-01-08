package all.instance;

import java.awt.Image;
import java.awt.Rectangle;

public abstract class Thing
{
	private int x,y,speed,dx,dy;
	private Image sprite;

	public Thing(int x, int y, int speed, Image sprite)
	{
		setX(x);
		setY(y);
		setSpeed(speed);
		setDx(0);
		setDy(0);
		setSprite(sprite);
	}

	//SET
	public void setX(int x)
		{this.x = x;}
	public void setY(int y)
		{this.y = y;}
	public void setSpeed(int speed)
		{this.speed = speed;}
	public void setDx(int dx)
		{this.dx = dx;}
	public void setDy(int dy)
		{this.dy = dy;}
	public void setSprite(Image sprite)
	{
		if(sprite!=null)
			{this.sprite=sprite;}
	}

	//GET
	public int getX()
		{return x;}
	public int getY()
		{return y;}
	public int getSpeed()
		{return speed;}
	public int getDx()
		{return dx;}
	public int getDy()
		{return dy;}
	public Image getSprite()
		{return sprite;}
	public Rectangle getBounds()
		{return new Rectangle(x, y, sprite.getWidth(null), sprite.getHeight(null));}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thing other = (Thing) obj;
		if (sprite == null) {
			if (other.sprite != null)
				return false;
		} else if (!sprite.toString().equals(other.sprite.toString()))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/*public void move()
	{
		setX(getX() + getDx());
		setY(getY() + getDy());
		if(getX()<0)
			{setX(768);}
		else if(getX()>768)
			{setX(0);}
		if(getY()<0)
			{setY(568);}
		else if(getY()>568)
			{setY(0);}
	}*/
	
	public boolean collidesWith(Thing t)
	{
		int dx = x - t.getX();
		int dy = y - t.getY();
		int radii = 10;
		return ( ( dx * dx )  + ( dy * dy ) < radii * radii );
		//return(getBounds().intersects(t.getBounds()));
	}
	
	public static int random(int max)
	{return (int) (Math.random()*max);}
}
