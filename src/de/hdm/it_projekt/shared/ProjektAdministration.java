package de.hdm.it_projekt.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.it_projekt.shared.bo.*;

@RemoteServiceRelativePath("projektadmin")
public interface ProjektAdministration extends RemoteService {

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusätzlich zum No Argument Constructor der implementierenden
	 * Klasse {@link ProjektAdministrationImpl} notwendig. Bitte diese Methode
	 * direkt nach der Instantiierung aufrufen.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	public Vector<ProjektMarktplatz> getAlleProjektMarktplaetze() throws IllegalArgumentException;

	public Organisationseinheit getBewerberFor(Bewerbung bw) throws IllegalArgumentException;
	
	public Beteiligung createBeteiligungFor(Bewerbung bw) throws IllegalArgumentException;
	
	public Organisationseinheit getBeteiligterFor(Beteiligung bt) throws IllegalArgumentException;
	
	public ProjektMarktplatz getProjektMarktplatzById(int id) throws IllegalArgumentException;

	public Organisationseinheit getOrganisationseinheitById(int id) throws IllegalArgumentException;
	
	public Vector<Projekt> getAlleProjekteFor(ProjektMarktplatz pm) throws IllegalArgumentException;
	
	public Vector<Projekt> getProjektByProjektleiter(Organisationseinheit p, ProjektMarktplatz pm) throws IllegalArgumentException;

	public Vector<Projekt> getProjektByName(String name) throws IllegalArgumentException;
	
	public Beteiligung getBeteiligungFor(Bewerbung bw) throws IllegalArgumentException;

	public Projekt getProjektById(int id) throws IllegalArgumentException;

	public Vector<Ausschreibung> getAusschreibungFor(Projekt pr) throws IllegalArgumentException;

	public Ausschreibung getAusschreibungById(int id) throws IllegalArgumentException;
	
	public Ausschreibung getAusschreibungby(Partnerprofil pp) throws IllegalArgumentException;

	public Partnerprofil getPartnerprofilById(int id) throws IllegalArgumentException;
	
	public Partnerprofil getPartnerprofilFor(Ausschreibung as) throws IllegalArgumentException;

	public Vector<Eigenschaft> getEigenschaftenFor(Partnerprofil pr) throws IllegalArgumentException;

	public Vector<Bewerbung> getBewerbungFor(Ausschreibung as) throws IllegalArgumentException;

	public Bewertung getBewertungFor(Bewerbung bw) throws IllegalArgumentException;

	public Vector<Beteiligung> getBeteiligungenFor(Projekt pr) throws IllegalArgumentException;

	public Vector<Beteiligung> getBeteiligungenFor(Organisationseinheit o) throws IllegalArgumentException;
	
	public Vector<ProjektMarktplatz> getProjektMarktplaetzeByOrganisation(Organisationseinheit o) throws IllegalArgumentException;
	
	public Organisationseinheit findByGoogleId(LoginInfo li) throws IllegalArgumentException;
	
	public Person getProjektleiterFor(Projekt pr) throws IllegalArgumentException;
	
	public Bewertung createBewertungFor(Bewerbung bw, float wert, String stellungnahme) throws IllegalArgumentException;
	
	public Ausschreibung getAusschreibungBy(Bewerbung bw) throws IllegalArgumentException;
	
	public Bewerbung getBewerbungById(int id) throws IllegalArgumentException;
	
	public Vector<Bewerbung> getBewerbungBy(Ausschreibung as) throws IllegalArgumentException;
	
	public Vector<Ausschreibung> getAusschreibungByMatch(Organisationseinheit o) throws IllegalArgumentException;
	
	
	
	/**
	 * 
	 * @param bez
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ProjektMarktplatz createProjektMarktplatz(String bez, int adminID) throws IllegalArgumentException;

	public Projekt createProjektFor(ProjektMarktplatz pm, String name, Date startdatum, Date enddatum,
			String beschreibung, Organisationseinheit projektleiter) throws IllegalArgumentException;

	public Ausschreibung createAusschreibungFor(Projekt pr, String bezeichnung, Date bewerbungsfrist,
			String ausschreibungstext) throws IllegalArgumentException;
	
	public Bewerbung createBewerbungFor(Ausschreibung as, Organisationseinheit o, String bewerbungstext) throws IllegalArgumentException;

	public Partnerprofil createPartnerprofilFor(Ausschreibung as) throws IllegalArgumentException;

	public Partnerprofil createPartnerprofilFor(Organisationseinheit or) throws IllegalArgumentException;

	public Eigenschaft createEigenschaftFor(Partnerprofil pp, String name, String value)
			throws IllegalArgumentException;

	public Person createPerson(String name, String vorname, String email, String strasse, int plz, String ort,
			String tel) throws IllegalArgumentException;

	public Unternehmen createUnternehmen(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException;

	public Team createTeam(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException;

	public Bewerbung bewerben(Ausschreibung as, Organisationseinheit organisation, String bewerbungstext) throws IllegalArgumentException;

	public Bewertung bewerten(Bewerbung bw, String stellungnahme, float wert) throws IllegalArgumentException;

	public Beteiligung beteiligen(Projekt pr, Organisationseinheit or, int personentage, Date startdatum, Date enddatum)
			throws IllegalArgumentException;

	public void save(ProjektMarktplatz pm) throws IllegalArgumentException;

	public void save(Projekt pr) throws IllegalArgumentException;

	public void save(Ausschreibung as) throws IllegalArgumentException;

	public void save(Partnerprofil pp) throws IllegalArgumentException;

	public void save(Eigenschaft e) throws IllegalArgumentException;

	public void save(Bewerbung bw) throws IllegalArgumentException;

	public void save(Bewertung bwt) throws IllegalArgumentException;

	public void save(Beteiligung bt) throws IllegalArgumentException;

	public void save(Person ps) throws IllegalArgumentException;

	public void save(Unternehmen u) throws IllegalArgumentException;

	public void save(Team t) throws IllegalArgumentException;

	public void delete(ProjektMarktplatz pm) throws IllegalArgumentException;

	public void delete(Projekt pr) throws IllegalArgumentException;

	public void delete(Ausschreibung as) throws IllegalArgumentException;

	public void delete(Partnerprofil pp) throws IllegalArgumentException;

	public void delete(Eigenschaft e) throws IllegalArgumentException;

	public void delete(Bewerbung bw) throws IllegalArgumentException;

	public void delete(Bewertung bwt) throws IllegalArgumentException;

	public void delete(Beteiligung bt) throws IllegalArgumentException;

	public void delete(Person ps) throws IllegalArgumentException;

	public void delete(Unternehmen u) throws IllegalArgumentException;

	public void delete(Team t) throws IllegalArgumentException;

}
