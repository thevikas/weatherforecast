package com.thevikas.textinput;

import java.util.Observable;

public class WeatherReport extends Observable
{

	private int mRainfall;
	private int mWindspeed;
	private int mMaxTemp;
	private int mCloudCover;
	private int mMinTemp;
	
	public WeatherReport()
	{
		this.mRainfall = 0;
	}
	
	public void setRailfall(int i) 
	{
		mRainfall = i;	
		this.notifyObservers();
		this.setChanged();
	}

	public Integer getRailfall() {		
		return this.mRainfall;
	}

	public void setField(int field, int value) {
		switch(field)
		{
		case R.id.rainfall:
			this.setRailfall(value);
			break;
		case R.id.winspeed:
			this.setWindspeed(value);
			break;
		case R.id.maxtemp:
			this.setMaxTemp(value);
			break;
		case R.id.mintemp:
			this.setMinTemp(value);
			break;
		case R.id.cloudcover:
			this.setCloudCover(value);
			break;
		}
		
	}

	public void setMinTemp(int value) 
	{
		this.mMinTemp = value;		
	}

	public Integer getMinTemp() 
	{
		return this.mMinTemp;		
	}

	public void setCloudCover(int value) 
	{
		this.mCloudCover = value;
		
	}
	
	public Integer getCloudCover() 
	{
		return this.mCloudCover;
		
	}

	public void setMaxTemp(int value) 
	{
		this.mMaxTemp = value;
		
	}
	
	public Integer getMaxTemp() 
	{
		return this.mMaxTemp;
		
	}

	public void setWindspeed(int value) {
		this.mWindspeed = value;
		
	}
	
	public Integer getWindspeed() 
	{
		return mWindspeed;		
	}

}
