package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.OssService;
import cn.zhibanxia.zbxserver.util.RandomUtil;
import cn.zhibanxia.zbxserver.controller.param.Result;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 文件上传接口
 * Created by zzy on  2018/10/03 22:17
 */
@RestController("rest/upload")
public class UploadFileCtrl {
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
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        byte[] imgData = Base64.decodeBase64(val);
        if (imgData == null || imgData.length == 0) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }

        try {
            String url = ossService.uploadFile(imgData, getFileNameByBizType(bizType));
            return Result.ResultBuilder.success(url);
        } catch (BizException e) {
            return Result.ResultBuilder.fail(e);
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
