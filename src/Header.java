import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
    <Pomodoro:  An application to track productive time.>
    Copyright (C) <2022>  <Cromega>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
* */

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
