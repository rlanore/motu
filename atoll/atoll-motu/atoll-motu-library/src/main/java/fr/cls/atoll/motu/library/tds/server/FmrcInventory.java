//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-3268 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.25 at 11:44:51 AM CET 
//


package fr.cls.atoll.motu.library.tds.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fmrcInventory element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="fmrcInventory">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;attribute name="fmrcDefinition" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="location" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="olderThan" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "fmrcInventory")
public class FmrcInventory {

    @XmlAttribute(required = true)
    protected String fmrcDefinition;
    @XmlAttribute(required = true)
    protected String location;
    @XmlAttribute
    protected String olderThan;
    @XmlAttribute
    protected String suffix;

    /**
     * Gets the value of the fmrcDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFmrcDefinition() {
        return fmrcDefinition;
    }

    /**
     * Sets the value of the fmrcDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFmrcDefinition(String value) {
        this.fmrcDefinition = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the olderThan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOlderThan() {
        return olderThan;
    }

    /**
     * Sets the value of the olderThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOlderThan(String value) {
        this.olderThan = value;
    }

    /**
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
    }

}
