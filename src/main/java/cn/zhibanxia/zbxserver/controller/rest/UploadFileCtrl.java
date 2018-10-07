package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.OssService;
import cn.zhibanxia.zbxserver.util.RandomUtil;
import cn.zhibanxia.zbxserver.controller.param.Result;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 文件上传接口
 * Created by zzy on  2018/10/03 22:17
 */
@RestController
@RequestMapping("/rest/upload")
public class UploadFileCtrl {
    private static Logger logger = LoggerFactory.getLogger(UploadFileCtrl.class);
    @Autowired
    private OssService ossService;

    /**
     * 通用的上传图片接口
     *
     * @param imgBase64 图片base64字符串
     * @param bizType   1.回收人员的图片审核；2.业主上传的纸板照片
     * @return
     */
    @PostMapping("uploadImg")
    public Result<String> uploadImg(@RequestParam("imgBase64") String imgBase64, @RequestParam("bizType") Integer bizType) {
        String val;
        if ((val = StringUtils.trimToNull(imgBase64)) == null) {
            logger.warn("imgBase64 is empty");
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        String[] params = val.split("base64,");
        if (params == null || params.length != 2) {
            logger.warn("imgBase64 is invalid, imgBase64={}", imgBase64);
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }

        // 根据文件前缀匹配文件名
        String dataPriffix = params[0];
        String imgFileSuffix = "";
        if ("data:image/jpeg;".equalsIgnoreCase(dataPriffix)) {
            imgFileSuffix = ".jpg";
        } else if ("data:image/x-icon;".equalsIgnoreCase(dataPriffix)) {
            imgFileSuffix = ".ico";
        } else if ("data:image/gif;".equalsIgnoreCase(dataPriffix)) {
            imgFileSuffix = ".gif";
        } else if ("data:image/png;".equalsIgnoreCase(dataPriffix)) {
            imgFileSuffix = ".png";
        } else {
            logger.warn("img type is invalid, imgBase64={}", imgBase64);
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_IMG_TYPE_ERROR);
        }

        byte[] imgData = Base64.decodeBase64(params[1]);
        if (imgData == null || imgData.length == 0) {
            logger.warn("img decode base64 failed, base64={}", params[1]);
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }

        try {
            String url = ossService.uploadFile(imgData, getFileNameByBizType(bizType) + imgFileSuffix);
            return Result.ResultBuilder.success(url);
        } catch (BizException e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(e);
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    /**
     * 生成文件名称
     * bizType: 1.回收人员的图片审核；2.业主上传的纸板照片
     *
     * @param bizType
     * @return
     * @throws BizException
     */
    private String getFileNameByBizType(Integer bizType) throws BizException {
        if (Objects.equals(1, bizType)) {
            return "huishou/" + RequestLocal.get().getHuishouUid() + "_" + RandomUtil.getRandomString(5);
        } else if (Objects.equals(2, bizType)) {
            return "yezhu/" + RequestLocal.get().getYezhuUid() + "_" + RandomUtil.getRandomString(5);
        } else {
            throw new BizException(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
    }
}
