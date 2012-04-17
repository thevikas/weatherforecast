package com.thevikas.textinput;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Hello2Activity extends Activity implements Observer,OnClickListener,OnFocusChangeListener 
{
    /** Called when the activity is first created. */
	
	private Place mPlace;
	private Button mButton;
	
	private Button mNextDay;
	private Button mBackDay;
	
	private EditText mEditText;
	private WeatherAdapter mAdapter;
	private List<WeatherReport> mReport;
	
	private TextView mTextRainfall;
	private TextView mTextWindspeed;
	private TextView mTextCloudCover;
	private TextView mTextMaxTemp;
	private TextView mTextMinTemp;
	private int day;
	
	private TextView mPlaceName;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.mPlace = new Place();
        
        this.mButton = (Button) findViewById(R.id.button1);
        this.mNextDay = (Button) findViewById(R.id.nextday);
        this.mBackDay = (Button) findViewById(R.id.backday);
        
        this.mPlaceName = (Button) findViewById(R.id.placename);
        
        this.mEditText = (EditText) findViewById(R.id.editText1);
        mPlace.addObserver(this);
        
        this.mButton.setOnClickListener(this);        
        this.mNextDay.setOnClickListener(this);
        this.mBackDay.setOnClickListener(this);
        
        this.mEditText.setOnFocusChangeListener(this);
        
        this.mTextRainfall = (TextView) findViewById(R.id.rainfall);
        this.mTextCloudCover = (TextView) findViewById(R.id.cloudcover);
        this.mTextMinTemp = (TextView) findViewById(R.id.mintemp);
        this.mTextMaxTemp = (TextView) findViewById(R.id.maxtemp);
        this.mTextWindspeed = (TextView) findViewById(R.id.winspeed);
        
        this.mReport = new ArrayList<WeatherReport>(5);
        for(int i=0; i<5; i++)
        {
        	WeatherReport rp  = new WeatherReport();
        	rp.addObserver(this);
        	this.mReport.add(rp);	
        }    	
		
        this.mAdapter = new WeatherAdapter();
        this.mAdapter.setReportList(this.mReport);
        
        try
		{
			this.mAdapter.setPlace(this.mPlace);
		}
		catch(Exception e)
		{
			this.mButton.setText("Fucked" + e.getMessage());
		}
    }

	@Override
	public void onClick(View v) 
	{
		this.mPlaceName.setText(this.mEditText.getText().toString());
		this.mPlace.setName(this.mEditText.getText().toString());
		
		if(v.getId() == R.id.backday)
		{
			if(this.day>0)
				this.day--;
			this.mPlace.doRepaint();
		}
		else if(v.getId() == R.id.nextday)
		{
			if(this.day<4)
				this.day++;
			this.mPlace.doRepaint();
		}
		else if(R.id.button1 == v.getId())
		{
			try
			{				
				this.mAdapter.setPlace(this.mPlace);
			}
			catch(Exception e)
			{
				this.mButton.setText("Fucked" + e.getMessage());
			}			
		}
		
		boolean bback = true;
		boolean bnext = true;
		
		
		if(this.day == 0)
			bback = false;
		if(this.day == 4)
			bnext = false;
		
		this.mBackDay.setEnabled(bback);
		this.mNextDay.setEnabled(bnext);
		
		
	}

	@Override
	public void update(Observable arg0, Object arg1) 
	{
		//this.mButton.setText("Place: " + this.mPlace.getName());
		this.mTextRainfall.setText(this.mReport.get(this.day).getRailfall().toString());
		this.mTextWindspeed.setText(this.mReport.get(this.day).getWindspeed().toString());
		this.mTextMaxTemp.setText(this.mReport.get(this.day).getMaxTemp().toString());
		this.mTextMinTemp.setText(this.mReport.get(this.day).getMinTemp().toString());
		this.mTextCloudCover.setText(this.mReport.get(this.day).getCloudCover().toString());
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		if(v.getId() == R.id.editText1)
		{
			if(!hasFocus)
			{
				this.mPlace.setName(this.mEditText.getText().toString());
			}
		}
		
	}
}