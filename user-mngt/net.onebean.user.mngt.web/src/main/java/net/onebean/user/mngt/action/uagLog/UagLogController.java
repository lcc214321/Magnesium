package net.onebean.user.mngt.action.uagLog;


import net.onebean.core.BasePaginationRequest;
import net.onebean.core.BasePaginationResponse;
import net.onebean.core.Pagination;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagOperatorLogService;
import net.onebean.user.mngt.vo.FindUagLogReq;
import net.onebean.user.mngt.vo.UagLogVo;
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
@RequestMapping("uagLog")
public class UagLogController {


    private final static Logger logger = LoggerFactory.getLogger(UagLogController.class);
    @Autowired
    private UagOperatorLogService uagOperatorLogService;


    @SuppressWarnings("unchecked")
    @PostMapping(value = "find", produces = {"application/json"}, consumes = {"application/json"})
    public BasePaginationResponse<UagLogVo> find(@RequestBody @Validated BasePaginationRequest<FindUagLogReq> request, BindingResult result) {
        logger.info("find method access " + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UagLogVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                response.setErrCode(ErrorCodesEnum.REQUEST_PARAM_ERROR.code());
                response.setErrMsg(result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            FindUagLogReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC, "id"));
            response = BasePaginationResponse.ok(uagOperatorLogService.findUagLogInfo(param, page, sort), page);
        } catch (BusinessException e) {
            response.setErrCode(e.getCode());
            response.setErrMsg(e.getMsg());
            logger.info("find method BusinessException ex = ", e);
        } catch (Exception e) {
            response.setErrCode(ErrorCodesEnum.OTHER.code());
            response.setErrMsg(ErrorCodesEnum.OTHER.msg());
            logger.error("find method catch Exception e = ", e);
        }
        return response;
    }

}
