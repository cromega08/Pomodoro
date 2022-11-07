import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

public class Resources {

	public Toolkit tools;
	public int screenHeight, screenWidth, windowHeight, windowWidth;
	public Dimension screenDimensions, horizontalMarginDimension, verticalMarginDimension;
	public Color workMain, workSecond, workThird, workContrast, restMain, restSecond, restThird, restContrast;
	public EmptyBorder emptyBorder, paddingBorder;
	public ImageIcon helpIcon, settingsIcon, logoIcon;

	public Resources() throws IOException {

		//? Generic Tools

		tools = Toolkit.getDefaultToolkit();

		//? Dimensions

		screenDimensions = tools.getScreenSize();
		screenHeight = (int) screenDimensions.getHeight();
		screenWidth = (int) screenDimensions.getWidth();
		windowHeight = screenHeight;
		windowWidth = screenWidth/2;
		horizontalMarginDimension = new Dimension(1, windowHeight/10);
		verticalMarginDimension = new Dimension(windowWidth/25, 1);

		//? Borders

		emptyBorder = new EmptyBorder(1, 1, 1, 1);
		paddingBorder = new EmptyBorder(10, 10, 10, 10);

		//? Colors

		//* Working Palette

		workMain = new Color(0x26001B);
		workSecond = new Color(0x810034);
		workThird = new Color(0xFF005C);
		workContrast = new Color(0xFFF600);

		//* Resting Palette

		restMain = new Color(0x371B58);
		restSecond = new Color(0x4C3575);
		restThird = new Color(0x5B4B8A);
		restContrast = new Color(0x7858A6);

		//? Icons

		File helpFile = new File("../res/information.png");
		Image helpImage = ImageIO.read(helpFile).getScaledInstance(windowHeight/42, windowHeight/42, Image.SCALE_DEFAULT);

		File settingsFile = new File("../res/settings.png");
		Image settingsImage = ImageIO.read(settingsFile).getScaledInstance(windowHeight/42, windowHeight/42, Image.SCALE_DEFAULT);

		File logoFile = new File("../res/cromega.png");
		Image logoImage = ImageIO.read(logoFile).getScaledInstance(windowHeight/42, windowHeight/42, Image.SCALE_DEFAULT);

		helpIcon = new ImageIcon(helpImage, "Help Icon Image");
		settingsIcon = new ImageIcon(settingsImage, "Settings Icon Image");
		logoIcon = new ImageIcon(logoImage, "Cromega Logo");
	}
}
