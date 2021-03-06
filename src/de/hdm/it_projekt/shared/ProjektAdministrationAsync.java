package de.hdm.it_projekt.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.*;

public interface ProjektAdministrationAsync {

	void beteiligen(Projekt pr, Organisationseinheit or, int personentage, Date startdatum, Date enddatum,
			AsyncCallback<Beteiligung> callback);

	void bewerben(Ausschreibung as, Organisationseinheit organisation, String bewerbungstext, AsyncCallback<Bewerbung> callback);

	void bewerten(Bewerbung bw, String stellungnahme, float wert, AsyncCallback<Bewertung> callback);

	void createAusschreibungFor(Projekt pr, String bezeichnung, Date bewerbungsfrist, String ausschreibungstext, AsyncCallback<Ausschreibung> callback);

	void createPartnerprofilFor(Organisationseinheit or, AsyncCallback<Partnerprofil> callback);

	void createEigenschaftFor(Partnerprofil pp, String name, String value, AsyncCallback<Eigenschaft> callback);

	void createPartnerprofilFor(Ausschreibung as, AsyncCallback<Partnerprofil> callback);

	void createProjektFor(ProjektMarktplatz pm, String name, Date startdatum, Date enddatum, String beschreibung, Organisationseinheit projektleiter,
			AsyncCallback<Projekt> callback);

	void createPerson(String name, String vorname, String email, String strasse, int plz, String ort, String tel,
			AsyncCallback<Person> callback);

	void createUnternehmen(String name, String email, String strasse, int plz, String ort, String tel,
			AsyncCallback<Unternehmen> callback);

	void delete(Partnerprofil pp, AsyncCallback<Void> callback);

	void createProjektMarktplatz(String bez, int adminID, AsyncCallback<ProjektMarktplatz> callback);

	void createTeam(String name, String email, String strasse, int plz, String ort, String tel,
			AsyncCallback<Team> callback);

	void delete(Bewertung bwt, AsyncCallback<Void> callback);

	void getAusschreibungFor(Projekt pr, AsyncCallback<Vector<Ausschreibung>> callback);

	void getBeteiligungenFor(Projekt pr, AsyncCallback<Vector<Beteiligung>> callback);

	void delete(Bewerbung bw, AsyncCallback<Void> callback);

	void delete(ProjektMarktplatz pm, AsyncCallback<Void> callback);

	void delete(Person ps, AsyncCallback<Void> callback);

	void delete(Eigenschaft e, AsyncCallback<Void> callback);

	void delete(Ausschreibung as, AsyncCallback<Void> callback);

	void delete(Team t, AsyncCallback<Void> callback);

	void delete(Unternehmen u, AsyncCallback<Void> callback);

	void delete(Beteiligung bt, AsyncCallback<Void> callback);

	void delete(Projekt pr, AsyncCallback<Void> callback);

	void getAlleProjektMarktplaetze(AsyncCallback<Vector<ProjektMarktplatz>> callback);

	void getAusschreibungById(int id, AsyncCallback<Ausschreibung> callback);

	void getBewerbungFor(Ausschreibung as, AsyncCallback<Vector<Bewerbung>> callback);

	void getBeteiligungenFor(Organisationseinheit o, AsyncCallback<Vector<Beteiligung>> callback);

	void getEigenschaftenFor(Partnerprofil pr, AsyncCallback<Vector<Eigenschaft>> callback);

	void getBewertungFor(Bewerbung bw, AsyncCallback<Bewertung> callback);

	void getPartnerprofilById(int id, AsyncCallback<Partnerprofil> callback);

	void getProjektByName(String name, AsyncCallback<Vector<Projekt>> callback);

	void getProjektById(int id, AsyncCallback<Projekt> callback);

	void getProjektMarktplatzById(int id, AsyncCallback<ProjektMarktplatz> callback);

	void init(AsyncCallback<Void> callback);

	void save(Person ps, AsyncCallback<Void> callback);

	void save(Projekt pr, AsyncCallback<Void> callback);

	void save(Eigenschaft e, AsyncCallback<Void> callback);

	void save(Bewerbung bw, AsyncCallback<Void> callback);

	void save(Unternehmen u, AsyncCallback<Void> callback);

	void save(Bewertung bwt, AsyncCallback<Void> callback);

	void save(Beteiligung bt, AsyncCallback<Void> callback);

	void save(Team t, AsyncCallback<Void> callback);

	void save(ProjektMarktplatz pm, AsyncCallback<Void> callback);

	void save(Partnerprofil pp, AsyncCallback<Void> callback);

	void save(Ausschreibung as, AsyncCallback<Void> callback);

	void getAlleProjekteFor(ProjektMarktplatz pm, AsyncCallback<Vector<Projekt>> callback);

	void getProjektMarktplaetzeByOrganisation(Organisationseinheit o,
			AsyncCallback<Vector<ProjektMarktplatz>> callback);

	void findByGoogleId(LoginInfo li, AsyncCallback<Organisationseinheit> callback);

	void getProjektleiterFor(Projekt pr, AsyncCallback<Person> callback);

	void getAusschreibungby(Partnerprofil pp, AsyncCallback<Ausschreibung> callback);

	void createBewertungFor(Bewerbung bw, float wert, String stellungnahme, AsyncCallback<Bewertung> callback);

	void getAusschreibungBy(Bewerbung bw, AsyncCallback<Ausschreibung> callback);

	void getBewerbungBy(Ausschreibung as, AsyncCallback<Vector<Bewerbung>> callback);

	void getBewerbungById(int id, AsyncCallback<Bewerbung> callback);

	void getBewerberFor(Bewerbung bw, AsyncCallback<Organisationseinheit> callback);

	void getPartnerprofilFor(Ausschreibung as, AsyncCallback<Partnerprofil> callback);

	void getBeteiligterFor(Beteiligung bt, AsyncCallback<Organisationseinheit> callback);

	void getProjektByProjektleiter(Organisationseinheit p, ProjektMarktplatz pm, AsyncCallback<Vector<Projekt>> callback);

	void createBewerbungFor(Ausschreibung as, Organisationseinheit o, String bewerbungstext,
			AsyncCallback<Bewerbung> callback);

	void getBeteiligungFor(Bewerbung bw, AsyncCallback<Beteiligung> callback);

	void createBeteiligungFor(Bewerbung bw, AsyncCallback<Beteiligung> callback);

	void getAusschreibungByMatch(Organisationseinheit o, AsyncCallback<Vector<Ausschreibung>> callback);

	void getOrganisationseinheitById(int id, AsyncCallback<Organisationseinheit> callback);


}
