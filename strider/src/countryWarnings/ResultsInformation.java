package countryWarnings;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ResultsInformation {

	public double fuelConsumption;
	public double distance;
	public double priceOfe95=5;
	public double priceOfs98;
	public double priceOfON;
	public double priceOfLPG;
	public String selectedFuel;
	private static String url = "http://nafta.wnp.pl/ceny_paliw/";
	
	
	
	public ResultsInformation(double fC, double dist, String fuelType)
	{
		
		this.fuelConsumption = fC;
		this.distance = dist/1000;
		this.selectedFuel = fuelType;
		System.out.println(fuelType);
		Document document;
		
		try {
			document = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com")		
					.get();
		
			Elements elems = document.select(("td"));//ostrzezenia o panstwie
			
			boolean check = false;
			for (int i = 0; i < elems.size(); i++){
				if(elems.get(i).text().equals("Œrednia")){
					
					if(check){
						priceOfON = Double.valueOf(elems.get(i+1).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));
						priceOfe95 = Double.valueOf(elems.get(i+2).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));
						priceOfs98 = Double.valueOf(elems.get(i+3).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));
						priceOfLPG = Double.valueOf(elems.get(i+4).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));
						
					}
				
					else
						check = true;
					
				}
				
				
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
				
		
	}
	
	
	public StringBuilder getResultsInformationHtml()
	{
		
		StringBuilder resultsInformation = new StringBuilder();
		   
		resultsInformation.append("Koszt paliwa [z³]: " + getFuelCost());		
		
		return resultsInformation;
	}
	
	
	private double getFuelCost()
	{
		
		if(this.selectedFuel.equals("e95"))
			return this.distance/100 * this.fuelConsumption * this.priceOfe95;
		else if(this.selectedFuel.equals("s98"))
			return this.distance/100 * this.fuelConsumption * this.priceOfs98;
		else if(this.selectedFuel.equals("LPG"))
			return this.distance/100 * this.fuelConsumption * this.priceOfLPG;
		else if(this.selectedFuel.equals("ON"))
			return this.distance/100 * this.fuelConsumption * this.priceOfON;
		else return 0;
	}
	
	
	
}
