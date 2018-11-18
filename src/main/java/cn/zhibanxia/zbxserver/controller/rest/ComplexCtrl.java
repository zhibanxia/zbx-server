package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.ComplexVo;
import cn.zhibanxia.zbxserver.controller.param.Result;
import cn.zhibanxia.zbxserver.entity.ComplexEntity;
import cn.zhibanxia.zbxserver.service.ComplexService;
import cn.zhibanxia.zbxserver.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zzy on  2018/11/18 17:54
 */
@RestController
@RequestMapping("/rest/complex")
public class ComplexCtrl {
    @Autowired
    private ComplexService complexService;

    @PostMapping("searchByComplexName")
    public Result<List<ComplexVo>> searchByComplexName(@RequestBody ComplexVo complexVo) {
        if (complexVo == null || complexVo.getProvinceId() == null || complexVo.getCityId() == null
                || complexVo.getAreaId() == null || complexVo.getComplexName() == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        return Result.ResultBuilder.success(BeanUtil.copyList(complexService.searchComplex(BeanUtil.copy(complexVo, ComplexEntity.class)), ComplexVo.class));
    }
}
