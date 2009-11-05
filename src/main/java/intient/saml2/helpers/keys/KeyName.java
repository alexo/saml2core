package intient.saml2.helpers.keys;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Ensures creation of a KeyName is simplified for library consumers, abstracts away JAXBElement requirements.
 * 
 * @author Bradley Beddoes
 */
public class KeyName extends JAXBElement<String> {
	private static final long serialVersionUID = 6032597376102630268L;

	private static final String qName = "ds:KeyName";

	/**
	 * @param value
	 *            The name of this key to be stored in XML
	 */
	public KeyName(String value) {
		super(new QName(qName), String.class, value);
	}
}
