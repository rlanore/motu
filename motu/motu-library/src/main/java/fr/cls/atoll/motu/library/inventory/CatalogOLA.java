//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.01 at 05:10:30 PM CET 
//


package fr.cls.atoll.motu.library.inventory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://purl.org/cls/atoll}resourcesOLA"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "resourcesOLA"
})
@XmlRootElement(name = "catalogOLA")
public class CatalogOLA {

    @XmlElement(required = true)
    protected ResourcesOLA resourcesOLA;
    @XmlAttribute
    protected String name;

    /**
     * Gets the value of the resourcesOLA property.
     * 
     * @return
     *     possible object is
     *     {@link ResourcesOLA }
     *     
     */
    public ResourcesOLA getResourcesOLA() {
        return resourcesOLA;
    }

    /**
     * Sets the value of the resourcesOLA property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourcesOLA }
     *     
     */
    public void setResourcesOLA(ResourcesOLA value) {
        this.resourcesOLA = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}