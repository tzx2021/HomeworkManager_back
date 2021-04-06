package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.DeleteFileInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.PageQueryInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.PublishFileInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.UpdateFileInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.FileDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.GenericPageDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.SimpleFileOutput;

/**
 * @author tzx
 * @date 2021-04-07 0:28
 */
@Api(tags = "文件服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/file")
public class FileController {


    @ApiOperation(value = "获取文件分页数据")
    @PostMapping(value = "/page")
    public GenericPageDataOutput<FileDataOutput> getFilePage(@RequestBody PageQueryInput input){
        return null;
    }

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public SimpleFileOutput uploadFile(@RequestParam("file") MultipartFile file){
        return null;
    }

    @ApiOperation(value = "文件发布")
    @PostMapping(value = "/publish")
    public GenericResponse publishFile(@RequestBody PublishFileInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "文件名称更新")
    @PostMapping(value = "/update")
    public GenericResponse updateFilename(@RequestBody UpdateFileInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "文件删除",notes = "当前版本文件删除仅删除数据库记录，不删除服务器中的文件数据")
    @PostMapping(value = "/delete")
    public GenericResponse deleteFile(@RequestBody DeleteFileInput input){
        return GenericResponse.success();
    }

}
