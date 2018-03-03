package com.tacstp.test;

import org.junit.Assert;
import org.junit.Test;

import com.tacstp.services.ServicioWebservicex;

public class WebservicexTest {

	private static final String xmlDePrueba = "<?xml version='1.0' encoding='utf-8'?><string xmlns='http://www.webserviceX.NET'><NewDataSet><Table><AirportCode>EZE</AirportCode><CityOrAirportName>BUENOS AIRES PISTARINI</CityOrAirportName><Country>Argentina</Country><CountryAbbrviation>AR</CountryAbbrviation><CountryCode>303</CountryCode><GMTOffset>3</GMTOffset><RunwayLengthFeet>10827</RunwayLengthFeet><RunwayElevationFeet>66</RunwayElevationFeet><LatitudeDegree>34</LatitudeDegree><LatitudeMinute>40</LatitudeMinute><LatitudeSecond>0</LatitudeSecond><LatitudeNpeerS>S</LatitudeNpeerS><LongitudeDegree>58</LongitudeDegree><LongitudeMinute>38</LongitudeMinute><LongitudeSeconds>0</LongitudeSeconds><LongitudeEperW>W</LongitudeEperW></Table><Table><AirportCode>EZE</AirportCode><CityOrAirportName>BUENOS AIRES PISTARINI</CityOrAirportName><Country>Argentina</Country><CountryAbbrviation>AR</CountryAbbrviation><CountryCode>303</CountryCode><GMTOffset>3</GMTOffset><RunwayLengthFeet>10827</RunwayLengthFeet><RunwayElevationFeet>66</RunwayElevationFeet><LatitudeDegree>34</LatitudeDegree><LatitudeMinute>40</LatitudeMinute><LatitudeSecond>0</LatitudeSecond><LatitudeNpeerS>S</LatitudeNpeerS><LongitudeDegree>58</LongitudeDegree><LongitudeMinute>38</LongitudeMinute><LongitudeSeconds>0</LongitudeSeconds><LongitudeEperW>W</LongitudeEperW></Table></NewDataSet></string>";
	private static final String[] coordenadasEsperadas = new String[]{"-34.400","-58.380"};
	
	@Test
	public void testLatYLongDeXML() throws Exception{
		Assert.assertArrayEquals(coordenadasEsperadas, new ServicioWebservicex().obtenerLatYLongDeXML(xmlDePrueba));
	}
	
	@Test
	public void testObtenerLatYLongWebservicex() throws Exception{
		Assert.assertArrayEquals(coordenadasEsperadas, new ServicioWebservicex().obtenerLatitudYLongitudDeAeropuerto("EZE"));
	}
}
