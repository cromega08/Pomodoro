import org.jdom2.JDOMException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Resources {
	
	public HandlerTools handlerTools;
	public ProjectDimensions projectDimensions;
	public Colors colors;
	public Borders borders;
	public Icons icons;

	public Resources() throws IOException, JDOMException {

		//? Set: Handlers and Tools
		
		handlerTools = new HandlerTools();

		//? Set: Dimensions

		projectDimensions = new ProjectDimensions();
		
		//? Set: Colors
	
		colors = new Colors();
		
		//? Set: Borders

		borders = new Borders();

		//? Set: Icons
		
		icons = new Icons();
	}
	
	public class HandlerTools {
		
		public String user;
		public FileHandler fileHandler;
		public Toolkit tools;
		
		HandlerTools() throws IOException, JDOMException {
			user = setUserName();
			fileHandler = new FileHandler();
			tools = Toolkit.getDefaultToolkit();
		}

		private String setUserName() {
			String userName = System.getProperty("user.name");
			String[] splitUserName = userName.split("[^a-zA-Z0-9]");

			return splitUserName[0].substring(0, 1).toUpperCase() + splitUserName[0].substring(1);
		}
	}
	
	public class ProjectDimensions {

		public int screenHeight, screenWidth, windowHeight, windowWidth;
		public Dimension screenDimensions, horizontalMarginDimension, verticalMarginDimension, minimumDimension;
		
		ProjectDimensions() {
			screenDimensions = handlerTools.tools.getScreenSize();
			screenHeight = (int) screenDimensions.getHeight();
			screenWidth = (int) screenDimensions.getWidth();
			windowHeight = screenHeight;
			windowWidth = screenWidth/2;
			horizontalMarginDimension = new Dimension(1, windowHeight/10);
			verticalMarginDimension = new Dimension(windowWidth/25, 1);
			minimumDimension = new Dimension(screenWidth/3, (screenHeight/2) + 50);
		}
	}
	
	public class Colors {

		public boolean restPalette;
		public Color workMain, workSecond, workThird, workContrast, restMain, restSecond, restThird, restContrast;
		
		Colors() {
			restPalette = false;

			//* Working Palette

			workMain = new Color(0x26001B);
			workSecond = new Color(0x810034);
			workThird = new Color(0xFF005C);
			workContrast = new Color(0xFFF600);

			//* Break and Resting Palette

			restMain = new Color(0x371B58);
			restSecond = new Color(0x4C3575);
			restThird = new Color(0x5B4B8A);
			restContrast = new Color(0x7858A6);
		}
	}
	
	public class Borders {

		public Border emptyBorder, paddingBorder, lineWorkBorder, lineRestBorder, underlineWorkBorder, underlineRestBorder;
		
		Borders() {
			emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
			paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			lineWorkBorder = BorderFactory.createLineBorder(colors.workSecond, 3);
			lineRestBorder = BorderFactory.createLineBorder(colors.restSecond, 3);
			underlineWorkBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, colors.workSecond);
			underlineRestBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, colors.restSecond);
		}
	}
	
	public class Icons {

		public ImageIcon helpIcon, settingsIcon, logoIcon, checkIcon, uncheckIcon;

		Icons() throws IOException {
			File helpFile = new File("res/imgs/help.png");
			Image helpImage = ImageIO.read(helpFile).getScaledInstance(projectDimensions.windowHeight/42, projectDimensions.windowHeight/42, Image.SCALE_DEFAULT);
			helpIcon = new ImageIcon(helpImage, "Help Icon Image");

			File settingsFile = new File("res/imgs/settings.png");
			Image settingsImage = ImageIO.read(settingsFile).getScaledInstance(projectDimensions.windowHeight/42, projectDimensions.windowHeight/42, Image.SCALE_DEFAULT);
			settingsIcon = new ImageIcon(settingsImage, "Settings Icon Image");

			File logoFile = new File("res/imgs/cromega.png");
			Image logoImage = ImageIO.read(logoFile).getScaledInstance(projectDimensions.windowHeight/42, projectDimensions.windowHeight/42, Image.SCALE_DEFAULT);
			logoIcon = new ImageIcon(logoImage, "Cromega Logo");

			File checkFile = new File("res/imgs/check.png");
			Image checkImage = ImageIO.read(checkFile).getScaledInstance(30, 30, Image.SCALE_DEFAULT);
			checkIcon = new ImageIcon(checkImage, "CheckBox Check Icon");

			File uncheckFile = new File("res/imgs/uncheck.png");
			Image uncheckImage = ImageIO.read(uncheckFile).getScaledInstance(30, 30, Image.SCALE_DEFAULT);
			uncheckIcon = new ImageIcon(uncheckImage, "Checkbox Uncheck Icon");
		}
	}
}
