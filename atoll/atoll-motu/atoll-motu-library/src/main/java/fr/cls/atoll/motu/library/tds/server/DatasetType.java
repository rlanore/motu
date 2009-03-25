//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-3268 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.25 at 11:44:51 AM CET 
//


package fr.cls.atoll.motu.library.tds.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for DatasetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatasetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0}service" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0}threddsMetadataGroup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0}access" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2}netcdf" minOccurs="0"/>
 *         &lt;element ref="{http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0}dataset" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="alias" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="authority" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="collectionType" type="{http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0}collectionTypes" />
 *       &lt;attribute name="dataType" type="{http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0}dataTypes" />
 *       &lt;attribute name="harvest" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="resourceControl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="urlPath" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatasetType", propOrder = {
    "service",
    "threddsMetadataGroup",
    "access",
    "netcdf",
    "dataset"
})
public class DatasetType {

    @XmlElement(namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", required = true)
    protected List<Service> service;
    @XmlElementRefs({
        @XmlElementRef(name = "geospatialCoverage", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = GeospatialCoverage.class),
        @XmlElementRef(name = "authority", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "project", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "publisher", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "metadata", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = Metadata.class),
        @XmlElementRef(name = "documentation", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "variables", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = Variables.class),
        @XmlElementRef(name = "dataFormat", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "date", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "serviceName", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "dataType", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "dataSize", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = DataSize.class),
        @XmlElementRef(name = "creator", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "contributor", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = Contributor.class),
        @XmlElementRef(name = "property", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = Property.class),
        @XmlElementRef(name = "keyword", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class),
        @XmlElementRef(name = "timeCoverage", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class)
    })
    protected List<Object> threddsMetadataGroup;
    @XmlElement(namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", required = true)
    protected List<Access> access;
    @XmlElement(namespace = "http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2")
    protected Netcdf netcdf;
    @XmlElementRef(name = "dataset", namespace = "http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0", type = JAXBElement.class)
    protected List<JAXBElement<? extends DatasetType>> dataset;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String id;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String alias;
    @XmlAttribute
    protected String authority;
    @XmlAttribute
    protected String collectionType;
    @XmlAttribute
    protected String dataType;
    @XmlAttribute
    protected Boolean harvest;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected String resourceControl;
    @XmlAttribute
    protected String serviceName;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String urlPath;

    /**
     * Gets the value of the service property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the service property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Service }
     * 
     * 
     */
    public List<Service> getService() {
        if (service == null) {
            service = new ArrayList<Service>();
        }
        return this.service;
    }

    /**
     * Gets the value of the threddsMetadataGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the threddsMetadataGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getThreddsMetadataGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeospatialCoverage }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link ControlledVocabulary }{@code >}
     * {@link JAXBElement }{@code <}{@link SourceType }{@code >}
     * {@link JAXBElement }{@code <}{@link DocumentationType }{@code >}
     * {@link Metadata }
     * {@link Variables }
     * {@link JAXBElement }{@code <}{@link DateTypeFormatted }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link DataSize }
     * {@link JAXBElement }{@code <}{@link SourceType }{@code >}
     * {@link Contributor }
     * {@link Property }
     * {@link JAXBElement }{@code <}{@link ControlledVocabulary }{@code >}
     * {@link JAXBElement }{@code <}{@link TimeCoverageType }{@code >}
     * 
     * 
     */
    public List<Object> getThreddsMetadataGroup() {
        if (threddsMetadataGroup == null) {
            threddsMetadataGroup = new ArrayList<Object>();
        }
        return this.threddsMetadataGroup;
    }

    /**
     * Gets the value of the access property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the access property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Access }
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
     * Gets the value of the netcdf property.
     * 
     * @return
     *     possible object is
     *     {@link Netcdf }
     *     
     */
    public Netcdf getNetcdf() {
        return netcdf;
    }

    /**
     * Sets the value of the netcdf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Netcdf }
     *     
     */
    public void setNetcdf(Netcdf value) {
        this.netcdf = value;
    }

    /**
     * Gets the value of the dataset property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataset property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataset().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link CatalogRef }{@code >}
     * {@link JAXBElement }{@code <}{@link DatasetType }{@code >}
     * {@link JAXBElement }{@code <}{@link DatasetFmrc }{@code >}
     * {@link JAXBElement }{@code <}{@link DatasetScan }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends DatasetType>> getDataset() {
        if (dataset == null) {
            dataset = new ArrayList<JAXBElement<? extends DatasetType>>();
        }
        return this.dataset;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the authority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Sets the value of the authority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthority(String value) {
        this.authority = value;
    }

    /**
     * Gets the value of the collectionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectionType() {
        return collectionType;
    }

    /**
     * Sets the value of the collectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectionType(String value) {
        this.collectionType = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the harvest property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHarvest() {
        return harvest;
    }

    /**
     * Sets the value of the harvest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHarvest(Boolean value) {
        this.harvest = value;
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
     * Gets the value of the resourceControl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceControl() {
        return resourceControl;
    }

    /**
     * Sets the value of the resourceControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceControl(String value) {
        this.resourceControl = value;
    }

    /**
     * Gets the value of the serviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the urlPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPath() {
        return urlPath;
    }

    /**
     * Sets the value of the urlPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPath(String value) {
        this.urlPath = value;
    }

}
