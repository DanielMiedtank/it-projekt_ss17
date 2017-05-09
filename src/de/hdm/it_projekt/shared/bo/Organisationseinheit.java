package de.hdm.it_projekt.shared.bo;

/**
 * 
 * @author Daniel Miedtank
 *
 */
public abstract class Organisationseinheit extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Name der Organisationseinheit
	 */
	private String name;
	
	/**
	 * E-Mail Adresse der Organisationseinheit
	 */
	private String email;

	/**
	 * R�ckgabe des Namens der Organisationseinheit
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzen des Namens der Organisationseinheit
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * R�ckgabe der E-Mail Adresse
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setzen der E-Mail Adresse
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
