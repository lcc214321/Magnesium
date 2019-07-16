//package net.onebean.sso.api.utils;
//
//import net.onebean.component.SpringUtil;
//import ConstantEnum;
//import net.onebean.sso.api.vo.UagLoginSessionInfo;
//import net.onebean.util.JSONUtil;
//import net.onebean.util.ReflectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URLDecoder;
//import java.util.Optional;
//
//public class UagSsoUtils {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UagSsoUtils.class);
//
//    public static UagLoginSessionInfo getCurrentUagLoginSessionInfo() {
//        UagLoginSessionInfo uagLoginInfo;
//        try {
//            String uagLoginInfoJson = Optional.ofNullable(SpringUtil.getHttpServletRequest().getSession().getAttribute(ConstantEnum.UAG_SSO_LOGIN_INFO.getName())).map(s -> s + "").orElse("");
//            uagLoginInfo = JSONUtil.toBean(uagLoginInfoJson, UagLoginSessionInfo.class);
//            LOGGER.info("getCurrentUagLoginInfo uagLoginInfo = " + uagLoginInfo);
//        } catch (Exception e) {
//            LOGGER.error("getCurrentUagLoginSessionInfo failure e = ", e);
//            return null;
//        }
//        return uagLoginInfo;
//    }
//
//    public static UagLoginSessionInfo getCurrentUagLoginHeaderInfo(){
//        UagLoginSessionInfo uagLoginInfo = null;
//        String uagHeaderUserInfoStr =  SpringUtil.getHttpServletRequest().getHeader("uagHeaderUserInfo");
//        try {
//            uagHeaderUserInfoStr = URLDecoder.decode(uagHeaderUserInfoStr, "utf-8");
//            uagLoginInfo = JSONUtil.toBean(uagHeaderUserInfoStr,UagLoginSessionInfo.class);
//        } catch (Exception e) {
//            LOGGER.error("getCurrentUagLoginHeaderInfo failure e = ", e);
//        }
//        return uagLoginInfo;
//    }
//
//
//    public static void setUagUserInfoByHeader(Object model){
//        UagLoginSessionInfo uagLoginInfo = getCurrentUagLoginHeaderInfo();
//        String uagUserId = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUserId).orElse("");
//        String uagUsername = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUsername).orElse("");
//        try {
//            ReflectionUtils.setFieldValue(model,"operatorId",uagUserId);
//            ReflectionUtils.setFieldValue(model,"operatorName",uagUsername);
//        } catch (Exception e) {
//            LOGGER.error("setUagUserInfoByHeader failure e = ", e);
//        }
//    }
//
//    public static void setUagUserInfoBySession(Object model){
//        UagLoginSessionInfo uagLoginInfo = getCurrentUagLoginSessionInfo();
//        String uagUserId = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUserId).orElse("");
//        String uagUsername = Optional.ofNullable(uagLoginInfo).map(UagLoginSessionInfo::getUagUsername).orElse("");
//        try {
//            ReflectionUtils.setFieldValue(model,"operatorId",uagUserId);
//            ReflectionUtils.setFieldValue(model,"operatorName",uagUsername);
//        } catch (Exception e) {
//            LOGGER.error("setUagUserInfoBySession failure e = ", e);
//        }
//    }
//
//}
