import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class Header extends JPanel {

	private JTextField userName;
	private Resources resources;

	public Header(Resources resource) {

		//? Set: Gloabl Variables

		resources = resource;

		//? Set: Local Font

		Font font = getFont().deriveFont((float) 30);

		//? Create: JTextField Element (Header Message)

		userName = new JTextField();
		userName.setEditable(false);
		userName.setOpaque(false);
		userName.setBorder(new EmptyBorder(40, 20, 20, 20));
		userName.setText("Welcome, Cromega");
		userName.setHorizontalAlignment(JTextField.CENTER);
		userName.setFont(font);
		userName.setForeground(resources.workThird);

		//? Set: JPanel Parameters

		this.setPreferredSize(resources.horizontalMarginDimension);
		this.setOpaque(false);

		this.add(userName);
	}
}
