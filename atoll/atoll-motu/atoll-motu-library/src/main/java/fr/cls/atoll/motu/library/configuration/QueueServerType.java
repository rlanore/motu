//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.01 at 08:08:39 AM CET 
//


package fr.cls.atoll.motu.library.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Queue Server Settings
 * 
 * <p>Java class for queueServerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queueServerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queues" type="{}queueType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="defaultPriority" default="2">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *             &lt;minInclusive value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="maxPoolAnonymous" default="10">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *             &lt;minInclusive value="-1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="maxPoolAuth" default="1">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *             &lt;minInclusive value="-1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queueServerType", propOrder = {
    "queues"
})
public class QueueServerType {

    @XmlElement(required = true)
    protected List<QueueType> queues;
    @XmlAttribute
    protected Short defaultPriority;
    @XmlAttribute
    protected Short maxPoolAnonymous;
    @XmlAttribute
    protected Short maxPoolAuth;

    /**
     * Gets the value of the queues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the queues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueueType }
     * 
     * 
     */
    public List<QueueType> getQueues() {
        if (queues == null) {
            queues = new ArrayList<QueueType>();
        }
        return this.queues;
    }

    /**
     * Gets the value of the defaultPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getDefaultPriority() {
        if (defaultPriority == null) {
            return ((short) 2);
        } else {
            return defaultPriority;
        }
    }

    /**
     * Sets the value of the defaultPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setDefaultPriority(Short value) {
        this.defaultPriority = value;
    }

    /**
     * Gets the value of the maxPoolAnonymous property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getMaxPoolAnonymous() {
        if (maxPoolAnonymous == null) {
            return ((short) 10);
        } else {
            return maxPoolAnonymous;
        }
    }

    /**
     * Sets the value of the maxPoolAnonymous property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setMaxPoolAnonymous(Short value) {
        this.maxPoolAnonymous = value;
    }

    /**
     * Gets the value of the maxPoolAuth property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getMaxPoolAuth() {
        if (maxPoolAuth == null) {
            return ((short) 1);
        } else {
            return maxPoolAuth;
        }
    }

    /**
     * Sets the value of the maxPoolAuth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setMaxPoolAuth(Short value) {
        this.maxPoolAuth = value;
    }

}
