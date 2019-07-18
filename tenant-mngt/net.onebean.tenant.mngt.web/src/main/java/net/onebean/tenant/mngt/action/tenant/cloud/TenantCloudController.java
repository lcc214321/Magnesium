package net.onebean.tenant.mngt.action.tenant.cloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseResponse;
import net.onebean.tenant.mngt.api.model.FindTtenantInfoVo;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoBatchSyncFlagReq;
import net.onebean.tenant.mngt.api.model.ModifyTtenantInfoStatusReq;
import net.onebean.tenant.mngt.api.model.TenantCityInfoVo;
import net.onebean.tenant.mngt.common.ErrorCodesEnum;
import net.onebean.tenant.mngt.model.TtenantInfo;
import net.onebean.tenant.mngt.service.TtenantInfoService;
import net.onebean.util.DateUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cloud/tenant")
public class TenantCloudController {

    private final static Logger logger = LoggerFactory.getLogger(TenantCloudController.class);


    @Autowired
    private TtenantInfoService tenantInfoService;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findTenantInfoByTenantId",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<FindTtenantInfoVo> findTenantInfoByTenantId(@RequestParam("tenantId")String id){
        logger.info("findTenantInfoByTenantId method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<FindTtenantInfoVo> response = new BaseResponse<>();
        try {
            logger.debug("findTenantInfoByTenantId method id = "+ JSON.toJSONString(id, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(tenantInfoService.findVoById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("findTenantInfoByTenantId method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findTenantInfoByTenantId method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findTenantCityInfoByTenantId",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<TenantCityInfoVo> findTenantCityInfoByTenantId(@RequestParam("tenantId")String id){
        logger.info("findTenantCityInfoByTenantId method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<TenantCityInfoVo> response = new BaseResponse<>();
        try {
            logger.debug("findTenantCityInfoByTenantId method id = "+ JSON.toJSONString(id, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(tenantInfoService.findTenantCityInfoByTenantId(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("findTenantCityInfoByTenantId method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findTenantCityInfoByTenantId method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/updateBatchSyncFlag",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse updateBatchSyncFlag(@RequestBody @Validated ModifyTtenantInfoBatchSyncFlagReq req, BindingResult result){
        logger.info("updateBatch method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("updateBatch method req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(tenantInfoService.updateBatchSyncFlag(req));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("updateBatch method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("updateBatch method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/updateStatus",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse updateStatus(@RequestBody @Validated ModifyTtenantInfoStatusReq req, BindingResult result){
        logger.info("method updateStatus access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("method updateStatus req = "+ JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
            TtenantInfo target = new TtenantInfo();
            BeanUtils.copyProperties(target,req);
            logger.debug("method updateStatus target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(tenantInfoService.update(target));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("method updateStatus BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("method updateStatus catch Exception e = ",e);
        }
        return response;
    }
}
