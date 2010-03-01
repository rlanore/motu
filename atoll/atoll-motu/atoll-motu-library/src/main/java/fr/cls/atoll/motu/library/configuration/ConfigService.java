//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.01 at 08:08:39 AM CET 
//


package fr.cls.atoll.motu.library.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for configService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="configService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="catalog" type="{}catalogService"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="group" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultGroupService" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="veloTemplatePrefix" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="httpBaseRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultLanguage" type="{http://www.w3.org/2001/XMLSchema}string" default="UK" />
 *       &lt;attribute name="keepDataFilesList" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configService", propOrder = {
    "catalog"
})
public class ConfigService {

    @XmlElement(required = true)
    protected CatalogService catalog;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected String description;
    @XmlAttribute
    protected String group;
    @XmlAttribute
    protected Boolean defaultGroupService;
    @XmlAttribute
    protected String veloTemplatePrefix;
    @XmlAttribute
    protected String httpBaseRef;
    @XmlAttribute
    protected String defaultLanguage;
    @XmlAttribute
    protected Boolean keepDataFilesList;

    /**
     * Gets the value of the catalog property.
     * 
     * @return
     *     possible object is
     *     {@link CatalogService }
     *     
     */
    public CatalogService getCatalog() {
        return catalog;
    }

    /**
     * Sets the value of the catalog property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogService }
     *     
     */
    public void setCatalog(CatalogService value) {
        this.catalog = value;
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

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroup(String value) {
        this.group = value;
    }

    /**
     * Gets the value of the defaultGroupService property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDefaultGroupService() {
        if (defaultGroupService == null) {
            return false;
        } else {
            return defaultGroupService;
        }
    }

    /**
     * Sets the value of the defaultGroupService property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDefaultGroupService(Boolean value) {
        this.defaultGroupService = value;
    }

    /**
     * Gets the value of the veloTemplatePrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeloTemplatePrefix() {
        return veloTemplatePrefix;
    }

    /**
     * Sets the value of the veloTemplatePrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeloTemplatePrefix(String value) {
        this.veloTemplatePrefix = value;
    }

    /**
     * Gets the value of the httpBaseRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHttpBaseRef() {
        return httpBaseRef;
    }

    /**
     * Sets the value of the httpBaseRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHttpBaseRef(String value) {
        this.httpBaseRef = value;
    }

    /**
     * Gets the value of the defaultLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultLanguage() {
        if (defaultLanguage == null) {
            return "UK";
        } else {
            return defaultLanguage;
        }
    }

    /**
     * Sets the value of the defaultLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultLanguage(String value) {
        this.defaultLanguage = value;
    }

    /**
     * Gets the value of the keepDataFilesList property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isKeepDataFilesList() {
        if (keepDataFilesList == null) {
            return false;
        } else {
            return keepDataFilesList;
        }
    }

    /**
     * Sets the value of the keepDataFilesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepDataFilesList(Boolean value) {
        this.keepDataFilesList = value;
    }

}
