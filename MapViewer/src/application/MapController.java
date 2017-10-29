package application;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;

public class MapController 
{
	Map map;
	
	@FXML 
	BorderPane bp;
	
	@FXML 
	MenuItem mAxe;
	
	@FXML 
	MenuItem mBoat;
	
	public void initialize() 
	{
		map = new Map(this);
		bp.setCenter(map.getMap());
	}
	
	@FXML 
	public void onClickTips() 
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		String split = "\n";
		String set = "1. Pick the item you want to set by clicking 'Set'.";
		String select = "2. You can change the selected item if you have not set it on the tile.";
		String move = "3. If you want to reset the postion of items, first, click on one item, then click the tile you want to set the item on.";
		String tip1 = "4. If you put axe and boat in the same tile, you will always pick up axe first.";
		String solution1 = "First, click on one tile to set the position of axe, then click the position they used to be, finally, click the tile you want to set boat on.";
		String tip2 = "5. If you picked 'Boat' from 'Set' before you set 'Axe' on a tile:";
		String solution2 = "First, set the boat on a tile, then click the position where axe used to be, finally, click the tile you want to set axe on.";
		String info = set + split + select + split + move + split + tip1 + split + tip2 + split + solution1 + split + solution2;
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText(info);
		alert.show();
	}

	@FXML 
	public void onSetBoat() 
	{
		map.setNewItem(Map.BOAT);
	}
	
	@FXML 
	public void onSetAxe() 
	{
		map.setNewItem(Map.AXE);
	}
	
	public void setDisable(int type) 
	{
		if(type == Map.AXE) 
		{
			mAxe.setDisable(true);
		}
		else if(type == Map.BOAT) 
		{
			mBoat.setDisable(true);
		}
	}
}
