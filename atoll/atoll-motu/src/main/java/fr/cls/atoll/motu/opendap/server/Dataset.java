//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-3268 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.12.21 at 08:38:29 AM CET 
//

package fr.cls.atoll.motu.opendap.server;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for dataset element declaration.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 *  &lt;element name=&quot;dataset&quot;&gt;
 *    &lt;complexType&gt;
 *      &lt;complexContent&gt;
 *        &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *          &lt;sequence&gt;
 *            &lt;choice maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;&gt;
 *              &lt;element ref=&quot;{http://www.unidata.ucar.edu/thredds}service&quot;/&gt;
 *              &lt;element ref=&quot;{http://www.unidata.ucar.edu/thredds}documentation&quot;/&gt;
 *              &lt;element ref=&quot;{http://www.unidata.ucar.edu/thredds}metadata&quot;/&gt;
 *              &lt;element ref=&quot;{http://www.unidata.ucar.edu/thredds}property&quot;/&gt;
 *            &lt;/choice&gt;
 *            &lt;element ref=&quot;{http://www.unidata.ucar.edu/thredds}access&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *            &lt;choice maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;&gt;
 *              &lt;element ref=&quot;{http://www.unidata.ucar.edu/thredds}dataset&quot;/&gt;
 *              &lt;element ref=&quot;{http://www.unidata.ucar.edu/thredds}catalogRef&quot;/&gt;
 *            &lt;/choice&gt;
 *          &lt;/sequence&gt;
 *          &lt;attribute name=&quot;ID&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}ID&quot; /&gt;
 *          &lt;attribute name=&quot;alias&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}IDREF&quot; /&gt;
 *          &lt;attribute name=&quot;authority&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *          &lt;attribute name=&quot;dataType&quot; type=&quot;{http://www.unidata.ucar.edu/thredds}DataType&quot; /&gt;
 *          &lt;attribute name=&quot;name&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *          &lt;attribute name=&quot;serviceName&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *          &lt;attribute name=&quot;urlPath&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *        &lt;/restriction&gt;
 *      &lt;/complexContent&gt;
 *    &lt;/complexType&gt;
 *  &lt;/element&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "serviceOrDocumentationOrMetadata", "access", "datasetOrCatalogRef" })
@XmlRootElement(name = "dataset")
public class Dataset {

    @XmlElements( {
            @XmlElement(name = "property", namespace = "http://www.unidata.ucar.edu/thredds", required = true, type = Property.class),
            @XmlElement(name = "service", namespace = "http://www.unidata.ucar.edu/thredds", required = true, type = Service.class),
            @XmlElement(name = "documentation", namespace = "http://www.unidata.ucar.edu/thredds", required = true, type = Documentation.class),
            @XmlElement(name = "metadata", namespace = "http://www.unidata.ucar.edu/thredds", required = true, type = Metadata.class) })
    protected List<Object> serviceOrDocumentationOrMetadata;
    @XmlElement(namespace = "http://www.unidata.ucar.edu/thredds", required = true)
    protected List<Access> access;
    @XmlElements( {
            @XmlElement(name = "dataset", namespace = "http://www.unidata.ucar.edu/thredds", required = true, type = Dataset.class),
            @XmlElement(name = "catalogRef", namespace = "http://www.unidata.ucar.edu/thredds", required = true, type = CatalogRef.class) })
    protected List<Object> datasetOrCatalogRef;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    @XmlAttribute
    @XmlIDREF
    protected Object alias;
    @XmlAttribute
    protected String authority;
    @XmlAttribute
    protected DataType dataType;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected String serviceName;
    @XmlAttribute
    protected String urlPath;

    /**
     * Gets the value of the serviceOrDocumentationOrMetadata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification
     * you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
     * method for the serviceOrDocumentationOrMetadata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getServiceOrDocumentationOrMetadata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Property } {@link Service }
     * {@link Documentation } {@link Metadata }
     * 
     * 
     */
    public List<Object> getServiceOrDocumentationOrMetadata() {
        if (serviceOrDocumentationOrMetadata == null) {
            serviceOrDocumentationOrMetadata = new ArrayList<Object>();
        }
        return this.serviceOrDocumentationOrMetadata;
    }

    /**
     * Gets the value of the access property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification
     * you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
     * method for the access property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAccess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Access }
     * 
     * 
     */
    public List<Access> getAccess() {
        if (access == null) {
            access = new ArrayList<Access>();
        }
        return this.access;
    }

    /**
     * Gets the value of the datasetOrCatalogRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification
     * you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
     * method for the datasetOrCatalogRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDatasetOrCatalogRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Dataset } {@link CatalogRef }
     * 
     * 
     */
    public List<Object> getDatasetOrCatalogRef() {
        if (datasetOrCatalogRef == null) {
            datasetOrCatalogRef = new ArrayList<Object>();
        }
        return this.datasetOrCatalogRef;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public Object getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value allowed object is {@link Object }
     * 
     */
    public void setAlias(Object value) {
        this.alias = value;
    }

    /**
     * Gets the value of the authority property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Sets the value of the authority property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setAuthority(String value) {
        this.authority = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return possible object is {@link DataType }
     * 
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value allowed object is {@link DataType }
     * 
     */
    public void setDataType(DataType value) {
        this.dataType = value;
    }

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
     * Gets the value of the serviceName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the urlPath property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUrlPath() {
        return urlPath;
    }

    /**
     * Sets the value of the urlPath property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setUrlPath(String value) {
        this.urlPath = value;
    }

}