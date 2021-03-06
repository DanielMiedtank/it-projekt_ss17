/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

/**
 * Mapper-Klasse, die <code>ProjektMarktplatz</code>-Objekte auf eine
 * relationale Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur
 * Verfuegung gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt,
 * modifiziert und geloescht werden koennen. Das Mapping ist bidirektional.
 * D.h., Objekte koennen in DB-Strukturen und DB-Strukturen in Objekte
 * umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * 
 * @author Tugba Bulat
 */
public class ProjektMarktplatzMapper {

	/**
	 * Die Klasse ProjektMarktplatzMapper wird nur einmal instantiiert. Man
	 * spricht hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static ProjektMarktplatzMapper projektMarktplatzMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected ProjektMarktplatzMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProjektMarktplatzMapper.projektmarktplatzMapper()</code>. Sie
	 * stellt die Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur
	 * eine einzige Instanz von <code>ProjektMarktplatzMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ProjektMarktplatzMapper sollte nicht mittels
	 * <code>new</code> instantiiert werden, sondern stets durch Aufruf dieser
	 * statischen Methode.
	 * 
	 * @return DAS <code>ProjektMarktplatzMapper</code>-Objekt.
	 */
	public static ProjektMarktplatzMapper projektMarktplatzMapper() {
		if (projektMarktplatzMapper == null) {
			projektMarktplatzMapper = new ProjektMarktplatzMapper();
		}

		return projektMarktplatzMapper;
	}

	/**
	 * Diese Methode ermoeglicht es einen Projektmarktplatz in der Datenbank
	 * anzulegen.
	 * 
	 * @param pm
	 * @return
	 */
	public ProjektMarktplatz insert(ProjektMarktplatz pm) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschluesselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM projektmarktplatz ");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {

				/*
				 * pm erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */
				pm.setId(rs.getInt("maxid") + 1);
				
				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO projektmarktplatz (ID, Bezeichnung, Admin_ID) VALUES (" + pm.getId() + ",'"
						+ pm.getBezeichnung() + "'," + pm.getAdminId() + ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pm;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param pm
	 *            - das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */
	public ProjektMarktplatz update(ProjektMarktplatz pm) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE projektmarktplatz " + "SET Bezeichnung=\"" + pm.getBezeichnung() + "\","
					+ " Admin_ID=" + pm.getAdminId()
					+ " WHERE ID=" + pm.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(ProjektMarktplatz pm) zu wahren, geben
		// wir pm zurueck
		return pm;
	}

	/**
	 * Loeschen der Daten eines <code>ProjektMarktplatz</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param pm
	 *            - das aus der DB zu loeschende "Objekt"
	 */
	public void delete(ProjektMarktplatz pm) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM projektmarktplatz " + "WHERE ID=" + pm.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller ProjektMarktplaetze.
	 * 
	 * @return Ein Vektor mit Projektmarktplatz-Objekten, die saemtliche
	 *         Projektmarktplaetze repraesentieren. Bei evtl. Exceptions wird
	 *         eine partiell gefuellter oder ggf. auch leerer Vektor
	 *         zurueckgeliefert.
	 */
	public Vector<ProjektMarktplatz> findAll() throws IllegalArgumentException {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<ProjektMarktplatz> result = new Vector<ProjektMarktplatz>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, Bezeichnung, Admin_ID FROM projektmarktplatz");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projektmarktplatz-Objekt erstellt.

			while (rs.next()) {
				ProjektMarktplatz pm = new ProjektMarktplatz();
				pm.setId(rs.getInt("ID"));
				pm.setBezeichnung(rs.getString("Bezeichnung"));
				pm.setAdminId(rs.getInt("Admin_ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pm);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen eines Projektmarktplatzes mit vorgegebener ID. Da diese eindeutig
	 * ist, wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            - Primaerschluesselattribut in DB
	 * @return Projektmarktplatz-Objekt, das dem uebergebenen Schluessel
	 *         entspricht, null bei nicht vorhandenem DB-Tupel.
	 */
	public ProjektMarktplatz findById(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT ID, Bezeichnung, Admin_ID FROM projektmarktplatz WHERE ID=" + id + " ORDER BY ID");

			/*
			 * Da ID der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts

				ProjektMarktplatz pm = new ProjektMarktplatz();
				pm.setId(rs.getInt("ID"));
				pm.setBezeichnung(rs.getString("Bezeichnung"));
				pm.setAdminId(rs.getInt("Admin_ID"));

				return pm;
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
			return null;
		}

		return null;
	}


	/**
	 * 
	 * @param o
	 * @return
	 */
	public Vector<ProjektMarktplatz> getByOrganisation(Organisationseinheit o) {

		Connection con = DBConnection.connection();

		Vector<ProjektMarktplatz> result = new Vector<ProjektMarktplatz>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT pm.ID, pm.Admin_ID, pm.Bezeichnung FROM projektmarktplatz AS pm "
					+ "INNER JOIN projektmarktplatz_has_organisationseinheit ON pm.id=Projektmarktplatz_ID "
					+ "WHERE Organisationseinheit_ID= " + o.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// ProjektMarktplatz-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				ProjektMarktplatz pm = new ProjektMarktplatz();
				pm.setId(rs.getInt("pm.ID"));
				pm.setBezeichnung(rs.getString("pm.Bezeichnung"));
				pm.setAdminId(rs.getInt("pm.Admin_ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pm);
			}
		} catch (SQLException e6) {
			e6.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}
}