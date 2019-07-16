package net.onebean.component;

import net.onebean.component.provider.UagOperationLogMqSender;
import net.onebean.core.model.UagLoginSessionInfo;
import net.onebean.uag.log.annotation.UagOperationLog;
import net.onebean.uag.log.vo.UagOperationLogMqReq;
import net.onebean.util.UagSsoUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@Aspect
public class UagOperationLogAop {


    private final String ExpGetResultDataPonit = "(execution(* net.onebean..action..*.*(..))) && @annotation(uagOperationLog)";

    @Autowired
    private UagOperationLogMqSender uagOperationLogMqSender;

    /**
     * 环绕aop 设置枚举映射值
     * @param proceedingJoinPoint 切入点
     * @param uagOperationLog 注解
     * @return obj
     * @throws Throwable 抛出异常
     */
    @Around(value = ExpGetResultDataPonit)
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint, UagOperationLog uagOperationLog) throws Throwable {

        UagLoginSessionInfo uagLoginSessionInfo  = UagSsoUtils.getCurrentUagLoginSessionInfo();
        if (null == uagLoginSessionInfo){
            uagLoginSessionInfo = UagSsoUtils.getCurrentUagLoginHeaderInfo();
        }
        String description = uagOperationLog.description();
        String appId = Optional.ofNullable(System.getProperty("app.id")).orElse("");
        String uagUserId =  Optional.ofNullable(uagLoginSessionInfo).map(UagLoginSessionInfo::getUagUserId).orElse("");
        String uagUserNickName = Optional.ofNullable(uagLoginSessionInfo).map(UagLoginSessionInfo::getUagUserNickName).orElse("");
        UagOperationLogMqReq req = new UagOperationLogMqReq(description,appId,uagUserId,uagUserNickName);
        uagOperationLogMqSender.send(req);
        System.out.println("=== description "+appId+" description ===  "+description);
        Object result = proceedingJoinPoint.proceed();
        return result;
    }
}