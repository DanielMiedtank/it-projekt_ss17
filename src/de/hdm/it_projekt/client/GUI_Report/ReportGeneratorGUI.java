package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.client.GUI.LoginPanel;
import de.hdm.it_projekt.shared.LoginService;
import de.hdm.it_projekt.shared.LoginServiceAsync;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.*;


/**
 * EntryPoint des ReportGenerators 
 * 
 * @author Daniel
 *
 */
public class ReportGeneratorGUI implements EntryPoint {

	/*	 * Begin Attribute fuer Login
	 */
	protected static LoginInfo loginInfo = null;
	protected static ProjektMarktplatz cpm = null;

	ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

	HorizontalPanel hPanel = null;
	VerticalPanel menuPanel = null;
	VerticalPanel contentPanel = null;

	/**
	 * Methode welche beim Seitenaufruf geladen wird und prueft ob User
	 * eingeloggt ist. Falls ja wird Methode loadMyProjekt() aufgerufen, falls
	 * nicht die Methode loadLogin()
	 */
	public void onModuleLoad() {
		// Check login status using login service
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "reportgenerator.html", new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					pa.findByGoogleId(loginInfo, new LoginCallback());
				} else {
					LoginPanel loginPanel = new LoginPanel(loginInfo);
					loginPanel.run();
				}
			}
		});
	}

	public void loadMyProjekt() {

		/**
		 * Ueberschirft fuer den Rpeort Generator
		 */
		Label ueberschriftLabel = new Label("Report Generator");
		ueberschriftLabel.setStyleName("h1");
		RootPanel.get("menu").add(ueberschriftLabel);

		/**
		 * Definition der Panels
		 */
		hPanel = new HorizontalPanel();
		menuPanel = new VerticalPanel();
		contentPanel = new VerticalPanel();

		hPanel.add(menuPanel);
		hPanel.add(contentPanel);

		RootPanel.get("content").clear();
		RootPanel.get("content").add(hPanel);

		/**
		 * Button Definitionen
		 */
		Button alleAusschreibungenButton = new Button("Zeige Alle Ausschreibungen");
		alleAusschreibungenButton.setStyleName("myprojekt-reportbutton");
		alleAusschreibungenButton.addClickHandler(new AlleAusschreibungenClickhandler());

		Button passendeAusschreibungenButton = new Button("Zeige zu mir passende Ausschreibungen");
		passendeAusschreibungenButton.setStyleName("myprojekt-reportbutton");
		passendeAusschreibungenButton.addClickHandler(new PassendeAusschreibungClickHandler());

		Button bewerbungAufAusschreibungenButton = new Button("Zeige Bewerbungen für meine Ausschreibungen");
		bewerbungAufAusschreibungenButton.setStyleName("myprojekt-reportbutton");
		bewerbungAufAusschreibungenButton.addClickHandler(new BewerbungenZuAusschreibungenClickhandler());

		Button ausschreibungenAufBewerbungButton = new Button("Zeige meine Bewerbungen");
		ausschreibungenAufBewerbungButton.setStyleName("myprojekt-reportbutton");
		ausschreibungenAufBewerbungButton.addClickHandler(new AlleBewerbungenClickhandler());

		Button fanOutButton = new Button("Zeige Fan Out");
		fanOutButton.setStyleName("myprojekt-reportbutton");
		fanOutButton.addClickHandler(new FanOutClickHandler());

		Button fanInButton = new Button("Zeige Fan In");
		fanInButton.setStyleName("myprojekt-reportbutton");
		fanInButton.addClickHandler(new FanInClickHandler());

		Button zugehoerigeProjekteButton = new Button("Zeige Projektverflechtungen meiner Bewerber");
		zugehoerigeProjekteButton.setStyleName("myprojekt-reportbutton");
		zugehoerigeProjekteButton.addClickHandler(new ProjektverflechtungenClickHandler());

		Button pmGUIButton = new Button("Zum Projektmarktplatz");
		pmGUIButton.setStyleName("myprojekt-switchreportbutton");
		pmGUIButton.addClickHandler(new pmClickHandler());

		/**
		 * Hinzufuegen der Buttons zum vertikel Panel
		 */
		menuPanel.add(alleAusschreibungenButton);
		menuPanel.add(passendeAusschreibungenButton);
		menuPanel.add(bewerbungAufAusschreibungenButton);
		menuPanel.add(ausschreibungenAufBewerbungButton);
		menuPanel.add(fanOutButton);
		menuPanel.add(fanInButton);
		menuPanel.add(zugehoerigeProjekteButton);
		menuPanel.add(pmGUIButton);

		if (cpm == null) {
			contentPanel.add(new ReportMarktuebersicht(this));
			menuPanel.setVisible(false);
		}
	}

	private class AlleAusschreibungenClickhandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			contentPanel.clear();
			contentPanel.add(new AlleAusschreibungenHTML(cpm));

		}
	}

	private class AlleBewerbungenClickhandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			contentPanel.clear();
			contentPanel.add(new AlleBewerbungenHTML(loginInfo.getCurrentUser()));

		}
	}

	private class PassendeAusschreibungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (loginInfo.getCurrentUser().getPartnerprofilId() == 0) {
				Window.alert("Sie besitzen noch kein Partnerprofil.");
			} else {
				contentPanel.clear();
				contentPanel.add(new PassendeAusschreibungenHTML(loginInfo.getCurrentUser()));

			}
		}
	}

	private class BewerbungenZuAusschreibungenClickhandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			contentPanel.clear();
			contentPanel.add(new BewerbungenZuAusschreibungenHTML(loginInfo.getCurrentUser()));

		}
	}

	private class ProjektverflechtungenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			contentPanel.clear();
			contentPanel.add(new ProjektverfelchtungenHTML(loginInfo.getCurrentUser(), cpm));

		}
	}

	private class FanOutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			contentPanel.clear();
			contentPanel.add(new FanOutHTML(loginInfo.getCurrentUser()));
		}
	}
	
	private class FanInClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			contentPanel.clear();
			contentPanel.add(new FanInHTML(loginInfo.getCurrentUser()));
		}
	}

	private class LoginCallback implements AsyncCallback<Organisationseinheit> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Es ist ein Fehler aufgetreten.");

		}

		@Override
		public void onSuccess(Organisationseinheit result) {

			if (result != null) {
				loginInfo.setCurrentUser(result);
				loadMyProjekt();
			} else {
				Window.alert("Sie besitzen noch kein Benutzerkonto bei uns, bitte legen Sie erst eins an.");
				Window.Location.assign(GWT.getHostPageBaseURL() + "projektmarktplatz.html");
			}

		}

	}

	private class pmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.Location.assign(GWT.getHostPageBaseURL() + "projektmarktplatz.html");
		}

	}

}
