package utils;

import java.util.Map;

public class FlowComponent {
    private String componentName;
    private Map<String, String> componentAttributes;
    
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Map<String, String> getComponentAttributes() {
        return componentAttributes;
    }
    

    public void setComponentAttributes(Map<String, String> componentAttributes) {
        this.componentAttributes = componentAttributes;
    }
}
