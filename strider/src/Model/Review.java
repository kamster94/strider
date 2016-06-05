package Model;

public class Review 
{
	int cleanliness;
	int comfort;
	int localization;
	int amenities;
	int personel;
	int valueformoney;
	int averagegrade;
	String notes;
	
	public Review(int a, int b, int c, int d, int e, int f, int g, String h) 
	{
		cleanliness = a;
		comfort = b;
		localization = c;
		amenities = d;
		personel = e;
		valueformoney = f;
		averagegrade = g;
		notes = h;
	}
	
	public int getCleanlinessRating()
	{
		return cleanliness;
	}
	public int getComfortRating()
	{
		return comfort;
	}
	public int getLocalizationRating()
	{
		return localization;
	}
	public int getAmenitiesRating()
	{
		return amenities;
	}
	public int getPersonelRating()
	{
		return personel;
	}
	public int getValueForMoneyRating()
	{
		return valueformoney;
	}
	public int getAverageRating()
	{
		return averagegrade;
	}
	public String getReviewNotes()
	{
		return notes;
	}
}
