//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.05 at 02:32:28 PM CET 
//

package fr.cls.atoll.motu.library.misc.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for catalogService complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catalogService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="urlSite" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="casAuthentication" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catalogService")
public class CatalogService {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "urlSite")
    protected String urlSite;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "ncss")
    protected String ncss = "disabled";
    @XmlAttribute(name = "casAuthentication")
    protected Boolean casAuthentication;

    /**
     * Gets the value of the name property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the urlSite property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUrlSite() {
        return urlSite;
    }

    /**
     * Sets the value of the urlSite property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setUrlSite(String value) {
        this.urlSite = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the value of the ncss property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNCSS() {
        return ncss;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Sets the value of the ncss property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNCSS(String value) {
        this.ncss = value;
    }

    /**
     * Gets the value of the casAuthentication property.
     * 
     * @return possible object is {@link Boolean }
     * 
     */
    public boolean getCasAuthentication() {
        if (casAuthentication == null) {
            return false;
        } else {
            return casAuthentication;
        }
    }

    /**
     * Sets the value of the casAuthentication property.
     * 
     * @param value allowed object is {@link Boolean }
     * 
     */
    public void setCasAuthentication(Boolean value) {
        this.casAuthentication = value;
    }

}
