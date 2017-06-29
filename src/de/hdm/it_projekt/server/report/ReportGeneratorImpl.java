package de.hdm.it_projekt.server.report;

/**
 * 
 * To be Done
 * createMatchingAusschreibungenReport - Methode die Vektor zurück gibt in Projektadministartion
 * fan in analyse methode zum auslesen abgebrochener Ausschreibungen
 */
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.it_projekt.server.ProjektAdministrationImpl;
import de.hdm.it_projekt.server.db.*;
import de.hdm.it_projekt.shared.ProjektAdministration;
import de.hdm.it_projekt.shared.ReportGenerator;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Eigenschaft;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AlleBewerbungenReport;
import de.hdm.it_projekt.shared.report.BewerbungenZuAusschreibungenReport;
import de.hdm.it_projekt.shared.report.Column;
import de.hdm.it_projekt.shared.report.CompositeParagraph;
import de.hdm.it_projekt.shared.report.PassendeAusschreibungenReport;
import de.hdm.it_projekt.shared.report.ProjektverflechtungenReport;
import de.hdm.it_projekt.shared.report.Report;
import de.hdm.it_projekt.shared.report.Row;
import de.hdm.it_projekt.shared.report.SimpleParagraph;
import de.hdm.it_projekt.shared.report.SimpleReport;

