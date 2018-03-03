package com.dl.incose.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dl.incose.model.Amigo;
import com.dl.incose.model.Recomendacion;
import com.dl.incose.model.Segmento;
import com.dl.incose.model.Usuario;
import com.dl.incose.model.Viaje;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.tacstp.dao.UsuarioDAO;
import com.tacstp.dao.UsuarioDAODatastore;
import com.tacstp.exceptions.TacsException;
import com.tacstp.rest.model.RecomendacionAmigo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DatastoreTest {


	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig());

	private Usuario usuario;
	private Usuario unAmigo;
	private Usuario usuarioFail;
	private Segmento unSegmentoIdaMock;
	private Segmento unSegmentoVueltaMock;
	private List<Segmento> segmentosIda;
	private List<Segmento> segmentosVuelta;
	private Viaje unViajeMock;
	private RecomendacionAmigo unaRecAmigo;
	private Recomendacion unaRecomendacion;

	protected Closeable session;



	@BeforeClass
	public static void setUpBeforeClass() {
		// Reset the Factory so that all translators work properly.
		ObjectifyService.setFactory(new ObjectifyFactory());
	}

	@Before
	public void setUp() {
		this.session = ObjectifyService.begin();
		helper.setUp();

		usuarioFail = new Usuario();
		usuarioFail.setFbId("2");
		usuarioFail.setFbToken("2");
		usuario = new Usuario();
		usuario.setFbId("1");
		usuario.setFbToken("1");
		
		unAmigo = new Usuario();
		unAmigo.setFbId("3");
		unAmigo.setFbToken("3");
		
		unSegmentoIdaMock = new Segmento("AA", "1", "2015-05-21", "10", "BUE",
				"MIA", "1");
		unSegmentoVueltaMock = new Segmento("AA", "1", "2015-05-21", "10",
				"MIA", "BUE", "1");

		segmentosIda = new ArrayList<>();
		segmentosIda.add(unSegmentoIdaMock);
		segmentosVuelta = new ArrayList<>();
		segmentosVuelta.add(unSegmentoVueltaMock);

		unViajeMock = new Viaje(segmentosIda, segmentosVuelta, new BigDecimal(
				30), "idIt");
		
		unaRecAmigo = new RecomendacionAmigo();
		unaRecAmigo.setFbId("1");
		unaRecAmigo.setFlightId("idIt");
		
		unaRecomendacion = new Recomendacion();
		unaRecomendacion.setIdRecomendacion(1);
		unaRecomendacion.setEstado("aprobada");
	}

	@After
	public void tearDown() {
		this.session.close();
		helper.tearDown();
	}

	@Test
	public void guardarUsuario() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
	}

	@Test
	public void obtenerUsuario() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals("1",usuarioDAODatastore.obtenerUsuario(String.valueOf(usuarioDAODatastore.guardarUsuario(usuario))).getFbId());
	}

	@Test
	public void obtenerUsuarioInexistente() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		assertNull(usuarioDAODatastore.obtenerUsuario(usuarioFail.getFbId()));
	}
	
	@Test
	public void agregarViajeAUsuario() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
	}
	
	@Test
	public void obtenerViajes() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		assertNotNull(usuarioDAODatastore.obtenerViajesDeUsuario("1"));
	}
	
	@Test(expected=RuntimeException.class)
	public void agregarRecomendacionAUsuarioInexistente() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		usuarioDAODatastore.agregarRecomendacionDeUsuario(unAmigo.getFbId(), new Recomendacion(new Amigo(usuario.getFbId(), ""), usuarioDAODatastore.obtenerViajeARecomendar(usuario.getFbId(), unaRecAmigo), "espera"));
	}
	
	@Test
	public void agregarRecomendacionAUsuario() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		assertEquals(3,usuarioDAODatastore.guardarUsuario(unAmigo));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		usuarioDAODatastore.agregarRecomendacionDeUsuario(unAmigo.getFbId(), new Recomendacion(new Amigo(usuario.getFbId(), ""), usuarioDAODatastore.obtenerViajeARecomendar(usuario.getFbId(), unaRecAmigo), "espera"));
	}
	
	@Test
	public void modificarEstadoRecomendacionAUsuario() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		assertEquals(3,usuarioDAODatastore.guardarUsuario(unAmigo));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		usuarioDAODatastore.agregarRecomendacionDeUsuario(unAmigo.getFbId(), new Recomendacion(new Amigo(usuario.getFbId(), ""), usuarioDAODatastore.obtenerViajeARecomendar(usuario.getFbId(), unaRecAmigo), "espera"));
		unaRecomendacion.setIdRecomendacion(usuarioDAODatastore.obtenerRecomendacionesDeUsuario(unAmigo.getFbId()).iterator().next().getIdRecomendacion());
		usuarioDAODatastore.cambiarEstadoRecomendacionDeUsuario(unAmigo.getFbId(), unaRecomendacion);
	}
	
	@Test(expected=TacsException.class)
	public void modificarEstadoRecomendacionInexistenteEnUsuario() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		assertEquals(3,usuarioDAODatastore.guardarUsuario(unAmigo));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		usuarioDAODatastore.agregarRecomendacionDeUsuario(unAmigo.getFbId(), new Recomendacion(new Amigo(usuario.getFbId(), ""), usuarioDAODatastore.obtenerViajeARecomendar(usuario.getFbId(), unaRecAmigo), "espera"));
		unaRecomendacion.setIdRecomendacion(usuarioDAODatastore.obtenerRecomendacionesDeUsuario(unAmigo.getFbId()).iterator().next().getIdRecomendacion());
		usuarioDAODatastore.cambiarEstadoRecomendacionDeUsuario(usuario.getFbId(), unaRecomendacion);
	}
	
	@Test
	public void obtenerAmigoQueRecomendo() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		assertEquals(3,usuarioDAODatastore.guardarUsuario(unAmigo));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		usuarioDAODatastore.agregarRecomendacionDeUsuario(unAmigo.getFbId(), new Recomendacion(new Amigo(usuario.getFbId(), ""), usuarioDAODatastore.obtenerViajeARecomendar(usuario.getFbId(), unaRecAmigo), "espera"));
		unaRecomendacion.setIdRecomendacion(usuarioDAODatastore.obtenerRecomendacionesDeUsuario(unAmigo.getFbId()).iterator().next().getIdRecomendacion());
		assertEquals(usuario.getFbId(), usuarioDAODatastore.obtenerAmigoQueRecomendo(unAmigo.getFbId(), unaRecomendacion));
	}
	
	@Test(expected=TacsException.class)
	public void obtenerAmigoQueRecomendoInexistente() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		assertEquals(3,usuarioDAODatastore.guardarUsuario(unAmigo));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		usuarioDAODatastore.agregarRecomendacionDeUsuario(unAmigo.getFbId(), new Recomendacion(new Amigo(usuario.getFbId(), ""), usuarioDAODatastore.obtenerViajeARecomendar(usuario.getFbId(), unaRecAmigo), "espera"));
		usuarioDAODatastore.obtenerAmigoQueRecomendo(unAmigo.getFbId(), unaRecomendacion);
	}
	
	@Test
	public void obtenerViajeRecomendado() {
		UsuarioDAO usuarioDAODatastore = new UsuarioDAODatastore();
		assertEquals(1,usuarioDAODatastore.guardarUsuario(usuario));
		assertEquals(3,usuarioDAODatastore.guardarUsuario(unAmigo));
		usuarioDAODatastore.agregarViajeAUsuario(usuario.getFbId(), unViajeMock);
		usuarioDAODatastore.agregarRecomendacionDeUsuario(unAmigo.getFbId(), new Recomendacion(new Amigo(usuario.getFbId(), ""), usuarioDAODatastore.obtenerViajeARecomendar(usuario.getFbId(), unaRecAmigo), "espera"));
		assertEquals(unViajeMock.getPrecio(), usuarioDAODatastore.obtenerViajeRecomendado(unAmigo.getFbId(),usuarioDAODatastore.obtenerRecomendacionesDeUsuario(unAmigo.getFbId()).iterator().next().getIdRecomendacion()).getPrecio());
	}
	
}
