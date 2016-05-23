package countryWarnings;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeatherInformation {

	CityInformation city;
	public String pictureAdress;
	public String celsius;
	public String weatherDescription;
	private static Document doc;
	
	public WeatherInformation(CityInformation c)
	{		
		this.city = c;
		
		String weather = "";
		String text = "";
		
		try{
			  
		  String urlText ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"
		  + URLEncoder.encode(this.city.cityName, "UTF-8")+ "%22)%20and%20u%3D'c'&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";		 
		  URL url = new URL(urlText);		 
		  InputStream stream = url.openStream();
		  DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		  doc = dBuilder.parse(stream);
		  
		  }catch(Exception e){
			e.printStackTrace();  
		  }

		  NodeList nList = doc.getElementsByTagName("yweather:condition");
		  
		  
		  for (int temp = 0; temp < nList.getLength(); temp++) 
		  {

				Node nNode = nList.item(temp);
				NamedNodeMap attributes = nNode.getAttributes();
		        int numAttrs = attributes.getLength();

	            for (int i = 0; i < numAttrs; i++) 
	            {
		            Attr attr = (Attr) attributes.item(i);
		            String attrName = attr.getNodeName();
		            String attrValue = attr.getNodeValue();
 
		            if(attrName.equals("temp"))
		            	this.celsius = attrValue + " C";	            
		            else if(attrName.equals("text"))
		            	weather += attrValue;		            		  	
		        }
		  }
		  
		  		NodeList nList2 = doc.getElementsByTagName("description");
		  		Node nNode = nList2.item(1);
		  		text = nNode.getTextContent();
		        String regexString = Pattern.quote("src=") + "(.*?)" + Pattern.quote(">");
		         
		        Pattern p = Pattern.compile(regexString);
		        Matcher m = p.matcher(text);
		        m.find();
		        String adress = m.group(1);
		        adress = adress.replaceAll("\"/", "");
		        adress = adress.replaceAll("\"", "");
	        
		      this.pictureAdress = adress;
		      this.weatherDescription = weather;
		      changeToPolish();
	}
	
	private void changeToPolish(){
		
		 String weather = this.getWeatherDescription();
		
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
	      else if(weather.equals("Windy"))
	    	  weather = "Wietrznie";
	      else if(weather.equals("Cloudy"))
	    	  weather = "Pochmurnie";
	     
		  setWeatherDescription(weather);
	}
	
	
	public CityInformation getCity() {
		return city;
	}

	public String getPictureAdress() {
		return pictureAdress;
	}

	public String getCelsius() {
		return celsius;
	}
		
	public String getWeatherDescription() {
		return weatherDescription;
	}
	
	public void setWeatherDescription(String desc) {
		this.weatherDescription = desc;
	}
	
}
