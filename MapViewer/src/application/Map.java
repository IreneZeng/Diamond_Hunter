package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Map 
{
	MapController mc;
	
	// canvas
	private Canvas canvas;
	private GraphicsContext gc;
	
	// image
	private Image image;
	private Image itemsImage;
	
	private int hand;
	
	// items
	private int[][] items; 
	public static final int AXE = 1;
	public static final int BOAT = 0;
	private int newItem = -1;
	
	// map
	private int[][] map;
	private int numRows;
	private int numCols;
	private final int tileSize = 16;
	private final int numTilesAcross = 20;
	private final int numOfRows = 40;
	private final int numOfCols = 40;
	
	public Map(MapController mc) 
	{
		this.mc = mc;
		
		String path = getClass().getResource("/").getPath() + "../../DiamondHunter/Resources/Tilesets/testtileset.gif";
		image = new Image("file:" + path);
		
		map = loadMap("/Maps/testmap.map");
	
		canvas = new Canvas(map[0].length * tileSize, map.length * tileSize);
		gc = canvas.getGraphicsContext2D();
		
		draw();
	}
	
	// first time to set items
	public void setNewItem(int newItem) 
	{
		this.newItem = newItem;
		hand = newItem;
	}
	
	public Canvas getMap() 
	{
		return canvas;
	}
	
	public int[][] loadMap(String s)
	{
		try 
		{
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			
			map = new int[numRows][numCols];
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) 
			{
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) 
				{
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return map;
	}
	
	public void draw() 
	{	
		hand = -1;
		String path = getClass().getResource("/").getPath() + "../../DiamondHunter/Resources/Sprites/items.gif";
		itemsImage = new Image("file:" + path);
		
		items = new int[2][3];
		items[0][0] = -1;
		items[0][1] = -1;
		items[0][2] = -1;
		items[1][0] = -1;
		items[1][1] = -1;
		items[1][2] = -1;
	
		// draw the whole map
		for(int row = 0; row < numOfRows; row++) 
		{
			for(int col = 0; col < numOfCols; col++) 
			{
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				gc.drawImage(image, c * tileSize, r * tileSize, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
			}
		}
		
		// click on items to set their position
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent event) 
			{
				int x = (int) event.getX() / tileSize;
				int y = (int) event.getY() / tileSize;
				
				int rc = map[y][x];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				String path_items = getClass().getResource("/").getPath() + "../../DiamondHunter/Resources/Maps/";
				String dataX = String.valueOf(x);
				String dataY = String.valueOf(y);
				String split = " ";
				
				String file_axe = path_items + "axe" + ".txt";
				File fileAxe = new File(file_axe);
				
				String file_boat = path_items + "boat" + ".txt";
				File fileBoat = new File(file_boat);
				
				if(hand == -1) 
				{	
					for(int i = 0; i < items.length; i++) 
					{
						if(items[i][1] == x && items[i][2] == y) 
						{	
							hand = items[i][0];
							gc.drawImage(image,c * tileSize, r * tileSize, tileSize, tileSize, x * tileSize, y * tileSize, tileSize, tileSize);
							break;
						}
					}
					// if axe and boat are in the same position
					// always pick up axe first
					if(items[0][1] == items[1][1] && items[0][2] == items[1][2] && items[0][1] != -1 && items[0][2] != -1 && items[1][1] != -1 && items[1][2] != -1)
					{
						gc.drawImage(itemsImage, 0 *  tileSize, tileSize, tileSize, tileSize, x * tileSize, y * tileSize, tileSize, tileSize);
					}
				}
				else
				{
					if(r == 0) 
					{
						int flag = 0;
						
						if(flag == 0) 
						{
							gc.drawImage(itemsImage, hand *  tileSize, tileSize, tileSize, tileSize, x * tileSize, y * tileSize, tileSize, tileSize);
							
							// first time to set items
							if(newItem != -1) 
							{
								if(items == null) 
								{
									items = new int[2][3];
									items[0][0] = -1;
									items[1][0] = -1;
								}
								
								for(int i = 0; i < items.length; i++) 
								{
									if(items[i][0] == -1) 
									{
										items[i][1] = x;
										items[i][2] = y;
					
										if(newItem == AXE)
										{
											try
											{
												items[0][0] = AXE;
												items[0][1] = x;
												items[0][2] = y;
												
												// create the file for axe
												fileAxe.createNewFile();
												
												FileWriter fileWritter = new FileWriter(fileAxe, false);
												BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
														
												String Axe = String.valueOf(items[0][0]) + split + String.valueOf(items[0][1]) + split + String.valueOf(items[0][2]);
														
												bufferWritter.write(Axe);
												bufferWritter.close();
												
											}
											catch(IOException e)
											{
												e.printStackTrace();
											}
										}
										
										if(newItem == BOAT)
										{
											try
											{	
												if(items[0][0] == -1)
												{
													items[0][1] = -1;
													items[0][2] = -1;
												}
												
												items[1][0] = BOAT;
												items[1][1] = x;
												items[1][2] = y;
												
												// create the file for boat
												fileBoat.createNewFile();
												
												FileWriter fileWritter = new FileWriter(fileBoat, false);
												BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
														
												String Boat = String.valueOf(items[1][0]) + split + String.valueOf(items[1][1]) + split + String.valueOf(items[1][2]);
														
												bufferWritter.write(Boat);
												bufferWritter.close();
												
											}
											catch(IOException e)
											{
												e.printStackTrace();
											}
										}
										
										gc.drawImage(itemsImage, items[i][0] * tileSize, tileSize, tileSize, tileSize, x * tileSize, y * tileSize, tileSize, tileSize);		
										break;
									}
								}
								mc.setDisable(newItem);	// you can only add one of each items on the map
								newItem = -1;
							}
							
							//update the position
							for(int j = 0; j < items.length; j++) 
							{
								if(items[j][0] == hand)
								{
									items[j][1] = x;
									items[j][2] = y;
								}
							}
							
							//save new position into file;
							if(hand == 1)
							{
								//axe
								try
								{
									File file = new File(file_axe);
									
									FileWriter fileWritter = new FileWriter(file, false);
									BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
									
									String data = "1" + split + dataX + split + dataY;
									
									bufferWritter.write(data);
									bufferWritter.close();
								}
								catch(IOException e)
								{
									e.printStackTrace();
								}
							}
							else if(hand == 0)
							{
								//boat
								try
								{
									File file = new File(file_boat);
									
									FileWriter fileWritter = new FileWriter(file, false);
									BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
									String data = "0" + split + dataX + split + dataY;
									
									bufferWritter.write(data);
									bufferWritter.close();
								}
								catch(IOException e)
								{
									e.printStackTrace();
								}
							}
							hand = -1;
						}
					}
				}
			}
		});
	}
}