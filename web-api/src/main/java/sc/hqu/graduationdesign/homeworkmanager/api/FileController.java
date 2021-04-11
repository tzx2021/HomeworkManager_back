package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.FilePublishDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.FileService;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.DeleteFileInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.PageQueryInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.PublishFileInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.UpdateFileInput;

/**
 * @author tzx
 * @date 2021-04-07 0:28
 */
@Api(tags = "文件服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private GenericCacheProvider cacheProvider;

    @ApiOperation(value = "获取文件分页数据")
    @PostMapping(value = "/page")
    public Object getFilePage(@RequestBody PageQueryInput input){
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        return fileService.getFilePageData(account, input.getPageSize(), input.getPageNum());
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        // 返回的是文件的url
        return fileService.upload(file);
    }

    @ApiOperation(value = "文件发布")
    @PostMapping(value = "/publish")
    public GenericResponse publishFile(@RequestBody PublishFileInput input){
        // 创建且同时发布
        FilePublishDto dto = new FilePublishDto();
        BeanUtils.copyProperties(input,dto);
        dto.setType(input.getType());
        dto.setPid(input.getPublishId());
        fileService.create(dto,1);
        return GenericResponse.success();
    }

    @ApiOperation("保存文件")
    @PostMapping(value = "/save")
    public GenericResponse saveFile(@RequestBody PublishFileInput input){
        FilePublishDto dto = new FilePublishDto();
        BeanUtils.copyProperties(input,dto);
        // publish传入0代表文件仅保存不进行发布
        fileService.create(dto,0);
        return GenericResponse.success();
    }

    @ApiOperation(value = "文件名称更新")
    @PostMapping(value = "/update")
    public GenericResponse updateFilename(@RequestBody UpdateFileInput input){
        fileService.updateFilename(input.getFileId(), input.getFilename());
        return GenericResponse.success();
    }

    @ApiOperation(value = "文件删除",notes = "当前版本文件删除仅删除数据库记录，不删除服务器中的文件数据")
    @PostMapping(value = "/delete")
    public GenericResponse deleteFile(@RequestBody DeleteFileInput input){
        fileService.deleteFile(input.getFileId(), false);
        return GenericResponse.success();
    }

}
