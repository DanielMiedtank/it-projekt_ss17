
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Projekt;

/**
 * Mapper-Klasse, die <code>Ausschreibung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author ElifY
 */

public class AusschreibungMapper {

	/**
	   * Die Klasse AusschreibungMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   */
	
	private static AusschreibungMapper ausschreibungMapper = null;

	  /**
	   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   */
	  protected AusschreibungMapper() {
	  }
	  

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>AusschreibungMapper.ausschreibungMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
	   * Instanz von <code>AusschreibungMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> AusschreibungMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>AusschreibungMapper</code>-Objekt.
	   */
	  public static AusschreibungMapper ausschreibungMapper() {
	    if (ausschreibungMapper == null) {
	      ausschreibungMapper = new AusschreibungMapper();
	    }

	    return ausschreibungMapper;
	  }
	  
	  /**
	   * Einfuegen eines <code>Ausschreibung</code>-Objekts in die Datenbank. Dabei
	   * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
	   * berichtigt.
	   * 
	   * @param as das zu speichernde Objekt
	   * @return das bereits uebergeben Objekt, jedoch mit ggf. korrigierter
	   * 		<code>id</id>
	   */
	
	  	public Ausschreibung insert(Ausschreibung as){
	  		
	  		//DB-Verbindung herstellen
	  		Connection con = DBConnection.connection();
	  		
	  		try{
	  			//Leeres SQL-Statement (JDBC) anlegen
	  			Statement stmt = con.createStatement();
	  			
	  			//Momentan hoechsten Primaerschluesselwert pruefen
  				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
  			          + "FROM ausschreibungen ");
	  		
  				// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
  				if (rs.next()) {
  					
  					  /*
    		    	   * as erhaelt den bisher maximalen, nun um 1 inkrementierten
    		    	   * Primaerschluessel.
    		    	   */
    		    	  
    		    	  	as.setId(rs.getInt("maxid") + 1);
    		    	  	
    		    	  	stmt = con.createStatement();
    		    	  	
    		    	 // Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
      			        stmt.executeUpdate("INSERT INTO ausschreibungen (id, bezeichnung, ausschreibungstext, bewerbungsfrist) "
      			            + "VALUES (" + Ausschreibung.getId() + ","
      			            + as.getBezeichnung() + ","
      			            + as.getAusschreibungstext() + ","
      			            + as.getBewerbungsfrist() + ")");
  						}	  	
  				}
	  						catch (SQLException e1){
	  						   e1.printStackTrace();
	  						}
						//Rueckgabe, der evtl. korrigierten Ausschreibung.
						return as;
						
						/**
				  		 * Wiederholtes Schreiben eines Objekts in die Datenbank.
				  		 * 
				  		 * @param as
				  		 * 		das Objekt, das in die DB geschrieben werden soll
				  		 * @return das als Parameter uebergebene Objekt
				  		 */
					
						public Ausschreibung update(Ausschreibung as){
							
							//DB-Verbindung herstellen
							Connection con = DBConnection.connection();
				  			
				  			try{
				  				//Leeres SQL-Statement (JDBC) anlegen
				  				Statement stmt = con.createStatement();
				  				
				  				//Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				  				 stmt.executeUpdate("UPDATE ausschreibungen " + "SET bezeichnung=\""
				  				 + as.getBezeichnung() + "\"," + "ausschreibungstext=\"" + as.getAusschreibungstext()
				  				 + "bewerbungsfrist=\"" + as.getBewerbungsfrist()
				  			     + "WHERE id=" + Ausschreibung.getId());		
				  				 
				  			  }	
			  			 	
				  			catch (SQLException e2){
			  				 e2.printStackTrace();
			  			 }
				  		
