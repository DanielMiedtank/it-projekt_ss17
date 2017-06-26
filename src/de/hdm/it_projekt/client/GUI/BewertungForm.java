package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Bewertung;

public class BewertungForm extends Showcase {

	Bewertung bwtToDisplay = null;
	ProjektTreeViewModel ptvm = null;

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	Label formTitel = new Label();
	DateBox erstellDb = new DateBox();
	TextBox textTb = new TextBox();
	DoubleBox wertDb = new DoubleBox();

	public BewertungForm() {

		formTitel.setText("Bewertung");
		formTitel.setStyleName("h1");
		this.add(formTitel);

		Grid form = new Grid(3, 2);
		form.addStyleName("myprojekt-formlabel");
		this.add(form);

		form.setWidget(0, 0, new Label("Erstelldatum"));
		form.setWidget(0, 1, erstellDb);
		erstellDb.setFormat(new DateBox.DefaultFormat(fmt));
		erstellDb.setStyleName("myproject-textfield");
		erstellDb.setEnabled(false);

		form.setWidget(1, 0, new Label("Bewertung"));
		form.setWidget(1, 1, wertDb);
		wertDb.setStyleName("myproject-textfield");

		form.setWidget(2, 0, new Label("Stellungnahme"));
		form.setWidget(2, 1, textTb);
		textTb.setStyleName("myproject-textfield");

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		this.add(buttonsPanel);

		Button changeButton = new Button("Ändern");
		changeButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton.addClickHandler(new ChangeClickHandler());
		buttonsPanel.add(changeButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);

		Button newButton = new Button("Neu");
		newButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		buttonsPanel.add(newButton);
		buttonsPanel.addStyleName("myprojekt-buttonspanel");

	}

	void setSelected(Bewertung bwt) {

		if (bwt != null) {
			this.bwtToDisplay = bwt;
			erstellDb.setValue(bwtToDisplay.getErstelldatum());
			textTb.setText(bwtToDisplay.getStellungnahme());
			wertDb.setValue((double) bwtToDisplay.getWert());
		} else {
			erstellDb.setValue(null);
			textTb.setText("");
			wertDb.setValue(null);
		}
	}

	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}

	private class ChangeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (bwtToDisplay != null) {
				bwtToDisplay.setStellungnahme(textTb.getText());
				bwtToDisplay.setWert(wertDb.getValue().floatValue());

				pa.save(bwtToDisplay, new SaveEigenschaftCallback());
			} else
				Window.alert("Es wurde nichts ausgewählt");
		}
	}

	class SaveEigenschaftCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");

		}

		@Override
		public void onSuccess(Void result) {
			ptvm.updateBewertung(bwtToDisplay);
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (bwtToDisplay != null) {
				pa.delete(bwtToDisplay, new DeleteBewertungCallback(bwtToDisplay, ptvm.getSelectedBewerbung()));
			} else {
				Window.alert("Es wurde nichts ausgewählt.");
			}
		}
	}

	class DeleteBewertungCallback implements AsyncCallback<Void> {

		Bewertung bewertung = null;
		Bewerbung bewerbung = null;

		public DeleteBewertungCallback(Bewertung bwt, Bewerbung bw) {
			bewertung = bwt;
			bewerbung = bw;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			if (bewerbung != null && bewertung != null) {
				setSelected(null);
				ptvm.removeBewertungForBewerbung(bewertung, bewerbung);
			}
		}
	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Bewerbung bw = ptvm.getSelectedBewerbung();
			pa.getBewertungFor(bw, new AsyncCallback<Bewertung>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Es is ein Fehler aufgetreten.");
				}

				@Override
				public void onSuccess(Bewertung result) {
					if (result == null) {
						pa.createBewertungFor(ptvm.getSelectedBewerbung(), wertDb.getValue().floatValue(),
								textTb.getText(), new CreateBewertungCallback(ptvm.getSelectedBewerbung()));
					} else {
						Window.alert("Es existiert schon eine Bewertung.");
					}
				}
			});
		}
	}

	class CreateBewertungCallback implements AsyncCallback<Bewertung> {

		Bewerbung bewerbung = null;

		public CreateBewertungCallback(Bewerbung bw) {
			bewerbung = bw;
		}

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Anlegen fehlgeschlagen");

		}

		@Override
		public void onSuccess(Bewertung bewertung) {
			if (bewertung != null && bewerbung != null) {
				setSelected(bewertung);
				ptvm.addBewertungForBewerbung(bewertung, bewerbung);
			}
		}
	}
}