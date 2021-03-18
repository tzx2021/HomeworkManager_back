package sc.hqu.graduationdesign.homeworkmanager.model;

/**
 * 通用的消息模型数据接口
 * @author tzx
 * @date 2021-03-17 13:32
 */
public interface GenericMessageModel {

    /**
     * 获取消息主题
     * @return      消息主题
     */
    String getTopic();

    /**
     * 获取消息标签
     * @return      消息标签
     */
    String getTag();

    /**
     * 获取json消息数据
     * @return      json消息数据
     */
    String getJsonData();

}
