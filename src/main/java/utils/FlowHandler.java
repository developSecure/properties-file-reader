package utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class FlowHandler extends DefaultHandler {
    private static final String API_FLOW = "flow";
    private static final String SUB_FLOW = "sub-flow";
    private static final String FLOW_REF = "flow-ref";
    private static final String HTTP_REQUEST = "http:request";
    private static final String HTTP_REQUEST_CONFIG = "http:request-config";
    private static final String SCHEDULER = "scheduler";
    private static final String CRON = "cron";

    private Root root;
    private ApiFlow currentFlow;
    private ApiConfig currentApiConfig;
    private FlowComponent currentFlowComponent;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        root = new Root();
        root.setApiFlows(new ArrayList<>());
        root.setApiConfigs(new ArrayList<>());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case API_FLOW:
                initializeApiFlow(API_FLOW, attributes);
                break;
            case SUB_FLOW:
                initializeApiFlow(SUB_FLOW, attributes);
                break;
            case FLOW_REF:
                currentFlowComponent = new FlowComponent();
                currentFlowComponent.setComponentAttributes(new HashMap<>());
                currentFlowComponent.setComponentName(attributes.getValue("name"));
                currentFlowComponent.getComponentAttributes().put("flowRefName", attributes.getValue("name"));
                break;
            case HTTP_REQUEST:
                currentFlowComponent = new FlowComponent();
                currentFlowComponent.setComponentAttributes(new HashMap<>());
                currentFlowComponent.setComponentName("request");
                currentFlowComponent.getComponentAttributes().put("httpRequestMethod", attributes.getValue("method"));
                currentFlowComponent.getComponentAttributes().put("httpRequestPath", attributes.getValue("path"));
                currentFlowComponent.getComponentAttributes().put("httpRequestConfigRef", attributes.getValue("config-ref"));
                break;
            case HTTP_REQUEST_CONFIG:
                initializeApiConfig();
                currentFlowComponent = new FlowComponent();
                currentFlowComponent.setComponentAttributes(new HashMap<>());
                currentFlowComponent.setComponentName(attributes.getValue("name"));
                currentFlowComponent.getComponentAttributes().put("httpRequestConfigName", attributes.getValue("name"));
                currentFlowComponent.getComponentAttributes().put("httpRequestBasePath", attributes.getValue("basePath"));
                break;
            case SCHEDULER:
                currentFlowComponent = new FlowComponent();
                currentFlowComponent.setComponentAttributes(new HashMap<>());
                currentFlowComponent.setComponentName(String.valueOf(new StringBuilder(attributes.getValue("doc:name")).append("_scheduler")));
                break;
            case CRON:
                if(currentFlowComponent.getComponentName().contains("_scheduler")) {
                    currentFlowComponent.getComponentAttributes().put("timeProperty",attributes.getValue("expression"));
                };
                break;
        }
    }

    private void initializeApiFlow(String flowType, Attributes attributes) {
        currentFlow = new ApiFlow();
        currentFlow.setFlowComponents(new ArrayList<>());
        currentFlow.setFlowAttributes(new HashMap<>());
        currentFlow.setFlowName(attributes.getValue("name"));
        if (flowType != null && flowType.equals("sub-flow")) {
            currentFlow.getFlowAttributes().put("subFlowName", attributes.getValue("name"));
        } else {
            currentFlow.getFlowAttributes().put("flowName", attributes.getValue("name"));
        }
        root.getApiFlows().add(currentFlow);
    }

    private void initializeApiConfig() {
        currentApiConfig = new ApiConfig();
        currentApiConfig.setFlowComponents(new ArrayList<>());
        root.getApiConfigs().add(currentApiConfig);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case FLOW_REF:
                if (currentFlowComponent != null 
                && !currentFlowComponent.getComponentAttributes().get("flowRefName").matches("^.*mckesson-mule-common.*")
                && !currentFlowComponent.getComponentAttributes().get("flowRefName").equals(currentFlow.getFlowName())) {
                    currentFlow.getFlowComponents().add(currentFlowComponent);
                }
                currentFlowComponent = null;
                break;
            case SUB_FLOW:
                if (currentFlowComponent != null) {
                    currentFlow.getFlowComponents().add(currentFlowComponent);
                }
                break;
            case HTTP_REQUEST:
                if (currentFlowComponent != null) {
                    currentFlow.getFlowComponents().add(currentFlowComponent);
                }
                currentFlowComponent = null;
                break;
            case HTTP_REQUEST_CONFIG:
                if (currentFlowComponent != null) {
                    currentApiConfig.getFlowComponents().add(currentFlowComponent);
                }
                break;
            case SCHEDULER:
                if (currentFlowComponent != null) {
                    currentFlow.getFlowComponents().add(currentFlowComponent);
                }
                currentFlowComponent = null;
                break;
        }
    }

    public Root getRoot() {
        return root;
    }
}
