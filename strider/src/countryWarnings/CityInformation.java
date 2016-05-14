package countryWarnings;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lynden.gmapsfx.javascript.object.LatLong;

public class CityInformation {
	
	public String cityName;
	private String cityURL;
	public LatLong coordinations;
	
	public CityInformation(String name){
		
		this.cityName = name;
		
    	
		String url = "https://pl.wikipedia.org/w/api.php?action=query&list=geosearch&gsradius=10000&gspage=";
		String url2 =  "&gslimit=100&gsprop=type|name|&format=json";	   
		url += name;
		url += url2;
		
		this.cityURL = url;
		url = "https://pl.wikipedia.org/wiki/" + name;
		
		try {
		Document document = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com")		
				.get();
				
				Elements elems = document.select(("span[class=latitude]"));
				Elements elems2 = document.select(("span[class=longitude]"));
		
				this.coordinations = new LatLong(Double.parseDouble(elems.get(1).text().replaceAll(",",".")), 
												 Double.parseDouble(elems2.get(1).text().replaceAll(",",".")));
			
		
		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	

    public StringBuilder getCityInformationHtml(){
    	
    StringBuilder information = new StringBuilder("<font face='WildWest'>");
    	
	String htmlString = "";

	try{
	
	  InputStream is = new URL(this.cityURL).openStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);

      JSONArray arr  = json.getJSONObject("query").getJSONArray("geosearch");
	  
	  htmlString += "<b><font face='WildWest'>INTERESUJ¥CE MIEJSCA I WYDARZENIA</b></font></br></br>";
		for (int i = 0; i < arr.length(); i++)
		{
			String title = arr.getJSONObject(i).getString("title");  				   		
		    htmlString += title;
		    htmlString += "</br>";  	  				    
		}
		htmlString += "</font>";
		
		
		createHtmlFile(htmlString);  //tworzenie pliku html a la "cookies"
	    information.append(htmlString);
					
    }catch(JSONException e){
    e.printStackTrace();
    information.append("<b>Nie znaleziono informacji o mieœcie<b>");
    }
	catch(IOException e1){
	e1.printStackTrace();
	information.append("<b>Proszê poprawnie wpisaæ nazwê miasta<b>");
	}

    return information;
    	
    }
    
    
    public void createHtmlFile(String html) throws IOException{
    	
    	File htmlTemplateFile = new File("./htmls/template.html");
		String htmlString = FileUtils.readFileToString(htmlTemplateFile);
		String title = "";
		htmlString = htmlString.replace("$title", title);
		htmlString = htmlString.replace("$body", html);
		File newHtmlFile = new File("./htmls/" + this.cityName + ".html");
		FileUtils.writeStringToFile(newHtmlFile, htmlString);
    	   	      	
    }
	
	
    public static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	
}
