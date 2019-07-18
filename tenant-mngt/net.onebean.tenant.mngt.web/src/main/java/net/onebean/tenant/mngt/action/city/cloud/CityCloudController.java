package net.onebean.tenant.mngt.action.city.cloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseResponse;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.service.TtenantCityService;
import net.onebean.tenant.mngt.api.model.FIndListTenantCityResp;
import net.onebean.tenant.mngt.api.model.FIndTenantCityByNameReq;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud/city")
public class CityCloudController {

    @Autowired
    private TtenantCityService ttenantCityService;

    private final static Logger logger = LoggerFactory.getLogger(CityCloudController.class);

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findCityByName",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<FIndListTenantCityResp> findCityVoByName(@RequestBody @Validated FIndTenantCityByNameReq req, BindingResult result){
        logger.info("CityController findListByProvinceCode method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<FIndListTenantCityResp> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("CityController findListByProvinceCode method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(ttenantCityService.findCityVoByName(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("CityController findListByProvinceCode method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("CityController findListByProvinceCode method catch Exception e = ",e);
        }
        return response;
    }


}
