package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

public class BewerbungForm extends Showcase {

	Bewerbung bwToDisplay = null;
	ProjektTreeViewModel ptvm = null;

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	Label formTitel = new Label();
	DateBox erstellDb = new DateBox();
	TextArea textTb = new TextArea();
	Label bewerberLb = new Label("nicht angelegt");

	public BewerbungForm() {

		formTitel.setText("Bewerbung");
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

		form.setWidget(1, 0, new Label("Bewerber"));
		form.setWidget(1, 1, bewerberLb);

		form.setWidget(2, 0, new Label("Bewerbungstext"));
		form.setWidget(2, 1, textTb);
		textTb.setStyleName("myproject-textfield");
		textTb.setEnabled(false);

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		this.add(buttonsPanel);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);
		buttonsPanel.addStyleName("myprojekt-buttonspanel");

	}

	void setSelected(Bewerbung bw) {

		if (bw != null) {
			this.bwToDisplay = bw;
			erstellDb.setValue(bwToDisplay.getErstelldatum());
			textTb.setText(bwToDisplay.getBewerbungstext());

			pa.getBewerberFor(bwToDisplay, new AsyncCallback<Organisationseinheit>() {

				@Override
				public void onSuccess(Organisationseinheit result) {
					if (result != null) {
						if (result instanceof Person) {
							Person p = (Person) result;
							bewerberLb.setText(p.getName() + ", " + p.getVorname());
						} else {
							bewerberLb.setText(result.getName());
						}
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});

		} else {
			erstellDb.setValue(null);
			textTb.setText("");
			bewerberLb.setText("");
		}
	}

	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

		}

	}
}
