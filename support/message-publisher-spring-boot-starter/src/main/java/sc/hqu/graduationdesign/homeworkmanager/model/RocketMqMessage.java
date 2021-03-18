package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Setter;

/**
 * @author tzx
 * @date 2021-03-17 13:42
 */
@Setter
public class RocketMqMessage implements GenericMessageModel{

    private String topic;
    private String tag;
    private String jsonData;

    public RocketMqMessage(String topic,String tag,String jsonData){
        this.topic = topic;
        this.tag = tag;
        this.jsonData = jsonData;
    }

    public RocketMqMessage(String topic,String jsonData){
        this(topic,"undefined",jsonData);
    }

    public RocketMqMessage(){}

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getJsonData() {
        return jsonData;
    }
}
