package sc.hqu.graduationdesign.homeworkmanager.provider;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.config.FileServiceConfig;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.FileUploadException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 基于ftp服务器的文件上传以及文件删除等服务的提供者对象
 * @author tzx
 * @date 2021-03-29 18:13
 */
public class FtpServiceProvider implements GenericFileServiceProvider{

    private final Logger log = LoggerFactory.getLogger(FtpServiceProvider.class);

    /**
     * ftp服务器中，用户登录后的工作目录（文件访问或保存的相对根目录）
     */
    private static final String WORK_DIR = "/ftpfiles/clb";

    @Value("${ftp-server.hostname}")
    private String hostname;

    @Value("${ftp-server.port:21}")
    private int port;

    @Value("${ftp-server.username}")
    private String ftpUsername;

    @Value("${ftp-server.password}")
    private String ftpPassword;

    @Value("${ftp-server.dir}")
    private String ftpBaseDir;

    @Value("${ftp-server.timeout:120000}")
    private int ftpTimeout;

    /**
     * 初始化FTP客户端
     * @return          {@link FTPClient}
     */
    public FTPClient getClient(){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        String hostPort = hostname + ":" + port;
        try {
            ftpClient.setDataTimeout(ftpTimeout);
            log.info("Start to connect to ftp server [{}]...",hostPort);
            ftpClient.connect(hostname,port);
            // 登录到ftp服务器
            ftpClient.login(ftpUsername,ftpPassword);
            // 获取响应码，获取响应码成功代码连接成功
            int replyCode = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(replyCode)){
                // 服务器连接或登录失败
                log.error("Fail to connect to ftp server [{}]",hostPort);
            }
            log.info("Connect to ftp server [{}] successfully, reply code is: {}",hostPort,replyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    @Override
    public String upload(MultipartFile file) throws FileUploadException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null){
            throw new FileUploadException("Original filename must not be null!");
        }
        String filename = generateUniqueFilename(originalFilename);
        // 获取客户端对象
        FTPClient client = getClient();
        String fileSavePath = WORK_DIR + "/" + ftpBaseDir;
        InputStream inputStream = null;
        try {
            if (client.isConnected()){
                log.info("Start to upload file[{}] to ftp server...",filename);
                // 设置上传文件类型为二进制，否则会无法打开文件
                client.setFileType(FTP.BINARY_FILE_TYPE);
                client.makeDirectory(fileSavePath);
                // 切换目录到文件保存的目录
                if (client.changeWorkingDirectory(fileSavePath)){
                    log.info("Change working dir[{}] successfully",fileSavePath);
                }
                // 设置为被动模式
                client.enterLocalPassiveMode();
                // 获取文件的输入流，进行上传
                inputStream = file.getInputStream();
                client.storeFile(filename,inputStream);
                log.info("File[{}] uploads to ftp server successfully!",filename);
            }
        } catch (IOException e) {
           log.error("Fail to change dir: {}",fileSavePath,e);
           throw new FileUploadException(e.getMessage());
        }finally {
            closeClient(client);
            closeStream(inputStream);
        }
        // 返回文件访问的真实url，该url会保存到数据库中
        return FileServiceConfig.fileAccessPathPrefix + filename;
    }

    @Override
    public String getType(String originalFilename) {
        if (originalFilename != null){
            String[] parts = originalFilename.split("\\.");
            return parts[parts.length - 1];
        }
        return "unknown";
    }

    @Override
    public String generateUniqueFilename(String originalFilename){
        // 将文件名和文件类型后缀分开
        String[] fileNameParts = originalFilename.split("\\.");
        StringBuilder fileNameBuilder = new StringBuilder();
        int len = fileNameParts.length;
        for (int i = 0; i < len - 1; i++) {
            fileNameBuilder.append(fileNameParts[i]);
        }
        // 生成固定格式的文件名，加入uuid防止文件名重复
        // 如果原始文件名为：test.docx，那么生成的文件名应为：file_test-[uuid].docx
        return "file_" + fileNameBuilder.toString() + "-" + UUID.randomUUID().toString() + "." + fileNameParts[len - 1];
    }

    private void closeStream(Closeable closeable){
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    private void closeClient(FTPClient ftpClient){
        if (ftpClient.isConnected()){
            try {
                ftpClient.disconnect();
            }catch (IOException e){
                log.error(e.getMessage(),e);
            }
        }
    }

}