				  			// Um Analogie zu insert(Ausschreibung as) zu wahren, geben wir as zurueck
			  				return as;
			  		  }
				  				
						/**
				  		  * Loeschen der Daten eines <code>Ausschreibung</code>-Objekts aus der DB
				  		  * @param as
				  		  * 		das aus der DB zu loeschende Objekt
				  		  * @return 
				  		  */
						
						public void delete(Ausschreibung as){
							
							//DB-Verbindung herstellen
			  				Connection con = DBConnection.connection();
							
			  				try{
			  					//Leeres SQL-Statement (JDBC) anlegen
			  					Statement stmt = con.createStatement();
			  					
			  					stmt.executeUpdate("DELETE FROM ausschreibungen" + "WHERE id=" + Ausschreibung.getId());
			  				}
			  				 catch (SQLException e3){
			  					 e3.printStackTrace();
			  				 }
			  			 }
			 
						/**
			  			 * Auslesen aller Ausschreibungen
			  			 * @return Ein Vektor mit Ausschreibung-Objekten, die saemtliche
			  			 * Ausschreibungen repraesentieren. Bei evtl. Exceptions wird eine
			  			 * partiell gefuellter oder ggf. auch leerer Vektor zurueckgeliefert
			  			 */
						
						public Vector<Ausschreibung> findAll(){
							
							//DB-Verbindung herstellen
		  					Connection con = DBConnection.connection();
		  		 		 
		  					//Ergebnisvektor vorbereiten
		  					Vector<Ausschreibung> result = new Vector<Ausschreibung>();
							
		  					try{
		  			 			
		  			 			 //Leeres SQL-Statement (JDBC) anlegen
		  			 			 Statement stmt = con.createStatement();
		  			 			 
		  			 			 ResultSet rs = stmt.executeQuery("SELECT id, bezeichnung, ausschreibungstext, bewerbungsfrist "
		  			 				+ "FROM ausschreibungen " + " ORDER BY id");
		  			 			 
		  			 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein
		  			 			 //Ausschreibung-Objekt erstellt.
		  			 			 while (rs.next()) {
		  			 				 Ausschreibung as = new Ausschreibung();
		  			 				 as.setId(rs.getInt("id"));
		  			 				 as.setBezeichnung(rs.getString("bezeichnung"));
		  			 				 as.setAusschreibungstext(rs.getString("ausschreibungstext"));
		  			 				 as.setBewerbungsfrist(rs.getDate("bewerbungsfrist"));
		  			 			 
		  			 			//Hinzufuegen des neuen Objekts zum Ergebnisvektor
		    			 		       result.addElement(as);
		    			 		      		}
		    			 		 		}
		    			 		    catch (SQLException e4) {
		    			 		    e4.printStackTrace();
		    			 		    		}	

		    			 		   	// Ergebnisvektor zurueckgeben
		    			 		   	return result;
		    			 	 		}
		    			 
		    			 			
					    /** Suchen einer Ausschreibung mit vorgegebener ID. Da diese eindeutig ist,
					 	  * wird genau ein Objekt zurueckgegeben.
					 	  * 
					 	  * @param id
					 	  * 	Primaerschluesselattribut in DB
					 	  * @return Ausschreibung-Objekt, das dem uebergebenen Schluessel entspricht,
					 	  * null bei nicht vorhandenem DB-Tupel.
					 	  */
		  			 			 public Vector<Ausschreibung> findById(int id){
		  			 			 
		  			 				 //DB-Verbindung herstellen
		  			 				 Connection con = DBConnection.connection();
		  			 				 
		  			 				try{
	    					 		  //Leeres SQL-Statement (JDBC) anlegen
		    					 		Statement stmt = con.createStatement();
		    					 		
		    					 	  //Statement ausfuellen und als Query an die DB schicken
		    					 		ResultSet rs= stmt.executeQuery("SELECT id, bezeichnung, ausschreibunstext, bewerbungsfrist FROM ausschreibungen "
		    						 				 + "WHERE id=" + id + "ORDER BY id");
						
		    					 		/*
	    						 		  * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
	    						 		  * zurueckgegeben werden.
	    						 		  * Pruefung, ob ein Ergebnis vorliegt.
	    						 		  */
	    					 		  		if (rs.next()){
	    						
	    					 		  		//Umwandlung des Ergebnis-Tupel in ein Objekt und
	    						 			//Ausgabe des Ergebnis-Objekts.
	    						 				 
	    						 				 Ausschreibung as = new Ausschreibung();
	    						 				 as.setId(rs.getInt("id"));
	    						 				 as.setBezeichnung(rs.getString("bezeichnung"));
	    						 				 as.setAusschreibungstext(rs.getString("ausschreibungstext"));
	    						 				 as.setBewerbungsfrist(rs.getDate("bewerbungsfrist"));
	    						 				 
	    						 				return as;
	    						 			 }
	    						 		 } 
	    					 		 			catch (SQLException e5){
	    					 		 			   e5.printStackTrace();
	    					 		 			   return null;
	    					 		 		}
	    				 		 
	    					 		 	return null;
	    				 	 	}
	    			 		   	
	    			  /**
	    			   * Suchen einer Ausschreibung anhand der Bezeichnung
	    			   * @param bezeichnung
	    			   * @return
	   		 		   */
		  			 			 
	    			 		public Vector<Ausschreibung> findByBezeichnung(String bezeichnung){
			  					
	    			 			//DB-Verbindung herstellen
    			 		   		Connection con = DBConnection.connection();
    			 		   		
    			 		   		//Ergebnisvektor vorbereiten
    					 		 Vector<Ausschreibung> result = new Vector<Ausschreibung>();
			  					
    					 		try{
   					 			 //Leeres SQL-Statement (JDBC) anlegen
   					 			 Statement stmt = con.createStatement();
   					 			 
   					 			 //Statement ausfuellen und als Query an die DB schicken
					 			 ResultSet rs = stmt.executeQuery("SELECT id, bezeichnung, ausschreibungstext, bewerbungsfrist FROM ausschreibungen "
					 					 + "WHERE bezeichnung=" + bezeichnung + " ORDER BY bezeichnung");
					 			 
					 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein Ausschreibung-Objekt
					 			 //erstellt
					 			  while (rs.next()){
					 				  Ausschreibung as = new Ausschreibung();
					 				  as.setId(rs.getInt("id"));
					 				  as.setBezeichnung(rs.getString("bezeichnung"));
					 				  as.setAusschreibungstext(rs.getString("ausschreibungstext"));
					 				  as.setBewerbungsfrist(rs.getDate("bewerbungsfrist"));
					 				  
					 				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
					 				  result.addElement(as);
					 			  }
					 		   }
    					 			catch (SQLException e6){
    					 				e6.printStackTrace();
					 		 }
					 		 
    					 		//Ergebnisvektor zurueckgeben
    					 		return result;
					 				  
					 			  }
		
	    			 		
	    			 		/**
	    			 		 * Suchen einer Ausschreibung durch eine Bewerbungsfrist
	    			 		 * @param bewerbungsfrist
	    			 		 * @return
	    			 		 */
	    			 		
	    			 			public Vector <Ausschreibung>findByBewerbungsfrist(date bewerbungsfrist){
	    			 		
	    			 				//DB-Verbindung herstellen
	    			 		   		Connection con = DBConnection.connection();
	    			 		   		
	    			 		   		//Ergebnisvektor vorbereiten
	    					 		 Vector<Ausschreibung> result = new Vector<Ausschreibung>();
				  					
	    					 		try{
	   					 			 //Leeres SQL-Statement (JDBC) anlegen
	   					 			 Statement stmt = con.createStatement();
	   					 			 
	   					 			 //Statement ausfuellen und als Query an die DB schicken
						 			 ResultSet rs = stmt.executeQuery("SELECT id, bezeichnung, ausschreibungstext, bewerbungsfrist FROM ausschreibungen "
						 					 + "WHERE bewerbungsfrist=" + bewerbungsfrist + " ORDER BY bewerbungsfrist");
						 			 
						 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein Ausschreibung-Objekt
						 			 //erstellt
						 			  while (rs.next()){
						 				  Ausschreibung as = new Ausschreibung();
						 				  as.setId(rs.getInt("id"));
						 				  as.setBezeichnung(rs.getString("bezeichnung"));
						 				  as.setAusschreibungstext(rs.getString("ausschreibungstext"));
						 				  as.setBewerbungsfrist(rs.getDate("bewerbungsfrist"));
						 				  
						 				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
						 				  result.addElement(as);
						 			  }
						 		   }
	    					 			catch (SQLException e7){
	    					 				e7.printStackTrace();
						 		 }
						 		 
	    					 		//Ergebnisvektor zurueckgeben
	    					 		return result;
						 				  
						 			  }
	    			 			
	    			 	
	    			 	/**
	    			 	 * Auslesen einer Ausschreibung durch den Ausschreibungstext		
	    			 	 * @param ausschreibungstext
	    			 	 * @return
	    			 	 */
	    			 	
	    			 	public Vector<Ausschreibung> findByAusschreibungstext(String ausschreibungstext){
	  
	    			 		//DB-Verbindung herstellen
			 		   		Connection con = DBConnection.connection();
			 		   		
			 		   		//Ergebnisvektor vorbereiten
					 		 Vector<Ausschreibung> result = new Vector<Ausschreibung>();
		  					
					 		try{
					 			 //Leeres SQL-Statement (JDBC) anlegen
					 			 Statement stmt = con.createStatement();
					 			 
					 			 //Statement ausfuellen und als Query an die DB schicken
				 			 ResultSet rs = stmt.executeQuery("SELECT id, bezeichnung, ausschreibungstext, bewerbungsfrist FROM ausschreibungen "
				 					 + "WHERE ausschreibungstext=" + ausschreibungstext + " ORDER BY ausschreibungstext");
				 			 
				 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein Ausschreibung-Objekt
				 			 //erstellt
				 			  while (rs.next()){
				 				  Ausschreibung as = new Ausschreibung();
				 				  as.setId(rs.getInt("id"));
				 				  as.setBezeichnung(rs.getString("bezeichnung"));
				 				  as.setAusschreibungstext(rs.getString("ausschreibungstext"));
				 				  as.setBewerbungsfrist(rs.getDate("bewerbungsfrist"));
				 				  
				 				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 				  result.addElement(as);
				 			  }
				 		   }
					 			catch (SQLException e8){
					 				e8.printStackTrace();
				 		 }
				 		 
					 		//Ergebnisvektor zurueckgeben
					 		return result;
				 				  
				 			  }
	    			 	
	    			 	/**
	    			 	 * Das Erhalten einer Ausschreibung von einer Person
	    			 	 * @param p
	    			 	 * @return
	    			 	 */
	    			 	public Vector<Ausschreibung> getByPerson(Person p){
	    			 		
	    			 	}	 	 
	    			 	
	
	    			 	}
	
	
}
