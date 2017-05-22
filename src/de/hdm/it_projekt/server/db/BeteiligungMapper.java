/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Bewertung;

/**
 * Mapper-Klasse, die <code>Beteiligung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author ElifY
 */
public class BeteiligungMapper {

  /**
   * Die Klasse BeteiligungMapper wird nur einmal instantiiert. Man spricht hierbei
   * von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   */
  private static BeteiligungMapper beteiligungMapper = null;

  /**
   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
   * Instanzen dieser Klasse zu erzeugen.
   */
  protected BeteiligungMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>BeteiligungMapper.beteiligungMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
   * Instanz von <code>BeteiligungMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> BeteiligungMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>BeteiligungMapper</code>-Objekt.
   */
  public static BeteiligungMapper beteiligungMapper() {
    if (beteiligungMapper == null) {
      beteiligungMapper = new BeteiligungMapper();
    }

    return beteiligungMapper;
  }

public Beteiligung insert(Beteiligung bet){
	
	//DB-Verbindung herstellen
	Connection con = DBConnection.connection();

	try {
		    //Leeres SQL-Statement (JDBC) anlegen
	    	Statement stmt = con.createStatement();

	       //Momentan hoechsten Primaerschluesselwert pruefen
	       ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
	          + "FROM transactions ");

	      // Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	        
	    	/*
	         * bet erhaelt den bisher maximalen, nun um 1 inkrementierten
	         * Primaerschluessel.
	         */
	        bet.setId(rs.getInt("maxid") + 1);

	        stmt = con.createStatement();

	        // Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
	        stmt.executeUpdate("INSERT INTO beteiligungs (id, personentage, enddatum, startdatum) "
	            + "VALUES (" + Beteiligung.getId() + ","
	            + bet.getPersonentage() + ","
	            + bet.getEnddatum() + "," + bet.getStartdatum() + ")");
	      }
	    }
	    catch (SQLException e1) {
	      e1.printStackTrace();
	    }
	
	    //Rueckgabe, der evtl. korrigierten Beteiligung.
	    return bet;
	  }

/**
 * Wiederholtes Schreiben eines Objekts in die Datenbank.
 * 
 * @param bet das Objekt, das in die DB geschrieben werden soll
 * @return das als Parameter uebergebene Objekt
 */

	 public Beteiligung update(Beteiligung bet){
		 
		 //DB-Verbindung herstellen
		 Connection con = DBConnection.connection();
		 
		 try{
			 //Leeres SQL-Statement (JDBC) anlegen
			 Statement stmt = con.createStatement();
			 
		     // Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
			 stmt.executeUpdate("UPDATE beteiligungs " + "SET peronentage=\""
		     + bet.getPersonentage() + "\"," + "enddatum=\"" + bet.getEnddatum()
		     + "\", " + "startdatum=\"" + bet.getStartdatum() + "\" "
		     + "WHERE id=" + Beteiligung.getId());
		 
		 }
		 catch (SQLException e2){
			 e2.printStackTrace();
		 }
		 
		 // Um Analogie zu insert(Bewertung bet) zu wahren, geben wir bt zurueck
	     return bet;
	  }
	 
}