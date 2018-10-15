package com.sc.common.util.res;

/**
 * @author hp
 * @version 1.0
 * @description: 统一错误返回
 * 自定义异常规范：模块 + 业务类型 + ERROR（log打印）
 * 对应的错误代码：1000是基数，每一个模块对应1000（针对与业务逻辑出错的错误代码，http错误代码不变且已定义）
 * 如service层远程调用失败异常  RemoteCallException可以定义为1999
 * 可以根据业务需要不断向后添加非http异常
 * http --------------------->100~1000
 * common ------------------->1000~2000
 * user --------------------->20000~30000
 * order -------------------->30000~40000
 * comment------------------->40000~50000
 * Goods---------------------->50000~60000
 * repertory----------------->60000~70000
 * ...
 * @date: 10:25 2018/10/15 0015
 */
public enum ResultEnum {
    /**
     * 自定义异常规范：模块 + 业务类型 + ERROR（log打印）
     */
    NOT_AUTH(401, "你所在的用户组没有该权限"),
    NOT_FOUND(404, "不存在的映射路径"),
    REQUEST_TO_FAST(403, "请求速度太快，歇会吧"),
    REQUEST_TYPE_TO_METHOD_NOT_ALLOW(405, "请求类型错误"),
    UNSUPPORTED_MEDIA_TYPE(415, "错误的请求方式"),
    ERROR_UNKNOWN(500, "服务器跑路了"),

    DATA_ERROR(1001, "数据转换错误"),
    MYSQL_OPERATION_FAILED(1002, "数据库sql操作失败"),
    INSERT_REPETITION(1003, "重复插入"),
    TOKEN_INFO_ERROR(1004, "token中获取用户id不存在"),
    QUERY_RESULT_IS_NULL(1005, "查询结果为空"),
    SUCCESS(2000, "success"),

    NICKNAME_REPETITION(20001, "昵称重复"),
    EXIST_ACCOUNT(20002, "账号已存在"),
    PARAMS_ERROR(20003, "参数格式错误"),
    ERROR_PHONE(20004, "手机号与用户id不符合"),
    SMS_CODE_SEND_FAILED(20005, "验证码发送失败"),
    EXIST_BINDING(20006, "该手机已绑定其他账号"),
    CODE_AUTHENTICATION_FAILED(20007, "手机验证码错误"),
    EXIST_PHONE(20008, "手机号已注册过，请登录"),
    ERROR_ACCOUNT_OR_PASSWORD(20010, "不存在的用户名或密码输入不正确"),

    SIGN_IN_REPETITION(40001, "重复签到"),
    PRE_ORDER_ERROR(40002, "预下单失败"),
    CREATE_ORDER_FAILED(40003, "创建订单失败");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
