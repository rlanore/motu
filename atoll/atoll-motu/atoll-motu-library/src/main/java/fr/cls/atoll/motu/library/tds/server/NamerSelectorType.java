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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NamerSelectorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NamerSelectorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="regExp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="replaceString" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamerSelectorType")
public class NamerSelectorType {

    @XmlAttribute
    protected String regExp;
    @XmlAttribute
    protected String replaceString;

    /**
     * Gets the value of the regExp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegExp() {
        return regExp;
    }

    /**
     * Sets the value of the regExp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegExp(String value) {
        this.regExp = value;
    }

    /**
     * Gets the value of the replaceString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplaceString() {
        return replaceString;
    }

    /**
     * Sets the value of the replaceString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplaceString(String value) {
        this.replaceString = value;
    }

}
