package fr.cls.atoll.motu.metadata;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ucar.ma2.MAMath;
import ucar.nc2.Dimension;
import ucar.nc2.Variable;
import ucar.nc2.constants.AxisType;
import ucar.nc2.dataset.CoordinateAxis;
import fr.cls.atoll.motu.data.DatasetBase;
import fr.cls.atoll.motu.data.Product;
import fr.cls.atoll.motu.exception.MotuException;
import fr.cls.atoll.motu.exception.NetCdfVariableException;
import fr.cls.atoll.motu.exception.NetCdfVariableNotFoundException;
import fr.cls.atoll.motu.netcdf.NetCdfReader;
import fr.cls.atoll.motu.netcdf.NetCdfWriter;
import fr.cls.commons.util5.DatePeriod;

//CSOFF: MultipleStringLiterals : avoid message in constants declaration and trace log.

/**
 * This class represents the metadata of a product. The metadata are similar to all products.
 * 
 * @author $Author: dearith $
 * @version $Revision: 1.1 $ - $Date: 2009-02-20 13:00:26 $
 */

/**
 * The Class ProductMetaData.
 * 
 * @author $Author: dearith $
 * @version $Revision: 1.1 $ - $Date: 2009-02-20 13:00:26 $
 */
public class ProductMetaData {

    /** The Constant PRODUCT_TYPE_ALONG_TRACK. */
    private static final String PRODUCT_TYPE_ALONG_TRACK = "ALONG_TRACK_PRODUCT";

    /** The Constant QUICKLOOK_KEY. */
    private static final String QUICKLOOK_KEY = "quicklook";

    /** The Constant LAS_URL_KEY. */
    private static final String LAS_URL_KEY = "las";

    /** The Constant FTP_URL_KEY. */
    private static final String FTP_URL_KEY = "ftp";

    /** The Constant PAGE_SITE_WEB_URL_KEY. */
    private static final String PAGE_SITE_WEB_URL_KEY = "info";

    /** The Constant BULLETIN_SITE_URL_KEY. */
    private static final String BULLETIN_SITE_URL_KEY = "bulletinsite";

    /** The Constant QUICKLOOK_FILEEXT. */
    private static final String QUICKLOOK_FILEEXT = ".gif";

    /** The Constant TYPE_KEY. */
    private static final String TYPE_KEY = "type";

    /** The Constant TYPE_VALUE_ATP. */
    private static final String TYPE_VALUE_ATP = "along track product";

    /** The Constant TYPE_VALUE_GRID. */
    @SuppressWarnings("unused")
    private static final String TYPE_VALUE_GRID = "gridded product";

    /**
     * Default constructor.
     */
    public ProductMetaData() {
        init();
    }

    /**
     * Initialization.
     */
    private void init() {
        // documentations = new ArrayList<DocMetaData>();
        // deliveries = new ArrayList<Delivery>();

    }

    // /**
    // * Builds a unique id for the product.
    // * the built id is equal to :
    // * "'[Product Metadata Type].[Product Metadata Subtype 0].' ....'[Product
    // Metadata Subtype n].[Product Metadata Title]'
    // * @return built id of the product.
    // */
    // public String buildId() {
    // StringBuffer buffer = new StringBuffer();
    // buffer.append(getProductType());
    // for (Iterator<String> it = getProductSubTypes().iterator() ; it.hasNext()
    // ;) {
    // buffer.append("-");
    // buffer.append(it.next());
    // }
    // buffer.append(getTitle());
    // String str = buffer.toString().replaceAll("[\\s*,\\W*,_*]", "x");
    // //UUID uuid = UUID.fromString(str);
    // //System.out.println(uuid.toString());
    // //return uuid.toString();
    // //System.out.println(str);
    // return str.toLowerCase();
    // }

    /**
     * Compares content of product subtypes list with another product subtypes list.
     * 
     * @param productSubTypesTmp list to be compared
     * 
     * @return the index from where the content is not equal.
     */
    public int compareSubTypes(List<String> productSubTypesTmp) {
        if (productSubTypes.size() != productSubTypesTmp.size()) {
            return -1;
        }
        int i = 0;
        for (i = 0; i < productSubTypes.size(); i++) {
            if (productSubTypes.get(i) != productSubTypesTmp.get(i)) {
                break;
            }
        }
        return i;
    }

    /**
     * Tests if the product is an 'along track' product : (if product's category equals
     * PRODUCT_TYPE_ALONG_TRACK constant or catalog service 'type' equals TYPE_VALUE_ATP constant.
     * 
     * @return Returns true if product type is an 'along track' product.
     */
    public boolean isProductAlongTrack() {
        return productCategory.equalsIgnoreCase(PRODUCT_TYPE_ALONG_TRACK) || getProductTypeServiceValue().equalsIgnoreCase(TYPE_VALUE_ATP);
    }

    /** product id or short name. */
    private String productId = "";

    /**
     * Getter of the property <tt>id</tt>.
     * 
     * @return Returns the id.
     * 
     * @uml.property name="productId"
     */
    public String getProductId() {
        // if (productId.equals("")) {
        // productId = buildId();
        // }
        return productId;
    }

    /**
     * Setter of the property <tt>id</tt>.
     * 
     * @param productId The id to set.
     * 
     * @uml.property name="productId"
     */
    public void setProductId(String productId) {
        this.productId = null;
        if (productId != null) {
            this.productId = productId.trim();
        }
    }

    /** Type of product. */
    private String productType = "";

    /**
     * Getter of the property <tt>productType</tt>.
     * 
     * @return Returns the productType.
     * 
     * @uml.property name="productType"
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Setter of the property <tt>productType</tt>.
     * 
     * @param productType The productType to set.
     * 
     * @uml.property name="productType"
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /** Category of product ('along track', 'gridded'). */
    private String productCategory = "";

    /**
     * Getter of the property <tt>productCategory</tt>.
     * 
     * @return Returns the productCategory.
     * 
     * @uml.property name="productCategory"
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * Setter of the property <tt>productCategory</tt>.
     * 
     * @param productCategory The productCategory to set.
     * 
     * @uml.property name="productCategory"
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /** Level of product (along track or gridded). */
    private String outputType = "";

    /**
     * Getter of the property <tt>outputType</tt>.
     * 
     * @return Returns the outputType.
     * 
     * @uml.property name="outputType"
     */
    public String getOutputType() {
        return outputType;
    }

    /**
     * Setter of the property <tt>outputType</tt>.
     * 
     * @param outputType The outputType to set.
     * 
     * @uml.property name="outputType"
     */
    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    /** Spatial resolution in various units (degrees, Km, ...). */
    private String spatialResolution = "";

    /**
     * Getter of the property <tt>spatialResolution</tt>.
     * 
     * @return Returns the spatialResolution.
     * 
     * @uml.property name="spatialResolution"
     */
    public String getSpatialResolution() {
        return spatialResolution;
    }

    /**
     * Setter of the property <tt>spatialResolution</tt>.
     * 
     * @param spatialResolution The spatialResolution to set.
     * 
     * @uml.property name="spatialResolution"
     */
    public void setSpatialResolution(String spatialResolution) {
        this.spatialResolution = spatialResolution;
    }

    /** Temporal resolution - Could be different from process frequency. */
    private String temporalResolution = "";

    /**
     * Getter of the property <tt>temporalResolution</tt>.
     * 
     * @return Returns the temporalResolution.
     * 
     * @uml.property name="temporalResolution"
     */
    public String getTemporalResolution() {
        return temporalResolution;
    }

    /**
     * Setter of the property <tt>temporalResolution</tt>.
     * 
     * @param temporalResolution The temporalResolution to set.
     * 
     * @uml.property name="temporalResolution"
     */
    public void setTemporalResolution(String temporalResolution) {
        this.temporalResolution = temporalResolution;
    }

    /** Frequency of data processings. */
    private String updated = "";

