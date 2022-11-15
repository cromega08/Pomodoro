import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class FileHandler {

	private final Writer settingsWriter;
	private final XMLOutputter xmlOutput;
	private final Document settingsDoc;
	private final Element settingsElement;
	private final FileChannel settingsChannel;


	FileHandler() throws IOException, JDOMException {

		Path settingsPath = Paths.get("res/settings/settings.xml");
		File settingsFile = settingsPath.toFile();
		boolean settingsExist = settingsFile.createNewFile();
		settingsChannel = FileChannel.open(settingsPath, StandardOpenOption.WRITE);
		settingsWriter = Channels.newWriter(
				settingsChannel,
				StandardCharsets.UTF_8);
		xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());

		if (settingsExist) createSettings();

		SAXBuilder saxBuilder = new SAXBuilder();
		settingsDoc = saxBuilder.build(settingsFile);
		settingsElement = settingsDoc.getRootElement();
	}

	private void createSettings() throws IOException {

		Element settingsElement = new Element("settings"),
				timeElement = new Element("time"),
				timeSessions = new Element("time-sessions"),
				timeWorkElement = new Element("time-work"),
				timeBreakElement = new Element("time-break"),
				timeRestElement = new Element("time-rest"),
				timeIntervalElement = new Element("time-interval"),
				autoStartElement = new Element("auto-start"),
				startWorkElement = new Element("start-work"),
				startBreakElement = new Element("start-break"),
				startRestElement = new Element("start-rest");
		Document preSettingsDoc = new Document(settingsElement);

		timeSessions.setText("8");
		timeWorkElement.setText("25");
		timeBreakElement.setText("5");
		timeRestElement.setText("15");
		timeIntervalElement.setText("3");

		timeElement.addContent(timeSessions);
		timeElement.addContent(timeWorkElement);
		timeElement.addContent(timeBreakElement);
		timeElement.addContent(timeRestElement);
		timeElement.addContent(timeIntervalElement);

		startWorkElement.setText("true");
		startBreakElement.setText("true");
		startRestElement.setText("true");

		autoStartElement.addContent(startWorkElement);
		autoStartElement.addContent(startBreakElement);
		autoStartElement.addContent(startRestElement);
		
		settingsElement.addContent(timeElement);
		settingsElement.addContent(autoStartElement);

		xmlOutput.output(preSettingsDoc, settingsWriter);
	}

	public String[] getTimeValues() {
		Element timeGroupElement = settingsElement.getChild("time");
		List<Element> timeChildren = timeGroupElement.getChildren();
		int timeChildrenSize = timeChildren.size();
		String[] values = new String[timeChildrenSize];

		for (int index = 0; index < timeChildrenSize; ++index) {
			Element timeChild = timeChildren.get(index);
			values[index] = timeChild.getText();
		}

		return values;
	}

	public String[] getStartValues() {
		Element startGroupElement = settingsElement.getChild("auto-start");
		List<Element> startChildren = startGroupElement.getChildren();
		int startChildrenSize = startChildren.size();
		String[] values = new String[startChildrenSize];

		for (int index = 0; index < startChildrenSize; ++index) {
			Element startChild = startChildren.get(index);
			values[index] = startChild.getText();
		}

		return values;
	}

	public String getElementValue(String element) {
		Element groupElement = settingsElement.getChild(element.startsWith("time")? "time":"auto-start"),
				toGetElement = groupElement.getChild(element);

		return toGetElement.getText();
	}

	public void setElementValues(boolean[] newStart, String[] newTexts) throws IOException, JDOMException {

		Element timeElement = settingsElement.getChild("time"),
				autoStartElement = settingsElement.getChild("auto-start");
		List<Element> timeElements = timeElement.getChildren(),
						autoStartElements = autoStartElement.getChildren();

		for (int index = 0; index < autoStartElements.size(); ++index) {
			autoStartElements.get(index).setText(String.valueOf(newStart[index]));
		}

		for (int index = 0; index < timeElements.size(); ++index) {
			timeElements.get(index).setText(newTexts[index]);
		}

		settingsChannel.truncate(0);
		settingsWriter.flush();
		settingsChannel.force(true);
		xmlOutput.output(settingsDoc, settingsWriter);
		settingsWriter.flush();
		settingsChannel.force(true);
	}
}
