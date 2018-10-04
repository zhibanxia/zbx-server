package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.config.OssConfigBean;
import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.service.OssService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by zzy on  2018/10/04 21:07
 */
@Service
public class OssServiceImpl implements OssService {
    private static Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);
    @Autowired
    private OssConfigBean ossConfigBean;
    @Autowired
    private OSS oss;

    @Override
    public String uploadFile(byte[] data, String fileName) throws BizException {
        try (InputStream in = new ByteArrayInputStream(data)) {
            //文件大小
            int fileSize = data.length;
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(fileSize);
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //上传文件   (上传文件流的形式)
            oss.putObject(ossConfigBean.getBucketName(), fileName, in, metadata);
            return ossConfigBean.getEndPoint() + "/" + fileName;
        } catch (Exception e) {
            logger.warn("", e);
            throw new BizException(ErrorCode.CODE_OSS_UPLOAD_ERROR);
        }
    }


    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    private static String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "application/octet-stream";
    }
}
