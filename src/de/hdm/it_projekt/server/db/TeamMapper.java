/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.bo.Team;

/**
 * Mapper-Klasse, die <code>Team</code>-Objekte auf eine relationale Datenbank
 * abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung gestellt, mit
 * deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und geloescht werden
 * koennen. Das Mapping ist bidirektional. D.h., Objekte koennen in
 * DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * 
 * @author Tugba Bulat
 */
public class TeamMapper {

	/**
	 * Die Klasse TeamMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static TeamMapper teamMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected TeamMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>TeamMapper.teamMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>TeamMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> TeamMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>TeamMapper</code>-Objekt.
	 */
	public static TeamMapper teamMapper() {
		if (teamMapper == null) {
			teamMapper = new TeamMapper();
		}

		return teamMapper;
	}

	/**
	 * Die Methode ermoeglicht es Team-Objekte in der Datenbank anzulegen.
	 * 
	 * @param t
	 *            - das in der Datenbank anzulegende Objekt
	 * @return
	 * @throws Exception
	 */
	public Team insert(Team t) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschluesselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM organisationseinheit ");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * t erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */
				t.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				stmt.executeUpdate(
						"INSERT INTO organisationseinheit (ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID) "
								+ "VALUES (" + t.getId() + "," + t.getName() + "," + t.getEmail() + "," + t.getStrasse()
								+ "," + t.getPlz() + "," + t.getOrt() + "," + t.getTel() + "," + t.getGoogleID() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return t;
	}

	/**
	 * Wiederholtes Schreiben eines Team-Objekts in die Datenbank.
	 * 
	 * @param t
	 *            - das Objekt, das in die Datenbank geschrieben werden soll
	 * @return
	 */
	public Team update(Team t) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE organisationseinheit " + "SET Name=\"" + t.getName() + "\" " + "SET Email=\""
					+ t.getEmail() + "\" " + "SET Strasse=\"" + t.getStrasse() + "\" " + "SET PLZ=\"" + t.getPlz()
					+ "\" " + "SET Ort=\"" + t.getOrt() + "\" " + "SET Tel=\"" + t.getTel() + "\" " + "SET GoogleID=\""
					+ t.getGoogleID() + "\" " + "WHERE ID=" + t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Team t) zu wahren,
		// geben wir t zurueck.
		return t;
	}

	/**
	 * Loeschen der Daten eines Team-Objekts aus der Datenbank.
	 * 
	 * @param t
	 *            - das zu loeschende Objekt
	 */
	public void delete(Team t) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM organisationseinheit " + "WHERE ID=" + t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Auslesen aller Teams.
	 * 
	 * @return
	 */
	public Vector<Team> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Team> result = new Vector<Team>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID "
					+ "FROM organisationseinheit" + " ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Team-Objekt erstellt.

			while (rs.next()) {
				Team t = new Team();
				t.setId(rs.getInt("ID"));
				t.setName(rs.getString("Name"));
				t.setEmail(rs.getString("Email"));
				t.setStrasse(rs.getString("Strasse"));
				t.setPlz(rs.getInt("PLZ"));
				t.setOrt(rs.getString("Ort"));
				t.setTel(rs.getString("Tel"));
				t.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(t);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen eines Teams mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            - Primaerschluesselattribut in DB
	 * @return
	 */
	public Team findById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID FROM organisationseinheit"
							+ "WHERE ID=" + id + " ORDER BY ID");

			/*
			 * Da ID der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts

				Team t = new Team();
				t.setId(rs.getInt("ID"));
				t.setName(rs.getString("Name"));
				t.setEmail(rs.getString("Email"));
				t.setStrasse(rs.getString("Strasse"));
				t.setPlz(rs.getInt("PLZ"));
				t.setOrt(rs.getString("Ort"));
				t.setTel(rs.getString("Tel"));
				t.setGoogleID(rs.getString("GoogleID"));

				return t;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * Auslesen eines Teams mit einem bestimmten Namen.
	 * 
	 * @param name
	 * @return
	 */
	public Vector<Team> findByName(String name) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Team> result = new Vector<Team>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID "
					+ "FROM organisationseinheit" + "WHERE name=" + name + " ORDER BY name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Team-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Team t = new Team();
				t.setId(rs.getInt("ID"));
				t.setName(rs.getString("Name"));
				t.setEmail(rs.getString("Email"));
				t.setStrasse(rs.getString("Strasse"));
				t.setPlz(rs.getInt("PLZ"));
				t.setOrt(rs.getString("Ort"));
				t.setTel(rs.getString("Tel"));
				t.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(t);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;

	}

	/**
	 * Auslesen eines Teams anhand einer bestimmten Email-Adresse.
	 * 
	 * @param email
	 * @return
	 */
	public Vector<Team> findByMail(String email) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Team> result = new Vector<Team>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID "
					+ "FROM organisationseinheit" + "WHERE email=" + email + " ORDER BY email");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Team-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Team t = new Team();
				t.setId(rs.getInt("ID"));
				t.setName(rs.getString("Name"));
				t.setEmail(rs.getString("Email"));
				t.setStrasse(rs.getString("Strasse"));
				t.setPlz(rs.getInt("PLZ"));
				t.setOrt(rs.getString("Ort"));
				t.setTel(rs.getString("Tel"));
				t.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(t);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;

	}

	/**
	 * Auslesen des zugehoerigen <code>Team</code>-Objekts zu einem gegebenen
	 * Projekt.
	 * 
	 * @param pr
	 * @return
	 */
	public Vector<Team> getByProjekt(Projekt pr) {
		return null;
	}

	/**
	 * Auslesen des zugehoerigen <code>Team</code>-Objekts zu einem gegebenen
	 * Projektmarktplatzes.
	 * 
	 * @param pm
	 * @return
	 */
	public Vector<Team> getByProjektmarktplatz(ProjektMarktplatz pm) {
		return null;
	}

}
