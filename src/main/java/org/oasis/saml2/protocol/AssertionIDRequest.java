//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.04 at 11:10:49 AM EST 
//


package org.oasis.saml2.protocol;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AssertionIDRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssertionIDRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SAML:2.0:protocol}RequestAbstractType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AssertionIDRef" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssertionIDRequestType", propOrder = {
    "assertionIDReves"
})
@XmlRootElement(name = "AssertionIDRequest")
public class AssertionIDRequest
    extends RequestAbstractType
{

    @XmlElement(name = "AssertionIDRef", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected List<String> assertionIDReves;

    /**
     * Gets the value of the assertionIDReves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assertionIDReves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssertionIDReves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAssertionIDReves() {
        if (assertionIDReves == null) {
            assertionIDReves = new ArrayList<String>();
        }
        return this.assertionIDReves;
    }

}
