package utils;

import java.util.List;
import java.util.Map;

public class ApiFlow {
    private String flowName;
    private List<FlowComponent> flowComponents;
    private Map<String,String> flowAttributes;

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public List<FlowComponent> getFlowComponents() {
        return flowComponents;
    }

    public void setFlowComponents(List<FlowComponent> flowComponents) {
        this.flowComponents = flowComponents;
    }

    public Map<String, String> getFlowAttributes() {
        return flowAttributes;
    }

    public void setFlowAttributes(Map<String, String> flowAttributes) {
        this.flowAttributes = flowAttributes;
    }
}
