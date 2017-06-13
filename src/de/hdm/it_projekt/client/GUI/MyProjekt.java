package de.hdm.it_projekt.client.GUI;

import java.util.Vector;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.it_projekt.shared.LoginService;
import de.hdm.it_projekt.shared.LoginServiceAsync;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.LoginInfo;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.bo.Team;
import de.hdm.it_projekt.client.ClientsideSettings;

public class MyProjekt implements EntryPoint {

	/**
	 * Begin Attribute fuer Login
	 */
	protected LoginInfo loginInfo = null;
	protected static ProjektMarktplatz cpm = null;
	protected static Organisationseinheit cu = null;

	/* Ende Attribute fuer Login */

	/**
	 * Methode welche beim Seitenaufruf geladen wird und prueft ob User
	 * eingeloggt ist. Falls ja wird Methode loadMyProjekt() aufgerufen, falls
	 * nicht die Methode loadLogin()
	 */
	public void onModuleLoad() {
		// Check login status using login service
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadMyProjekt();
				} else {
					LoginPanel loginPanel = new LoginPanel(loginInfo);
					loginPanel.run();
				}
			}
		});
	}

	/**
	 * Mit der Methode laodMyProjekt() wird der eigentliche Seiteninhalt geladen
	 */
	private void loadMyProjekt() {

		final HorizontalPanel menu = new HorizontalPanel();
		menu.add(new Label("menü ??"));
		RootPanel.get("menu").add(menu);

		final ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

		final HorizontalPanel loginHeader = new HorizontalPanel();

		final Label userInfo = new Label(loginInfo.toString());
		loginHeader.add(userInfo);

		final Button signOutBtn = new Button("Logout");
		signOutBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.assign(loginInfo.getLogoutUrl());
			}
		});

		loginHeader.add(signOutBtn);

		RootPanel.get("userinfo").add(loginHeader);

		if (cu == null) {
			pa.findByGoogleId(loginInfo, new AsyncCallback<Organisationseinheit>() {

				@Override
				public void onSuccess(Organisationseinheit result) {

					Showcase showcase;

					if (result != null) {
						cu = result;

						showcase = new Marktuebersicht();
					} else {

						showcase = new NewOrganisationseinheitForm(loginInfo.getEmailAddress());
					}

					RootPanel.get("content").clear();
					RootPanel.get("content").add(showcase);

				}

				@Override
				public void onFailure(Throwable caught) {

					Window.alert("Es ist ein Datenbankfehler aufgetreten.");
					LoginPanel loginPanel = new LoginPanel(loginInfo);
					loginPanel.run();
				}
			});
		}

	}

}
