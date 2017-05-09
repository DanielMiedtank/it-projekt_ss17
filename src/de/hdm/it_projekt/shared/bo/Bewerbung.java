package de.hdm.it_projekt.shared.bo;

import java.util.Date;

/**
 * Klasse Bewerbung (Unterklasse von BusinessObject)
 * @author Sid Heiland
 *
 */
public class Bewerbung extends BusinessObject {
	
	/**
	 * Erstelldatum der Bewerbung
	 */
	private Date erstelldatum;
	
	/**
	 * Text der Bewerbung
	 */
	private String bewerbungstext;
	
	/**
	 * Auslesen des Erstelldatums
	 * @return erstelldatum
	 */
	public Date getErstelldatum() {
		return erstelldatum;
	}
	
	/**
	 * Setzen des Erstelldatums
	 * @param erstelldatum
	 */
	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}
	
	/**
	 * Auslesen des Bewerbungstext
	 * @return bewerbungstext
	 */
	public String getBewerbungstext() {
		return bewerbungstext;
	}
	
	/**
	 * Setzen des Bewebrungstext
	 * @param bewerbungstext
	 */
	public void setBewerbungstext(String bewerbungstext) {
		this.bewerbungstext = bewerbungstext;
	}

}