    /**
     * Getter of the property <tt>updated</tt>.
     * 
     * @return Returns the updated.
     * 
     * @uml.property name="updated"
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * Setter of the property <tt>updated</tt>.
     * 
     * @param updated The updated to set.
     * 
     * @uml.property name="updated"
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /** The parameter categories. */
    private Collection<ParameterCategory> parameterCategories;

    /**
     * Getter of the property <tt>parameterCategories</tt>.
     * 
     * @return Returns the parameterCategories.
     * 
     * @uml.property name="parameterCategories"
     */
    public Collection<ParameterCategory> getParameterCategories() {
        return parameterCategories;
    }

    /**
     * Returns an iterator over the elements in this collection.
     * 
     * @return an <tt>Iterator</tt> over the elements in this collection
     * 
     * @see java.util.Collection#iterator()
     * @uml.property name="parameterCategories"
     */
    public Iterator<ParameterCategory> parameterCategoriesIterator() {
        return parameterCategories.iterator();
    }

    /**
     * Returns <tt>true</tt> if this collection contains no elements.
     * 
     * @return <tt>true</tt> if this collection contains no elements
     * 
     * @see java.util.Collection#isEmpty()
     * @uml.property name="parameterCategories"
     */
    public boolean isParameterCategoriesEmpty() {
        return parameterCategories.isEmpty();
    }

    /**
     * Contains parameter categories.
     * 
     * @param element whose presence in this collection is to be tested.
     * 
     * @return <tt>true</tt> if this collection contains the specified element.
     * 
     * @see java.util.Collection#contains(Object)
     * @uml.property name="parameterCategories"
     */
    public boolean containsParameterCategories(ParameterCategory element) {
        return parameterCategories.contains(element);
    }

    /**
     * Contains all parameter categories.
     * 
     * @param elements collection to be checked for containment in this collection.
     * 
     * @return <tt>true</tt> if this collection contains all of the elements in the specified collection.
     * 
     * @see java.util.Collection#containsAll(Collection)
     * @uml.property name="parameterCategories"
     */
    public boolean containsAllParameterCategories(Collection<? extends ParameterCategory> elements) {
        return this.parameterCategories.containsAll(elements);
    }

    /**
     * Returns the number of elements in this collection.
     * 
     * @return the number of elements in this collection
     * 
     * @see java.util.Collection#size()
     * @uml.property name="parameterCategories"
     */
    public int parameterCategoriesSize() {
        return parameterCategories.size();
    }

    /**
     * Returns all elements of this collection in an array.
     * 
     * @return an array containing all of the elements in this collection
     * 
     * @see java.util.Collection#toArray()
     * @uml.property name="parameterCategories"
     */
    public ParameterCategory[] parameterCategoriesToArray() {
        return parameterCategories.toArray(new ParameterCategory[parameterCategories.size()]);
    }

    /**
     * Returns an array containing all of the elements in this collection; the runtime type of the returned
     * array is that of the specified array.
     * 
     * @param <T> generic type
     * @param a the array into which the elements of this collection are to be stored.
     * 
     * @return an array containing all of the elements in this collection
     * 
     * @see java.util.Collection#toArray(Object[])
     * @uml.property name="parameterCategories"
     */
    public <T extends ParameterCategory> T[] parameterCategoriesToArray(T[] a) {
        return (T[]) this.parameterCategories.toArray(a);
    }

    /**
     * Ensures that this collection contains the specified element (optional operation).
     * 
     * @param element whose presence in this collection is to be ensured.
     * 
     * @return true if this collection changed as a result of the call.
     * 
     * @see java.util.Collection#add(Object)
     * @uml.property name="parameterCategories"
     */
    public boolean addParameterCategories(ParameterCategory element) {
        return parameterCategories.add(element);
    }

    /**
     * Setter of the property <tt>parameterCategories</tt>.
     * 
     * @param parameterCategories the parameterCategories to set.
     * 
     * @uml.property name="parameterCategories"
     */
    public void setParameterCategories(Collection<ParameterCategory> parameterCategories) {
        this.parameterCategories = parameterCategories;
    }

    /**
     * Removes a single instance of the specified element from this collection, if it is present (optional
     * operation).
     * 
     * @param element to be removed from this collection, if present.
     * 
     * @return true if this collection changed as a result of the call.
     * 
     * @see java.util.Collection#add(Object)
     * @uml.property name="parameterCategories"
     */
    public boolean removeParameterCategories(ParameterCategory element) {
        return parameterCategories.remove(element);
    }

    /**
     * Removes all of the elements from this collection (optional operation).
     * 
     * @see java.util.Collection#clear()
     * @uml.property name="parameterCategories"
     */
    public void clearParameterCategories() {
        parameterCategories.clear();
    }

    /** title or long name of the product. */
    private String title = "";

    /**
     * Getter of the property <tt>title</tt>.
     * 
     * @return Returns the title.
     * 
     * @uml.property name="title"
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter of the property <tt>title</tt>.
     * 
     * @param title The title to set.
     * 
     * @uml.property name="title"
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Textual description of the product. */
    private String description = "";

    /**
     * Getter of the property <tt>description</tt>.
     * 
     * @return Returns the description.
     * 
     * @uml.property name="description"
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of the property <tt>description</tt>.
     * 
     * @param description The description to set.
     * 
     * @uml.property name="description"
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** The coordinate system. */
    private CoordinateSystem coordinateSystem;

