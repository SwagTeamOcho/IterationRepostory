
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.ComboPopup;

public class XComboBox extends JComboBox<String> {
	
	private static final long serialVersionUID = 83;
	private ListSelectionListener listener;
	private Node hovered;
	private EndUserGUI gui;
	private Map map;

	public XComboBox(EndUserGUI gui) {
		uninstall();
		install();
		this.gui = gui;
	}
	
	public void setMap(Map map){
		this.map = map;
	}

	@Override
	public void updateUI() {
		uninstall();
		super.updateUI();
		install();
	}

	private void uninstall() {
		if (listener == null) return;
		getPopupList().removeListSelectionListener(listener);
		listener = null;
	}


	protected void install() {
		listener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) return;

				@SuppressWarnings("rawtypes")
				JList list = getPopupList();
				hovered = getNodeByName(String.valueOf(list.getSelectedValue()), map.getNodes());
				if (hovered != null){
					//System.out.println("--> " + hovered.getX() + "---" + hovered.getY());
					System.out.println(getPopupName());
					
					if(getPopupName().equals("Start")){
						gui.setStartClicked(true);
						gui.setStartNode(hovered);
						System.out.println("START SELECTED");
					}
					else if(getPopupName().equals("End")){
						gui.setEndClicked(true);
						gui.setEndNode(hovered);
						System.out.println("END SELECTED");
					}
				}
			
	}

			private Node getNodeByName(String name, LinkedList<Node> nodes) {
				for(int i = 0; i < nodes.size(); i++){
					if(nodes.get(i).getName().equals(name)){
						return nodes.get(i);
					}
				}
				return null;
			}
		};
		getPopupList().addListSelectionListener(listener);
	}

	@SuppressWarnings("rawtypes")
	private JList getPopupList() {
		ComboPopup popup = (ComboPopup) getUI().getAccessibleChild(this, 0);
		return popup.getList();

	}

	@SuppressWarnings("rawtypes")
	private String getPopupName() {
		JComboBox jcb = (JComboBox) getUI().getAccessibleChild(this, 0).getAccessibleContext().getAccessibleParent();
		return jcb.getName();
	}
}