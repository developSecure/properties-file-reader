package utils;

import java.util.List;

public class Root {
    private List<ApiFlow> apiFlows;
    private List<ApiConfig> apiConfigs;

    public List<ApiFlow> getApiFlows() {
        return apiFlows;
    }

    public void setApiFlows(List<ApiFlow> apiFlows) {
        this.apiFlows = apiFlows;
    }

    public List<ApiConfig> getApiConfigs() {
        return apiConfigs;
    }

    public void setApiConfigs(List<ApiConfig> apiConfigs) {
        this.apiConfigs = apiConfigs;
    }
}