import java.util.Date;
import java.util.Vector;

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	private AusschreibungMapper asMapper = null;
	private BeteiligungMapper btMapper = null;
	private BewerbungMapper bwMapper = null;
	private BewertungMapper bwtMapper = null;
	private EigenschaftMapper eMapper = null;
	private PartnerprofilMapper ppMapper = null;
	private PersonMapper pMapper = null;
	private ProjektMapper prMapper = null;
	private ProjektMarktplatzMapper pmMapper = null;
	private TeamMapper tMapper = null;
	private UnternehmenMapper uMapper = null;

	private ProjektAdministrationImpl pa = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	public void init() throws IllegalArgumentException {

		this.asMapper = AusschreibungMapper.ausschreibungMapper();
		this.btMapper = BeteiligungMapper.beteiligungMapper();
		this.bwMapper = BewerbungMapper.bewerbungMapper();
		this.bwtMapper = BewertungMapper.bewertungMapper();
		this.eMapper = EigenschaftMapper.eigenschaftMapper();
		this.ppMapper = PartnerprofilMapper.partnerprofilMapper();
		this.pMapper = PersonMapper.personMapper();
		this.prMapper = ProjektMapper.projektMapper();
		this.pmMapper = ProjektMarktplatzMapper.projektMarktplatzMapper();
		this.tMapper = TeamMapper.teamMapper();
		this.uMapper = UnternehmenMapper.unternehmenMapper();

		pa = new ProjektAdministrationImpl();
		pa.init();
	}

	@Override
	public AlleAusschreibungenReport createAlleAusschreibungenReport(ProjektMarktplatz pm)
			throws IllegalArgumentException {

		AlleAusschreibungenReport report = new AlleAusschreibungenReport();

		report.setTitle("Alle Ausschreibungen Report");
		report.setHeaderData(new SimpleParagraph("Projektmarktplatz: " + pm.getBezeichnung()));
		report.setCreated(new Date());

		Row headline = new Row();
		headline.addColumn(new Column("Ausschreibung"));
		report.addRow(headline);

		Vector<Ausschreibung> asV = getAlleAusschreibungenFor(pm);

		for (Ausschreibung as : asV) {
			Row row = new Row();
			row.addColumn(new Column(as.getBezeichnung()));
			report.addRow(row);
		}

		return report;
	}

	@Override
	public PassendeAusschreibungenReport createPassendeAusschreibungenReport(Organisationseinheit o)
			throws IllegalArgumentException {

		PassendeAusschreibungenReport report = new PassendeAusschreibungenReport();

		report.setTitle("Dem eigenen Partnerprofil entsprechende Ausschreibungen");
		report.setHeaderData(new SimpleParagraph("Partnerprofilvergleich für: " + o.getName()));
		report.setCreated(new Date());

		Vector<Ausschreibung> asMatch = pa.getAusschreibungByMatch(o);

		Row headline = new Row();
		headline.addColumn(new Column("Ausschreibung"));
		report.addRow(headline);

		if (asMatch.size() == 0) {
			Row row = new Row();
			row.addColumn(new Column("Es wurden keine passenden Ausschreibungen gefunden."));
			report.addRow(row);
		} else {

			for (Ausschreibung as : asMatch) {
				Row row = new Row();
				row.addColumn(new Column(as.getBezeichnung()));
				report.addRow(row);
			}
		}

		return report;
	}

	@Override
	public BewerbungenZuAusschreibungenReport createBewerbungenZuAusschreibungenReport(Organisationseinheit o)
			throws IllegalArgumentException {

		BewerbungenZuAusschreibungenReport report = new BewerbungenZuAusschreibungenReport();

		report.setTitle("Bewerbungen auf Ausschreibungen Report");
		report.setHeaderData(new SimpleParagraph("Ausschreibender: " + o.getName()));
		report.setCreated(new Date());

		Row headline = new Row();
		headline.addColumn(new Column("Objekt"));
		headline.addColumn(new Column("Name"));
		report.addRow(headline);

		
		Vector<Ausschreibung> asV = getAusschreibungFor(o);

		for (Ausschreibung as : asV) {
			Row asRow = new Row();
			asRow.addColumn(new Column("Ausschreibung"));
			asRow.addColumn(new Column(as.getBezeichnung()));
			report.addRow(asRow);

			Vector<Bewerbung> bwV = getBewerbungenFor(as);

			if (bwV.size() == 0) {
				Row row = new Row();
				row.addColumn(new Column(" "));
				row.addColumn(new Column("<i>keine Bewerbungen..</i>"));
				report.addRow(row);
			} else {
				for (Bewerbung bw : bwV) {

					Organisationseinheit bewerber = pa.getBewerberFor(bw);

					Row row = new Row();
					row.addColumn(new Column("Bewerbung von :"));
					row.addColumn(new Column(bewerber.getName()));
					report.addRow(row);
				}
			}

			Row gabRow = new Row();
			gabRow.addColumn(new Column(" "));
			gabRow.addColumn(new Column(" "));
			report.addRow(gabRow);
		}

		return report;
	}

	@Override
	public AlleBewerbungenReport createAlleBewerbungenReport(Organisationseinheit o) throws IllegalArgumentException {

		AlleBewerbungenReport report = new AlleBewerbungenReport();

		report.setTitle("Meine Bewerbungen Report");
		report.setHeaderData(new SimpleParagraph("Bewerbungen von: " + o.getName()));
		report.setCreated(new Date());

		Vector<Bewerbung> bwV = getBewerbungenFrom(o);

		Row headline = new Row();
		headline.addColumn(new Column("Ausschreibung"));
		report.addRow(headline);

		for (Bewerbung bw : bwV) {
			Row row = new Row();
			Ausschreibung a = pa.getAusschreibungById(bw.getAusschreibungId());
			row.addColumn(new Column(a.getBezeichnung()));
			report.addRow(row);
		}

		return report;
	}

	@Override
	public ProjektverflechtungenReport createProjektverflechtungenReport(Organisationseinheit o, ProjektMarktplatz pm)
			throws IllegalArgumentException {

		ProjektverflechtungenReport report = new ProjektverflechtungenReport();

		report.setTitle("Projektverflechtungen");
		report.setHeaderData(new SimpleParagraph("Verflechtungen von: " + o.getName()));
		report.setCreated(new Date());
		
		Vector<Projekt> prV = pa.getProjektByProjektleiter((Person)o, pm);
		
		Vector<Bewerbung> bwO = getBewerbungenFrom(o);
		Vector<Beteiligung> btO = getBeteiligungenFor(o);

		Row headline = new Row();
		headline.addColumn(new Column("Objekt"));
		headline.addColumn(new Column("Name"));
		report.addRow(headline);

		if (bwO.size() != 0) {
			for (Bewerbung bw : bwO) {
				Ausschreibung as = pa.getAusschreibungById(bw.getAusschreibungId());
				Row row = new Row();
				row.addColumn(new Column("Bewerbung: "));
				row.addColumn(new Column(as.getBezeichnung()));
				report.addRow(row);
			}
		}

		if (btO.size() != 0) {
			for (Beteiligung bt : btO) {
				Projekt pr = pa.getProjektById(bt.getProjektId());
				Row row = new Row();
				row.addColumn(new Column("Projekt: "));
				row.addColumn(new Column(pr.getName()));
				report.addRow(row);
			}
		}

		return report;
	}

	@Override
	public Vector<Ausschreibung> getAlleAusschreibungenFor(ProjektMarktplatz pm) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return asMapper.getByProjektmarktplatz(pm);
	}

	@Override
	public Vector<Ausschreibung> getAusschreibungFor(Organisationseinheit o) throws IllegalArgumentException {
		return asMapper.getByProjektleiter(o);
	}

	@Override
	public Vector<Bewerbung> getBewerbungenFor(Ausschreibung as) throws IllegalArgumentException {
		return bwMapper.getByAusschreibung(as);
	}

	@Override
	public Vector<Bewerbung> getBewerbungenFrom(Organisationseinheit o) throws IllegalArgumentException {
		return bwMapper.getByOrganisationseinheit(o);
	}

	@Override
	public Vector<Organisationseinheit> getBewerberForAusschreibenden(Organisationseinheit o)
			throws IllegalArgumentException {
		return null;
	}

	@Override
	public Vector<Ausschreibung> getMatchingAusschreibungenFor(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Beteiligung> getBeteiligungenFor(Organisationseinheit o) throws IllegalArgumentException {
		return btMapper.getByOrganisationseinheit(o);
	}
}