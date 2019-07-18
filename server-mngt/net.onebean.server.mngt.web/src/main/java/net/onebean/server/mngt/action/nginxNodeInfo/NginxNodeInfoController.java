package net.onebean.server.mngt.action.nginxNodeInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseResponse;
import net.onebean.core.BasePaginationRequest;
import net.onebean.core.BasePaginationResponse;
import net.onebean.core.Json.EnableEnumDeserialize;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.provider.mq.DevopsUpdateNginxUpSteamNodeSender;
import net.onebean.server.mngt.service.NginxNodeService;
import net.onebean.server.mngt.vo.NginxNodeAddReq;
import net.onebean.server.mngt.vo.NginxNodeInfoModifyReq;
import net.onebean.server.mngt.vo.NginxNodeVo;
import net.onebean.uag.conf.api.service.NginxInfoCloudApi;
import net.onebean.uag.log.annotation.UagOperationLog;
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

import java.util.Optional;

@RestController
@RequestMapping("/nginxNodeInfo")
public class NginxNodeInfoController {

    private final static Logger logger = LoggerFactory.getLogger(NginxNodeInfoController.class);

    @Autowired
    private NginxNodeService nginxNodeService;
    @Autowired
    private DevopsUpdateNginxUpSteamNodeSender devopsUpdateNginxUpSteamNodeSender;
    @Autowired
    private NginxInfoCloudApi nginxInfoCloudApi;


    @UagOperationLog(description = "添加nginx节点")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse add(@RequestBody @Validated NginxNodeAddReq  request, BindingResult result){
        logger.info("NginxNodeInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(nginxNodeService.add(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("NginxNodeInfoController add method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("add method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @EnableEnumDeserialize
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<NginxNodeVo> find(@RequestBody BasePaginationRequest<NginxNodeAddReq> request){
        logger.info("NginxNodeInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<NginxNodeVo> response = new BasePaginationResponse<>();
        try {
            NginxNodeAddReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            logger.debug("find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BasePaginationResponse.ok(nginxNodeService.findNginxNodeVo(param,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("NginxNodeInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "更新nginx节点")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse update(@RequestBody @Validated NginxNodeInfoModifyReq request, BindingResult result){
        logger.info("NginxNodeInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BaseResponse.ok(nginxNodeService.update(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("NginxNodeInfoController update method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("update method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除nginx节点")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse delete(@RequestBody @Validated NginxNodeInfoModifyReq request, BindingResult result){
        logger.info("NginxNodeInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            String id = Optional.ofNullable(request).map(NginxNodeInfoModifyReq::getId).orElse(null);
            response = BaseResponse.ok(nginxNodeService.deleteById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("NginxNodeInfoController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("delete method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<NginxNodeVo> findById(@RequestBody @Validated NginxNodeInfoModifyReq request, BindingResult result){
        logger.info("NginxNodeInfoController findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<NginxNodeVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("findById method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            Object id = Optional.ofNullable(request).map(NginxNodeInfoModifyReq::getId).orElse(null);
            response = BaseResponse.ok(nginxNodeService.findVoById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("NginxNodeInfoController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findById method catch Exception e = ",e);
        }
        return response;
    }



    @UagOperationLog(description = "同步nginx节点信息")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/syncNginxNodeInfo",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> syncNginxNodeInfo(){
        logger.info("AppInfoController syncNginxNodeInfo method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            response = BaseResponse.ok(nginxInfoCloudApi.syncNginxConf());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("AppInfoController syncNginxNodeInfo method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("AppInfoController syncNginxNodeInfo method catch Exception e = ",e);
        }
        return response;
    }



}
