package com.dl.incose.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dl.incose.model.Recomendacion;
import com.dl.incose.model.Segmento;
import com.dl.incose.model.Usuario;
import com.dl.incose.model.Viaje;
import com.dl.incose.services.ServicioDespegar;
import com.dl.incose.services.ServicioFB;
import com.google.gson.GsonBuilder;
import com.tacstp.app.AppContext;
import com.tacstp.dao.UsuarioDAOEnMemoria;
import com.tacstp.rest.RecursoUsuario;
import com.tacstp.rest.RecursoViaje;
import com.tacstp.rest.model.RecomendacionAmigo;

public class RecursosTest {

	private final String it = new ServicioDespegar().listaViajes("ar", "BUE", "MIA", "2015-08-20","2015-08-30").iterator().next().getIdItinerario();
	
	private final RecursoViaje unRecursoViaje = new RecursoViaje();
	private final RecursoUsuario unRecursoUsuario =  new RecursoUsuario(new UsuarioDAOEnMemoria());
	
	private Viaje unViajeMock;
	private Set<Viaje> viajes;
	private Usuario usuarioPost;
	private Usuario usuarioPostViaje;
	private Usuario unAmigo;
	private Segmento unSegmentoIdaMock;
	private Segmento unSegmentoVueltaMock;
	private List<Segmento> segmentosIda;
	private List<Segmento> segmentosVuelta;
	private RecomendacionAmigo unaRecAmigo;
	private RecomendacionAmigo unaRecAmigoFail;
	private Recomendacion unaRecomendacionPut;
	private ServicioFB sFB = new ServicioFB();
	private String accessToken;
	private String itJson;
	
	
	@Before
	public void setUp(){
		accessToken = sFB.obtenerAccessToken();
		usuarioPost = new Usuario();
		unAmigo = new Usuario();
		unAmigo.setFbId("100009879124092");
		unAmigo.setFbToken(accessToken);
		usuarioPostViaje = new Usuario();
		usuarioPost.setFbId("100009649302654");
		usuarioPost.setFbToken(accessToken);
		usuarioPostViaje.setFbId("100009649302654");
		usuarioPostViaje.setFbToken(accessToken);
		unSegmentoIdaMock = new Segmento("AA", "1", "2015-05-21", "10",  "BUE", "MIA","1");
		unSegmentoVueltaMock = new Segmento("AA", "1", "2015-05-21", "10",  "MIA", "BUE","1");
		
		segmentosIda = new ArrayList<>();
		segmentosIda.add(unSegmentoIdaMock);
		segmentosVuelta = new ArrayList<>();
		segmentosVuelta.add(unSegmentoVueltaMock);
		
		unViajeMock = new Viaje(segmentosIda,segmentosVuelta, new BigDecimal(30),it);
		
		unaRecAmigo = new RecomendacionAmigo();
		unaRecAmigo.setFbId("100009879124092");
		unaRecAmigo.setFlightId(it);
		
		unaRecAmigoFail = new RecomendacionAmigo();
		unaRecAmigoFail.setFbId("10");
		unaRecAmigoFail.setFlightId(it);
		
		
		unaRecomendacionPut = new Recomendacion();
		unaRecomendacionPut.setIdRecomendacion(1);
		
		viajes = new HashSet<Viaje>();
		viajes.add(unViajeMock);
		usuarioPostViaje.setViajes(viajes);
		itJson = String.format("{ 'itineraryId': '%s', 'inBoundChoice': '1','outBoundChoice': '1'}", it);
	}
	
	@Ignore("Tarda mucho cuando corro los tests pero anda joya!!")@Test 
	public void testGetViajes() {
		org.junit.Assert.assertNotNull(unRecursoViaje.getViajes("ar", "BUE", "MIA", "2015-08-20","2015-08-30"));
	}

	@Test
	public void testPostUsuarios(){
		org.junit.Assert.assertNotNull(unRecursoUsuario.postUsuario(usuarioPost));
	}
	
	@Test
	public void testGetUsuariosSinViajesYRecomendaciones(){
		org.junit.Assert.assertNotNull(unRecursoUsuario.postUsuario(usuarioPost));
		org.junit.Assert.assertEquals(200,unRecursoUsuario.getUsuario("100009649302654", accessToken).getStatus());
	}
	
