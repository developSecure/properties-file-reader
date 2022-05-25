package utils;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class XMLFlowParser {
    private static Root getRoot(String filePath) throws Exception {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser xmlParser = saxParserFactory.newSAXParser();

        FlowHandler handler = new FlowHandler();
        if (filePath != null) {
            xmlParser.parse(filePath, handler);
        }
        return handler.getRoot();
    }

    public static List<ApiFlow> readFlows(String filePath, String[] arr) throws Exception {
    	//String[] arr = {"^.*api-main.*","^.*api-console.*"};
        Root root = getRoot(filePath);
        return root.getApiFlows().stream().filter(f -> !f.getFlowName().matches(arr[0]) && !f.getFlowName().matches(arr[1])).collect(Collectors.toList());
    }

    public static List<ApiConfig> readConfigs(String filePath) throws Exception {
        Root root = getRoot(filePath);
        return root.getApiConfigs();
    }
}
