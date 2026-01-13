package ru.haskov.dto;

import java.util.List;

public class PipelineSchemaDto {
    private String dataType;
    private List<ChannelDto> channels;
    private List<NodeDto> nodes;

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public List<ChannelDto> getChannels() { return channels; }
    public void setChannels(List<ChannelDto> channels) { this.channels = channels; }

    public List<NodeDto> getNodes() { return nodes; }
    public void setNodes(List<NodeDto> nodes) { this.nodes = nodes; }
}
