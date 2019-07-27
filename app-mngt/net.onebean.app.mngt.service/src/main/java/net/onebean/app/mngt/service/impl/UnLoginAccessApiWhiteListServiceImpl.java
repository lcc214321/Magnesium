package net.onebean.app.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.app.mngt.common.ErrorCodesEnum;
import net.onebean.app.mngt.dao.UnLoginAccessApiWhiteListDao;
import net.onebean.app.mngt.model.UnLoginAccessApiWhiteList;
import net.onebean.app.mngt.service.UnLoginAccessApiWhiteListService;
import net.onebean.app.mngt.vo.UnLoginAccessApiWhiteListVo;
import net.onebean.core.error.BusinessException;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
/**
 * @author 0neBean
 * @description 未登录访问API白名单 serviceImpl
 * @date 2019-06-28 11:02:32
 */
@Service
public class UnLoginAccessApiWhiteListServiceImpl extends BaseBiz<UnLoginAccessApiWhiteList, UnLoginAccessApiWhiteListDao> implements UnLoginAccessApiWhiteListService{



    private final static Logger logger = LoggerFactory.getLogger(UnLoginAccessApiWhiteListServiceImpl.class);

    @Override
    public List<UnLoginAccessApiWhiteListVo> findByRsSalesUnLoginAccessWhiteListQueryRequest(UnLoginAccessApiWhiteListVo param, Pagination page, Sort sort) {
        Integer appId = Optional.of(param).map(UnLoginAccessApiWhiteListVo::getAppId).orElse(null);

        List<Condition> paramList = new ArrayList<>();
        if (null != appId) {
            Condition condition = Condition.parseModelCondition("appId@string@eq");
            condition.setValue(appId);
            paramList.add(condition);
        }
        //查出所有数据不分页
        List<UnLoginAccessApiWhiteList> list = this.find(null,paramList,sort);
        logger.debug("RsSalesUnLoginAccessWhiteListServiceImpl method findByRsSalesUnLoginAccessWhiteListQueryRequest list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<UnLoginAccessApiWhiteListVo> res = JSON.parseArray(JSON.toJSONString(list), UnLoginAccessApiWhiteListVo.class);
        logger.debug("RsSalesUnLoginAccessWhiteListServiceImpl method findByRsSalesUnLoginAccessWhiteListQueryRequest res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

    @Override
    public List<UnLoginAccessApiWhiteListVo> findUnBindingData(UnLoginAccessApiWhiteListVo param, Pagination page, Sort sort) {
        Integer appId = Optional.ofNullable(param).map(UnLoginAccessApiWhiteListVo::getAppId).orElse(null);
        String apiName = Optional.ofNullable(param).map(UnLoginAccessApiWhiteListVo::getApiName).orElse(null);
        if (null == appId) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of appId");
        }
        return baseDao.findUnBindingData(appId,apiName);
    }
}