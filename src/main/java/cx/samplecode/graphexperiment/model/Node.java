package cx.samplecode.graphexperiment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {

    private String name;
    private String type;
    private Integer inEdgesCount;
    private Integer outEdgesCount;
    private Integer startline;
    private Integer endline;
    private Map<String, Map<Metric, Number>> metricPerLanguageMap = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getInEdgesCount() {
        return inEdgesCount;
    }

    public void setInEdgesCount(Integer inEdgesCount) {
        this.inEdgesCount = inEdgesCount;
    }

    public Integer getOutEdgesCount() {
        return outEdgesCount;
    }

    public void setOutEdgesCount(Integer outEdgesCount) {
        this.outEdgesCount = outEdgesCount;
    }

    public Integer getStartline() {
        return startline;
    }

    public void setStartline(Integer startline) {
        this.startline = startline;
    }

    public Integer getEndline() {
        return endline;
    }

    public void setEndline(Integer endline) {
        this.endline = endline;
    }

    public Map<String, Map<Metric, Number>> getMetricPerLanguageMap() {
        return metricPerLanguageMap;
    }

    public void setMetricPerLanguageMap(Map<String, Map<Metric, Number>> metricPerLanguageMap) {
        this.metricPerLanguageMap = metricPerLanguageMap;
    }

    @Override
    public String toString(){
        return getName() + ", " + getType();
    }
}
