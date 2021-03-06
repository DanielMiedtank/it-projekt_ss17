/**
 * Die Klasse OEigenschaftForm dient dem Aufbau und der Interaktion mit dem Formular Eigenschften.
 */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.it_projekt.shared.bo.Eigenschaft;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

public class OEigenschaftForm extends Showcase {

	private Partnerprofil selectedPartnerprofil = null;
	private Eigenschaft eToDisplay = null;

	private TextBox nameTb = new TextBox();
	private TextBox wertTb = new TextBox();
	private Label formTitel = new Label();

	public OEigenschaftForm(Partnerprofil pp) {

		this.selectedPartnerprofil = pp;
		formTitel.setText("Eigenschaft");
		formTitel.setStyleName("h1");
		this.add(formTitel);

		Grid form = new Grid(2, 2);
		form.addStyleName("myprojekt-formlabel");
		this.add(form);

		form.setWidget(0, 0, new Label("Name"));
		form.setWidget(0, 1, nameTb);

		form.setWidget(1, 0, new Label("Wert"));
		form.setWidget(1, 1, wertTb);
		nameTb.setStyleName("myproject-textfield");
		wertTb.setStyleName("myproject-textfield"); 

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		this.add(buttonsPanel);

		Button changeButton = new Button("Ändern");
		changeButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton.addClickHandler(new ChangeClickHandler());
		buttonsPanel.add(changeButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);

		Button newButton = new Button("Anlegen");
		newButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		buttonsPanel.add(newButton);

	}

	void setSelected(Eigenschaft e) {

		if (e != null) {
			this.eToDisplay = e;
			nameTb.setText(eToDisplay.getName());
			wertTb.setText(eToDisplay.getWert());
		} else {
			nameTb.setText("");
			wertTb.setText("");
		}
	}

	private class ChangeClickHandler implements ClickHandler {

		/**
		 * Wird beim Klick auf den Ändern Button ausgeführt und ändert die Daten im TreeView und in der Datenbank.
		 * Dafür werden Create-, Set- und Get-Methoden verwendet, sowie AsyncCallbacks erstellt. 
		 */
		@Override
		public void onClick(ClickEvent event) {

			if (eToDisplay != null) {
				eToDisplay.setName(nameTb.getText());
				eToDisplay.setWert(wertTb.getText());

				pa.save(eToDisplay, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Es ist ein Fehler aufgetreten.");

					}

					@Override
					public void onSuccess(Void result) {

						OPartnerprofilForm.eL.remove(eToDisplay);
						OPartnerprofilForm.eL.add(eToDisplay);

					}
				});
			} else
				Window.alert("Es wurde nichts ausgewählt");
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			pa.delete(eToDisplay, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Es ist ein Fehler aufgetreten.");					
				}

				@Override
				public void onSuccess(Void result) {

					OPartnerprofilForm.eL.remove(eToDisplay);
					nameTb.setText("");
					wertTb.setText("");
					eToDisplay = null;
				}
			});
		}
	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			pa.createEigenschaftFor(selectedPartnerprofil, nameTb.getText(), wertTb.getText(),
					new AsyncCallback<Eigenschaft>() {

						@Override
						public void onFailure(Throwable caught) {

							Window.alert("Anlegen fehlgeschlagen");

						}

						@Override
						public void onSuccess(Eigenschaft result) {

							if (result != null) {
								OPartnerprofilForm.eL.add(result);
								
								nameTb.setText("");
								wertTb.setText("");
							}

						}
					});
		}
	}
}
