package com.zkzy.zyportal.system.api.constant;


/**
 * Created by Administrator on 2017/4/20.
 */
public class ReturnCode {

    public static final CodeObject SUCCESS = new CodeObject("0","成功");

    public static final CodeObject FAILED = new CodeObject("-1","失败");

    public static final CodeObject AREA_SUCCESS = new CodeObject("60001","提交成功！");

    public static final CodeObject AREA_FAILED = new CodeObject("60002","提交失败！");

    public static final CodeObject INSERT_FAILED = new CodeObject("60007","添加失败，信息已存在！");

    public static final CodeObject INSERTBM_FAILED = new CodeObject("60013","添加失败，参数编码已存在！");

    public static final CodeObject CHANGEBM_FAILED = new CodeObject("60014","修改失败，参数编码已存在！");

    public static final CodeObject add_FAILED = new CodeObject("60008","添加失败，节点名称已存在！");

    public static final CodeObject edit_FAILED = new CodeObject("60009","修改失败，节点名称已存在！");

    public static final CodeObject DEL_SUCCESS = new CodeObject("60003","删除成功！");

    public static final CodeObject DEL_FAILED = new CodeObject("60004","删除失败！");

    public static final CodeObject DELNode_FAILED = new CodeObject("60010","删除失败,该目录下含有文件或节点！");

    public static final CodeObject TOPDF_FAILED = new CodeObject("60012","PDF文件转换失败！");

    public static final CodeObject TOPDF_SUCESS = new CodeObject("60011","PDF文件转换成功！");

    public static final CodeObject TOPDF_LOADING = new CodeObject("60013","PDF文件正在转换！");

    public static final CodeObject TOPDF_NONEFILE = new CodeObject("60014","转换失败,转换文件不存在！");

    public static final CodeObject TOPDF_DISCONECT = new CodeObject("60015","转换失败,OpenOffice服务未启动！");

    public static final CodeObject TOPDF_BACKGROUNDTOPDF= new CodeObject("60016","手动转换失败,后台调度正在转换该文件！");

    public static final CodeObject UPDATE_SUCCESS = new CodeObject("60005","更新成功！");

    public static final CodeObject UPDATE_FAILED = new CodeObject("60006","更新失败！");

    public static final CodeObject BAD_REQUEST = new CodeObject("10001","400 (错误请求) 服务器不理解请求的语法。");

    public static final CodeObject NOT_FOUND = new CodeObject("10002","404 (未找到) 服务器找不到请求的资源。");

    public static final CodeObject METHOD_NOT_ALLOWED = new CodeObject("10003","405 (方法禁用) 禁用请求中指定的方法。");

    public static final CodeObject NOT_ACCEPTABLE = new CodeObject("10004","406 (不接受) 无法使用请求的内容特性响应请求的网页。");

    public static final CodeObject UNSUPPORTED_MEDIA_TYPE = new CodeObject("10005","415 (不支持的媒体类型) 请求的格式不受请求页面的支持。");

    public static final CodeObject INTERNAL_SERVER_ERROR = new CodeObject("10006","500 (服务器内部错误) 服务器遇到错误，无法完成请求。");

    public static final CodeObject UNAUTHORIZED = new CodeObject("10007","401 (未授权) 请求要求身份验证。 (Basic 认证错误或无权限头)");

    public static final CodeObject FORBIDDEN = new CodeObject("10008","403 (禁止) 服务器拒绝请求。");

    public static final CodeObject INVALID_FIELD = new CodeObject("20002","400 字段校验错误");

    public static final CodeObject INVALID_GRANT = new CodeObject("30001","400 用户名,密码错误");

    public static final CodeObject DISABLED_USER = new CodeObject("30002","403 用户被冻结");

    public static final CodeObject USER_EXIST = new CodeObject("30101","400 用户已存在");

    public static final CodeObject USER_NOT_EXIST = new CodeObject("30102","400 用户不存在");

    public static final CodeObject SMS_TOO_MUCH = new CodeObject("30103","403 短信发送太频繁");

    public static final CodeObject INVALID_CAPTCHA = new CodeObject("30201","400 无效验证码");

    public static final CodeObject ROLE_EXIST = new CodeObject("30301","400 角色已存在");

    public static final CodeObject ROLE_NOT_EXIST = new CodeObject("30302","400 角色不存在");

    public static final CodeObject NULL_OBJECT = new CodeObject("40001","对象不存在");

    public static final CodeObject EXIST_OBJECT = new CodeObject("40002","对象存在");

    public static final CodeObject UNKNOWN_ERROR =new CodeObject("99999","未知错误");

    public static final String PERSISTENCE_STATUS = "A";

    public static final CodeObject NO_AUTHORITY =new CodeObject("40401","权限不足");

    public static final String PERSISTENCE_DELETE_STATUS = "I";

    public static final String TREE_STATUS_OPEN = "open";

    public static final String TREE_STATUS_CLOSED = "closed";

    public static final CodeObject FIELD_CONNECTION =new CodeObject("40402","有未删除的关联字段，请检查后重试");

