package fr.cls.atoll.motu.processor.wps.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.vfs.FileObject;
import org.apache.log4j.Logger;
import org.apache.xerces.dom.DocumentImpl;
import org.geotoolkit.parameter.DefaultParameterDescriptor;
import org.geotoolkit.parameter.Parameter;
import org.jgrapht.DirectedGraph;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.opengis.parameter.InvalidParameterTypeException;
import org.opengis.parameter.ParameterDescriptor;
import org.opengis.parameter.ParameterValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.cls.atoll.motu.library.data.ExtractCriteriaLatLon;
import fr.cls.atoll.motu.library.exception.MotuException;
import fr.cls.atoll.motu.library.exception.MotuInvalidDateException;
import fr.cls.atoll.motu.library.exception.MotuMarshallException;
import fr.cls.atoll.motu.library.intfce.Organizer;
import fr.cls.atoll.motu.library.xml.XMLErrorHandler;
import fr.cls.atoll.motu.library.xml.XMLUtils;
import fr.cls.atoll.motu.processor.iso19139.OperationMetadata;
import fr.cls.atoll.motu.processor.iso19139.ServiceMetadata;
import fr.cls.atoll.motu.processor.jgraht.OperationRelationshipEdge;
import fr.cls.atoll.motu.processor.opengis.ows110.BoundingBoxType;
import fr.cls.atoll.motu.processor.opengis.ows110.CodeType;
import fr.cls.atoll.motu.processor.opengis.wps100.ComplexDataCombinationsType;
import fr.cls.atoll.motu.processor.opengis.wps100.ComplexDataDescriptionType;
import fr.cls.atoll.motu.processor.opengis.wps100.ComplexDataType;
import fr.cls.atoll.motu.processor.opengis.wps100.DataInputsType;
import fr.cls.atoll.motu.processor.opengis.wps100.DataType;
import fr.cls.atoll.motu.processor.opengis.wps100.Execute;
import fr.cls.atoll.motu.processor.opengis.wps100.InputDescriptionType;
import fr.cls.atoll.motu.processor.opengis.wps100.InputReferenceType;
import fr.cls.atoll.motu.processor.opengis.wps100.InputType;
import fr.cls.atoll.motu.processor.opengis.wps100.LiteralDataType;
import fr.cls.atoll.motu.processor.opengis.wps100.LiteralInputType;
import fr.cls.atoll.motu.processor.opengis.wps100.ObjectFactory;
import fr.cls.atoll.motu.processor.opengis.wps100.OutputDefinitionType;
import fr.cls.atoll.motu.processor.opengis.wps100.OutputDescriptionType;
import fr.cls.atoll.motu.processor.opengis.wps100.ProcessDescriptionType;
import fr.cls.atoll.motu.processor.opengis.wps100.ProcessDescriptions;
import fr.cls.atoll.motu.processor.opengis.wps100.ResponseFormType;
import fr.cls.atoll.motu.processor.opengis.wps100.SupportedCRSsType;
import fr.cls.atoll.motu.processor.opengis.wps100.SupportedComplexDataInputType;
import fr.cls.atoll.motu.processor.wps.MotuWPSProcess;

/**
 * <br>
 * <br>
 * Copyright : Copyright (c) 2009. <br>
 * <br>
 * Soci�t� : CLS (Collecte Localisation Satellites)
 * 
 * @author $Author: dearith $
 * @version $Revision: 1.13 $ - $Date: 2009-09-30 13:35:49 $
 */
public class WPSFactory {

    /** Logger for this class. */
    private static final Logger LOG = Logger.getLogger(WPSFactory.class);

    public static final String DATETIME_PATTERN1 = "yyyy-MM-dd";
    public static final String DATETIME_PATTERN2 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATETIME_PATTERN3 = "yyyy-MM-dd' 'HH:mm:ss";

    public static final Map<String, DateTimeFormatter> DATETIME_FORMATTERS = new HashMap<String, DateTimeFormatter>();

    static {
        DATETIME_FORMATTERS.put(DATETIME_PATTERN1, DateTimeFormat.forPattern(DATETIME_PATTERN1));
        DATETIME_FORMATTERS.put(DATETIME_PATTERN2, DateTimeFormat.forPattern(DATETIME_PATTERN2));
        DATETIME_FORMATTERS.put(DATETIME_PATTERN3, DateTimeFormat.forPattern(DATETIME_PATTERN3));
    }
    /** The Constant SCHEMA_WPS_ALL. */
    // protected static final String HTTP_SCHEMA_WPS_EXECUTE_REQUEST =
    // "http://schemas.opengis.net/wps/1.0.0/wpsExecute_request.xsd";
    // protected final static String OGC_WPS = "ogcwps";
    // protected String wpsSchema = "schema/wps";
    // protected String localWpsSchemaPath;
    // protected String localWpsExecuteRequestSchemaRelPath = "/wps/1.0.0/wpsExecute_request.xsd";
    /** The Constant UTF8. */
    public static final String UTF8 = "UTF-8";

    /** The Constant METHOD_POST. */
    public static final String METHOD_POST = "POST";

    /** The jaxb context wps. */
    private static JAXBContext jaxbContextWPS = null;

    /** The marshaller wps. */
    private static Marshaller marshallerWPS = null;

    /** The unmarshaller wps. */
    private static Unmarshaller unmarshallerWPS = null;

    /** The object factory wps. */
    private static ObjectFactory objectFactoryWPS = null;

    /** The object factory ows. */
    private static fr.cls.atoll.motu.processor.opengis.ows110.ObjectFactory objectFactoryOWS = null;

    // public static JAXBContext getJaxbContextWPS() {
    // return jaxbContextWPS;
    // }
    //
    // public static Marshaller getMarshallerWPS() {
    // return marshallerWPS;
    // }
    //
    // public static Unmarshaller getUnmarshallerWPS() {
    // return unmarshallerWPS;
    // }

    /** The wps info. */
    protected static WPSInfo wpsInfo = null;

    /** The schema locations. */
    protected static ConcurrentMap<String, String> schemaLocations = new ConcurrentHashMap<String, String>();

