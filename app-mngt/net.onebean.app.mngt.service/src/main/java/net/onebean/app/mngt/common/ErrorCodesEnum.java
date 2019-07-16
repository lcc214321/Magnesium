package net.onebean.app.mngt.common;


/**
 * 错误码定义
 * @author 0neBean
 * 1-4位 1101 代表 应用管理中心
 * 异常类型：5～6位，标识数据库、接口、缓存、权限等
 *	01-参数异常，参数有效性校验错误，在接口的response 的msg中请将校验有问题的参数返回给调用方；
 *	02-数据库异常，操作数据库异常，log的message中要有sql、参数信息，sql如果能在堆栈中体现，可以不放在message中；
 *	03-缓存异常，如对redis、JVM缓存操作失败；
 *	04-权限异常，功能权限、数据权限类异常；
 *	05-接口异常，接口调用异常，log 的message中要包含接口地址、接口参数信息，如果能在堆栈中体现，可以不放在message中；
 *	06-业务异常, 业务执行异常， 比如”用户名已存在”，此种类型的异常通常前端要关注；
 *	07-其它运行异常，如JAVA程序运行时异常；
 *	08-消息队列异常，如对rabbitMq 的操作失败；
 *	...
 *	99-除以上外的异常
 *	异常序列号：6～10位，每个模块自定义，要求模块范围内唯一性
 */
public enum ErrorCodesEnum {
    SUSSESS("0",""),
    //01
    REQUEST_PARAM_ERROR("1101010001","请求参数校验异常"),
    //02
    NONE_QUERY_DATA("1101020001","没有对应的数据"),
    INSERT_DATA_ERROR("1101020002","插入数据失败"),
    DIRTY_DATA_ERR("1101020003","脏数据异常"),
    REPEAT_DATA_ERR("1101020004","已有重复的数据"),
    //03,
    PUT_CACHE_ERR("1101030001","插入redis数据失败"),
    //05
    CLOUD_API_ERROR("1101050001","远程调用接口失败"),
    //06
    NO_DATA_TO_SYNC("1101060001","没有获取到需要同步的数据"),
    DATA_REPEAT_ERR("1101060002","已有重复的数据"),
    DATA_BINDING_ERR("1101060003","数据被引用,无法删除,请先解除关联"),
    //07
    JSON_CAST_ERROR("1101070001","序列化JSON异常"),
    REF_ERROR("1101070002","反射对象属性异常"),
    //08
    RABBIT_MQ_BUSSINES_ERR("1101080001","mq业务处理异常"),
    //99
    OTHER("1101999999","操作失败")
    ;

    private String code;

    private String msg;

    private ErrorCodesEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }
}
