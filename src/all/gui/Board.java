package all.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import all.instance.*;

public class Board extends JPanel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	//private int maxEaters = 1000;
	//private int maxFood = 1000;
	private int deathCount = 0;
	private ArrayList<Eater> eaters=new ArrayList<Eater>();
	private ArrayList<Food> allfood=new ArrayList<Food>();
	private Image eatersprite=(new ImageIcon("IMG/Eater.png").getImage());
	private Image foodsprite=new ImageIcon("IMG/Food.png").getImage();
	private Date startTime;
	private Date endTime;
	private long highScoreH;
	private long highScoreM;
	private long highScoreS;
	private long highScoreTotal;

	public Board()
	{
		resetTime();
		highScoreH = 0;
		highScoreM = 0;
		highScoreS = 0;
		highScoreTotal = 0;
		endTime = startTime;
		setBackground(Color.BLUE);
		addMouseListener(this);		
	}
	
	public void addEater()
	{
		eaters.add(new Eater((int)(Math.random()*(getWidth() - 40)) + 20,
								(int)(Math.random()*(getHeight() - 40)) + 20, eatersprite));
		resetTime();
	}
	
	public void addFood()
	{
		allfood.add(new Food((int)(Math.random()*(getWidth() - 40)) + 20,
								(int)(Math.random()*(getHeight() - 40)) + 20, foodsprite));
		resetTime();
		if(eaters.size() == 0)
		{
			endTime = new Date();
		}
	}
	
	public void removeEater()
	{
		resetTime();
		removeEater(eaters.size()-1);
	}
	
	public void removeEater(int i)
	{
		if(eaters.size()>0)
		{eaters.remove(i);}

		if(eaters.size() == 0)
		{
			endTime = new Date();
		}
	}
	
	public void removeFood()
	{
		resetTime();
		removeFood(allfood.size()-1);
	}
	public void removeFood(int i)
	{
		if(allfood.size()>0)
			{allfood.remove(i);}
		if(eaters.size() == 0)
		{
			endTime = new Date();
		}
	}
	
	public void killAll()
	{
		allfood.clear();
		eaters.clear();
		resetTime();
		endTime = startTime;
	}
	
	public void paint(Graphics g)
    {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		
		for(int i=0;i<eaters.size();i++)
		{
			Eater eater = eaters.get(i);
			g2d.drawImage(eater.getSprite(), eater.getX(), eater.getY(), this);
			//g2d.drawString(Integer.toString(eater.getHunger()), eater.getX(), eater.getY());
		}
		
		for(int i=0;i<allfood.size();i++)
		{
			Food food = allfood.get(i);
			g2d.drawImage(food.getSprite(), food.getX(), food.getY(), this);
			//g2d.drawString(Integer.toString(food.getAmount()), food.getX(), food.getY());
		}
		
		long diff = 0;

		if(endTime != null)
		{
			diff = endTime.getTime() - startTime.getTime();
		}
		else
		{
			diff = new Date().getTime() - startTime.getTime();
		}
		long hours = diff / 3600000;
		long minutes = (diff / 60000) % 60;
		long seconds = (diff / 1000) % 60;
		if(diff > highScoreTotal)
		{
			highScoreTotal = diff;
			highScoreH = hours;
			highScoreM = minutes;
			highScoreS = seconds;
		}
		
		Rectangle info=new Rectangle(0, 0, 110, 120);
		g2d.setColor(Color.CYAN);
		g2d.draw(info);
		g2d.fill(info);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Eaters: "+eaters.size(), 5, 20);
		g2d.drawString("Deaths: "+deathCount, 5, 40);
		g2d.drawString("Food: "+allfood.size(),5,60);
		g2d.drawString("Timer: " + hours + ":" + minutes + ":" + seconds ,5,80);
		g2d.drawString("HighScore: " + highScoreH + ":" + highScoreM + ":" + highScoreS ,5,100);
		
		
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

	public void refresh()
	{
		for(int i=0;i<eaters.size();i++)
		{
			Eater eater = eaters.get(i);
			eater.setHunger(eater.getHunger()-1);
			if(eater.getHunger()<0)
			{
				removeEater(i);
				allfood.add(new Food(eater.getX(),eater.getY(),70,foodsprite));
				deathCount++;
			}
			else if(eater.getHunger()>100)
			{
				Eater child=new Eater(eater.getX()+eater.getSprite().getWidth(null)+1, eater.getY(), eatersprite);
				child.setHunger(eater.getHunger()/2);
				eaters.add(child);
				eater.setHunger(eater.getHunger()/2);
			}
			else if(eater.getHunger()<50 && allfood.size()>0)
			{
				if(eater.getChasing()==null)
				{
					int tries = 0;
					int index = (int)(Math.random() * allfood.size());
					Food closest = allfood.get(index);
					double closestdist=distanceBetween(eater, closest);
					while(tries < allfood.size() * 0.5)
					{
						index = (int)(Math.random() * allfood.size());
						Food test=allfood.get(index);
						double testdist=distanceBetween(eater, test);
						if(testdist<closestdist)
						{
							closest=test;
							closestdist=testdist;
						}
						tries++;
					}
					
					eater.setChasing(closest);
				}
				else
				{
					if(eater.collidesWith(eater.getChasing()))
					{
						eater.setHunger(eater.getHunger()+(int)(eater.getChasing().getAmount() * ((Math.random() * 0.4) + 0.3) ));
						allfood.remove(eater.getChasing());
						eater.setChasing(null);
						eater.setDx(0);
						eater.setDy(0);
					}
					else
						{MoveAToB(eater,eater.getChasing());}
				}
			}
			else
				{MoveRandom(eater);}
			move(eater);
		}
		for(int i=0;i<allfood.size();i++)
		{
			Food food=allfood.get(i);
			if(!food.sporesReleased)
			{
				food.setAmount(food.getAmount()+2);
				if(food.getAmount()>100)
				{
					if(Math.random() > 0.5)
					{
						//new
						Food newFood = new Food((int)(Math.random()* (getWidth() - 40)) + 20,
												(int)(Math.random()* (getHeight() - 40)) + 20,
												50,foodsprite);

						if(food.getX()<20)
							{food.setX(20);}
						else if(food.getX()>getWidth() - 20)
							{food.setX(getWidth() - 20);}
						else if(food.getY()<20)
							{food.setY(20);}
						else if(food.getY()>getHeight() - 20)
							{food.setY(getHeight() - 20);}
						
						allfood.add(newFood);
						food.setAmount(50);
					}else{
						food.sporesReleased = true;
					}
				}
			}
		}
	}

	private void MoveRandom(Thing a)
	{
		if(Math.random() > 0.5)
		{
			a.setDx(-a.getSpeed());
		}else
		{
			a.setDx(a.getSpeed());
		}
		if(Math.random() > 0.5)
		{
			a.setDy(-a.getSpeed());
		}else
		{
			a.setDy(a.getSpeed());
		}
	}

	private void MoveAToB(Thing a, Thing b)
	{
		if(a.getX()>b.getX())
		{
			a.setDx(-a.getSpeed());
		}
		else if(a.getX()<b.getX())
		{
			a.setDx(a.getSpeed());
		}
		if(a.getY()>b.getY())
		{
			a.setDy(-a.getSpeed());
		}
		else if(a.getY()<b.getY())
		{
			a.setDy(a.getSpeed());
		}
	}

	private double distanceBetween(Thing one, Thing two)
	{
		int a=one.getX()-two.getX();
		int b=one.getY()-two.getY();
		return (a*a)+(b*b);
	}
	
	private void move(Thing t)
	{
		t.setX(t.getX() + t.getDx());
		t.setY(t.getY() + t.getDy());
	}
	
	private void resetTime()
	{
		startTime = new Date();
		endTime = null;
		//System.out.println("Reset");
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton()==1)
		{
			eaters.add(new Eater(e.getX(),e.getY(),eatersprite));
			resetTime();
		}
		else if(e.getButton()==3)
		{
			allfood.add(new Food(e.getX(),e.getY(),foodsprite));
			resetTime();
			if(eaters.size() == 0)
			{
				endTime = startTime;
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		
	}

	public void mouseEntered(MouseEvent e)
	{
		
	}

	public void mouseExited(MouseEvent e)
	{
		
	}
}
