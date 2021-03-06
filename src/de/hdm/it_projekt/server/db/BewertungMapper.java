package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Bewertung;

/**
 * Anlehnung an @author Thies
 * 
 * @author Elif Yavuz
 * @author Tugba Bulat
 * 
 *         Mapper-Klasse, die <code>Bewertung</code>-Objekte auf eine
 *         relationale Datenbank abbildet. Hierzu wird eine Reihe von Methoden
 *         zur Verfuegung gestellt, mit deren Hilfe z.B. Objekte gesucht,
 *         erzeugt, modifiziert und geloescht werden koennen. Das Mapping ist
 *         bidirektional. D.h., Objekte koennen in DB-Strukturen und
 *         DB-Strukturen in Objekte umgewandelt werden.
 */
public class BewertungMapper {

	/**
	 * Die Klasse BewertungMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static BewertungMapper bewertungMapper = null;
	private Vector<Bewertung> result;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected BewertungMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BewertungMapper.bewertungMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>BewertungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> BewertungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>BewertungMapper</code>-Objekt.
	 */
	public static BewertungMapper bewertungMapper() {
		if (bewertungMapper == null) {
			bewertungMapper = new BewertungMapper();
		}

		return bewertungMapper;
	}

	/**
	 * Einfuegen eines <code>Bewertung</code>-Objekts in die Datenbank. Dabei
	 * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und
	 * ggf. berichtigt.
	 * 
	 * @param bt
	 *            das zu speichernde Objekt
	 * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */

	public Bewertung insert(Bewertung bt) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan hoechsten Primaerschluessel pruefen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM bewertung");

			if (rs.next()) {

				/*
				 * bt erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */

				bt.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
				stmt.executeUpdate("INSERT INTO bewertung (ID, Wert, Stellungnahme, Erstelldatum, Bewerbung_ID)"
						+ "VALUES (" + bt.getId() + ",'" + Float.toString(bt.getWert()) + "','" + bt.getStellungnahme() + "','"
						+ DBConnection.convertToSQLDateString(bt.getErstelldatum()) + "','" + bt.getBewerbungId()
						+ "')");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Rueckgabe, der evtl. korrigierten Bewertung.
		return bt;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param bt
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */

	public Bewertung update(Bewertung bt) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
			stmt.executeUpdate("UPDATE bewertung " + "SET Wert=\"" + bt.getWert() + "\"," + "Stellungnahme=\""
					+ bt.getStellungnahme() + "\", " + "Erstelldatum=\"" + DBConnection.convertToSQLDateString(bt.getErstelldatum()) + "\" " + "WHERE ID="
					+ bt.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(Bewertung bt) zu wahren, geben wir bt zurueck
		return bt;
	}

	/**
	 * Loeschen der Daten eines <code>Bewertung</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param bt
	 *            das aus der DB zu loeschende Objekt
	 */

	public void delete(Bewertung bt) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM bewertung WHERE ID= " + bt.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Bewertungen.
	 * 
	 * @return Ein Vektor mit Bewertung-Objekten, die saemtliche Bewertungen
	 *         repraesentieren. Bei evtl. Exceptions wird eine partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 */

	public Vector<Bewertung> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Bewertung> result = new Vector<Bewertung>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT ID, Wert, Stellungnahme, Erstelldatum, Bewerbung_ID FROM bewertung ORDER BY Wert");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Bewertung-Objekt erstellt.

			while (rs.next()) {
				Bewertung bt = new Bewertung();
				bt.setId(rs.getInt("ID"));
				bt.setWert(rs.getFloat("Wert"));
				bt.setStellungnahme(rs.getString("Stellungnahme"));
				bt.setErstelldatum(rs.getDate("Erstelldatum"));
				bt.setBewerbungId(rs.getInt("Bewerbung_ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(bt);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen einer Bewertung mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            Primaerschluesselattribut in DB
	 * @return Bewertung-Objekt, das dem uebergebenen Schluessel entspricht,
	 *         null bei nicht vorhandenem DB-Tupel.
	 */

	public Bewertung findById(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Wert, Stellungnahme, Erstelldatum, Bewerbung_ID FROM bewertung WHERE ID=" + id);

			/*
			 * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */

			if (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und
				// Ausgabe des Ergebnis-Objekts.
				Bewertung bt = new Bewertung();
				bt.setId(rs.getInt("ID"));
				bt.setWert(rs.getFloat("Wert"));
				bt.setStellungnahme(rs.getString("Stellungnahme"));
				bt.setErstelldatum(rs.getDate("Erstelldatum"));
				bt.setBewerbungId(rs.getInt("Bewerbung_ID"));

				return bt;
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
			return null;
		}

		return null;
	}




	/**
	 * Auslesen einer Bewertung anhand einer Bewerbung.
	 * 
	 * @param bw
	 * @return
	 */
	public Bewertung getByBewerbung(Bewerbung bw) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Bewertung bwt = null;

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ID FROM bewertung WHERE Bewerbung_ID=" + bw.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Bewertungs-Objekt erstellt.
			if (rs.next()) {

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				bwt = findById(rs.getInt("ID"));
			}
		} catch (SQLException e7) {
			e7.printStackTrace();
		}

		return bwt;
	}

}