import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Header extends JPanel implements PaletteSetters {

	private final Title userName;
	private final Resources resources;

	public Header(Resources resourcesInstance) {

		//? Set: Global Variables

		resources = resourcesInstance;

		//? Create: JTextField Element (Header Message)

		userName = new Title(resourcesInstance.handlerTools.user);

		//? Set: JPanel Parameters

		this.setPreferredSize(resources.projectDimensions.horizontalMarginDimension);
		this.setOpaque(false);

		this.add(userName);
	}

	@Override
	public void setWorkPalette() {
		userName.setForeground(resources.colors.workThird);
	}

	@Override
	public void setRestPalette() {
		userName.setForeground(resources.colors.restThird);
	}

	private class Title extends JTextField {

		Title(String name) {

			this.setEditable(false);
			this.setOpaque(false);
			this.setBorder(new EmptyBorder(40, 20, 20, 20));
			this.setText(String.format("Welcome, %s", name));
			this.setHorizontalAlignment(JTextField.CENTER);
			this.setFont(getFont().deriveFont((float) 30));
			this.setForeground(resources.colors.workThird);
		}
	}
}
