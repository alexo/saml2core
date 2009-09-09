//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.08 at 09:19:19 AM EST 
//


package org.oasis.saml2.metadata;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SSODescriptorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SSODescriptorType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SAML:2.0:metadata}RoleDescriptorType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}ArtifactResolutionService" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}SingleLogoutService" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}ManageNameIDService" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}NameIDFormat" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SSODescriptorType", propOrder = {
    "artifactResolutionServices",
    "singleLogoutServices",
    "manageNameIDServices",
    "nameIDFormats"
})
@XmlSeeAlso({
    SPSSODescriptor.class,
    IDPSSODescriptor.class
})
public abstract class SSODescriptorType
    extends RoleDescriptorType
{

    @XmlElement(name = "ArtifactResolutionService")
    protected List<IndexedEndpointType> artifactResolutionServices;
    @XmlElement(name = "SingleLogoutService")
    protected List<EndpointType> singleLogoutServices;
    @XmlElement(name = "ManageNameIDService")
    protected List<EndpointType> manageNameIDServices;
    @XmlElement(name = "NameIDFormat")
    @XmlSchemaType(name = "anyURI")
    protected List<String> nameIDFormats;

    /**
     * Gets the value of the artifactResolutionServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the artifactResolutionServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArtifactResolutionServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndexedEndpointType }
     * 
     * 
     */
    public List<IndexedEndpointType> getArtifactResolutionServices() {
        if (artifactResolutionServices == null) {
            artifactResolutionServices = new ArrayList<IndexedEndpointType>();
        }
        return this.artifactResolutionServices;
    }

    /**
     * Gets the value of the singleLogoutServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the singleLogoutServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSingleLogoutServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EndpointType }
     * 
     * 
     */
    public List<EndpointType> getSingleLogoutServices() {
        if (singleLogoutServices == null) {
            singleLogoutServices = new ArrayList<EndpointType>();
        }
        return this.singleLogoutServices;
    }

    /**
     * Gets the value of the manageNameIDServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the manageNameIDServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManageNameIDServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EndpointType }
     * 
     * 
     */
    public List<EndpointType> getManageNameIDServices() {
        if (manageNameIDServices == null) {
            manageNameIDServices = new ArrayList<EndpointType>();
        }
        return this.manageNameIDServices;
    }

    /**
     * Gets the value of the nameIDFormats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameIDFormats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameIDFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNameIDFormats() {
        if (nameIDFormats == null) {
            nameIDFormats = new ArrayList<String>();
        }
        return this.nameIDFormats;
    }

}
