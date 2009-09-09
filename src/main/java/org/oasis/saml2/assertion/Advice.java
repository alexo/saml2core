//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.08 at 09:19:19 AM EST 
//


package org.oasis.saml2.assertion;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for AdviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AssertionIDRef"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AssertionURIRef"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Assertion"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}EncryptedAssertion"/>
 *         &lt;any processContents='lax' namespace='##other'/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdviceType", propOrder = {
    "assertionIDRevesAndAssertionURIRevesAndAssertions"
})
@XmlRootElement(name = "Advice")
public class Advice {

    @XmlElementRefs({
        @XmlElementRef(name = "AssertionIDRef", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", type = JAXBElement.class),
        @XmlElementRef(name = "EncryptedAssertion", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", type = JAXBElement.class),
        @XmlElementRef(name = "AssertionURIRef", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", type = JAXBElement.class),
        @XmlElementRef(name = "Assertion", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", type = Assertion.class)
    })
    @XmlAnyElement
    protected List<Object> assertionIDRevesAndAssertionURIRevesAndAssertions;

    /**
     * Gets the value of the assertionIDRevesAndAssertionURIRevesAndAssertions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assertionIDRevesAndAssertionURIRevesAndAssertions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssertionIDRevesAndAssertionURIRevesAndAssertions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link EncryptedElementType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link Assertion }
     * 
     * 
     */
    public List<Object> getAssertionIDRevesAndAssertionURIRevesAndAssertions() {
        if (assertionIDRevesAndAssertionURIRevesAndAssertions == null) {
            assertionIDRevesAndAssertionURIRevesAndAssertions = new ArrayList<Object>();
        }
        return this.assertionIDRevesAndAssertionURIRevesAndAssertions;
    }

}