	@Test
	public void testGetUsuariosSinViajesYRecomendaciones404(){
		org.junit.Assert.assertNotNull(unRecursoUsuario.postUsuario(usuarioPost));
		org.junit.Assert.assertEquals(404,unRecursoUsuario.getUsuario("100009879124092", accessToken).getStatus());
	}
	
	@Test
	public void testPostViajesDeUsuario(){
		unRecursoUsuario.postUsuario(usuarioPost).getEntity().toString();
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postViajes("100009649302654", itJson).getStatus());
	}
	
	@Test
	public void testPostViajesDeUsuario404(){
		org.junit.Assert.assertNotNull(unRecursoUsuario.postUsuario(usuarioPost));
		org.junit.Assert.assertEquals(404,unRecursoUsuario.postViajes("100009879124092", itJson).getStatus());
	}
	
	@Test
	public void testGetViajesDeUsuario(){
		unRecursoUsuario.postUsuario(usuarioPost).getEntity().toString();
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postViajes("100009649302654", itJson).getStatus());
		org.junit.Assert.assertEquals(200,unRecursoUsuario.getViajes("100009649302654").getStatus());
	}
	
	@Test
	public void testGetViajesDeUsuario404(){
		unRecursoUsuario.postUsuario(usuarioPost).getEntity().toString();
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postViajes("100009649302654", itJson).getStatus());
		org.junit.Assert.assertEquals(404,unRecursoUsuario.getViajes("100009879124092").getStatus());
	}
	
	@Test
	public void testPostRecomendacionEnUsuario(){
		unRecursoUsuario.postUsuario(usuarioPost).getEntity().toString();
		unRecursoUsuario.postUsuario(unAmigo).getEntity().toString();
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postViajes("100009649302654", itJson).getStatus());
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postRecomendacion("100009649302654", unaRecAmigo).getStatus());
	}
	
	@Test
	public void testPostRecomendacionEnUsuario404(){
		unRecursoUsuario.postUsuario(usuarioPost).getEntity().toString();
		unRecursoUsuario.postUsuario(unAmigo).getEntity().toString();
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postViajes("100009649302654", itJson).getStatus());
		org.junit.Assert.assertEquals(404,unRecursoUsuario.postRecomendacion("100009649302654", unaRecAmigoFail).getStatus());
	}
	
	@Ignore("Rompe porque el amigo no tiene los permisos de publicacion activados.")@Test 
	public void testPutRecomendacionEnUsuario(){
		//TODO FALTA VALIDAR QUE SE AGREGUE EL VIAJE AL USUARIO QUE APROBO LA RECOMENDACION.
		unRecursoUsuario.postUsuario(usuarioPost).getEntity().toString();
		unRecursoUsuario.postUsuario(unAmigo).getEntity().toString();
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postViajes("100009649302654", itJson).getStatus());
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postRecomendacion("100009649302654", unaRecAmigo).getStatus());
		unaRecomendacionPut.setEstado("aprobada");
		Usuario usuario = new GsonBuilder().create().fromJson((String) unRecursoUsuario.getUsuario("100009879124092","").getEntity(), Usuario.class);
		unaRecomendacionPut.setIdRecomendacion(usuario.getRecomendaciones().iterator().next().getIdRecomendacion());
		org.junit.Assert.assertEquals(200,unRecursoUsuario.putRecomendacion("100009879124092", unaRecomendacionPut).getStatus());
	}
	
	@Test
	public void testPutRecomendacionEnUsuario404(){
		unRecursoUsuario.postUsuario(usuarioPost).getEntity().toString();
		unRecursoUsuario.postUsuario(unAmigo).getEntity().toString();
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postViajes("100009649302654", itJson).getStatus());
		org.junit.Assert.assertEquals(201,unRecursoUsuario.postRecomendacion("100009649302654", unaRecAmigo).getStatus());
		unaRecomendacionPut.setEstado("aprobado");
		Usuario usuario = new GsonBuilder().create().fromJson((String) unRecursoUsuario.getUsuario("100009879124092","").getEntity(), Usuario.class);
		unaRecomendacionPut.setIdRecomendacion(usuario.getRecomendaciones().iterator().next().getIdRecomendacion());
		org.junit.Assert.assertEquals(404,unRecursoUsuario.putRecomendacion("100009649302654", unaRecomendacionPut).getStatus());
	}
	
	@After
	public void tearDown(){
		AppContext.getInstance().clean();
	}
}	


