package de.hdm.it_projekt.client;

import java.util.*;

public class Ausschreibung {

	private String bezeichnung;
	private Date bewerbungsfrist;
	private String ausschreibungstext;
	
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public Date getBewerbungsfrist() {
		return bewerbungsfrist;
	}
	public void setBewerbungsfrist(Date bewerbungsfrist) {
		this.bewerbungsfrist = bewerbungsfrist;
	}
	public String getAusschreibungstext() {
		return ausschreibungstext;
	}
	public void setAusschreibungstext(String ausschreibungstext) {
		this.ausschreibungstext = ausschreibungstext;
	}
	
	
}
