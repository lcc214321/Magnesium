package net.onebean.user.mngt.action.userInfo;

import net.onebean.app.mngt.api.service.AppInfoApi;
import net.onebean.common.exception.BusinessException;
import net.onebean.common.model.BaseResponse;
import net.onebean.core.BasePaginationRequest;
import net.onebean.core.BasePaginationResponse;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.uag.log.annotation.UagOperationLog;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagUserInfoService;
import net.onebean.user.mngt.vo.*;
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
@RequestMapping("uagUserInfo")
public class UagUserInfoController {


    private final static Logger logger = LoggerFactory.getLogger(UagUserInfoController.class);

    @Autowired
    private UagUserInfoService uagUserInfoService;
    @Autowired
    private AppInfoApi appInfoApi;

    
    @SuppressWarnings("unchecked")
    @PostMapping(value = "find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UagUserInfoVo> find(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request, BindingResult result) {
        logger.info("find method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UagUserInfoVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            FindUagUserInfoReq  param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = BasePaginationResponse.ok(uagUserInfoService.findByFindUagUserInfoReq(param,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagUserInfoVo> findById(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request, BindingResult result) {
        logger.info("findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagUserInfoVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            FindUagUserInfoReq  param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            response = BaseResponse.ok(uagUserInfoService.findVoById(param));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("findById method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findById method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "添加用户账户")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "addAccount",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> addAccount(@RequestBody @Validated AddAccountReq request, BindingResult result) {
        logger.info("addAccount method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.addAccount(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("addAccount method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("addAccount method catch Exception e = ",e);
        }
        return response;
    }


    @SuppressWarnings("unchecked")
    @PostMapping(value = "findUagUserAppList",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagUserInfoVo> findUagUserAppList() {
        logger.info("findUagUserAppList method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagUserInfoVo> response = new BaseResponse<>();
        try {
            response = BaseResponse.ok(appInfoApi.findUagUserAppList());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("findUagUserAppList method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("findUagUserAppList method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "更改账户锁定状态")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "toggleIsLockStatus",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> toggleIsLockStatus(@RequestBody @Validated ToggleIsLockStatusReq request, BindingResult result) {
        logger.info("toggleIsLockStatus method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.toggleIsLockStatus(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("toggleIsLockStatus method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("toggleIsLockStatus method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "重置账户密码")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "restPassword",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> restPassword(@RequestBody @Validated ToggleIsLockStatusReq request, BindingResult result) {
        logger.info("restPassword method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.restPassword(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("restPassword method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("restPassword method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "编辑账户信息")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "modify",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> modify(@RequestBody @Validated UserInfoModifyReq request, BindingResult result) {
        logger.info("modify method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = BaseResponse.ok(uagUserInfoService.modify(request));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("modify method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("modify method catch Exception e = ",e);
        }
        return response;
    }
}

