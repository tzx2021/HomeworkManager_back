package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 21:16
 */
@Data
public class SimpleFileDataDto {

    private Long id;

    private String name;

    private String url;

    public SimpleFileDataDto(){}

    public SimpleFileDataDto(Long id,String name,String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

}
