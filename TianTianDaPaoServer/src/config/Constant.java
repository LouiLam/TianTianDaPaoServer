package config;

public class Constant {

	public static final String UGENDER_MALE = "1";
	public static final String UGENDER_FEMALE="0";
	
	public static final String USTATUS_NORMAL = "0";
	public static final String USTATUS_LOCKOUT="1";
	public static final String USTATUS_GM="2";
	
	public static final int RET_LOGIN_SUCCESS = 0;//登录成功
	public static final int RET_ACCOUNT_LOCKOUT=1;//帐号被锁定
	public static final int RET_INVALID_ARG=2;//预留字段暂无用
	public static final int RET_INVALID_TOKEN=3;//非法或无效的token
	public static final int RET_INVALID_PLATFORM=4;//预留字段暂无用
	public static final int RET_REG_SUCCESS=5;//注册成功
	public static final int RET_REG_FAILED_ID_REPART=6; //注册失败，帐号名重复
	public static final int RET_SEARCH_INVALID=7;//预留字段暂无用
	public static final int RET_SEARCH_INVALID1=8;//预留字段暂无用
	public static final int RET_ACCOUNT_VALIDATE_INVALID=9;//注册帐号格式验证非法
	public static final int RET_PASSWORD_VALIDATE_INVALID=10;//注册密码格式验证非法
	public static final int RET_ACCOUNT_INVALID=11;//登录帐号不存在
	public static final int RET_JJC_SUCCESS = 12;//竞技场请求成功
	public static final int RET_JJC_FAILED = 13;//竞技场数据请求失败：utoken非法或不存在
	public static final int RET_JJC_PK_FAILED = 14;//竞技场挑战请求失败：utoken非法或不存在
	public static final int RET_JJC_PK_FAILED_UID_NOT_EXIST = 15;//竞技场挑战请求失败：被pk的用户uid不存在
	public static final int RET_JJC_PK_FAILED_UID_MYSELF = 16;//竞技场挑战请求失败：被pk的用户为自己的uid
	public static final int RET_JJC_PK_SUCCESS = 17;//竞技场挑战请求成功,数据更新
	public static final int RET_JJC_GET_SCORE_FAILED = 18;//竞技场获取积分请求失败：utoken非法或不存在
	public static final int RET_JJC_GET_SCORE_FAILED_HAVE_DONE = 19;//竞技场获取积分请求失败：该用户已领取过积分
	public static final int RET_JJC_GET_SCORE_SUCCESS = 20;//竞技场获取积分请求成功,数据更新
	public static final int RET_LOGIN_REWARD_SUCCESS = 21;//登录奖励领取成功
	public static final int RET_LOGIN_REWARD_FAILED_HAVE_DONE = 22;//领取登录奖励领取失败：今天该用户已经领取过了
	public static final int RET_LOGIN_REWARD_FAILED = 23;//领取登录奖励领取失败：utoken非法或不存在
	public static final int RET_JJC_PK_FAILED_UID_COUNT_LIMIT = 24;//竞技场非法请求：今天挑战次数已到上限
	public static final int RET_GAME_OVER_FAILED = 25;//游戏正常结束请求失败：utoken非法或不存在
	public static final int RET_GAME_OVER_SUCCESS = 26;//游戏正常结束请求成功
	public static final int RET_GAME_OVER_FAILED_MISS_ARG = 27;//游戏正常结束请求失败：缺少参数record
	public static final int RET_GAME_START_FAILED = 28;//游戏正常开始请求失败：utoken非法或不存在
	public static final int RET_GAME_START_FAILED_TILI_NOT_ENOUGH = 29;//游戏正常开始请求失败：体力不够
	public static final int RET_GAME_START_SUCCESS = 30;//游戏正常开始请求成功
	public static final int RET_GET_TILI_INFO_FAILED = 31;//获取体力信息请求失败：utoken非法或不存在
	public static final int RET_GET_TILI_INFO_SUCCESS = 32;//获取体力信息请求成功，数据更新
	public static final int RET_REG_FAILED_MISS_ARG = 33;//注册参数要求非法
	public static final int RET_SCORE_LOTTERY_FAILED = 34;//积分抽奖请求失败：utoken非法或不存在
	public static final int RET_SCORE_LOTTERY_SUCCESS = 35;//积分抽奖请求成功,数据更新
	public static final int RET_SCORE_LOTTERY_FAILED_SCORE_NOT_ENOUGH = 36;//积分抽奖请求失败：积分不够
	public static final int RET_JJC_PK_FAILED_RANK_ERROR = 37;//竞技场挑战请求失败：被PK用户排名比发起用户排名低，不允许挑战
	public static final int RET_SCORE_EXCHANGE_ITEMS_FAILED = 38;//积分兑换物品请求失败：utoken非法或不存在
	public static final int RET_SCORE_EXCHANGE_ITEMS_FAILED_SCORE_NOT_ENOUGH = 39;//积分兑换物品请求失败：积分不够
	public static final int RET_SCORE_EXCHANGE_ITEMS_SUCCESS = 40;//积分兑换物品请求成功,数据更新
	public static final int RET_SCORE_EXCHANGE_ITEMS_FAILED_ID_NOT_EXIST = 41;//积分兑换物品请求失败,兑换物品ID不存在
	public static final int RET_SCORE_EXCHANGE_ITEMS_FAILED_MISS_ARG = 42;//积分兑换物品请求失败,缺少参数item_id
	public static final int RET_SCORE_EXCHANGE_ITEMS_FAILED_REPEAT_EXCHANGE = 43;//积分兑换物品请求失败,一次性兑换物品不允许重复兑换
	public static final int RET_BUY_ITEMS_FAILED = 44;//购买物品请求失败：utoken非法或不存在
	public static final int RET_BUY_ITEMS_FAILED_MISS_ARG = 45;//购买物品请求失败,缺少参数item_id
	public static final int RET_BUY_ITEMS_FAILED_ID_NOT_EXIST = 46;//购买物品请求失败,兑换物品ID不存在
	public static final int RET_BUY_ITEMS_FAILED_MONEY_NOT_ENOUGH = 47;//购买物品请求失败：货币不够
	public static final int RET_BUY_ITEMS_FAILED_REPEAT_BUY = 48;//购买物品请求失败,一次性购买物品不允许重复购买
	public static final int RET_BUY_ITEMS_SUCCESS = 49;//购买物品请求成功,数据更新
	public static final int RET_CONFIG_INFO_FAILED = 50;//获取配置信息请求失败：utoken非法或不存在
	public static final int RET_CONFIG_INFO_SUCCESS = 51;//获取配置信息请求成功
	public final static String RET = "ret";
	public final static String MSG = "msg";
	
	
	
}
