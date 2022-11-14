import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Header extends JPanel {

	private final Title userName;
	private final Resources resources;

	public Header(Resources resource) {

		//? Set: Global Variables

		resources = resource;

		//? Create: JTextField Element (Header Message)

		userName = new Title(resource.user);

		//? Set: JPanel Parameters

		this.setPreferredSize(resources.horizontalMarginDimension);
		this.setOpaque(false);

		this.add(userName);
	}

	private class Title extends JTextField {

		Title(String name) {

			this.setEditable(false);
			this.setOpaque(false);
			this.setBorder(new EmptyBorder(40, 20, 20, 20));
			this.setText(String.format("Welcome, %s", name));
			this.setHorizontalAlignment(JTextField.CENTER);
			this.setFont(getFont().deriveFont((float) 30));
			this.setForeground(resources.workThird);
		}
	}
}
