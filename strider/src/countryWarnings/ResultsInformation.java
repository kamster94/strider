package countryWarnings;

public class ResultsInformation {

	public double fuelConsumption;
	public double distance;
	public static double priceOf95 = 4.5;
	
	
	public ResultsInformation(double fC, double dist)
	{
		
		this.fuelConsumption = fC;
		this.distance = dist/1000;
	}
	
	
	public StringBuilder getResultsInformationHtml()
	{
		
		StringBuilder resultsInformation = new StringBuilder();
		   
		resultsInformation.append("Koszt paliwa [z³]: " + getFuelCost());		
		
		return resultsInformation;
	}
	
	
	private double getFuelCost()
	{
		System.out.println(this.distance);
		return this.distance/100 * this.fuelConsumption * priceOf95;
	}
	
	
	
}