    /**
     * Getter of the property <tt>coordinateSystem</tt>.
     * 
     * @return Returns the coordinateSystem.
     * 
     * @uml.property name="coordinateSystem"
     */
    public CoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }

    /**
     * Setter of the property <tt>coordinateSystem</tt>.
     * 
     * @param coordinateSystem The coordinateSystem to set.
     * 
     * @uml.property name="coordinateSystem"
     */
    public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    /** The data provider. */
    private DataProvider dataProvider;

    /**
     * Getter of the property <tt>dataProvider</tt>.
     * 
     * @return Returns the dataProvider.
     * 
     * @uml.property name="dataProvider"
     */
    public DataProvider getDataProvider() {
        return dataProvider;
    }

    /**
     * Setter of the property <tt>dataProvider</tt>.
     * 
     * @param dataProvider The dataProvider to set.
     * 
     * @uml.property name="dataProvider"
     */
    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    /** The deliveries. */
    private Collection<Delivery> deliveries = null;

    /**
     * Getter of the property <tt>deliveries</tt>.
     * 
     * @return Returns the deliveries.
     * 
     * @uml.property name="deliveries"
     */
    public Collection<Delivery> getDeliveries() {
        return deliveries;
    }

    /**
     * Returns an iterator over the elements in this collection.
     * 
     * @return an <tt>Iterator</tt> over the elements in this collection
     * 
     * @see java.util.Collection#iterator()
     * @uml.property name="deliveries"
     */
    public Iterator<Delivery> deliveriesIterator() {
        return deliveries.iterator();
    }

    /**
     * Returns <tt>true</tt> if this collection contains no elements.
     * 
     * @return <tt>true</tt> if this collection contains no elements
     * 
     * @see java.util.Collection#isEmpty()
     * @uml.property name="deliveries"
     */
    public boolean isDeliveriesEmpty() {
        return deliveries.isEmpty();
    }

    /**
     * Contains deliveries.
     * 
     * @param element whose presence in this collection is to be tested.
     * 
     * @return <tt>true</tt> if this collection contains the specified element.
     * 
     * @see java.util.Collection#contains(Object)
     * @uml.property name="deliveries"
     */
    public boolean containsDeliveries(Delivery element) {
        return deliveries.contains(element);
    }

    /**
     * Contains all deliveries.
     * 
     * @param elements collection to be checked for containment in this collection.
     * 
     * @return <tt>true</tt> if this collection contains all of the elements in the specified collection.
     * 
     * @see java.util.Collection#containsAll(Collection)
     * @uml.property name="deliveries"
     */
    public boolean containsAllDeliveries(Collection<? extends Delivery> elements) {
        return this.deliveries.containsAll(elements);
    }

    /**
     * Returns the number of elements in this collection.
     * 
     * @return the number of elements in this collection
     * 
     * @see java.util.Collection#size()
     * @uml.property name="deliveries"
     */
    public int deliveriesSize() {
        return deliveries.size();
    }

    /**
     * Returns all elements of this collection in an array.
     * 
     * @return an array containing all of the elements in this collection
     * 
     * @see java.util.Collection#toArray()
     * @uml.property name="deliveries"
     */
    public Delivery[] deliveriesToArray() {
        return deliveries.toArray(new Delivery[deliveries.size()]);
    }

    /**
     * Returns an array containing all of the elements in this collection; the runtime type of the returned
     * array is that of the specified array.
     * 
     * @param <T> generic type
     * @param a the array into which the elements of this collection are to be stored.
     * 
     * @return an array containing all of the elements in this collection
     * 
     * @see java.util.Collection#toArray(Object[])
     * @uml.property name="deliveries"
     */
    public <T extends Delivery> T[] deliveriesToArray(T[] a) {
        return (T[]) this.deliveries.toArray(a);
    }

    /**
     * Ensures that this collection contains the specified element (optional operation).
     * 
     * @param element whose presence in this collection is to be ensured.
     * 
     * @return true if this collection changed as a result of the call.
     * 
     * @see java.util.Collection#add(Object)
     * @uml.property name="deliveries"
     */
    public boolean addDeliveries(Delivery element) {
        return deliveries.add(element);
    }

    /**
     * Setter of the property <tt>deliveries</tt>.
     * 
     * @param deliveries the deliveries to set.
     * 
     * @uml.property name="deliveries"
     */
    public void setDeliveries(Collection<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    /**
     * Removes a single instance of the specified element from this collection, if it is present (optional
     * operation).
     * 
     * @param element to be removed from this collection, if present.
     * 
     * @return true if this collection changed as a result of the call.
     * 
     * @see java.util.Collection#add(Object)
     * @uml.property name="deliveries"
     */
    public boolean removeDeliveries(Delivery element) {
        return deliveries.remove(element);
    }

    /**
     * Removes all of the elements from this collection (optional operation).
     * 
     * @see java.util.Collection#clear()
     * @uml.property name="deliveries"
     */
    public void clearDeliveries() {
        deliveries.clear();
    }

    /** The documentations. */
    private List<DocMetaData> documentations = null;

    /**
     * Getter of the property <tt>documentations</tt>.
     * 
     * @return Returns the documentations.
     * 
     * @uml.property name="documentations"
     */
    public List<DocMetaData> getDocumentations() {
        return documentations;
    }

    /**
     * Returns an iterator over the elements in this collection.
     * 
     * @return an <tt>Iterator</tt> over the elements in this collection
     * 
     * @see java.util.Collection#iterator()
     * @uml.property name="documentations"
     */
    public Iterator<DocMetaData> documentationsIterator() {
        return documentations.iterator();
    }

    /**
     * Returns <tt>true</tt> if this collection contains no elements.
     * 
     * @return <tt>true</tt> if this collection contains no elements
     * 
     * @see java.util.Collection#isEmpty()
     * @uml.property name="documentations"
     */
    public boolean isDocumentationsEmpty() {
        return documentations.isEmpty();
    }

    /**
     * Contains documentations.
     * 
     * @param element whose presence in this collection is to be tested.
     * 
     * @return <tt>true</tt> if this collection contains the specified element.
     * 
     * @see java.util.Collection#contains(Object)
     * @uml.property name="documentations"
     */
    public boolean containsDocumentations(DocMetaData element) {
        return documentations.contains(element);
    }

    /**
     * Contains all documentations.
     * 
     * @param elements collection to be checked for containment in this collection.
     * 
     * @return <tt>true</tt> if this collection contains all of the elements in the specified collection.
     * 
     * @see java.util.Collection#containsAll(Collection)
     * @uml.property name="documentations"
     */
    public boolean containsAllDocumentations(List<? extends DocMetaData> elements) {
        return this.documentations.containsAll(elements);
    }

    /**
     * Returns the number of elements in this collection.
     * 
     * @return the number of elements in this collection
     * 
     * @see java.util.Collection#size()
     * @uml.property name="documentations"
     */
    public int documentationsSize() {
        return documentations.size();
    }

    /**
     * Returns all elements of this collection in an array.
     * 
     * @return an array containing all of the elements in this collection
     * 
     * @see java.util.Collection#toArray()
     * @uml.property name="documentations"
     */
    public DocMetaData[] documentationsToArray() {
        return documentations.toArray(new DocMetaData[documentations.size()]);
    }

    /**
     * Returns an array containing all of the elements in this collection; the runtime type of the returned
     * array is that of the specified array.
     * 
     * @param <T> generic type
     * @param a the array into which the elements of this collection are to be stored.
     * 
     * @return an array containing all of the elements in this collection
     * 
     * @see java.util.Collection#toArray(Object[])
     * @uml.property name="documentations"
     */
    public <T extends DocMetaData> T[] documentationsToArray(T[] a) {
        return (T[]) this.documentations.toArray(a);
    }

    /**
     * Ensures that this collection contains the specified element (optional operation).
     * 
     * @param element whose presence in this collection is to be ensured.
     * 
     * @return true if this collection changed as a result of the call.
     * 
     * @see java.util.Collection#add(Object)
     * @uml.property name="documentations"
     */
    public boolean addDocumentations(DocMetaData element) {
        return documentations.add(element);
    }

    /**
     * Setter of the property <tt>documentations</tt>.
     * 
     * @param documentations the documentations to set.
     * 
     * @uml.property name="documentations"
     */
    public void setDocumentations(List<DocMetaData> documentations) {
        this.documentations = documentations;
    }

    /**
     * Removes a single instance of the specified element from this collection, if it is present (optional
     * operation).
     * 
     * @param element to be removed from this collection, if present.
     * 
     * @return true if this collection changed as a result of the call.
     * 
     * @see java.util.Collection#add(Object)
     * @uml.property name="documentations"
     */
    public boolean removeDocumentations(DocMetaData element) {
        return documentations.remove(element);
    }

    /**
     * Removes all of the elements from this collection (optional operation).
     * 
     * @see java.util.Collection#clear()
     * @uml.property name="documentations"
     */
    public void clearDocumentations() {
        documentations.clear();
    }

    /** is global, regional or local. */
    private String geographicalScale = "";

    /**
     * Getter of the property <tt>geographicalScale</tt>.
     * 
     * @return Returns the geographicalScale.
     * 
     * @uml.property name="geographicalScale"
     */
    public String getGeographicalScale() {
        return geographicalScale;
    }

    /**
     * Setter of the property <tt>geographicalScale</tt>.
     * 
     * @param geographicalScale The geographicalScale to set.
     * 
     * @uml.property name="geographicalScale"
     */
    public void setGeographicalScale(String geographicalScale) {
        this.geographicalScale = geographicalScale;
    }

    /**
     * Reads parameters (variables) metadata from a dataset (netCDF files). Only the first file of the dataset
     * is used to get metadata.
     * 
     * @param dataset dataset to read variables metadata.
     */
    public void readParameterMetaData(DatasetBase dataset) {

    }

    /**
     * Reads parameters (variables) metadata from an XML file.
     * 
     * @param url url of the XML file that contains metadata
     */
    public void readParameterMetaData(String url) {

    }

    /**
     * Reads product metadata from an XML file.
     * 
     * @param url url of the XML file that contains metadata
     */
    public void readProductMetaData(String url) {

    }

    /** Type of data calculation (ie. Mercator: hindcast, analysis/outcast, forecast). */
    private String computeType = "";

    /**
     * Getter of the property <tt>computeType</tt>.
     * 
     * @return Returns the computeType.
     * 
     * @uml.property name="computeType"
     */
    public String getComputeType() {
        return this.computeType;
    }

    /**
     * Setter of the property <tt>computeType</tt>.
     * 
     * @param computeType The computeType to set.
     * 
     * @uml.property name="computeType"
     */
    public void setComputeType(String computeType) {
        this.computeType = computeType;
    }

    /** Sub-types of the product (stored hierarchically in the list). */
    private List<String> productSubTypes;

    /**
     * Getter of the property <tt>productSubTypes</tt>.
     * 
     * @return Returns the productSubTypes.
     * 
     * @uml.property name="productSubTypes"
     */
    public List<String> getProductSubTypes() {
        if (productSubTypes == null) {
            productSubTypes = new ArrayList<String>();
        }
        return this.productSubTypes;
    }

    /**
     * Setter of the property <tt>productSubTypes</tt>.
     * 
     * @param productSubTypes The productSubTypes to set.
     * 
     * @uml.property name="productSubTypes"
     */
    public void setProductSubTypes(List<String> productSubTypes) {
        this.productSubTypes = productSubTypes;
    }

    /** The coordinate axes map. */
    private Map<AxisType, CoordinateAxis> coordinateAxesMap = null;

    /**
     * Getter of the property <tt>coordinateAxes</tt>.
     * 
     * @return Returns the coordinateAxesMap.
     * 
     * @uml.property name="coordinateAxes"
     */
    public Map<AxisType, CoordinateAxis> getCoordinateAxes() {
        return this.coordinateAxesMap;
    }

    /**
     * Returns a set view of the keys contained in this map.
     * 
     * @return a set view of the keys contained in this map.
     * 
     * @see java.util.Map#keySet()
     * @uml.property name="coordinateAxes"
     */
    public Set<AxisType> coordinateAxesKeySet() {
        return this.coordinateAxesMap.keySet();
    }

    /**
     * Returns a collection view of the values contained in this map.
     * 
     * @return a collection view of the values contained in this map.
     * 
     * @see java.util.Map#values()
     * @uml.property name="coordinateAxes"
     */
    public Collection<CoordinateAxis> coordinateAxesValues() {
        return this.coordinateAxesMap.values();
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified key.
     * 
     * @param key key whose presence in this map is to be tested.
     * 
     * @return <tt>true</tt> if this map contains a mapping for the specified key.
     * 
     * @see java.util.Map#containsKey(Object)
     * @uml.property name="coordinateAxes"
     */
    public boolean coordinateAxesContainsKey(AxisType key) {
        return this.coordinateAxesMap.containsKey(key);
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the specified value.
     * 
     * @param coordinateAxes value whose presence in this map is to be tested.
     * 
     * @return <tt>true</tt> if this map maps one or more keys to the specified value.
     * 
     * @see java.util.Map#containsValue(Object)
     * @uml.property name="coordinateAxes"
     */
    public boolean coordinateAxesContainsValue(CoordinateAxis coordinateAxes) {
        return this.coordinateAxesMap.containsValue(coordinateAxes);
    }

    /**
     * Returns the value to which this map maps the specified key.
     * 
     * @param key key whose associated value is to be returned.
     * 
     * @return the value to which this map maps the specified key, or <tt>null</tt> if the map contains no
     *         mapping for this key.
     * 
     * @see java.util.Map#get(Object)
     * @uml.property name="coordinateAxes"
     */
    public CoordinateAxis getCoordinateAxes(AxisType key) {
        return (CoordinateAxis) this.coordinateAxesMap.get(key);
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     * 
     * @return <tt>true</tt> if this map contains no key-value mappings.
     * 
     * @see java.util.Map#isEmpty()
     * @uml.property name="coordinateAxes"
     */
    public boolean isCoordinateAxesEmpty() {
        return this.coordinateAxesMap.isEmpty();
    }

    /**
     * Returns the number of key-value mappings in this map.
     * 
     * @return the number of key-value mappings in this map.
     * 
     * @see java.util.Map#size()
     * @uml.property name="coordinateAxes"
     */
    public int coordinateAxesSize() {
        return this.coordinateAxesMap.size();
    }

    /**
     * Setter of the property <tt>coordinateAxes</tt>.
     * 
     * @param coordinateAxes the coordinateAxesMap to set.
     * 
     * @uml.property name="coordinateAxes"
     */
    public void setCoordinateAxes(Map<AxisType, CoordinateAxis> coordinateAxes) {
        this.coordinateAxesMap = coordinateAxes;
    }

    /**
     * Associates the specified value with the specified key in this map (optional operation).
     * 
     * @param coordinateAxes value to be associated with the specified key.
     * @param key key with which the specified value is to be associated.
     * 
     * @return previous value associated with specified key, or <tt>null</tt>
     * 
     * @see java.util.Map#put(Object,Object)
     * @uml.property name="coordinateAxes"
     */
    public CoordinateAxis putCoordinateAxes(AxisType key, CoordinateAxis coordinateAxes) {
        return (CoordinateAxis) this.coordinateAxesMap.put(key, coordinateAxes);
    }

    /**
     * Removes the mapping for this key from this map if it is present (optional operation).
     * 
     * @param key key whose mapping is to be removed from the map.
     * 
     * @return previous value associated with specified key, or <tt>null</tt> if there was no mapping for
     *         key.
     * 
     * @see java.util.Map#remove(Object)
     * @uml.property name="coordinateAxes"
     */
    public CoordinateAxis removeCoordinateAxes(AxisType key) {
        return (CoordinateAxis) this.coordinateAxesMap.remove(key);
    }

    /**
     * Removes all mappings from this map (optional operation).
     * 
     * @see java.util.Map#clear()
     * @uml.property name="coordinateAxes"
     */
    public void clearCoordinateAxes() {
        this.coordinateAxesMap.clear();
    }

    /** The parameter meta datas map. */
    private Map<String, ParameterMetaData> parameterMetaDatasMap;

    /**
     * Getter of the property <tt>parameterMetaDatas</tt>.
     * 
     * @return Returns the parameterMetaDatasMap.
     * 
     * @uml.property name="parameterMetaDatas"
     */
    public Map<String, ParameterMetaData> getParameterMetaDatas() {
        return this.parameterMetaDatasMap;
    }

    /**
     * Returns a set view of the keys contained in this map.
     * 
     * @return a set view of the keys contained in this map.
     * 
     * @see java.util.Map#keySet()
     * @uml.property name="parameterMetaDatas"
     */
    public Set<String> parameterMetaDatasKeySet() {
        return this.parameterMetaDatasMap.keySet();
    }

    /**
     * Returns a collection view of the values contained in this map.
     * 
     * @return a collection view of the values contained in this map.
     * 
     * @see java.util.Map#values()
     * @uml.property name="parameterMetaDatas"
     */
    public Collection<ParameterMetaData> parameterMetaDatasValues() {
        return this.parameterMetaDatasMap.values();
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified key.
     * 
     * @param key key whose presence in this map is to be tested.
     * 
     * @return <tt>true</tt> if this map contains a mapping for the specified key.
     * 
     * @see java.util.Map#containsKey(Object)
     * @uml.property name="parameterMetaDatas"
     */
    public boolean parameterMetaDatasContainsKey(String key) {
        return this.parameterMetaDatasMap.containsKey(key);
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the specified value.
     * 
     * @param parameterMetaDatas value whose presence in this map is to be tested.
     * 
     * @return <tt>true</tt> if this map maps one or more keys to the specified value.
     * 
     * @see java.util.Map#containsValue(Object)
     * @uml.property name="parameterMetaDatas"
     */
    public boolean parameterMetaDatasContainsValue(ParameterMetaData parameterMetaDatas) {
        return this.parameterMetaDatasMap.containsValue(parameterMetaDatas);
    }

    /**
     * Returns the value to which this map maps the specified key.
     * 
     * @param key key whose associated value is to be returned.
     * 
     * @return the value to which this map maps the specified key, or <tt>null</tt> if the map contains no
     *         mapping for this key.
     * 
     * @see java.util.Map#get(Object)
     * @uml.property name="parameterMetaDatas"
     */
    public ParameterMetaData getParameterMetaDatas(String key) {
        return (ParameterMetaData) this.parameterMetaDatasMap.get(key);
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     * 
     * @return <tt>true</tt> if this map contains no key-value mappings.
     * 
     * @see java.util.Map#isEmpty()
     * @uml.property name="parameterMetaDatas"
     */
    public boolean isParameterMetaDatasEmpty() {
        return this.parameterMetaDatasMap.isEmpty();
    }

    /**
     * Returns the number of key-value mappings in this map.
     * 
     * @return the number of key-value mappings in this map.
     * 
     * @see java.util.Map#size()
     * @uml.property name="parameterMetaDatas"
     */
    public int parameterMetaDatasSize() {
        return this.parameterMetaDatasMap.size();
    }

    /**
     * Setter of the property <tt>parameterMetaDatas</tt>.
     * 
     * @param parameterMetaDatas the parameterMetaDatasMap to set.
     * 
     * @uml.property name="parameterMetaDatas"
     */
    public void setParameterMetaDatas(Map<String, ParameterMetaData> parameterMetaDatas) {
        this.parameterMetaDatasMap = parameterMetaDatas;
    }

    /**
     * Associates the specified value with the specified key in this map (optional operation).
     * 
     * @param parameterMetaDatas value to be associated with the specified key.
     * @param key key with which the specified value is to be associated.
     * 
     * @return previous value associated with specified key, or <tt>null</tt>
     * 
     * @see java.util.Map#put(Object,Object)
     * @uml.property name="parameterMetaDatas"
     */
    public ParameterMetaData putParameterMetaDatas(String key, ParameterMetaData parameterMetaDatas) {
        return (ParameterMetaData) this.parameterMetaDatasMap.put(key, parameterMetaDatas);
    }

    /**
     * Removes the mapping for this key from this map if it is present (optional operation).
     * 
     * @param key key whose mapping is to be removed from the map.
     * 
     * @return previous value associated with specified key, or <tt>null</tt> if there was no mapping for
     *         key.
     * 
     * @see java.util.Map#remove(Object)
     * @uml.property name="parameterMetaDatas"
     */
    public ParameterMetaData removeParameterMetaDatas(String key) {
        return (ParameterMetaData) this.parameterMetaDatasMap.remove(key);
    }

    /**
     * Removes all mappings from this map (optional operation).
     * 
     * @see java.util.Map#clear()
     * @uml.property name="parameterMetaDatas"
     */
    public void clearParameterMetaDatas() {
        this.parameterMetaDatasMap.clear();
    }

    /**
     * Checks for lat lon axis.
     * 
     * @return true if axes collection contains latitude and longitude axes.
     */
    public boolean hasLatLonAxis() {
        return (getLatAxis() != null) && (getLonAxis() != null);
    }

    /**
     * Checks for geo XY axis.
     * 
     * @return true if axes collection contains GeoX and GeoY axes.
     */
    public boolean hasGeoXYAxis() {
        return (getGeoXAxis() != null) && (getGeoYAxis() != null);
    }

    /**
     * Checks for geo XY axis with lon lat equivalence.
     * 
     * @param netCdfReader the net cdf reader
     * 
     * @return true if axes collection contains GeoX with Longitude equivalence and GeoY with Latitude
     * equivalenceaxes.
     * 
     * @throws MotuException the motu exception
     */
    public boolean hasGeoXYAxisWithLonLatEquivalence(NetCdfReader netCdfReader) throws MotuException {
        return (hasGeoXAxisWithLonEquivalence(netCdfReader) && hasGeoYAxisWithLatEquivalence(netCdfReader));
    }

    /**
     * Checks for geographical axis.
     * 
     * @return true if at least one of the axes is a geographical axis.
     */
    public boolean hasGeographicalAxis() {
        return (getZAxis() != null) || (getLatAxis() != null) || (getLonAxis() != null) || (getGeoXAxis() != null) || (getGeoYAxis() != null);
    }

    /**
     * Checks for unknown axis.
     * 
     * @return true if axis type is unknown.
     */
    public boolean hasUnknownAxis() {
        return (getTimeAxis() == null) && (getZAxis() == null) && (getLatAxis() == null) && (getLonAxis() == null) && (getGeoXAxis() == null)
                && (getGeoYAxis() == null);
    }

    /**
     * Checks for time axis.
     * 
     * @return true if time axis exists among coordinate axes.
     */
    public boolean hasTimeAxis() {
        return getTimeAxis() != null;
    }

    /**
     * Gets the time coordinate axis if exists.
     * 
     * @return Time axis if exists, or null.
     */
    public CoordinateAxis getTimeAxis() {
        return getCoordinateAxes(AxisType.Time);
    }

    /**
     * Gets the minimum value of the time axis.
     * 
     * @return the minimum value as a Date, or null if no time axis
     * 
     * @throws MotuException the motu exception
     */
    public Date getTimeAxisMinValue() throws MotuException {
        CoordinateAxis axis = getTimeAxis();
        if (axis == null) {
            return null;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getDate(minMax.min, axis.getUnitsString());
    }

    /**
     * Gets the minimum value of the time axis.
     * 
     * @return the minimum value as a double, or Double.NaN if no time axis
     */
    public double getTimeAxisMinValueAsDouble() {
        CoordinateAxis axis = getTimeAxis();
        if (axis == null) {
            return Double.NaN;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return minMax.min;
    }

    /**
     * Gets the maximum value of the time axis.
     * 
     * @return the maximum value as a Date, or null if no time axis
     * 
     * @throws MotuException the motu exception
     */
    public Date getTimeAxisMaxValue() throws MotuException {
        CoordinateAxis axis = getTimeAxis();
        if (axis == null) {
            return null;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        
        return NetCdfReader.getDate(minMax.max, axis.getUnitsString());
    }

    /**
     * Gets the maximum value of the time axis.
     * 
     * @return the maximum value as a double, or Double.NaN if no time axis
     */
    public double getTimeAxisMaxValueAsDouble() {
        CoordinateAxis axis = getTimeAxis();
        if (axis == null) {
            return Double.NaN;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return minMax.max;
    }

    /**
     * Gets the minimum value of the time axis.
     * 
     * @return the string representation of the minimum value, or null if no time axis
     * 
     * @throws MotuException the motu exception
     */
    public String getTimeAxisMinValueAsString() throws MotuException {
        CoordinateAxis axis = getTimeAxis();
        if (axis == null) {
            return null;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getDateAsGMTString(minMax.min, axis.getUnitsString());
    }

    /**
     * Gets the maximum value of the time axis.
     * 
     * @return the string representation of the minimum value, or null if no time axis
     * 
     * @throws MotuException the motu exception
     */
    public String getTimeAxisMaxValueAsString() throws MotuException {
        CoordinateAxis axis = getTimeAxis();
        if (axis == null) {
            return null;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getDateAsGMTString(minMax.max, axis.getUnitsString());
    }

    /**
     * Checks for Z axis.
     * 
     * @return true if 'Z' axis (Height or GeoZ) exists among coordinate axes.
     */
    public boolean hasZAxis() {
        return getZAxis() != null;
    }

    /**
     * Gets the 'Z' coordinate axis if exists.
     * 
     * @return 'Z' axis if exists, or null.
     */
    public CoordinateAxis getZAxis() {
        CoordinateAxis axis = getCoordinateAxes(AxisType.Height);
        if (axis == null) {
            axis = getCoordinateAxes(AxisType.GeoZ);
        }
        return axis;
    }

    /**
     * Gets the minimum value of the Z axis.
     * 
     * @return the minimum value, or Double.MIN_VALUE if no Z axis
     * 
     */

    public double getZAxisMinValue() {
        CoordinateAxis axis = getZAxis();
        if (axis == null) {
            return Double.MIN_VALUE;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return minMax.min;
    }

    /**
     * Gets the minimum value of the Z axis.
     * 
     * @return the string representation of the minimum value, or null if no Z axis
     * 
     */
    public String getZAxisMinValueAsString() {
        CoordinateAxis axis = getZAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getStandardZAsString(minMax.min);
    }

    /**
     * Gets the maximum value of the Z axis.
     * 
     * @return the maximum value, or Double.MAX_VALUE if no Z axis
     */
    public double getZAxisMaxValue() {
        CoordinateAxis axis = getZAxis();
        if (axis == null) {
            return Double.MAX_VALUE;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return minMax.max;
    }

    /**
     * Gets the maximum value of the Z axis.
     * 
     * @return the string representation of the maximum value, or null if no Z axis
     * 
     */
    public String getZAxisMaxValueAsString() {
        CoordinateAxis axis = getZAxis();
        if (axis == null) {
            return null;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return NetCdfReader.getStandardZAsString(minMax.max, axis.getUnitsString());
    }

    /**
     * Checks for lat axis.
     * 
     * @return true if Latitude axis exists among coordinate axes.
     */
    public boolean hasLatAxis() {
        return getLatAxis() != null;
    }

    /**
     * Gets the Latitude coordinate axis if exists.
     * 
     * @return Latitude axis if exists, or null.
     */
    public CoordinateAxis getLatAxis() {
        return getCoordinateAxes(AxisType.Lat);
    }

    /**
     * Gets the minimum value of the latitude axis.
     * 
     * @return the minimum value, or Double.MIN_VALUE if no latitude axis
     * 
     */
    public double getLatAxisMinValue() {
        CoordinateAxis axis = getLatAxis();
        if (axis == null) {
            return Double.MIN_VALUE;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return minMax.min;
    }

    /**
     * Gets the minimum value of the latitude axis.
     * 
     * @return the minimum value normalized between +/-90, or Double.MIN_VALUE if no latitude axis
     * 
     */
    public double getLatNormalAxisMinValue() {
        return NetCdfReader.getLatNormal(getLatAxisMinValue());
    }

    /**
     * Gets the minimum value of the latitude axis.
     * 
     * @return the string representation of the minimum value, or null if no latitude axis
     * 
     */
    public String getLatAxisMinValueAsString() {
        CoordinateAxis axis = getLatAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return NetCdfReader.getStandardLatAsString(minMax.min);
    }

    /**
     * Gets the maximum value of the latitude axis.
     * 
     * @return the string representation of the maximum value, or null if no latitude axis
     */
    public String getLatAxisMaxValueAsString() {
        CoordinateAxis axis = getLatAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getStandardLatAsString(minMax.max);
    }

    /**
     * Gets the maximum value of the latitude axis.
     * 
     * @return the maximum value, or Double.MAX_VALUE if no latitude axis
     */
    public double getLatAxisMaxValue() {
        CoordinateAxis axis = getLatAxis();
        if (axis == null) {
            return Double.MAX_VALUE;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getLatNormal(minMax.max);
    }

    /**
     * Gets the maximum value of the latitude axis.
     * 
     * @return the maximum value normalized between +/-90, or Double.MAX_VALUE if no latitude axis
     */
    public double getLatNormalAxisMaxValue() {
        return NetCdfReader.getLatNormal(getLatAxisMaxValue());
    }

    /**
     * Checks for lon axis.
     * 
     * @return true if Longitude axis exists among coordinate axes.
     */
    public boolean hasLonAxis() {
        return getLonAxis() != null;
    }

    /**
     * Gets the Longitude coordinate axis if exists.
     * 
     * @return Longitude axis if exists, or null.
     */
    public CoordinateAxis getLonAxis() {
        return getCoordinateAxes(AxisType.Lon);
    }

    /**
     * Gets the minimum value of the longitude axis.
     * 
     * @return the minimum value, or Double.MIN_VALUE if no longitude axis
     * 
     */
    public double getLonAxisMinValue() {
        CoordinateAxis axis = getLonAxis();
        if (axis == null) {
            return Double.MIN_VALUE;
        }
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return minMax.min;
    }

    /**
     * Gets the minimum value of the longitude axis.
     * 
     * @return the minimum value normalized between +/-180, or Double.MIN_VALUE if no longitude axis
     * 
     */
    public double getLonNormalAxisMinValue() {
        return NetCdfReader.getLonNormal(getLonAxisMinValue());
    }

    /**
     * Gets the minimum value of the longitude axis.
     * 
     * @return the string representation of the minimum value, or null if no longitude axis
     * 
     */
    public String getLonAxisMinValueAsString() {
        CoordinateAxis axis = getLonAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return NetCdfReader.getStandardLonAsString(minMax.min);
    }

    /**
     * Gets the maximum value of the longitude axis.
     * 
     * @return the string representation of the maximum value, or null if no longitude axis
     */
    public String getLonAxisMaxValueAsString() {
        CoordinateAxis axis = getLonAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getStandardLonAsString(minMax.max);
    }

    /**
     * Gets the maximum value of the longitude axis.
     * 
     * @return the maximum value, or Double.MAX_VALUE if no longitude axis
     */
    public double getLonAxisMaxValue() {
        CoordinateAxis axis = getLonAxis();
        if (axis == null) {
            return Double.MAX_VALUE;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return minMax.max;
    }

    /**
     * Gets the maximum value of the longitude axis.
     * 
     * @return the maximum value normalized between +/-180, or Double.MAX_VALUE if no longitude axis
     */
    public double getLonNormalAxisMaxValue() {
        return NetCdfReader.getLonNormal(getLonAxisMaxValue());
    }

    /**
     * Checks for geo X axis.
     * 
     * @return true if GeoX axis exists among coordinate axes.
     */
    public boolean hasGeoXAxis() {
        return getGeoXAxis() != null;
    }

    /**
     * Checks for geo X axis with lon equivalence.
     * 
     * @param netCdfReader the net cdf reader
     * 
     * @return true if GeoX axis exists among coordinate axes and if there is a longitude variable equivalence
     * (Variable whose name is 'longitude' and with at least two dimensions X/Y).
     * 
     * @throws MotuException the motu exception
     */
    public boolean hasGeoXAxisWithLonEquivalence(NetCdfReader netCdfReader) throws MotuException {
        CoordinateAxis coord = getGeoXAxis();
        if (coord == null) {
            return false;
        }

        ParameterMetaData parameterMetaData = findLongitudeIgnoreCase();

        if (parameterMetaData == null) {
            return false;
        }

        List<Dimension> listDims = parameterMetaData.getDimensions();

        return netCdfReader.hasGeoXYDimensions(listDims);
    }

    /**
     * Checks for geo Y axis with lat equivalence.
     * 
     * @param netCdfReader the net cdf reader
     * 
     * @return true if GeoX axis exists among coordinate axes and if there is a longitude variable equivalence
     * (Variable whose name is 'longitude' and with at least two dimensions X/Y).
     * 
     * @throws MotuException the motu exception
     */
    public boolean hasGeoYAxisWithLatEquivalence(NetCdfReader netCdfReader) throws MotuException {
        CoordinateAxis coord = getGeoYAxis();
        if (coord == null) {
            return false;
        }

        ParameterMetaData parameterMetaData = findLatitudeIgnoreCase();

        if (parameterMetaData == null) {
            return false;
        }

        List<Dimension> listDims = parameterMetaData.getDimensions();

        return netCdfReader.hasGeoXYDimensions(listDims);
    }

    /**
     * Finds ParameterMetaData corresponding to a longitude name .
     * 
     * @return ParameterMetaData instance if found, otherwise null
     */
    public ParameterMetaData findLongitudeIgnoreCase() {

        ParameterMetaData parameterMetaData = null;
        for (String name : NetCdfReader.LONGITUDE_NAMES) {
            parameterMetaData = getParameterMetaDatas(name);
            if (parameterMetaData != null) {
                break;
            }
        }
        return parameterMetaData;
    }

    /**
     * Finds ParameterMetaData corresponding to a latitude name .
     * 
     * @return ParameterMetaData instance if found, otherwise null
     */
    public ParameterMetaData findLatitudeIgnoreCase() {

        ParameterMetaData parameterMetaData = null;
        for (String name : NetCdfReader.LATITUDE_NAMES) {
            parameterMetaData = getParameterMetaDatas(name);
            if (parameterMetaData != null) {
                break;
            }
        }
        return parameterMetaData;
    }

    /**
     * Gets the GeoX coordinate axis if exists.
     * 
     * @return GeoX axis if exists, or null.
     */
    public CoordinateAxis getGeoXAxis() {
        return getCoordinateAxes(AxisType.GeoX);
    }

    /**
     * Finds longitude variable associated to X axis. (Use for Netcdf product with X/Y axis, ie Mercator Artic
     * area)
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return Longitude variable, or null if not found.
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws MotuException the motu exception
     */
    public Variable getGeoXAxisAsLon(Product product) throws MotuException, NetCdfVariableNotFoundException {
        CoordinateAxis coord = getGeoXAxis();
        if (coord == null) {
            return null;
        }

        ParameterMetaData parameterMetaData = findLongitudeIgnoreCase();

        if (parameterMetaData == null) {
            return null;
        }

        return product.findVariable(parameterMetaData.getName());

    }

    /**
     * Gets the minimum value of the GeoX axis.
     * 
     * @return the minimum value, or Double.MIN_VALUE if no GeoX axis
     * 
     */
    public double getGeoXAxisMinValue() {
        CoordinateAxis axis = getGeoXAxis();
        if (axis == null) {
            return Double.MIN_VALUE;
        }
        
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return minMax.min;
    }

    /**
     * Gets the minimum value of the GeoX axis as Longitude.
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return the minimum value, or Double.MIN_VALUE if no GeoX axis or no Longitude variable.
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoXAxisMinValueAsLon(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        Variable variable = getGeoXAxisAsLon(product);
        return product.getMinValue(variable);
    }

    /**
     * Gets the minimum value of the GeoX axis as Longitude.
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return the minimum value normalized between +/-180, or Double.MIN_VALUE if no latitude axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoXAxisMinValueAsLonNormal(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        return NetCdfReader.getLonNormal(getGeoXAxisMinValueAsLon(product));
    }

    /**
     * Gets the minimum value of the GeoX axis.
     * 
     * @return the string representation of the minimum value, or null if no GeoX axis
     * 
     */
    public String getGeoXAxisMinValueAsString() {
        CoordinateAxis axis = getGeoXAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return NetCdfReader.getStandardGeoXYAsString(minMax.min, axis.getUnitsString());
    }

    /**
     * Gets the minimum value of the GeoX axis as Longitude.
     * 
     * @param product product instance of the corresponding metadata.
     * 
     * @return the string representation of the minimum value, or null if no GeoX axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public String getGeoXAxisMinValueAsLonString(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        double min = getGeoXAxisMinValueAsLon(product);
        if (min == Double.MIN_VALUE) {
            return null;
        }
        return NetCdfReader.getStandardLonAsString(min);
    }

    /**
     * Gets the maximum value of the GeoX axis.
     * 
     * @return the maximum value, or Double.MAX_VALUE if no GeoX axis
     * 
     */
    public double getGeoXAxisMaxValue() {
        CoordinateAxis axis = getGeoXAxis();
        if (axis == null) {
            return Double.MAX_VALUE;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return minMax.max;
    }

    /**
     * Gets the maximum value of the GeoX axis as Longitude.
     * 
     * @param product product instance of the corresponding metadata.
     * 
     * @return the maximum value, or Double.MAX_VALUE if no GeoX axis or no Longitude variable.
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoXAxisMaxValueAsLon(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        Variable variable = getGeoXAxisAsLon(product);
        return product.getMaxValue(variable);
    }

    /**
     * Gets the maximum value of the GeoX axis as Longitude.
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return the maximum value normalized between +/-180, or Double.MAX_VALUE if no latitude axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoXAxisMaxValueAsLonNormal(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        return NetCdfReader.getLonNormal(getGeoXAxisMaxValueAsLon(product));
    }

    /**
     * Gets the maximum value of the GeoX axis.
     * 
     * @return the string representation of the maximum value, or null if no GeoX axis
     * 
     */
    public String getGeoXAxisMaxValueAsString() {
        CoordinateAxis axis = getGeoXAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getStandardGeoXYAsString(minMax.max, axis.getUnitsString());
    }

    /**
     * Gets the maximum value of the GeoX axis as Longitude.
     * 
     * @param product product instance of the corresponding metadata.
     * 
     * @return the string representation of the maximum value, or null if no GeoX axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public String getGeoXAxisMaxValueAsLonString(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        double max = getGeoXAxisMaxValueAsLon(product);
        if (max == Double.MAX_VALUE) {
            return null;
        }

        return NetCdfReader.getStandardLonAsString(max);
    }

    /**
     * Checks for geo Y axis.
     * 
     * @return true if GeoY axis exists among coordinate axes.
     */
    public boolean hasGeoYAxis() {
        return getGeoYAxis() != null;
    }

    /**
     * Gets the GeoY coordinate axis if exists.
     * 
     * @return GeoY axis if exists, or null.
     */
    public CoordinateAxis getGeoYAxis() {
        return getCoordinateAxes(AxisType.GeoY);
    }

    /**
     * Finds latitude variable associated to Y axis. (Use for Netcdf product with X/Y axis, ie Mercator Artic
     * area)
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return Latitude variable, or null if not found.
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws MotuException the motu exception
     */
    public Variable getGeoYAxisAsLat(Product product) throws MotuException, NetCdfVariableNotFoundException {
        CoordinateAxis coord = getGeoYAxis();
        if (coord == null) {
            return null;
        }

        ParameterMetaData parameterMetaData = findLatitudeIgnoreCase();

        if (parameterMetaData == null) {
            return null;
        }

        return product.findVariable(parameterMetaData.getName());

    }

    /**
     * Gets the minimum value of the GeoY axis.
     * 
     * @return the minimum value, or Double.MIN_VALUE if no GeoY axis
     */
    public double getGeoYAxisMinValue() {
        CoordinateAxis axis = getGeoYAxis();
        if (axis == null) {
            return Double.MIN_VALUE;
        }
        
        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return minMax.min;
    }

    /**
     * Gets the minimum value of the GeoY axis as Latitude.
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return the minimum value, or Double.MIN_VALUE if no GeoY axis or no Latitude variable.
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoYAxisMinValueAsLat(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        Variable variable = getGeoYAxisAsLat(product);
        return product.getMinValue(variable);
    }

    /**
     * Gets the minimum value of the GeoY axis as Latitude.
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return the minimum value normalized between +/-90, or Double.MIN_VALUE if no latitude axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoYAxisMinValueAsLatNormal(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        return NetCdfReader.getLatNormal(getGeoYAxisMinValueAsLat(product));
    }

    /**
     * Gets the minimum value of the GeoY axis.
     * 
     * @return the string representation of the minimum value, or null if no GeoY axis
     * 
     */
    public String getGeoYAxisMinValueAsString() {
        CoordinateAxis axis = getGeoYAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);

        return NetCdfReader.getStandardGeoXYAsString(minMax.min, axis.getUnitsString());
    }

    /**
     * Gets the minimum value of the GeoY axis as Latitude.
     * 
     * @param product product instance of the corresponding metadata.
     * 
     * @return the string representation of the minimum value, or null if no GeoX axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public String getGeoYAxisMinValueAsLatString(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        double min = getGeoYAxisMinValueAsLat(product);
        if (min == Double.MIN_VALUE) {
            return null;
        }
        return NetCdfReader.getStandardLatAsString(min);
    }

    /**
     * Gets the maximum value of the GeoY axis.
     * 
     * @return the string representation of the maximum value, or Double.MAX_VALUE if no GeoY axis
     * 
     */
    public double getGeoYAxisMaxValue() {
        CoordinateAxis axis = getGeoYAxis();
        if (axis == null) {
            return Double.MAX_VALUE;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return minMax.max;
    }

    /**
     * Gets the maximum value of the GeoY axis as Latitude.
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return the maximum value, or Double.MIN_VALUE if no GeoY axis or no Latitude variable.
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoYAxisMaxValueAsLat(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        Variable variable = getGeoYAxisAsLat(product);
        return product.getMaxValue(variable);
    }

    /**
     * Gets the maximum value of the GeoY axis as Latitude.
     * 
     * @param product product instance of the correspnding metadata.
     * 
     * @return the maximum value normalized between +/-90, or Double.MAX_VALUE if no latitude axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public double getGeoYAxisMaxValueAsLatNormal(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        return NetCdfReader.getLatNormal(getGeoYAxisMaxValueAsLat(product));
    }

    /**
     * Gets the maximum value of the GeoY axis.
     * 
     * @return the string representation of the maximum value, or null if no GeoY axis
     * 
     */
    public String getGeoYAxisMaxValueAsString() {
        CoordinateAxis axis = getGeoYAxis();
        if (axis == null) {
            return null;
        }

        MAMath.MinMax minMax =  NetCdfWriter.getMinMaxSkipMissingData(axis, null);
        return NetCdfReader.getStandardGeoXYAsString(minMax.max, axis.getUnitsString());
    }

    /**
     * Gets the maximum value of the GeoY axis as Latitude.
     * 
     * @param product product instance of the corresponding metadata.
     * 
     * @return the string representation of the maximum value, or null if no GeoX axis
     * 
     * @throws NetCdfVariableNotFoundException the net cdf variable not found exception
     * @throws NetCdfVariableException the net cdf variable exception
     * @throws MotuException the motu exception
     */
    public String getGeoYAxisMaxValueAsLatString(Product product) throws MotuException, NetCdfVariableNotFoundException, NetCdfVariableException {
        double max = getGeoYAxisMaxValueAsLat(product);
        if (max == Double.MAX_VALUE) {
            return null;
        }

        return NetCdfReader.getStandardLatAsString(max);
    }

    /**
     * Gets the Latitude and Longitude coordinate axes if both exists.
     * 
     * @return list of Latitude and Longitude coordinate axes if both exists. Otherwise, null.
     */
    public List<CoordinateAxis> getLatLonAxis() {
        if (!hasLatLonAxis()) {
            return null;
        }
        List<CoordinateAxis> list = new ArrayList<CoordinateAxis>();
        list.add(getLatAxis());
        list.add(getLonAxis());

        return list;
    }

    /**
     * Gets the GeoX and GeoY coordinate axes if both exists.
     * 
     * @return list of GeoX and GeoY coordinate axes if both exists. Otherwise, null.
     */
    public List<CoordinateAxis> getGeoXYAxis() {
        if (!hasGeoXYAxis()) {
            return null;
        }
        List<CoordinateAxis> list = new ArrayList<CoordinateAxis>();
        list.add(getGeoXAxis());
        list.add(getGeoYAxis());

        return list;
    }

    /**
     * Gets the an instance of DocMetaData from the Documentation collection.
     * 
     * @param name title of the documentation.
     * 
     * @return an instance of DocMetaData, or null if not found. If more than one documentations have If
     *         several documentations have the same title, the first found is returned
     */
    public DocMetaData getDocumentation(String name) {
        DocMetaData docMetaData = null;

        if (getDocumentations() == null) {
            return docMetaData;
        }
        boolean found = false;
        for (Iterator<DocMetaData> it = getDocumentations().iterator(); it.hasNext();) {
            docMetaData = it.next();
            if (docMetaData.getTitle().equals(name)) {
                found = true;
                break;
            }
        }
        if (!found) {
            docMetaData = null;
        }

        return docMetaData;
    }

    // CSOFF: MultipleStringLiterals : avoid message
    /**
     * Gets the URL path to the 'quicklook' image of a variable. The URL path is : 'quicklook' href base + "/" +
     * datasetName (productId) + "/" + variable name (in lowercase) + ".gif" The 'quicklook' href is searched
     * among the Documentation collection of the product's metadata. If the quicklook url doesn't exist, an
     * empty string is returned
     * 
     * @param parameterMetaData instance of a ParameterMetaData (variable) from which to get the quicklook.
     * 
     * @return the URL of the quicklook, or empty string if not found.
     */
    public String getQuickLook(ParameterMetaData parameterMetaData) {
        StringBuffer quickLook = new StringBuffer();
        if (parameterMetaData == null) {
            return "";
        }

        DocMetaData docMetaData = getDocumentation(QUICKLOOK_KEY);
        if (docMetaData == null) {
            return "";
        }

        String base = docMetaData.getResource();
        quickLook.append(base);
        if (!base.endsWith("/")) {
            quickLook.append("/");
        }

        quickLook.append(getProductId());
        quickLook.append("/");
        quickLook.append(parameterMetaData.getName().toLowerCase());
        quickLook.append(QUICKLOOK_FILEEXT);
        // try to open URL
        // if error then return an empty string.
        String urlPath = quickLook.toString();
        InputStream in = null;
        try {
            URL url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            in = conn.getInputStream();
        } catch (Exception e) {
            urlPath = "";
        }
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception io) {
            io.getMessage();
        }

        return urlPath;
    }

    // CSON: MultipleStringLiterals

    /**
     * Gets the URL path to the 'quicklook' image of a variable. The URL path is : 'quicklook' href base +
     * datasetName (productId) + "_" + variable name + ".gif" The 'quicklook' href is searched among the
     * Documentation collection of the product's metadata. If the quicklook url doesn't exist, an empty string
     * is returned
     * 
     * @param varName name of the variable from which to get the quicklook.
     * 
     * @return the URL of the quicklook, or empty string if not found.
     */
    public String getQuickLook(String varName) {

        ParameterMetaData parameterMetaData = getParameterMetaDatas(varName);
        return getQuickLook(parameterMetaData);
    }

    /**
     * Gets the FTP service URL.
     * 
     * @return Returns the URL path to FTP service, or empty string if not found.
     */
    public String getFTPServiceURL() {

        DocMetaData docMetaData = getDocumentation(FTP_URL_KEY);
        if (docMetaData == null) {
            return "";
        }

        return docMetaData.getResource();
    }

    /**
     * Gets the product type service value.
     * 
     * @return Returns the product type service, or empty string if not found.
     */
    public String getProductTypeServiceValue() {

        DocMetaData docMetaData = getDocumentation(TYPE_KEY);
        if (docMetaData == null) {
            return "";
        }

        return docMetaData.getResource();
    }

    /**
     * Gets the LAS viewing service URL.
     * 
     * @return Returns the URL path to LAS viewing service, or empty string if not found.
     */
    public String getLASViewingServiceURL() {

        DocMetaData docMetaData = getDocumentation(LAS_URL_KEY);
        if (docMetaData == null) {
            return "";
        }

        return docMetaData.getResource();
    }

    /**
     * Gets the page site web URL.
     * 
     * @return Returns the URL path to Page Site Web, or empty string if not found.
     */
    public String getPageSiteWebURL() {

        DocMetaData docMetaData = getDocumentation(PAGE_SITE_WEB_URL_KEY);
        if (docMetaData == null) {
            return "";
        }

        return docMetaData.getResource();
    }

    /**
     * Gets the bulletin site URL.
     * 
     * @return Returns the URL path to Bulletin Site, or empty string if not found.
     */
    public String getBulletinSiteURL() {

        DocMetaData docMetaData = getDocumentation(BULLETIN_SITE_URL_KEY);
        if (docMetaData == null) {
            return "";
        }

        return docMetaData.getResource();
    }

    /** The time coverage. */
    DatePeriod timeCoverage = null;

    /**
     * Gets the time coverage.
     * 
     * @return the time coverage
     */
    public DatePeriod getTimeCoverage() {
        return timeCoverage;
    }

    /**
     * Sets the time coverage.
     * 
     * @param timeEnd the time end
     * @param timeStart the time start
     */
    public void setTimeCoverage(Date timeStart, Date timeEnd) {
        this.timeCoverage = new DatePeriod(timeStart, timeEnd);
    }

    /**
     * Sets the time coverage.
     * 
     * @param timeCoverage the time coverage
     */
    public void setTimeCoverage(DatePeriod timeCoverage) {
        this.timeCoverage = timeCoverage;
    }

}
// CSON: MultipleStringLiterals