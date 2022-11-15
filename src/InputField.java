import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputField extends JTextField implements KeyListener {

    private final Resources resources;

    InputField(Resources resourcesInstance, String element, Font font) {

        resources = resourcesInstance;

        this.setOpaque(false);
        this.setColumns(2);
        this.setBorder(resources.colors.restPalette? resources.borders.underlineRestBorder :resources.borders.underlineWorkBorder);
        this.setCaretColor(resources.colors.restPalette? resources.colors.restThird:resources.colors.workThird);
        this.setText(resources.handlerTools.fileHandler.getElementValue(element));
        this.setFont(font);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setForeground(resources.colors.restPalette? resources.colors.restThird:resources.colors.workThird);
        this.addKeyListener(this);
    }

    InputField(Resources resourcesInstance, int indexValue, String... pairElements) {

        resources = resourcesInstance;

        this.setOpaque(false);
        this.setColumns(3);
        this.setBorder(resources.colors.restPalette? resources.borders.underlineRestBorder :resources.borders.underlineWorkBorder);
        this.setCaretColor(resources.colors.restPalette? resources.colors.restThird:resources.colors.workThird);
        this.setText(String.format("%d", Integer.parseInt(pairElements[indexValue])));
        this.setHorizontalAlignment(JTextField.TRAILING);
        this.setForeground(resources.colors.restPalette? resources.colors.restContrast:resources.colors.workContrast);
        this.addKeyListener(this);
    }

    private boolean checkDelete(char keyChar) {
        return (keyChar == KeyEvent.VK_DELETE || keyChar == KeyEvent.VK_BACK_SPACE);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

        char keyChar = keyEvent.getKeyChar();
        boolean isDelete = checkDelete(keyChar);
        Pattern pattern = Pattern.compile("[^0-9]", Pattern.UNICODE_CASE);
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