    /**
     * Gets the schema locations.
     * 
     * @return the schema locations
     */
    public static ConcurrentMap<String, String> getSchemaLocations() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getSchemaLocations() - entering");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("getSchemaLocations() - exiting");
        }
        return schemaLocations;
    }

    /**
     * Gets the wps info instance.
     * 
     * @return the wps info instance
     * 
     * @throws MotuException the motu exception
     */
    public WPSInfo getWpsInfoInstance() throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getWpsInfoInstance() - entering");
        }

        if (wpsInfo == null) {
            wpsInfo.loadDescribeProcess();
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("getWpsInfoInstance() - exiting");
        }
        return wpsInfo;
    }

    /**
     * Instantiates a new wPS factory.
     * 
     * @param url the url
     * 
     * @throws MotuException the motu exception
     */
    public WPSFactory(String url) throws MotuException {

        WPSFactory.initSchemaLocations();
        WPSFactory.initJAXBWPS();
        wpsInfo = new WPSInfo(url);
        wpsInfo.loadDescribeProcess();

    }

    /**
     * Inits the schema locations.
     * 
     * @throws MotuException the motu exception
     */
    private static void initSchemaLocations() throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("initSchemaLocations() - entering");
        }

        WPSFactory.schemaLocations.putIfAbsent("WPS1.0.0",
                                               "http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_request.xsd");

        if (LOG.isDebugEnabled()) {
            LOG.debug("initSchemaLocations() - exiting");
        }
    }

    /**
     * Inits the jaxbwps.
     * 
     * @throws MotuException the motu exception
     */
    private static void initJAXBWPS() throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("initJAXBWPS() - entering");
        }
        if (WPSFactory.jaxbContextWPS != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("initJAXBWPS() - exiting");
            }
            return;
        }

        try {
            WPSFactory.jaxbContextWPS = JAXBContext.newInstance(MotuWPSProcess.WPS100_SHEMA_PACK_NAME);
            WPSFactory.marshallerWPS = WPSFactory.jaxbContextWPS.createMarshaller();
            WPSFactory.marshallerWPS.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            WPSFactory.unmarshallerWPS = WPSFactory.jaxbContextWPS.createUnmarshaller();

        } catch (JAXBException e) {
            LOG.error("initJAXBWPS()", e);
            throw new MotuException("Error in WPSInfo - initJAXBWPS ", e);

        }

        objectFactoryWPS = new ObjectFactory();
        objectFactoryOWS = new fr.cls.atoll.motu.processor.opengis.ows110.ObjectFactory();

        if (LOG.isDebugEnabled()) {
            LOG.debug("initJAXBWPS() - exiting");
        }
    }

    // public Execute createExecuteProcessRequest(DirectedGraph<OperationMetadata,
    // OperationRelationshipEdge<String>> directedGraph,
    // GraphPath<OperationMetadata, OperationRelationshipEdge<String>> graphPath) throws MotuException {
    // List<OperationRelationshipEdge<String>> edges = graphPath.getEdgeList();
    //
    // return createExecuteProcessRequest(directedGraph, edges);
    //
    // }
    //
    // public Execute createExecuteProcessRequest(DirectedGraph<OperationMetadata,
    // OperationRelationshipEdge<String>> directedGraph,
    // List<OperationRelationshipEdge<String>> edges) throws MotuException {
    //
    // ListIterator<OperationRelationshipEdge<String>> edgeIterator = edges.listIterator();
    //
    // return createExecuteProcessRequest(directedGraph, edgeIterator, null);
    //
    // }
    //
    // public Execute createExecuteProcessRequest(DirectedGraph<OperationMetadata,
    // OperationRelationshipEdge<String>> directedGraph,
    // ListIterator<OperationRelationshipEdge<String>> edgeIterator,
    // Execute parent) throws MotuException {
    //
    // if (!edgeIterator.hasNext()) {
    // return parent;
    // }
    //
    // OperationRelationshipEdge<String> edge = edgeIterator.next();
    // OperationMetadata operationMetadata = directedGraph.getEdgeSource(edge);
    //
    // List<OperationRelationshipEdge<String>> edges = new ArrayList<OperationRelationshipEdge<String>>();
    // edges.add(edge);
    //
    // Execute execute = createExecuteProcessRequest(directedGraph, operationMetadata.getParameterValueMap(),
    // operationMetadata.getInvocationName(), edges);
    //
    // return createExecuteProcessRequest(directedGraph, edgeIterator, execute);
    //
    // }

    // public Execute createExecuteProcessRequest(DirectedGraph<OperationMetadata,
    // OperationRelationshipEdge<String>> directedGraph,
    // Map<String, ParameterValue<?>> dataInputValues,
    // String processName,
    // List<OperationRelationshipEdge<String>> sourceEdges) throws MotuException {
    //
    // ProcessDescriptionType processDescriptionType =
    // getWpsInfoInstance().getProcessDescription(processName);
    //
    // if (processDescriptionType == null) {
    // throw new
    // MotuException(String.format("WPSFactory#createExecuteProcessRequest : Unknown process name '%s'",
    // processName));
    // }
    // return createExecuteProcessRequest(directedGraph, dataInputValues, processDescriptionType,
    // sourceEdges);
    //
    // }

    /**
     * Creates a new WPS object.
     * 
     * @param operationMetadata the operation metadata
     * @param directedGraph the directed graph
     * 
     * @return the execute
     * 
     * @throws MotuException the motu exception
     */
    public Execute createExecuteProcessRequest(OperationMetadata operationMetadata,
                                               DirectedGraph<OperationMetadata, OperationRelationshipEdge<String>> directedGraph)
            throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("createExecuteProcessRequest(OperationMetadata, DirectedGraph<OperationMetadata,OperationRelationshipEdge<String>>) - entering");
        }

        Map<String, ParameterValue<?>> dataInputValues = operationMetadata.getParameterValueMap();

        ProcessDescriptionType processDescriptionType = getWpsInfoInstance().getProcessDescription(operationMetadata.getInvocationName());

        if (processDescriptionType == null) {
            throw new MotuException(String.format("WPSFactory#createExecuteProcessRequest : Unknown process name '%s'", operationMetadata
                    .getInvocationName()));
        }

        ProcessDescriptions processDescriptions = wpsInfo.getProcessDescriptions();
        if (processDescriptions == null) {
            throw new MotuException("WPSFactory#createExecuteProcessRequest : list of process descriptions is null");
        }

        // create the execute object
        Execute execute = createExecute(processDescriptionType);

        // loop through each expected input in the describeprocess, and set it
        // based on what we have in the provided input map.
        if (processDescriptionType.getDataInputs() == null) {
            throw new MotuException(String.format("WPSFactory#createExecuteProcessRequest : process '%s' has no data input (data input is null)",
                                                  operationMetadata.getInvocationName()));
        }

        List<InputDescriptionType> inputs = processDescriptionType.getDataInputs().getInput();

        DataInputsType dataInputsType = objectFactoryWPS.createDataInputsType();

        for (InputDescriptionType inputDescriptionType : inputs) {

            String identifier = inputDescriptionType.getIdentifier().getValue();
            ParameterValue<?> parameterValue = dataInputValues.get(identifier);

            if (parameterValue == null) {
                continue;
            }
            if (parameterValue.getValue() == null) {
                continue;
            }

            Object inputValue = parameterValue.getValue();
            List<?> valueList = null;

            if (inputValue instanceof Map) {
                valueList = (List<?>) ((Map<?, ?>) inputValue).values();
            } else if (inputValue instanceof List) {
                valueList = (List<?>) inputValue;
            } else if (inputValue instanceof Collection) {
                throw new MotuException(
                        String
                                .format("WPSFactory#createExecuteProcessRequest : the value of the parameter '%s' is a collection which is not supported : '%s'",
                                        identifier,
                                        inputValue.getClass()));
            } else {
                List<Object> list = new ArrayList<Object>();
                list.add(inputValue);
                valueList = list;
            }

            if (valueList.isEmpty()) {
                continue;
            }

            ParameterValue<?> parameterValueUsed = operationMetadata.createParameterValue(identifier);
            // @SuppressWarnings("unchecked")
            // ParameterDescriptor parameterDescriptor = new DefaultParameterDescriptor(identifier, null,
            // object.getClass(), null, true);
            // @SuppressWarnings("unchecked")
            // Parameter parameterValueUsed = new Parameter(parameterDescriptor);

            for (Object inValue : valueList) {

                parameterValueUsed.setValue(inValue);

                InputType inputType = createInputType(inputDescriptionType);

                Set<OperationRelationshipEdge<String>> edges = null;
                OperationRelationshipEdge<String> edge = null;

                if (directedGraph != null) {
                    edges = directedGraph.outgoingEdgesOf(operationMetadata);

                    WPSFactory.checkEdgeInputParameter(edges, processDescriptionType);

                    edge = WPSFactory.getEdgeParameter(edges, identifier);
                }

                if (edge == null) {

                    DataType dataType = createInputDataType(inputDescriptionType, parameterValueUsed);

                    if (dataType == null) {
                        continue;
                    }
                    inputType.setData(dataType);

                } else {

                    OperationMetadata operationMetadataTarget = directedGraph.getEdgeTarget(edge);
                    directedGraph.getEdgeTarget(edge);

                    Execute executeChain = createExecuteProcessRequest(operationMetadataTarget, directedGraph);

                    int indexParamIn = WPSFactory.getEdgeParameterInIndex(edge, identifier);

                    String paramOut = WPSFactory.getEdgeParameterOutByIndex(edge, indexParamIn);

                    if (paramOut == null) {
                        throw new MotuException(
                                String
                                        .format("ERROR in WPSFactory#createExecuteProcessRequest - Parameters between two operation doesn't match. Source operation invocation name: '%s', target operation invocation name : '%s', index %d, source operation parameter '%s'.",
                                                operationMetadata.getInvocationName(),
                                                operationMetadataTarget.getInvocationName(),
                                                indexParamIn,
                                                identifier));
                    }

                    ProcessDescriptionType processDescriptionTypeTarget = getWpsInfoInstance().getProcessDescription(operationMetadataTarget
                            .getInvocationName());

                    if (processDescriptionTypeTarget == null) {
                        throw new MotuException(String.format("WPSFactory#createExecuteProcessRequest : Unknown process name '%s'",
                                                              operationMetadataTarget.getInvocationName()));
                    }

                    WPSFactory.checkEdgeOutputParameter(edges, processDescriptionTypeTarget);

                    List<OutputDescriptionType> outputsTarget = processDescriptionTypeTarget.getProcessOutputs().getOutput();
                    CodeType codeTypeTarget = null;

                    for (OutputDescriptionType outputDescriptionType : outputsTarget) {

                        String identifierTarget = outputDescriptionType.getIdentifier().getValue();
                        if (identifierTarget.equals(paramOut)) {
                            codeTypeTarget = cloneCodeType(outputDescriptionType.getIdentifier());
                            break;
                        }
                    }

                    if (codeTypeTarget == null) {
                        throw new MotuException(String
                                .format("WPSFactory#createExecuteProcessRequest : Unknown output definition  '%s' (process name '%s')",
                                        paramOut,
                                        operationMetadataTarget.getInvocationName()));
                    }

                    OutputDefinitionType outputDefinitionType = objectFactoryWPS.createOutputDefinitionType();
                    outputDefinitionType.setIdentifier(codeTypeTarget);

                    ResponseFormType responseFormType = objectFactoryWPS.createResponseFormType();
                    responseFormType.setRawDataOutput(outputDefinitionType);

                    executeChain.setResponseForm(responseFormType);

                    InputReferenceType inputReferenceType = createInputReferenceType(executeChain, operationMetadataTarget);

                    // InputReferenceType inputReferenceType = createInputReferenceType(inputDescriptionType,
                    // parameterValueUsed)e(inputDescriptionType, parameterValueUsed);
                    inputType.setReference(inputReferenceType);
                }

                dataInputsType.getInput().add(inputType);
            }

        }

        execute.setDataInputs(dataInputsType);

        if (LOG.isDebugEnabled()) {
            LOG.debug("createExecuteProcessRequest(OperationMetadata, DirectedGraph<OperationMetadata,OperationRelationshipEdge<String>>) - exiting");
        }
        return execute;

    }

    /**
     * Creates a new WPS object.
     * 
     * @param processDescriptionType the process description type
     * 
     * @return the execute
     * 
     * @throws MotuException the motu exception
     */
    public Execute createExecute(ProcessDescriptionType processDescriptionType) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createExecute(ProcessDescriptionType) - entering");
        }

        if (wpsInfo == null) {
            throw new MotuException("WPSFactory#createExecute : WPS info is null");
        }
        if (processDescriptionType == null) {
            throw new MotuException("WPSFactory#createExecute : processDescriptionType is null");
        }

        ProcessDescriptions processDescriptions = wpsInfo.getProcessDescriptions();
        if (processDescriptions == null) {
            throw new MotuException("WPSFactory#createExecuteProcessRequest : list of process descriptions is null");
        }

        Execute execute = objectFactoryWPS.createExecute();
        execute.setIdentifier(cloneCodeType(processDescriptionType.getIdentifier()));
        execute.setService(processDescriptions.getService());
        execute.setVersion(processDescriptions.getVersion());
        execute.setLanguage(processDescriptions.getLang());

        if (LOG.isDebugEnabled()) {
            LOG.debug("createExecute(ProcessDescriptionType) - exiting");
        }
        return execute;

    }

    /**
     * Creates a new WPS object.
     * 
     * @param inputDescriptionType the input description type
     * 
     * @return the input type
     * 
     * @throws MotuException the motu exception
     */
    public InputType createInputType(InputDescriptionType inputDescriptionType) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createInputType(InputDescriptionType) - entering");
        }

        if (inputDescriptionType == null) {
            throw new MotuException("WPSFactory#createInputType : inputDescriptionType is null");
        }

        InputType inputType = objectFactoryWPS.createInputType();
        inputType.setIdentifier(cloneCodeType(inputDescriptionType.getIdentifier()));
        inputType.setAbstract(inputDescriptionType.getAbstract());
        inputType.setTitle(inputDescriptionType.getTitle());

        if (LOG.isDebugEnabled()) {
            LOG.debug("createInputType(InputDescriptionType) - exiting");
        }
        return inputType;
    }

    /**
     * Clone code type.
     * 
     * @param identifier the identifier
     * 
     * @return the code type
     * 
     * @throws MotuException the motu exception
     */
    public CodeType cloneCodeType(CodeType identifier) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cloneCodeType(CodeType) - entering");
        }

        if (identifier == null) {
            throw new MotuException("WPSFactory#cloneCodeType : identifier is null");
        }

        CodeType newIdentifier = objectFactoryOWS.createCodeType();
        newIdentifier.setValue(identifier.getValue());
        newIdentifier.setCodeSpace(identifier.getCodeSpace());

        if (LOG.isDebugEnabled()) {
            LOG.debug("cloneCodeType(CodeType) - exiting");
        }
        return newIdentifier;
    }

    /**
     * Creates a new WPS object.
     * 
     * @param inputDescriptionType the input description type
     * @param parameterValue the parameter value
     * 
     * @return the data type
     * 
     * @throws MotuException the motu exception
     */
    public DataType createInputDataType(InputDescriptionType inputDescriptionType, ParameterValue<?> parameterValue) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createInputDataType(InputDescriptionType, ParameterValue<?>) - entering");
        }

        if (inputDescriptionType == null) {
            throw new MotuException("WPSFactory#createInputDataType : createInputDataType is null");
        }

        DataType dataType = null;

        if (WPSInfo.isLiteralData(inputDescriptionType)) {

            dataType = createLiteralDataType(inputDescriptionType.getLiteralData(), parameterValue);

        } else if (WPSInfo.isBoundingBoxData(inputDescriptionType)) {

            dataType = createBoundingBoxInputType(inputDescriptionType.getBoundingBoxData(), parameterValue);

        } else if (WPSInfo.isComplexData(inputDescriptionType)) {

            dataType = createComplexDataType(inputDescriptionType.getComplexData(), parameterValue);

        } else {
            throw new MotuException(String.format("WPSFactory#createInputDataType : Identifer '%s' : Unknown input data type", inputDescriptionType
                    .getIdentifier().getValue()));
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("createInputDataType(InputDescriptionType, ParameterValue<?>) - exiting");
        }
        return dataType;
    }

    /**
     * Creates a new WPS object.
     * 
     * @param literalInputType the literal input type
     * @param parameterValue the parameter value
     * 
     * @return the data type
     * 
     * @throws MotuException the motu exception
     */
    public DataType createLiteralDataType(LiteralInputType literalInputType, ParameterValue<?> parameterValue) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createLiteralDataType(LiteralInputType, ParameterValue<?>) - entering");
        }

        if (literalInputType == null) {
            throw new MotuException("WPSFactory#createLiteralDataType : literalInputType is null");
        }

        if (parameterValue == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createLiteralDataType(LiteralInputType, ParameterValue<?>) - exiting");
            }
            return null;
        }
        if (parameterValue.getValue() == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createLiteralDataType(LiteralInputType, ParameterValue<?>) - exiting");
            }
            return null;
        }

        LiteralDataType literalDataType = objectFactoryWPS.createLiteralDataType();
        literalDataType.setDataType(literalInputType.getDataType().getValue());

        try {
            literalDataType.setValue(parameterValue.getValue().toString());
        } catch (InvalidParameterTypeException e) {
            LOG.error("createLiteralDataType(LiteralInputType, ParameterValue<?>)", e);

            throw new MotuException(String.format("WPSFactory#createLiteralDataType : parameter '%s' : invalid value.", parameterValue.toString()), e);
        }

        DataType dataType = objectFactoryWPS.createDataType();
        dataType.setLiteralData(literalDataType);

        if (LOG.isDebugEnabled()) {
            LOG.debug("createLiteralDataType(LiteralInputType, ParameterValue<?>) - exiting");
        }
        return dataType;

    }

    /**
     * Creates a new WPS object.
     * 
     * @param complexDataInputType the complex data input type
     * @param parameterValue the parameter value
     * 
     * @return the data type
     * 
     * @throws MotuException the motu exception
     */
    public DataType createComplexDataType(SupportedComplexDataInputType complexDataInputType, ParameterValue<?> parameterValue) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createComplexDataType(SupportedComplexDataInputType, ParameterValue<?>) - entering");
        }

        if (complexDataInputType == null) {
            throw new MotuException("WPSFactory#createComplexDataType : literalInputType is null");
        }

        if (parameterValue == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createComplexDataType(SupportedComplexDataInputType, ParameterValue<?>) - exiting");
            }
            return null;
        }
        if (parameterValue.getValue() == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createComplexDataType(SupportedComplexDataInputType, ParameterValue<?>) - exiting");
            }
            return null;
        }

        ComplexDataType complexDataType = objectFactoryWPS.createComplexDataType();
        complexDataType.getContent().add(parameterValue.getValue().toString());

        ComplexDataCombinationsType complexDataCombinationsType = complexDataInputType.getSupported();
        ComplexDataDescriptionType complexDataDescriptionType = null;

        if (complexDataCombinationsType != null) {
            complexDataDescriptionType = (ComplexDataDescriptionType) complexDataCombinationsType.getFormat().get(0);
        }

        if (complexDataDescriptionType != null) {
            complexDataType.setSchema(complexDataDescriptionType.getSchema());
            complexDataType.setEncoding(complexDataDescriptionType.getEncoding());
            complexDataType.setMimeType(complexDataDescriptionType.getMimeType());
        }

        DataType dataType = objectFactoryWPS.createDataType();
        dataType.setComplexData(complexDataType);

        if (LOG.isDebugEnabled()) {
            LOG.debug("createComplexDataType(SupportedComplexDataInputType, ParameterValue<?>) - exiting");
        }
        return dataType;

    }

    /**
     * Creates a new WPS object.
     * 
     * @param boundingBoxInputType the bounding box input type
     * @param parameterValue the parameter value
     * 
     * @return the data type
     * 
     * @throws MotuException the motu exception
     */
    public DataType createBoundingBoxInputType(SupportedCRSsType boundingBoxInputType, ParameterValue<?> parameterValue) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createBoundingBoxInputType(SupportedCRSsType, ParameterValue<?>) - entering");
        }

        if (boundingBoxInputType == null) {
            throw new MotuException("WPSFactory#createBoundingBoxInputType : boundingBoxInputType is null");
        }
        if (parameterValue == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createBoundingBoxInputType(SupportedCRSsType, ParameterValue<?>) - exiting");
            }
            return null;
        }
        if (parameterValue.getValue() == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createBoundingBoxInputType(SupportedCRSsType, ParameterValue<?>) - exiting");
            }
            return null;
        }

        BoundingBoxType boundingBoxType = objectFactoryOWS.createBoundingBoxType();

        double[] values = { 0d };
        try {
            values = parameterValue.doubleValueList();
        } catch (InvalidParameterTypeException e) {
            LOG.error("createBoundingBoxInputType(SupportedCRSsType, ParameterValue<?>)", e);

            throw new MotuException(
                    String
                            .format("WPSFactory#createBoundingBoxInputType : parameter '%s' (value type '%s') - Unable to get values : '%s' type was expected.",
                                    parameterValue.getDescriptor().getName(),
                                    parameterValue.getValue().getClass().getName(),
                                    values.getClass().getCanonicalName()),
                    e);
        }

        if (values == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createBoundingBoxInputType(SupportedCRSsType, ParameterValue<?>) - exiting");
            }
            return null;
        }

        switch (values.length) {
        case 4: {
            boundingBoxType.getLowerCorner().add(values[0]);
            boundingBoxType.getLowerCorner().add(values[1]);
            boundingBoxType.getUpperCorner().add(values[2]);
            boundingBoxType.getUpperCorner().add(values[3]);
            break;
        }
        case 3: {
            boundingBoxType.getLowerCorner().add(values[0]);
            boundingBoxType.getLowerCorner().add(values[1]);
            boundingBoxType.getUpperCorner().add(values[2]);
            boundingBoxType.getUpperCorner().add(Double.parseDouble(ExtractCriteriaLatLon.LONGITUDE_MAX));
            break;
        }
        case 2: {
            boundingBoxType.getLowerCorner().add(values[0]);
            boundingBoxType.getLowerCorner().add(values[1]);
            boundingBoxType.getUpperCorner().add(Double.parseDouble(ExtractCriteriaLatLon.LATITUDE_MAX));
            boundingBoxType.getUpperCorner().add(Double.parseDouble(ExtractCriteriaLatLon.LONGITUDE_MAX));
            break;
        }
        case 1: {
            boundingBoxType.getLowerCorner().add(values[0]);
            boundingBoxType.getLowerCorner().add(Double.parseDouble(ExtractCriteriaLatLon.LONGITUDE_MIN));
            boundingBoxType.getUpperCorner().add(Double.parseDouble(ExtractCriteriaLatLon.LATITUDE_MAX));
            boundingBoxType.getUpperCorner().add(Double.parseDouble(ExtractCriteriaLatLon.LONGITUDE_MAX));
            break;
        }
        default: {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createBoundingBoxInputType(SupportedCRSsType, ParameterValue<?>) - exiting");
            }
            return null;
        }
        }

        boundingBoxType.setCrs(boundingBoxInputType.getDefault().getCRS());

        DataType dataType = objectFactoryWPS.createDataType();
        dataType.setBoundingBoxData(boundingBoxType);

        if (LOG.isDebugEnabled()) {
            LOG.debug("createBoundingBoxInputType(SupportedCRSsType, ParameterValue<?>) - exiting");
        }
        return dataType;

    }

    /**
     * Creates a new WPS object.
     * 
     * @param body the body
     * @param operationMetadata the operation metadata
     * 
     * @return the input reference type
     * 
     * @throws MotuException the motu exception
     */
    public InputReferenceType createInputReferenceType(Object body, OperationMetadata operationMetadata) throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createInputReferenceType(Object, OperationMetadata) - entering");
        }

        InputReferenceType inputReferenceType = objectFactoryWPS.createInputReferenceType();
        inputReferenceType.setEncoding(WPSFactory.UTF8);
        inputReferenceType.setMethod(WPSFactory.METHOD_POST);

        inputReferenceType.setHref(operationMetadata.getConnectPoint(0));

        // As InputReferenceType.body member is a java.lang.Object
        // we can't assign directly Execute object
        // we have to marshal Execute object and its children into a Document end then
        // assign doucment root element to body.

        Document doc = new DocumentImpl();

        // Create an element (tagname doesn't matter)
        // and append this element to the document.
        Element element = doc.createElement("NoName");
        doc.appendChild(element);

        try {
            // marshal body content the created element of the document
            marshallerWPS.marshal(body, element);
        } catch (JAXBException e) {
            LOG.error("createInputReferenceType(Object, OperationMetadata)", e);

            throw new MotuException("Error in WPSFActory#createInputReferenceType", e);
        }

        // Assign the creted document to body member.
        inputReferenceType.setBody(doc.getDocumentElement());

        if (LOG.isDebugEnabled()) {
            LOG.debug("createInputReferenceType(Object, OperationMetadata) - exiting");
        }
        return inputReferenceType;
    }

    /**
     * As dom document.
     * 
     * @param pObject the object
     * @param result the result
     * 
     * @return the document
     * 
     * @throws JAXBException the JAXB exception
     * @throws ParserConfigurationException the parser configuration exception
     */
    public Document asDOMDocument(

    Object pObject, Document result) throws JAXBException, javax.xml.parsers.ParserConfigurationException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("asDOMDocument(Object, Document) - entering");
        }

        // org.w3c.dom.Document result = pFactory.newDocumentBuild().newDocument();
        result = new DocumentImpl();

        marshallerWPS.marshal(pObject, result);

        if (LOG.isDebugEnabled()) {
            LOG.debug("asDOMDocument(Object, Document) - exiting");
        }
        return result;
    }

    /**
     * Marshall execute.
     * 
     * @param execute the execute
     * @param writer the writer
     * @param schemaLocation the schema location
     * 
     * @throws MotuMarshallException the motu marshall exception
     */
    public static void marshallExecute(Execute execute, Writer writer, String schemaLocation) throws MotuMarshallException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("marshallExecute(Execute, Writer, String) - entering");
        }

        if (writer == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("marshallExecute(Execute, Writer, String) - exiting");
            }
            return;
        }

        try {
            synchronized (WPSFactory.marshallerWPS) {
                WPSFactory.marshallerWPS.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "");
                if (!Organizer.isNullOrEmpty(schemaLocation)) {
                    WPSFactory.marshallerWPS.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
                }

                WPSFactory.marshallerWPS.marshal(execute, writer);
                writer.flush();
                writer.close();
            }
        } catch (JAXBException e) {
            LOG.error("marshallExecute(Execute, Writer, String)", e);

            throw new MotuMarshallException("Error in WPSFactory - marshallExecute", e);
        } catch (IOException e) {
            LOG.error("marshallExecute(Execute, Writer, String)", e);

            throw new MotuMarshallException("Error in WPSFactory - marshallExecute", e);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("marshallExecute(Execute, Writer, String) - exiting");
        }
    }

    /**
     * Unmarshall execute.
     * 
     * @param xmlFile the xml file
     * 
     * @return the execute
     * 
     * @throws MotuMarshallException the motu marshall exception
     */
    public Execute unmarshallExecute(String xmlFile) throws MotuMarshallException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("unmarshallExecute(String) - entering");
        }

        Source srcFile = new StreamSource(xmlFile);

        Execute returnExecute = unmarshallExecute(srcFile);
        if (LOG.isDebugEnabled()) {
            LOG.debug("unmarshallExecute(String) - exiting");
        }
        return returnExecute;
    }

    /**
     * Unmarshall execute.
     * 
     * @param xmlSource the xml source
     * 
     * @return the execute
     * 
     * @throws MotuMarshallException the motu marshall exception
     */
    public Execute unmarshallExecute(Source xmlSource) throws MotuMarshallException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("unmarshallExecute(Source) - entering");
        }

        if (WPSFactory.marshallerWPS == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("unmarshallExecute(Source) - exiting");
            }
            return null;
        }
        Execute execute = null;
        try {
            synchronized (WPSFactory.marshallerWPS) {

                execute = (Execute) WPSFactory.unmarshallerWPS.unmarshal(xmlSource);
            }
        } catch (JAXBException e) {
            LOG.error("unmarshallExecute(Source)", e);

            throw new MotuMarshallException("Error in WPSFactory - unmarshallExecute", e);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("unmarshallExecute(Source) - exiting");
        }
        return execute;

    }

    /**
     * Unmarshall execute.
     * 
     * @param xmlSource the xml source
     * 
     * @return the execute
     * 
     * @throws MotuMarshallException the motu marshall exception
     */
    public Execute unmarshallExecute(InputStream xmlSource) throws MotuMarshallException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("unmarshallExecute(InputStream) - entering");
        }

        if (WPSFactory.marshallerWPS == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("unmarshallExecute(InputStream) - exiting");
            }
            return null;
        }
        Execute execute = null;
        try {
            synchronized (WPSFactory.marshallerWPS) {

                execute = (Execute) WPSFactory.unmarshallerWPS.unmarshal(xmlSource);
            }
        } catch (JAXBException e) {
            LOG.error("unmarshallExecute(InputStream)", e);

            throw new MotuMarshallException("Error in WPSFactory - unmarshallExecute", e);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("unmarshallExecute(InputStream) - exiting");
        }
        return execute;

    }

    /**
     * Creates a new WPS object.
     * 
     * @param name the name
     * @param type the type
     * @param value the value
     * 
     * @return the parameter<?>
     */
    @SuppressWarnings("unchecked")
    public static Parameter<?> createParameter(final String name, final Class<?> type, final Object value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, Class<?>, Object) - entering");
        }

        final ParameterDescriptor<?> descriptor = new DefaultParameterDescriptor(name, null, type, null, true);
        final Parameter<?> parameter = new Parameter(descriptor);
        parameter.setValue(value);

        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, Class<?>, Object) - exiting");
        }
        return parameter;
    }

    /**
     * Creates a new WPS object.
     * 
     * @param name the name
     * @param value the value
     * 
     * @return the parameter<?>
     */
    public static Parameter<?> createParameter(final String name, final int value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, int) - entering");
        }

        Parameter<?> returnParameter = Parameter.create(name, value);
        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, int) - exiting");
        }
        return returnParameter;
        // final ParameterDescriptor<Integer> descriptor = new DefaultParameterDescriptor<Integer>(name,
        // Integer.class, null, null);
        // final Parameter<Integer> parameter = new Parameter<Integer>(descriptor);
        // parameter.setValue(value);
        // return parameter;

    }

    /**
     * Creates a new WPS object.
     * 
     * @param name the name
     * @param value the value
     * 
     * @return the parameter<?>
     */
    public static Parameter<?> createParameter(final String name, final double value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, double) - entering");
        }

        Parameter<?> returnParameter = Parameter.create(name, value, null);
        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, double) - exiting");
        }
        return returnParameter;
    }

    /**
     * Creates a new WPS object.
     * 
     * @param name the name
     * @param value the value
     * 
     * @return the parameter<?>
     */
    public static Parameter<?> createParameter(final String name, final long value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, long) - entering");
        }

        final ParameterDescriptor<Long> descriptor = new DefaultParameterDescriptor<Long>(name, Long.class, null, null);
        final Parameter<Long> parameter = new Parameter<Long>(descriptor);
        parameter.setValue(value);

        if (LOG.isDebugEnabled()) {
            LOG.debug("createParameter(String, long) - exiting");
        }
        return parameter;
    }

    /**
     * Checks if is edge parameter.
     * 
     * @param edge the edge
     * @param identifier the identifier
     * 
     * @return true, if is edge parameter
     */
    public static boolean isEdgeParameter(OperationRelationshipEdge<String> edge, String identifier) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("isEdgeParameter(OperationRelationshipEdge<String>, String) - entering");
        }

        if (edge == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("isEdgeParameter(OperationRelationshipEdge<String>, String) - exiting");
            }
            return false;
        }

        boolean returnboolean = edge.getParamInStartVertex().contains(identifier);
        if (LOG.isDebugEnabled()) {
            LOG.debug("isEdgeParameter(OperationRelationshipEdge<String>, String) - exiting");
        }
        return returnboolean;
    }

    /**
     * Gets the edge parameter in index.
     * 
     * @param edge the edge
     * @param identifier the identifier
     * 
     * @return the edge parameter in index
     */
    public static int getEdgeParameterInIndex(OperationRelationshipEdge<String> edge, String identifier) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterInIndex(OperationRelationshipEdge<String>, String) - entering");
        }

        if (edge == null) {
            int returnint = -1;
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterInIndex(OperationRelationshipEdge<String>, String) - exiting");
            }
            return returnint;
        }

        int returnint = getEdgeParameterIndex(edge.getParamInStartVertex(), identifier);
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterInIndex(OperationRelationshipEdge<String>, String) - exiting");
        }
        return returnint;

    }

    /**
     * Gets the edge parameter out index.
     * 
     * @param edge the edge
     * @param identifier the identifier
     * 
     * @return the edge parameter out index
     */
    public static int getEdgeParameterOutIndex(OperationRelationshipEdge<String> edge, String identifier) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterOutIndex(OperationRelationshipEdge<String>, String) - entering");
        }

        if (edge == null) {
            int returnint = -1;
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterOutIndex(OperationRelationshipEdge<String>, String) - exiting");
            }
            return returnint;
        }

        int returnint = getEdgeParameterIndex(edge.getParamOutStartVertex(), identifier);
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterOutIndex(OperationRelationshipEdge<String>, String) - exiting");
        }
        return returnint;

    }

    /**
     * Gets the edge parameter index.
     * 
     * @param parameters the parameters
     * @param identifier the identifier
     * 
     * @return the edge parameter index
     */
    public static int getEdgeParameterIndex(Collection<String> parameters, String identifier) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterIndex(Collection<String>, String) - entering");
        }

        if (parameters == null) {
            int returnint = -1;
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterIndex(Collection<String>, String) - exiting");
            }
            return returnint;
        }

        int index = -1;
        String param = null;

        for (Iterator<String> it = parameters.iterator(); it.hasNext();) {

            param = it.next();

            index++;

            if (param.equals(identifier)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("getEdgeParameterIndex(Collection<String>, String) - exiting");
                }
                return index;
            }

        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterIndex(Collection<String>, String) - exiting");
        }
        return index;
    }

    /**
     * Gets the edge parameter by index.
     * 
     * @param parameters the parameters
     * @param index the index
     * 
     * @return the edge parameter by index
     */
    public static String getEdgeParameterByIndex(Collection<String> parameters, int index) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterByIndex(Collection<String>, int) - entering");
        }

        if (parameters == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterByIndex(Collection<String>, int) - exiting");
            }
            return null;
        }
        if (index < 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterByIndex(Collection<String>, int) - exiting");
            }
            return null;
        }

        String param = null;
        String[] arrayParams = parameters.toArray(new String[parameters.size()]);

        if (index >= parameters.size()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterByIndex(Collection<String>, int) - exiting");
            }
            return null;
        }
        String returnString = arrayParams[index];
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterByIndex(Collection<String>, int) - exiting");
        }
        return returnString;
    }

    /**
     * Gets the edge parameter in by index.
     * 
     * @param edge the edge
     * @param index the index
     * 
     * @return the edge parameter in by index
     */
    public static String getEdgeParameterInByIndex(OperationRelationshipEdge<String> edge, int index) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterInByIndex(OperationRelationshipEdge<String>, int) - entering");
        }

        if (edge == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterInByIndex(OperationRelationshipEdge<String>, int) - exiting");
            }
            return null;
        }

        String returnString = getEdgeParameterByIndex(edge.getParamInStartVertex(), index);
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterInByIndex(OperationRelationshipEdge<String>, int) - exiting");
        }
        return returnString;

    }

    /**
     * Gets the edge parameter out by index.
     * 
     * @param edge the edge
     * @param index the index
     * 
     * @return the edge parameter out by index
     */
    public static String getEdgeParameterOutByIndex(OperationRelationshipEdge<String> edge, int index) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterOutByIndex(OperationRelationshipEdge<String>, int) - entering");
        }

        if (edge == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameterOutByIndex(OperationRelationshipEdge<String>, int) - exiting");
            }
            return null;
        }

        String returnString = getEdgeParameterByIndex(edge.getParamOutStartVertex(), index);
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameterOutByIndex(OperationRelationshipEdge<String>, int) - exiting");
        }
        return returnString;

    }

    /**
     * Gets the edge parameter.
     * 
     * @param edges the edges
     * @param identifier the identifier
     * 
     * @return the edge parameter
     */
    public static OperationRelationshipEdge<String> getEdgeParameter(Collection<OperationRelationshipEdge<String>> edges, String identifier) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameter(Collection<OperationRelationshipEdge<String>>, String) - entering");
        }

        if (edges == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("getEdgeParameter(Collection<OperationRelationshipEdge<String>>, String) - exiting");
            }
            return null;
        }

        for (OperationRelationshipEdge<String> edge : edges) {
            if (WPSFactory.isEdgeParameter(edge, identifier)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("getEdgeParameter(Collection<OperationRelationshipEdge<String>>, String) - exiting");
                }
                return edge;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("getEdgeParameter(Collection<OperationRelationshipEdge<String>>, String) - exiting");
        }
        return null;
    }

    /**
     * Check edge input parameter.
     * 
     * @param edges the edges
     * @param processDescriptionType the process description type
     * 
     * @throws MotuException the motu exception
     */
    public static void checkEdgeInputParameter(Collection<OperationRelationshipEdge<String>> edges, ProcessDescriptionType processDescriptionType)
            throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("checkEdgeInputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - entering");
        }

        if (edges == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("checkEdgeInputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
            }
            return;
        }
        if (processDescriptionType == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("checkEdgeInputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
            }
            return;
        }
        if (processDescriptionType.getDataInputs() == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("checkEdgeInputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
            }
            return;
        }

        List<InputDescriptionType> inputs = processDescriptionType.getDataInputs().getInput();

        for (OperationRelationshipEdge<String> edge : edges) {

            for (String paramIn : edge.getParamInStartVertex()) {

                if (edge == null) {
                    continue;
                }
                if (!WPSFactory.isInputIdentifier(paramIn, inputs)) {
                    throw new MotuException(String
                            .format("WPSFactory - Edge '%s' contains an unknown WPS input definition '%s' (process name '%s').",
                                    edge.getLabel(),
                                    paramIn,
                                    processDescriptionType.getIdentifier().getValue()));

                }
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("checkEdgeInputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
        }
    }

    /**
     * Check edge output parameter.
     * 
     * @param edges the edges
     * @param processDescriptionType the process description type
     * 
     * @throws MotuException the motu exception
     */
    public static void checkEdgeOutputParameter(Collection<OperationRelationshipEdge<String>> edges, ProcessDescriptionType processDescriptionType)
            throws MotuException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("checkEdgeOutputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - entering");
        }

        if (edges == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("checkEdgeOutputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
            }
            return;
        }
        if (processDescriptionType == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("checkEdgeOutputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
            }
            return;
        }
        if (processDescriptionType.getProcessOutputs() == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("checkEdgeOutputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
            }
            return;
        }

        List<OutputDescriptionType> outputs = processDescriptionType.getProcessOutputs().getOutput();

        for (OperationRelationshipEdge<String> edge : edges) {

            for (String paramIn : edge.getParamInStartVertex()) {

                if (edge == null) {
                    continue;
                }
                if (!WPSFactory.isOutputIdentifier(paramIn, outputs)) {
                    throw new MotuException(String
                            .format("WPSFactory - Edge '%s' contains an unknown WPS input definition '%s' (process name '%s').",
                                    edge.getLabel(),
                                    paramIn,
                                    processDescriptionType.getIdentifier().getValue()));

                }
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("checkEdgeOutputParameter(Collection<OperationRelationshipEdge<String>>, ProcessDescriptionType) - exiting");
        }
    }

    /**
     * Checks if is input identifier.
     * 
     * @param identifier the identifier
     * @param inputs the inputs
     * 
     * @return true, if is input identifier
     */
    public static boolean isInputIdentifier(String identifier, List<InputDescriptionType> inputs) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("isInputIdentifier(String, List<InputDescriptionType>) - entering");
        }

        if (identifier == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("isInputIdentifier(String, List<InputDescriptionType>) - exiting");
            }
            return false;
        }
        if (inputs == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("isInputIdentifier(String, List<InputDescriptionType>) - exiting");
            }
            return false;
        }
        boolean identifierExists = false;

        for (InputDescriptionType inputDescriptionType : inputs) {

            if (inputDescriptionType.getIdentifier().getValue().equals(identifier)) {
                identifierExists = true;
                break;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("isInputIdentifier(String, List<InputDescriptionType>) - exiting");
        }
        return identifierExists;
    }

    /**
     * Checks if is output identifier.
     * 
     * @param identifier the identifier
     * @param outputs the outputs
     * 
     * @return true, if is output identifier
     */
    public static boolean isOutputIdentifier(String identifier, List<OutputDescriptionType> outputs) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("isOutputIdentifier(String, List<OutputDescriptionType>) - entering");
        }

        if (identifier == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("isOutputIdentifier(String, List<OutputDescriptionType>) - exiting");
            }
            return false;
        }
        if (outputs == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("isOutputIdentifier(String, List<OutputDescriptionType>) - exiting");
            }
            return false;
        }
        boolean identifierExists = false;

        for (OutputDescriptionType outputDefinitionType : outputs) {

            if (outputDefinitionType.getIdentifier().getValue().equals(identifier)) {
                identifierExists = true;
                break;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("isOutputIdentifier(String, List<OutputDescriptionType>) - exiting");
        }
        return identifierExists;
    }

    /**
     * Validate wps from string.
     * 
     * @param xmlFile the xml file
     * @param schemaPath the schema path
     * 
     * @return the list< string>
     * 
     * @throws MotuException the motu exception
     */
    public static List<String> validateWPSExecuteRequest(String wpsSchema, String localWpsSchemaPath, String localWpsRootSchemaRelPath, String xmlFile)
            throws MotuException {

        // String[] inSchema = new String[] { HTTP_SCHEMA_WPS_EXECUTE_REQUEST, };
        String[] inSchema = WPSFactory.getWPSExecuteRequestSchemaAsString(wpsSchema, localWpsSchemaPath, localWpsRootSchemaRelPath);
        XMLErrorHandler errorHandler = XMLUtils.validateXML(inSchema, xmlFile);

        if (errorHandler == null) {
            throw new MotuException("ERROR in WPSFactory.validateWPSExecuteRequest - Motu configuration schema : XMLErrorHandler is null");
        }
        return errorHandler.getErrors();

    }

    public static String[] getWPSExecuteRequestSchemaAsString(String schemaPath, String localWPSSchemaPath, String localWPSRootSchemaRelPath)
            throws MotuException {

        String[] inS = null;
        try {
            List<String> stringList = new ArrayList<String>();
            String localWPSRootSchemaPath = String.format("%s%s", localWPSSchemaPath, localWPSRootSchemaRelPath);

            FileObject dest = Organizer.resolveFile(localWPSRootSchemaPath);
            boolean hasWPSasLocalSchema = false;
            if (dest != null) {
                hasWPSasLocalSchema = dest.exists();
            }

            if (hasWPSasLocalSchema) {
                dest.close();

            } else {

                URL url = null;
                if (!ServiceMetadata.isNullOrEmpty(schemaPath)) {
                    url = Organizer.findResource(schemaPath);

                } else {
                    url = Organizer.findResource(localWPSRootSchemaRelPath);
                    String[] str = url.toString().split(localWPSRootSchemaRelPath);
                    url = new URL(str[0]);
                }

                FileObject jarFile = Organizer.resolveFile(url.toString());

                // List the children of the Jar file
                // FileObject[] children = null;
                // try {
                // children = jarFile.getChildren();
                // } catch (FileSystemException e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }
                // System.out.println("Children of " + jarFile.getName().getURI());
                // for (int i = 0; i < children.length; i++) {
                // System.out.println(children[i].getName().getBaseName());
                // }

                dest = Organizer.resolveFile(localWPSSchemaPath);
                Organizer.copyFile(jarFile, dest);
            }

            stringList.add(localWPSRootSchemaPath);
            inS = new String[stringList.size()];
            inS = stringList.toArray(inS);

        } catch (Exception e) {
            throw new MotuException("ERROR in WPSFactory#getWPSExecuteRequestSchemaAsString", e);
        }

        return inS;
    }

    /**
     * Convert a given date into a string representation.
     * 
     * @param dt the date to print.
     * @return the string representation.
     */
    public static String DateTimeToString(DateTime dt) {
        return WPSFactory.DATETIME_FORMATTERS.get(WPSFactory.DATETIME_PATTERN2).print(dt);
    }

    /**
     * Convert a given date into a string representation.
     * 
     * @param dt the date to print.
     * @return the string representation.
     */
    public static String DateToString(DateTime dt) {
        return WPSFactory.DATETIME_FORMATTERS.get(WPSFactory.DATETIME_PATTERN1).print(dt);
    }

    /**
     * Convert a given string date representation into an instance of Joda time date.
     * 
     * @param s the string to convert into a date.
     * @return a {@link DateTime} instance.
     * @throws MotuInvalidDateException
     */
    public static DateTime StringToDateTime(String s) throws MotuInvalidDateException {
        DateTime dateTime = null;

        StringBuffer stringBuffer = new StringBuffer();
        for (DateTimeFormatter dateTimeFormatter : WPSFactory.DATETIME_FORMATTERS.values()) {
            try {
                dateTime = dateTimeFormatter.parseDateTime(s);
            } catch (IllegalArgumentException e) {
                stringBuffer.append(e.getMessage());
                stringBuffer.append("\n");
            }
        }

        if (dateTime == null) {
            throw new MotuInvalidDateException(s, new MotuException(String.format("%s.\nAcceptable format are '%s'",
                                                                                  stringBuffer.toString(),
                                                                                  WPSFactory.DATETIME_FORMATTERS.keySet().toString())));
        }

        return dateTime;
    }

}
