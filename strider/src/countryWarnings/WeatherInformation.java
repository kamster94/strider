package countryWarnings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherInformation {

	CityInformation city;
	
	public WeatherInformation(CityInformation c){
		
		this.city = c;
	}
		
	public StringBuilder getWeatherInformationHtml(){
		
	StringBuilder information = new StringBuilder();		
	String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"
			+ this.city.cityName + "%22%20)%20and%20u%3D'c'&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";	

	try{
		  InputStream is = new URL(url).openStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);

	      JSONArray arr  = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast");
	      JSONObject obj  = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item");
	      JSONObject con  = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition");

	      String nL = System.getProperty("line.separator");
	      String date ="Date: "+ obj.getString("pubDate");
	      String temp ="Temperatura: "+ con.getString("temp"); 
	      String weather = arr.getJSONObject(0).getString("text");  
	      
	      if(weather.equals("Showers"))
	    	  weather = "Ulewa";
	      else if(weather.equals("Mostly Cloudy"))
	    	  weather = "Du¿e zachmurzenie";
	      else if(weather.equals("Partly Cloudy"))
	    	  weather = "Czêœciowe zachmurzenie";
	      else if(weather.equals("Scattered Showers"))
	    	  weather = "Miejscowe opady";
	      else if(weather.equals("Scattered Showers"))
	    	  weather = "Deszcz ze œniegiem";
	      else if(weather.equals("Rain"))
	    	  weather = "Deszcz";
	      
	      weather = "Pogoda: " + weather;
	      information.append(date + "</br></br>" + temp + "</br></br>" + weather);
	      

	    }catch(JSONException e){
	    	 information.append("Wyrzuci³o b³¹d JSON :/");
		      
	    }
  		catch(IOException e1){
  			 information.append("Wyrzuci³o b³¹d IO :/");
  		}
	     
	return information;
	
	}
	
	
	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	
}
