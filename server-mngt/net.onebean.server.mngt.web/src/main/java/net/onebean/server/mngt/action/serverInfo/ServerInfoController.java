package net.onebean.server.mngt.action.serverInfo;

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
import net.onebean.server.mngt.model.ServerInfo;
import net.onebean.server.mngt.service.ServerInfoService;
import net.onebean.server.mngt.vo.ServerInfoAddReq;
import net.onebean.server.mngt.vo.ServerInfoModifyReq;
import net.onebean.server.mngt.vo.ServerInfoVo;
import net.onebean.uag.log.annotation.UagOperationLog;
import net.onebean.util.DateUtils;
import net.onebean.util.UagSsoUtils;
import org.apache.commons.beanutils.BeanUtils;
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
@RequestMapping("/serverInfo")
public class ServerInfoController {

    private final static Logger logger = LoggerFactory.getLogger(ServerInfoController.class);

    @Autowired
    private ServerInfoService serverInfoService;

    @UagOperationLog(description = "添加服务信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse add(@RequestBody @Validated ServerInfoAddReq  request, BindingResult result){
        logger.info("ServerInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            ServerInfo serverInfo = new ServerInfo();
            BeanUtils.copyProperties(serverInfo,request);
            UagSsoUtils.setUagUserInfoByHeader(serverInfo);
            serverInfoService.save(serverInfo);
            response = BaseResponse.ok(serverInfo.getId());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ServerInfoController add method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ServerInfoController add method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @EnableEnumDeserialize
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<ServerInfoVo> find(@RequestBody BasePaginationRequest<ServerInfoAddReq> request){
        logger.info("ServerInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ServerInfoVo> response = new BasePaginationResponse<>();
        try {
            ServerInfoAddReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            logger.debug("ServerInfoController find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = BasePaginationResponse.ok(serverInfoService.findServerInfo(param,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ServerInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ServerInfoController find method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "更新服务信息")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse update(@RequestBody @Validated ServerInfoModifyReq request, BindingResult result){
        logger.info("ServerInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            ServerInfo target = new ServerInfo();
            BeanUtils.copyProperties(target,request);
            logger.debug("ServerInfoController update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            response = BaseResponse.ok(serverInfoService.update(target));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ServerInfoController update method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ServerInfoController update method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除服务信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> delete(@RequestBody @Validated ServerInfoModifyReq request, BindingResult result){
        logger.info("ServerInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));

            response = BaseResponse.ok(serverInfoService.deleteServerInfo(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ServerInfoController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ServerInfoController delete method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<ServerInfoVo> findById(@RequestBody @Validated ServerInfoModifyReq request, BindingResult result){
        logger.info("ServerInfoController findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<ServerInfoVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController findById method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            Object id = Optional.ofNullable(request).map(ServerInfoModifyReq::getId).orElse(null);
            response = BaseResponse.ok(serverInfoService.findVoById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ServerInfoController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ServerInfoController findById method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "同步应用和API的关联关系")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/syncAppApiRelationship",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse syncAppApiRelationship(){
        logger.info("ServerInfoController syncAppApiRelationship method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            response = BaseResponse.ok(serverInfoService.syncAppApiRelationship());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("ServerInfoController syncAppApiRelationship method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("ServerInfoController syncAppApiRelationship method catch Exception e = ",e);
        }
        return response;
    }
}
