//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.05 at 02:32:28 PM CET 
//


package fr.cls.atoll.motu.web.dal.config.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import fr.cls.atoll.motu.library.converter.jaxb.JodaPeriodAdapter;
import org.joda.time.Period;


/**
 * 
 * 			 File system configuration.
 * 			 Allows to set protocol communication options and parameters for a host.
 * 			
 * 
 * <p>Java class for configFileSystemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="configFileSystemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{}sftpOptions"/>
 *       &lt;attGroup ref="{}ftpOptions"/>
 *       &lt;attGroup ref="{}proxyOptions"/>
 *       &lt;attribute name="host" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configFileSystemType")
public class ConfigFileSystemType {

    @XmlAttribute(name = "host", required = true)
    protected String host;
    @XmlAttribute(name = "sftpUserDirIsRoot")
    protected Boolean sftpUserDirIsRoot;
    @XmlAttribute(name = "sftpSessionTimeOut")
    @XmlJavaTypeAdapter(JodaPeriodAdapter.class)
    @XmlSchemaType(name = "duration")
    protected Period sftpSessionTimeOut;
    @XmlAttribute(name = "strictHostKeyChecking")
    protected String strictHostKeyChecking;
    @XmlAttribute(name = "ftpUserDirIsRoot")
    protected Boolean ftpUserDirIsRoot;
    @XmlAttribute(name = "ftpPassiveMode")
    protected Boolean ftpPassiveMode;
    @XmlAttribute(name = "ftpDataTimeOut")
    @XmlJavaTypeAdapter(JodaPeriodAdapter.class)
    @XmlSchemaType(name = "duration")
    protected Period ftpDataTimeOut;
    @XmlAttribute(name = "useProxy")
    protected Boolean useProxy;
    @XmlAttribute(name = "useFtpProxy")
    protected Boolean useFtpProxy;
    @XmlAttribute(name = "useSftpProxy")
    protected Boolean useSftpProxy;
    @XmlAttribute(name = "useSocksProxy")
    protected Boolean useSocksProxy;
    @XmlAttribute(name = "sftpProxyLogin")
    protected String sftpProxyLogin;
    @XmlAttribute(name = "sftpProxyPwd")
    protected String sftpProxyPwd;
    @XmlAttribute(name = "sftpProxyHost")
    protected String sftpProxyHost;
    @XmlAttribute(name = "sftpProxyPort")
    protected String sftpProxyPort;
    @XmlAttribute(name = "socksProxyLogin")
    protected String socksProxyLogin;
    @XmlAttribute(name = "socksProxyPwd")
    protected String socksProxyPwd;
    @XmlAttribute(name = "socksProxyHost")
    protected String socksProxyHost;
    @XmlAttribute(name = "socksProxyPort")
    protected String socksProxyPort;
    @XmlAttribute(name = "proxyLogin")
    protected String proxyLogin;
    @XmlAttribute(name = "proxyPwd")
    protected String proxyPwd;
    @XmlAttribute(name = "proxyHost")
    protected String proxyHost;
    @XmlAttribute(name = "proxyPort")
    protected String proxyPort;
    @XmlAttribute(name = "ftpProxyLogin")
    protected String ftpProxyLogin;
    @XmlAttribute(name = "ftpProxyPwd")
    protected String ftpProxyPwd;
    @XmlAttribute(name = "ftpProxyHost")
    protected String ftpProxyHost;
    @XmlAttribute(name = "ftpProxyPort")
    protected String ftpProxyPort;

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Gets the value of the sftpUserDirIsRoot property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getSftpUserDirIsRoot() {
        return sftpUserDirIsRoot;
    }

    /**
     * Sets the value of the sftpUserDirIsRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSftpUserDirIsRoot(Boolean value) {
        this.sftpUserDirIsRoot = value;
    }

    /**
     * Gets the value of the sftpSessionTimeOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Period getSftpSessionTimeOut() {
        if (sftpSessionTimeOut == null) {
            return new JodaPeriodAdapter().unmarshal("PT0M");
        } else {
            return sftpSessionTimeOut;
        }
    }

    /**
     * Sets the value of the sftpSessionTimeOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSftpSessionTimeOut(Period value) {
        this.sftpSessionTimeOut = value;
    }

    /**
     * Gets the value of the strictHostKeyChecking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrictHostKeyChecking() {
        if (strictHostKeyChecking == null) {
            return "no";
        } else {
            return strictHostKeyChecking;
        }
    }

    /**
     * Sets the value of the strictHostKeyChecking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrictHostKeyChecking(String value) {
        this.strictHostKeyChecking = value;
    }

    /**
     * Gets the value of the ftpUserDirIsRoot property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getFtpUserDirIsRoot() {
        return ftpUserDirIsRoot;
    }

    /**
     * Sets the value of the ftpUserDirIsRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFtpUserDirIsRoot(Boolean value) {
        this.ftpUserDirIsRoot = value;
    }

    /**
     * Gets the value of the ftpPassiveMode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getFtpPassiveMode() {
        return ftpPassiveMode;
    }

    /**
     * Sets the value of the ftpPassiveMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFtpPassiveMode(Boolean value) {
        this.ftpPassiveMode = value;
    }

    /**
     * Gets the value of the ftpDataTimeOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Period getFtpDataTimeOut() {
        return ftpDataTimeOut;
    }

    /**
     * Sets the value of the ftpDataTimeOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpDataTimeOut(Period value) {
        this.ftpDataTimeOut = value;
    }

    /**
     * Gets the value of the useProxy property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getUseProxy() {
        if (useProxy == null) {
            return false;
        } else {
            return useProxy;
        }
    }

    /**
     * Sets the value of the useProxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseProxy(Boolean value) {
        this.useProxy = value;
    }

    /**
     * Gets the value of the useFtpProxy property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getUseFtpProxy() {
        if (useFtpProxy == null) {
            return false;
        } else {
            return useFtpProxy;
        }
    }

    /**
     * Sets the value of the useFtpProxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseFtpProxy(Boolean value) {
        this.useFtpProxy = value;
    }

    /**
     * Gets the value of the useSftpProxy property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getUseSftpProxy() {
        if (useSftpProxy == null) {
            return false;
        } else {
            return useSftpProxy;
        }
    }

    /**
     * Sets the value of the useSftpProxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseSftpProxy(Boolean value) {
        this.useSftpProxy = value;
    }

    /**
     * Gets the value of the useSocksProxy property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getUseSocksProxy() {
        if (useSocksProxy == null) {
            return false;
        } else {
            return useSocksProxy;
        }
    }

    /**
     * Sets the value of the useSocksProxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseSocksProxy(Boolean value) {
        this.useSocksProxy = value;
    }

    /**
     * Gets the value of the sftpProxyLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSftpProxyLogin() {
        return sftpProxyLogin;
    }

    /**
     * Sets the value of the sftpProxyLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSftpProxyLogin(String value) {
        this.sftpProxyLogin = value;
    }

    /**
     * Gets the value of the sftpProxyPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSftpProxyPwd() {
        return sftpProxyPwd;
    }

    /**
     * Sets the value of the sftpProxyPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSftpProxyPwd(String value) {
        this.sftpProxyPwd = value;
    }

    /**
     * Gets the value of the sftpProxyHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSftpProxyHost() {
        if (sftpProxyHost == null) {
            return "proxy-prod.cls.fr";
        } else {
            return sftpProxyHost;
        }
    }

    /**
     * Sets the value of the sftpProxyHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSftpProxyHost(String value) {
        this.sftpProxyHost = value;
    }

    /**
     * Gets the value of the sftpProxyPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSftpProxyPort() {
        if (sftpProxyPort == null) {
            return "8080";
        } else {
            return sftpProxyPort;
        }
    }

    /**
     * Sets the value of the sftpProxyPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSftpProxyPort(String value) {
        this.sftpProxyPort = value;
    }

    /**
     * Gets the value of the socksProxyLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocksProxyLogin() {
        return socksProxyLogin;
    }

    /**
     * Sets the value of the socksProxyLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocksProxyLogin(String value) {
        this.socksProxyLogin = value;
    }

    /**
     * Gets the value of the socksProxyPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocksProxyPwd() {
        return socksProxyPwd;
    }

    /**
     * Sets the value of the socksProxyPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocksProxyPwd(String value) {
        this.socksProxyPwd = value;
    }

    /**
     * Gets the value of the socksProxyHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocksProxyHost() {
        if (socksProxyHost == null) {
            return "proxy-prod.cls.fr";
        } else {
            return socksProxyHost;
        }
    }

    /**
     * Sets the value of the socksProxyHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocksProxyHost(String value) {
        this.socksProxyHost = value;
    }

    /**
     * Gets the value of the socksProxyPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocksProxyPort() {
        if (socksProxyPort == null) {
            return "1080";
        } else {
            return socksProxyPort;
        }
    }

    /**
     * Sets the value of the socksProxyPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocksProxyPort(String value) {
        this.socksProxyPort = value;
    }

    /**
     * Gets the value of the proxyLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyLogin() {
        return proxyLogin;
    }

    /**
     * Sets the value of the proxyLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyLogin(String value) {
        this.proxyLogin = value;
    }

    /**
     * Gets the value of the proxyPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyPwd() {
        return proxyPwd;
    }

    /**
     * Sets the value of the proxyPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyPwd(String value) {
        this.proxyPwd = value;
    }

    /**
     * Gets the value of the proxyHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyHost() {
        if (proxyHost == null) {
            return "proxy-prod.cls.fr";
        } else {
            return proxyHost;
        }
    }

    /**
     * Sets the value of the proxyHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyHost(String value) {
        this.proxyHost = value;
    }

    /**
     * Gets the value of the proxyPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyPort() {
        if (proxyPort == null) {
            return "8080";
        } else {
            return proxyPort;
        }
    }

    /**
     * Sets the value of the proxyPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyPort(String value) {
        this.proxyPort = value;
    }

    /**
     * Gets the value of the ftpProxyLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtpProxyLogin() {
        return ftpProxyLogin;
    }

    /**
     * Sets the value of the ftpProxyLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpProxyLogin(String value) {
        this.ftpProxyLogin = value;
    }

    /**
     * Gets the value of the ftpProxyPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtpProxyPwd() {
        return ftpProxyPwd;
    }

    /**
     * Sets the value of the ftpProxyPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpProxyPwd(String value) {
        this.ftpProxyPwd = value;
    }

    /**
     * Gets the value of the ftpProxyHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtpProxyHost() {
        if (ftpProxyHost == null) {
            return "proxy-prod.cls.fr";
        } else {
            return ftpProxyHost;
        }
    }

    /**
     * Sets the value of the ftpProxyHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpProxyHost(String value) {
        this.ftpProxyHost = value;
    }

    /**
     * Gets the value of the ftpProxyPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtpProxyPort() {
        if (ftpProxyPort == null) {
            return "8080";
        } else {
            return ftpProxyPort;
        }
    }

    /**
     * Sets the value of the ftpProxyPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpProxyPort(String value) {
        this.ftpProxyPort = value;
    }

}
