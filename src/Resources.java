import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class Resources {

	public Toolkit tools;
	public int screenHeight, screenWidth, windowHeight, windowWidth;
	public Dimension screenDimensions, horizontalMarginDimension, verticalMarginDimension, minimumDimension;
	public Color workMain, workSecond, workThird, workContrast, restMain, restSecond, restThird, restContrast;
	public Border emptyBorder, paddingBorder,
			lineWorkLightBorder, lineWorkDarkBorder, underlineWorkLightBorder, underlineWorkDarkBorder,
			lineRestLightBorder, lineRestDarkBorder, underlineRestLightBorder, underlineRestDarkBorder,
			inputWorkLightBorder, inputWorkDarkBorder, inputRestLightBorder, inputRestDarkBorder;
	public ImageIcon helpIcon, settingsIcon, logoIcon, checkIcon, uncheckIcon;

	public Resources() throws IOException {

		//? Set: Generic Tools

		tools = Toolkit.getDefaultToolkit();

		//? Set: Dimensions

		screenDimensions = tools.getScreenSize();
		screenHeight = (int) screenDimensions.getHeight();
		screenWidth = (int) screenDimensions.getWidth();
		windowHeight = screenHeight;
		windowWidth = screenWidth/2;
		horizontalMarginDimension = new Dimension(1, windowHeight/10);
		verticalMarginDimension = new Dimension(windowWidth/25, 1);
		minimumDimension = new Dimension(screenWidth/3, (screenHeight/2) + 50);
		
		//? Set: Colors

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

		//? Set: Borders

		emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		lineWorkLightBorder = BorderFactory.createLineBorder(workContrast, 4);
		lineWorkDarkBorder = BorderFactory.createLineBorder(workSecond, 3);
		underlineWorkLightBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, workContrast);
		underlineWorkDarkBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, workSecond);
		lineRestLightBorder = BorderFactory.createLineBorder(restContrast, 4);
		lineRestDarkBorder = BorderFactory.createLineBorder(restSecond, 3);
		underlineRestLightBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, restContrast);
		underlineRestDarkBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, restSecond);
		inputWorkLightBorder = BorderFactory.createCompoundBorder(underlineWorkLightBorder, paddingBorder);
		inputWorkDarkBorder = BorderFactory.createCompoundBorder(underlineWorkDarkBorder, paddingBorder);
		inputRestLightBorder = BorderFactory.createCompoundBorder(underlineRestLightBorder, paddingBorder);
		inputRestDarkBorder = BorderFactory.createCompoundBorder(underlineRestDarkBorder, paddingBorder);

		//? Set: Icons

		File helpFile = new File("res/imgs/help.png");
		Image helpImage = ImageIO.read(helpFile).getScaledInstance(windowHeight/42, windowHeight/42, Image.SCALE_DEFAULT);
		helpIcon = new ImageIcon(helpImage, "Help Icon Image");

		File settingsFile = new File("res/imgs/settings.png");
		Image settingsImage = ImageIO.read(settingsFile).getScaledInstance(windowHeight/42, windowHeight/42, Image.SCALE_DEFAULT);
		settingsIcon = new ImageIcon(settingsImage, "Settings Icon Image");

		File logoFile = new File("res/imgs/cromega.png");
		Image logoImage = ImageIO.read(logoFile).getScaledInstance(windowHeight/42, windowHeight/42, Image.SCALE_DEFAULT);
		logoIcon = new ImageIcon(logoImage, "Cromega Logo");

		File checkFile = new File("res/imgs/check.png");
		Image checkImage = ImageIO.read(checkFile).getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		checkIcon = new ImageIcon(checkImage, "CheckBox Check Icon");

		File uncheckFile = new File("res/imgs/uncheck.png");
		Image uncheckImage = ImageIO.read(uncheckFile).getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		uncheckIcon = new ImageIcon(uncheckImage, "Checkbox Uncheck Icon");
	}
}
