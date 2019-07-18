package net.onebean.user.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.BaseBiz;
import net.onebean.core.Condition;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.dao.UagOperatorLogDao;
import net.onebean.user.mngt.model.UagOperatorLog;
import net.onebean.user.mngt.service.UagOperatorLogService;
import net.onebean.user.mngt.vo.FindUagLogReq;
import net.onebean.user.mngt.vo.UagLogVo;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
* @author 0neBean
* @description 操作日志 serviceImpl
* @date 2019-06-27 23:45:23
*/
@Service
public class UagOperatorLogServiceImpl extends BaseBiz<UagOperatorLog, UagOperatorLogDao> implements UagOperatorLogService{


    private final static Logger logger = LoggerFactory.getLogger(UagOperatorLogServiceImpl.class);
    
    @Override
    public List<UagLogVo> findUagLogInfo(FindUagLogReq param, Pagination page, Sort sort) {
        String operatorName = Optional.ofNullable(param).map(FindUagLogReq::getOperatorName).orElse("");


        List<Condition> paramList = new ArrayList<>();
        if (StringUtils.isNotEmpty(operatorName)) {
            Condition condition = Condition.parseModelCondition("operatorName@string@like");
            condition.setValue(operatorName);
            paramList.add(condition);
        }


        List<UagOperatorLog> list = this.find(page, paramList, sort);
        logger.debug("method findUagLogInfo list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<UagLogVo> res = JSON.parseArray(JSON.toJSONString(list), UagLogVo.class);
        logger.debug("method findUagLogInfo res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }
}