    //承包返回码110000-120000用于REST API 返回码，http://www.yuntongxun.com/doc/rest/restabout/3_1_1_7.html
    //620215-620224也被承包了

    public static final CodeObject SYSTEM_BUSY =new CodeObject("50000","系统繁忙");

    public static final CodeObject ADD_ACCOUNT_SUCCESS =new CodeObject("50001","添加公众号成功");

    public static final CodeObject ADD_ACCOUNT_FAILED =new CodeObject("50002","添加公众号失败");

    public static final CodeObject DELETE_ACCOUNT_SUCCESS =new CodeObject("50003","删除公众号成功");

    public static final CodeObject DELETE_ACCOUNT_FAILED =new CodeObject("50004","删除公众号失败");

    public static final CodeObject UPDATE_ACCOUNT_SUCCESS =new CodeObject("50005","更新公众号成功");

    public static final CodeObject UPDATE_ACCOUNT_FAILED =new CodeObject("50006","更新公众号失败");

    public static final CodeObject ACCESS_TOKEN_INVALID =new CodeObject("50010","无效的access_token，请重试");

    public static final CodeObject TAG_ADD_SUCCESS =new CodeObject("50011","添加成功");

    public static final CodeObject TAG_REPEAT =new CodeObject("50012","标签名非法，请注意不能和其他标签重名");

    public static final CodeObject TAG_NAME_OVERFLOW =new CodeObject("50013","标签名长度超过30个字节");

    public static final CodeObject TAG_ADDFAILED_NUM_OVERFLOW =new CodeObject("50014","创建的标签数过多，请注意不能超过100个");

    public static final CodeObject TAG_ADD_FAILED =new CodeObject("50015","添加失败");

    public static final CodeObject DATA_ERROR =new CodeObject("50016","非法数据");

    public static final CodeObject TAG_DELETE_SUCCESS =new CodeObject("50021","删除成功");

    public static final CodeObject TAG_NOCHANGE_DEFAULT =new CodeObject("50022","该标签为系统默认保留的标签，不可修改");

    public static final CodeObject TAG_DELETE_FAILED_DIRECT =new CodeObject("50023","该标签下粉丝数超过10w，不允许直接删除");

    public static final CodeObject TAG_DELETE_FAILED =new CodeObject("50024","删除失败");

    public static final CodeObject TAG_UPDATE_SUCCESS =new CodeObject("50031","更新成功");

    public static final CodeObject TAG_UPDATE_FAILED =new CodeObject("50032","更新失败");

    public static final CodeObject TAG_SYNCHRONIZE_SUCCESS =new CodeObject("50041","同步标签信息成功");

    public static final CodeObject TAG_SYNCHRONIZE_FAILED =new CodeObject("50042","同步标签信息失败，请选择一个公众号进行同步");

    public static final CodeObject User_UPDATEREMARK_SUCCESS =new CodeObject("50051","设置备注名成功");

    public static final CodeObject User_UPDATEREMARK_FAILED =new CodeObject("50052","设置备注名失败");

    public static final CodeObject User_UPDATETAG_SUCCCESS =new CodeObject("50053","添加分组成功");

    public static final CodeObject User_UPDATETAG_ILLEGALTAG =new CodeObject("50054","非法的分组");

    public static final CodeObject User_UPDATETAG_TAGNUMMAX =new CodeObject("50055","用户分组数量达到上限（不能超过20个）");

    public static final CodeObject User_UPDATETAG_ILLEGALUSER =new CodeObject("50056","非法用户");

    public static final CodeObject User_UPDATETAG_FAILED =new CodeObject("50057","添加分组失败");

    public static final CodeObject User_DELETETAG_SUCCESS =new CodeObject("50058","删除分组成功");

    public static final CodeObject User_DELETETAG_FAILED =new CodeObject("50059","删除分组失败");

    public static final CodeObject User_SYNCHRONIZE_SUCCESS =new CodeObject("50060","同步用户成功");

    public static final CodeObject User_SYNCHRONIZE_FAILED =new CodeObject("50061","同步用户失败");

    public static final CodeObject MESSAGE_SEND_SUCCESS =new CodeObject("50071","消息发送成功");

    public static final CodeObject MESSAGE_SEND_NOQUOTA =new CodeObject("50072","群发次数已用完");

    public static final CodeObject MESSAGE_SEND_FAILED =new CodeObject("50073","消息发送失败");

    public static final CodeObject MESSAGE_ADD_SUCCESS =new CodeObject("50081","消息记录保存成功");

    public static final CodeObject MESSAGE_ADD_FAILED =new CodeObject("50082","消息记录保存失败");

    public static final CodeObject MESSAGE_DELETE_SUCCESS =new CodeObject("50083","消息记录删除成功");

    public static final CodeObject MESSAGE_DELETE_FAILED =new CodeObject("50084","消息记录删除失败");

    public static final CodeObject DELETEBOOK_HAVEMANS = new CodeObject("70010","该目录无法删除，下含子目录或人员！");

}
