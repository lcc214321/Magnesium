package net.onebean.app.mngt.action.unLoginAccessApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.app.mngt.common.ErrorCodesEnum;
import net.onebean.app.mngt.model.UnLoginAccessApiWhiteList;
import net.onebean.app.mngt.service.UnLoginAccessApiWhiteListService;
import net.onebean.app.mngt.vo.RsSalesUnLoginAccessWhiteListAddReq;
import net.onebean.app.mngt.vo.RsSalesUnLoginAccessWhiteListModfiyReq;
import net.onebean.app.mngt.vo.UnLoginAccessApiWhiteListVo;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseResponse;
import net.onebean.core.BasePaginationRequest;
import net.onebean.core.BasePaginationResponse;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
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

/**
 *  易开伙伴 未登录访问接口白名单
 * @author 0neBean
 */
@RestController
@RequestMapping("/unLoginAccessWhiteList")
public class UnLoginAccessWhiteListController {

    private final static Logger logger = LoggerFactory.getLogger(UnLoginAccessWhiteListController.class);

    @Autowired
    private UnLoginAccessApiWhiteListService accessWhiteListService;


    @UagOperationLog(description = "添加未登录API访问白名单")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse add(@RequestBody @Validated RsSalesUnLoginAccessWhiteListAddReq request, BindingResult result) {
        logger.info("UnLoginAccessWhiteListController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UnLoginAccessWhiteListController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UnLoginAccessApiWhiteList target = new UnLoginAccessApiWhiteList();
            BeanUtils.copyProperties(target,request);
            logger.debug("UnLoginAccessWhiteListController add method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoByHeader(target);
            accessWhiteListService.save(target);
            response = BaseResponse.ok(target.getId());
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("UnLoginAccessWhiteListController add method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("UnLoginAccessWhiteListController add method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除未登录API访问白名单")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse delete(@RequestBody RsSalesUnLoginAccessWhiteListModfiyReq  appInfoVo) {
        logger.info("UnLoginAccessWhiteListController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse response = new BaseResponse();
        try {
            logger.debug("UnLoginAccessWhiteListController delete method appInfoVo = "+ JSON.toJSONString(appInfoVo, SerializerFeature.WriteMapNullValue));
            Object id = Optional.ofNullable(appInfoVo).map(RsSalesUnLoginAccessWhiteListModfiyReq::getId).orElse(null);
            response = BaseResponse.ok(accessWhiteListService.deleteById(id));
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("UnLoginAccessWhiteListController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("UnLoginAccessWhiteListController delete method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> find(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        logger.info("UnLoginAccessWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UnLoginAccessApiWhiteListVo> response = new BasePaginationResponse<>();
        try {
            UnLoginAccessApiWhiteListVo param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = BasePaginationResponse.ok(accessWhiteListService.findByRsSalesUnLoginAccessWhiteListQueryRequest(param,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("UnLoginAccessWhiteListController find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("UnLoginAccessWhiteListController find method catch Exception e = ",e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findUnBindingData",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> findUnBindingData(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        logger.info("UnLoginAccessWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UnLoginAccessApiWhiteListVo> response = new BasePaginationResponse<>();
        try {
            UnLoginAccessApiWhiteListVo param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = BasePaginationResponse.ok(accessWhiteListService.findUnBindingData(param,page,sort),page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("UnLoginAccessWhiteListController findUnBindingData method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("UnLoginAccessWhiteListController findUnBindingData method catch Exception e = ",e);
        }
        return response;
    }


}
