//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation,
// vhudson-jaxb-3268
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2007.12.21 at 08:38:29 AM CET
//

package fr.cls.atoll.motu.opendap.server;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * <p>
 * Java class for ServiceType.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * 
 * <pre>
 *  &lt;simpleType name=&quot;ServiceType&quot;&gt;
 *    &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}token&quot;&gt;
 *      &lt;enumeration value=&quot;DODS&quot;/&gt;
 *      &lt;enumeration value=&quot;ADDE&quot;/&gt;
 *      &lt;enumeration value=&quot;NetCDF&quot;/&gt;
 *      &lt;enumeration value=&quot;Catalog&quot;/&gt;
 *      &lt;enumeration value=&quot;QueryCapability&quot;/&gt;
 *      &lt;enumeration value=&quot;Resolver&quot;/&gt;
 *      &lt;enumeration value=&quot;HTTP&quot;/&gt;
 *      &lt;enumeration value=&quot;FTP&quot;/&gt;
 *      &lt;enumeration value=&quot;GridFTP&quot;/&gt;
 *      &lt;enumeration value=&quot;WMS&quot;/&gt;
 *      &lt;enumeration value=&quot;WFS&quot;/&gt;
 *      &lt;enumeration value=&quot;WCS&quot;/&gt;
 *      &lt;enumeration value=&quot;WSDL&quot;/&gt;
 *      &lt;enumeration value=&quot;Compound&quot;/&gt;
 *      &lt;enumeration value=&quot;Other&quot;/&gt;
 *      &lt;enumeration value=&quot;XML&quot;/&gt;
 *    &lt;/restriction&gt;
 *  &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlEnum
public enum ServiceType {

    ADDE("ADDE"), @XmlEnumValue("Catalog")
    CATALOG("Catalog"), @XmlEnumValue("Compound")
    COMPOUND("Compound"), DODS("DODS"), FTP("FTP"), @XmlEnumValue("GridFTP")
    GRID_FTP("GridFTP"), HTTP("HTTP"), @XmlEnumValue("NetCDF")
    NET_CDF("NetCDF"), @XmlEnumValue("Other")
    OTHER("Other"), @XmlEnumValue("QueryCapability")
    QUERY_CAPABILITY("QueryCapability"), @XmlEnumValue("Resolver")
    RESOLVER("Resolver"), WCS("WCS"), WFS("WFS"), WMS("WMS"), WSDL("WSDL"), XML("XML");
    private final String value;

    ServiceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceType fromValue(String v) {
        for (ServiceType c : ServiceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}