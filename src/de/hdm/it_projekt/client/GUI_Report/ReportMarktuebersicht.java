package de.hdm.it_projekt.client.GUI_Report;

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.client.GUI.Cell.ProjektMarktplatzCell;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class ReportMarktuebersicht extends ReportShowcase {


	final Label ausgabe = new Label();
	
	ReportGenerator reportgenerator = null;
	
	ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

	
	final ProvidesKey<ProjektMarktplatz> KEY_PROVIDER = new ProvidesKey<ProjektMarktplatz>() {

		@Override
		public Integer getKey(ProjektMarktplatz item) {
			
			return item.getId();			
		}		
	};

	public ReportMarktuebersicht(ReportGenerator rp) {
		
		reportgenerator = rp;

		Cell<ProjektMarktplatz> pmCell = new ProjektMarktplatzCell();
		
		final CellList<ProjektMarktplatz> memberPmCl = new CellList<ProjektMarktplatz>(pmCell, KEY_PROVIDER);
		
		final SingleSelectionModel<ProjektMarktplatz> pmSelectionModel = new SingleSelectionModel<ProjektMarktplatz>(KEY_PROVIDER);
		memberPmCl.setSelectionModel(pmSelectionModel);
		memberPmCl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		pmSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {

				ReportGenerator.cpm = pmSelectionModel.getSelectedObject();
				
				reportgenerator.menuPanel.setVisible(true);
				reportgenerator.contentPanel.clear();
			}			
		});
		
		
		ListDataProvider<ProjektMarktplatz> pmDataProvider = new ListDataProvider<ProjektMarktplatz>();
		pmDataProvider.addDataDisplay(memberPmCl);

		final List<ProjektMarktplatz> pmL = pmDataProvider.getList();
		
		pa.getAlleProjektMarktplaetze(new AsyncCallback<Vector<ProjektMarktplatz>>() {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Fehler beim Abruf der Projekt Marktplätze");
			}

			@Override
			public void onSuccess(Vector<ProjektMarktplatz> result) {
				
				for(ProjektMarktplatz pm : result) {
					pmL.add(pm);
				}
			}
		});
		
		
		ausgabe.setText("Bitte wählen Sie einen Marktplatz aus:");
		ausgabe.setStyleName("h2"); 

		this.add(ausgabe);
		this.add(memberPmCl);

	}
}

