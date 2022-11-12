import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class FileHandler {

	FileHandler() throws JDOMException, IOException {

		File inputFile = new File("input.txt");
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(inputFile);

		Element classElement = document.getRootElement();

		System.out.println(classElement);
	}
}
