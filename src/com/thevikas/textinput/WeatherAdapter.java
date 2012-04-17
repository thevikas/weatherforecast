package com.thevikas.textinput;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class WeatherAdapter 
{
	List<WeatherReport> reps;
		
	public WeatherAdapter()
	{
		reps = new ArrayList<WeatherReport>(5);
	}
	
	
	public void setPlace(Place mPlace) throws URISyntaxException, MalformedURLException, IOException 
	{
		URI uri =  new URI("http://www.imd.gov.in/section/nhac/distforecast/" + mPlace.getName() + ".htm");
		
		
		HttpContext httpContext = new BasicHttpContext();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse response = httpClient.execute(httpGet, httpContext);
		int statusCode = response.getStatusLine().getStatusCode();
		HttpEntity entity = response.getEntity();
		String page = EntityUtils.toString(entity);
		String lines[] = page.split("\n");
						
		for(int i = 0; i<lines.length; i++)
		{
			String line = lines[i].toString();
			String dates = new String(); 
			if(line.indexOf("ISSUED ON") > 0)
			{
				String parts[] = line.trim().split(" ");
				dates = parts[parts.length -1]; 
			}
			
			if(line.length() < 110)
				continue;
			
			String fieldType = line.substring(0, 14).trim();
			fieldType.trim();
			String vals[] = new String[5];
			
			int field = 0;
			
			vals[0] = new String(line.substring(28, 35).trim());
			vals[1] = new String(line.substring(47, 55).trim());
			vals[2] = new String(line.substring(68, 75).trim());
			vals[3] = new String(line.substring(88, 95).trim());
			vals[4] = new String(line.substring(108).trim());
			
			
			
			if(fieldType.compareTo("Rainfall (mm)") == 0)				
				field = R.id.rainfall;
			else if(fieldType.compareTo("Wind speed (km") == 0)
				field = R.id.winspeed;
			else if(fieldType.compareTo("Total clo") == 0)
				field = R.id.cloudcover;
			else if(fieldType.compareTo("Max Temperatur") == 0)
				field = R.id.maxtemp;
			else if(fieldType.compareTo("Min Temperatur") == 0)
				field = R.id.mintemp;
			else
			{
				field = 99;
				continue;
			}
			
			int j=0;
			
			for(j=0; j<5; j++)
			{
				reps.get(j).setField(field,Integer.parseInt(vals[j]));			
			}
		}		
	}

	public void setReportList(List<WeatherReport> pReps) 
	{
		this.reps = pReps;
		
	}
	
}
