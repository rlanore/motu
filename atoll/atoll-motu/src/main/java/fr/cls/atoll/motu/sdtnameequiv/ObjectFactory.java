//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-3268 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.12.21 at 10:21:41 AM CET 
//

package fr.cls.atoll.motu.sdtnameequiv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated
 * in the fr.cls.atoll.motu.sdtnameequiv package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML
 * content. The Java representation of XML content can consist of schema derived interfaces and classes
 * representing the binding of schema type definitions, element declarations and model groups. Factory methods
 * for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NetcdfName_QNAME = new QName("", "netcdfName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for
     * package: fr.cls.atoll.motu.sdtnameequiv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StandardName }
     * 
     */
    public StandardName createStandardName() {
        return new StandardName();
    }

    /**
     * Create an instance of {@link StandardNames }
     * 
     */
    public StandardNames createStandardNames() {
        return new StandardNames();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "netcdfName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createNetcdfName(String value) {
        return new JAXBElement<String>(_NetcdfName_QNAME, String.class, null, value);
    }

}