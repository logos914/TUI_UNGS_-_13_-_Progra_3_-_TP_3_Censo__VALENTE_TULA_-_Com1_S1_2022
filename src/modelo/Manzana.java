package modelo;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

public class Manzana {

	private Coordinate geoLocalizacion;
	private Censita censitaAsignado;
	private MapPolygonImpl geoRectangulo;

	public Manzana(Coordinate geoLocalizacion) {
		this.geoLocalizacion = geoLocalizacion;
	}

	public static Manzana soloCoordenada(Coordinate geoLocalizacion) {
		return new Manzana(geoLocalizacion);
	}

	public static Manzana conRectangulo(Coordinate geoLocalizacion, MapPolygonImpl geoRectangulo) {
		Manzana manzana = new Manzana(geoLocalizacion);
		manzana.creargeoRectangulo(geoRectangulo);
		return manzana;
	}

	public void asignarCensista(Censita censista) {
		this.censitaAsignado = censista;
	}

	public void creargeoRectangulo(MapPolygonImpl geoRectangulo) {
		this.geoRectangulo = geoRectangulo;
	}

	public Coordinate getGeoLocalizacion() {
		return geoLocalizacion;
	}

	public Censita getCensitaAsignado() {
		return censitaAsignado;
	}

	public MapPolygonImpl getGeoRectangulo() {
		return geoRectangulo;
	}

	public RumboCardinal dondeSeUbicaConRespecto(Manzana otraManzana) {

		// Primero evaluamos Lat, y dentro de ella Long

		// Al Norte
		if (this.estaAlNorteDe(otraManzana.getGeoLocalizacion())) {
			if (this.estaAlOesteDe(otraManzana.getGeoLocalizacion())) {
				return RumboCardinal.NOROESTE;
			} else if (this.estaAlEsteDe(otraManzana.getGeoLocalizacion())) {
				return RumboCardinal.NORESTE;
			} else {
				return RumboCardinal.NORTE;
			}

		// Al Sur
		} else if (this.estaAlSurDe(otraManzana.getGeoLocalizacion())) {
			if (this.estaAlOesteDe(otraManzana.getGeoLocalizacion())) {
				return RumboCardinal.SUROESTE;
			} else if (this.estaAlEsteDe(otraManzana.getGeoLocalizacion())) {
				return RumboCardinal.SURESTE;
			} else {
				return RumboCardinal.SUR;
			}

		// Misma Latitud
		} else {
			if (this.estaAlOesteDe(otraManzana.getGeoLocalizacion())) {
				return RumboCardinal.OESTE;
			} else if (this.estaAlEsteDe(otraManzana.getGeoLocalizacion())) {
				return RumboCardinal.ESTE;
			} else {
				return RumboCardinal.IGUAL;
			}
		}

	}

	public boolean estaAlNorteDe(Coordinate coordenada) {
		return this.geoLocalizacion.getLat() > coordenada.getLat() ? true : false;
	}

	public boolean estaAlSurDe(Coordinate coordenada) {
		return this.geoLocalizacion.getLat() < coordenada.getLat() ? true : false;
	}

	public boolean estaAlEsteDe(Coordinate coordenada) {
		return this.geoLocalizacion.getLon() > coordenada.getLon() ? true : false;
	}

	public boolean estaAlOesteDe(Coordinate coordenada) {
		return this.geoLocalizacion.getLon() < coordenada.getLon() ? true : false;
	}

}
