import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputField extends JTextField implements KeyListener, PaletteSetters {

    private final Resources resources;

    InputField(Resources resourcesInstance, String element, Font font) {

        resources = resourcesInstance;

        this.setBorder(resources.borders.underlineWorkBorder);
        this.setCaretColor(resources.colors.workThird);
        this.setText(resources.handlerTools.fileHandler.getElementValue(element));
        this.setFont(font);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setForeground(resources.colors.workThird);

        setBasicProperties();
    }

    InputField(Resources resourcesInstance, int indexValue, String... pairElements) {

        resources = resourcesInstance;

        this.setBorder(resources.colors.restPalette? resources.borders.underlineRestBorder :resources.borders.underlineWorkBorder);
        this.setCaretColor(resources.colors.restPalette? resources.colors.restThird:resources.colors.workThird);
        this.setText(String.format("%d", Integer.parseInt(pairElements[indexValue])));
        this.setHorizontalAlignment(JTextField.TRAILING);
        this.setForeground(resources.colors.restPalette? resources.colors.restContrast:resources.colors.workContrast);

        setBasicProperties();
    }

    private void setBasicProperties() {
        this.setOpaque(false);
        this.setColumns(2);
        this.addKeyListener(this);
    }

    public void enableInput() {this.setEditable(true);}

    public void disableInput() {this.setEditable(false);}

    public void setWorkPalette() {
        this.setBorder(resources.borders.underlineWorkBorder);
        this.setCaretColor(resources.colors.workThird);
        this.setForeground(resources.colors.workThird);
    }

    public void setRestPalette() {
        this.setBorder(resources.borders.underlineRestBorder);
        this.setCaretColor(resources.colors.restThird);
        this.setForeground(resources.colors.restThird);
    }

    private boolean checkDelete(char keyChar) {
        return (keyChar == KeyEvent.VK_DELETE || keyChar == KeyEvent.VK_BACK_SPACE);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

        char keyChar = keyEvent.getKeyChar();
        boolean isDelete = checkDelete(keyChar);
        Pattern pattern = Pattern.compile("[^0-9]+", Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(String.format("%s", keyChar));

        if ((this.getText().length() >= 2 && !isDelete) || matcher.find()) {
            if (!isDelete) resources.handlerTools.tools.beep();
            keyEvent.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}
