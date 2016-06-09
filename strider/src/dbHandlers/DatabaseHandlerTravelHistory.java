package dbHandlers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Attraction;
import Model.Day;
import Model.Hotel;
import Model.Transport;
import Model.Travel;
import Model.User;
import dbConnection.DbAccess;
import dbHandlers.DatabaseHandlerCommon;

public class DatabaseHandlerTravelHistory {
	
	private DbAccess dbConnection;
	private DatabaseHandlerCommon commons;
	private static DatabaseHandlerTravelHistory self;
	private User user;
	
	private DatabaseHandlerTravelHistory(){
		dbConnection = DbAccess.getInstance();
		user = User.getInstance();
		commons = DatabaseHandlerCommon.getInstance();
	}
	
	public static DatabaseHandlerTravelHistory getInstance(){
		if (self==null) self = new DatabaseHandlerTravelHistory();
		return self;
	}
	
	public List<Travel> getUserTravels(){
		List<Integer> ids = dbConnection.getIntegersFromDb("SELECT IDTrip FROM DBA.Trip WHERE IDUser = " + user.getId(), Arrays.asList("IDTrip"));
		List<Travel> travels = new ArrayList<Travel>();
		if (!ids.isEmpty()){
			for (int id : ids){
				String name = dbConnection.getSingeStringFromDb("SELECT TripName FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id, "TripName");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				System.out.println(dbConnection.getSingeStringFromDb("SELECT TripBeginDatetime FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id, "TripBeginDatetime"));
				LocalDate startDate = LocalDate.parse(dbConnection.getSingeStringFromDb("SELECT TripBeginDatetime FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id, "TripBeginDatetime"), formatter);
				LocalDate endDate = LocalDate.parse(dbConnection.getSingeStringFromDb("SELECT TripEndDatetime FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id, "TripEndDatetime"), formatter);
				Travel travel = new Travel(name, startDate, endDate);
				for (Day day : travel.days){ //Trzeba daæ kurwa komentarze, bo siê zgubiê
					//Hotel
					int hotelId = dbConnection.getIntFromDb("SELECT IDHotel FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND ArrivalDate LIKE '" + day.date + " %'");
					if (hotelId != -1){
						int countryId = dbConnection.getIntFromDb("SELECT IDCountry FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND ArrivalDate LIKE '" + day.date + " %'");
						int cityId = dbConnection.getIntFromDb("SELECT IDCity FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND ArrivalDate LIKE '" + day.date + " %'");
						Hotel hotel = new Hotel();
						hotel.country = commons.getCountryName(countryId);
						hotel.city = commons.getCityName(countryId, cityId);
						hotel.name = dbConnection.getSingeStringFromDb("SELECT HotelName FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "HotelName");
						hotel.street = dbConnection.getSingeStringFromDb("SELECT StreetName FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "StreetName");
						hotel.zipcode = dbConnection.getSingeStringFromDb("SELECT ZipCode FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "ZipCode");
						hotel.number = dbConnection.getSingeStringFromDb("SELECT StreetNumber FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "StreetNumber");
						hotel.accomodation_startdate = LocalDateTime.parse(dbConnection.getSingeStringFromDb("SELECT ArrivalDate FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND ArrivalDate LIKE '" + day.date + " %'", "ArrivalDate"));
						hotel.accomodation_enddate = LocalDateTime.parse(dbConnection.getSingeStringFromDb("SELECT LeavingDate FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND ArrivalDate LIKE '" + day.date + " %'", "LeavingDate"));
						hotel.pricepernite = dbConnection.getFloatFromDb("SELECT HotelPrice FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND ArrivalDate LIKE '" + day.date + " %'");
						int currencyId = dbConnection.getIntFromDb("SELECT IDCurrency FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND ArrivalDate LIKE '" + day.date + " %'");
						hotel.currency = commons.getCurrencyName(currencyId);
						hotel.notes = dbConnection.getSingeStringFromDb("SELECT HotelNotes FROM DBA.HotelDetail WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "HotelNotes");
						hotel.filepath = dbConnection.getSingeStringFromDb("SELECT LinkToHotel FROM DBA.HotelDetail WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "LinkToHotel");
						day.hotel = hotel;
					}
					//Transport
					int transportId = dbConnection.getIntFromDb("SELECT IDTransport FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
					if (transportId != -1){
						int countryLeavingId = dbConnection.getIntFromDb("SELECT IDCountryLeavingTransport FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						int countryArrivalId = dbConnection.getIntFromDb("SELECT IDCountryArrivalTransport FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						int cityLeavingId = dbConnection.getIntFromDb("SELECT IDCityLeavingTransport FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						int cityArrivalId = dbConnection.getIntFromDb("SELECT IDCityArrivalTransport FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						Transport transport = new Transport();
						transport.country_start = commons.getCountryName(countryLeavingId);
						transport.country_end = commons.getCountryName(countryArrivalId);
						transport.city_start = commons.getCityName(countryLeavingId, cityLeavingId);
						transport.city_end = commons.getCityName(countryArrivalId, cityArrivalId);
						transport.startdatetime = LocalDateTime.parse(dbConnection.getSingeStringFromDb("SELECT TransportLeavingDatetime FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'", "TransportLeavingDatetime"));
						transport.enddatetime = LocalDateTime.parse(dbConnection.getSingeStringFromDb("SELECT TransportArrivalDatetime FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'", "TransportArrivalDatetime"));
						int transportCategoryId = dbConnection.getIntFromDb("SELECT IDTransportCategory FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						transport.transportcategory = dbConnection.getSingeStringFromDb("SELECT TransportCategoryName FROM DBA.TransportCategory WHERE IDTransportCategory = " + transportCategoryId, "TransportCategoryName");
						transport.provider = dbConnection.getSingeStringFromDb("SELECT TransportName FROM DBA.Transport WHERE IDTransport = " + transportId + " AND IDTransportCategory = " + transportCategoryId, "TransportName");
						transport.price = dbConnection.getFloatFromDb("SELECT TransportPrice FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						int currencyId = dbConnection.getIntFromDb("SELECT IDCurrency FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						transport.currency = commons.getCurrencyName(currencyId);
						transport.notes = dbConnection.getSingeStringFromDb("SELECT TransportNotes FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'", "TransportNotes");
						transport.calcdcost = dbConnection.getFloatFromDb("SELECT EstimatedTransportPrice FROM DBA.TransportDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND TransportLeavingDatetime LIKE '" + day.date + " %'");
						day.transport = transport;
					}
					//Atrakcje
					List<Integer> attractionsIds = new ArrayList<Integer>();
					List<Attraction> attractions = new ArrayList<Attraction>();
					attractionsIds = dbConnection.getIntegersFromDb("SELECT * FROM DBA.AttractionDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'", Arrays.asList("IDAttraction"));
					if (!attractionsIds.isEmpty()){
						for (int attractionId : attractionsIds){
							int countryId = dbConnection.getIntFromDb("SELECT IDCountry FROM DBA.AttractionDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'");
							int cityId = dbConnection.getIntFromDb("SELECT IDCity FROM DBA.AttractionDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'");
							Attraction attraction = new Attraction();
							attraction.country = commons.getCountryName(countryId);
							attraction.city = commons.getCityName(countryId, cityId);
							attraction.name = dbConnection.getSingeStringFromDb("SELECT AttractionName FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "AttractionName");
							attraction.street = dbConnection.getSingeStringFromDb("SELECT StreetName FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "StreetName");
							attraction.number = dbConnection.getSingeStringFromDb("SELECT StreetNumber FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "StreetNumber");
							attraction.zipcode = dbConnection.getSingeStringFromDb("SELECT ZipCode FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "ZipCode");
							attraction.openfrom = dbConnection.getSingeStringFromDb("SELECT OpeningTime FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "OpeningTime");
							attraction.opento = dbConnection.getSingeStringFromDb("SELECT ClosingTime FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "ClosingTime");
							System.out.println("SELECT VisitDate FROM DBA.AttractionDetails WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'");
							attraction.date = LocalDate.parse(dbConnection.getSingeStringFromDb("SELECT VisitDate FROM DBA.AttractionDetails WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'", "VisitDate"));
							int currencyId = dbConnection.getIntFromDb("SELECT IDCurrency FROM DBA.AttractionDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'");
							attraction.currency = commons.getCurrencyName(currencyId);
							attraction.notes = dbConnection.getSingeStringFromDb("SELECT AttractionNotes FROM DBA.AttractionDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'", "AttractionNotes");
							attraction.price = dbConnection.getFloatFromDb("SELECT AttractionPrice FROM DBA.AttractionDetail WHERE IDUser = " + user.getId() + " AND IDTrip = " + id + " AND VisitDate = '" + day.date + "'");
							attractions.add(attraction);
						}
						day.attractions = attractions;
					}
					//Kraj i miasto
					day.country = day.transport.country_start;
					day.city = day.transport.city_start;
				}
				//Uzupelnienie travela
				travel.setId(id);
				travel.setCountryOriginId(dbConnection.getIntFromDb("SELECT IDCountryLeavingTrip FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id));
				travel.setCityOriginId(dbConnection.getIntFromDb("SELECT IDCityLeavingTrip FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id));
				travel.setCompanionsNumber(dbConnection.getIntFromDb("SELECT CompanionsNumber FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id));
				travel.setTransportcost(dbConnection.getIntFromDb("SELECT TransportCost FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id));
				travel.setAttractioncost(dbConnection.getIntFromDb("SELECT AttractionCost FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id));
				travel.setHotelcost(dbConnection.getIntFromDb("SELECT HotelCost FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id));
				travel.setAllcost(dbConnection.getIntFromDb("SELECT TotalCost FROM DBA.Trip WHERE IDUser = " + user.getId() + " AND IDTrip = " + id));
				travels.add(travel);
			}
			return travels;
		}
		else return null;
	}

}
