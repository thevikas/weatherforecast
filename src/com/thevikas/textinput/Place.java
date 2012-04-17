package com.thevikas.textinput;

import java.util.Observable;

public class Place extends Observable
{

	private String mName;
	
	public Place()
	{
		this.mName = new String();
		this.mName = "none";
	}
	
	public void doRepaint()
	{
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setName(String text) 
	{		
		this.mName = text.toString();
		this.doRepaint();
	}
	
	public String getName()
	{
		return this.mName;
	}
	
}
