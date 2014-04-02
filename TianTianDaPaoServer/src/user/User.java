package user;

public class User {
	private int uid;
	private String  sessionKey;;
	//usergame
	private int ustatus;
	private int ulogindays;
	private int uloginbonus;
	private int uchip;
	private int ulevel;
	private int uexp;
	//userinfo
	private String puid;
	private String id;
	private String password;
	private String uemail;
	private int pfid;
	private int usid;
	private String udevice;
	private String uname;
	private int uface;
	private int gender;
	public User(){
		
	}
	
	public User(int ustatus, int ulogindays,int uloginbonus,int uchip,int ulevel,
			int uexp,int uid, String puid, String id, String password, String uemail,
			int pfid, int usid, String udevice, String uname, int uface,
			int gender)
	{
	this.ustatus=ustatus;
	this.ulogindays=ulogindays;
	this.uloginbonus=uloginbonus;
	this.uchip=uchip;
	this.ulevel=ulevel;
	this.uexp=uexp;
	
	this.uid = uid;
	this.puid = puid;
	this.id = id;
	this.password = password;
	this.uemail = uemail;
	this.pfid = pfid;
	this.usid = usid;
	this.udevice = udevice;
	this.uname = uname;
	this.uface = uface;
	this.gender = gender;
	}

	public int getUstatus() {
		return ustatus;
	}

	public void setUstatus(int ustatus) {
		this.ustatus = ustatus;
	}

	public int getUlogindays() {
		return ulogindays;
	}

	public void setUlogindays(int ulogindays) {
		this.ulogindays = ulogindays;
	}

	public int getUloginbonus() {
		return uloginbonus;
	}

	public void setUloginbonus(int uloginbonus) {
		this.uloginbonus = uloginbonus;
	}

	public int getUchip() {
		return uchip;
	}

	public void setUchip(int uchip) {
		this.uchip = uchip;
	}

	public int getUlevel() {
		return ulevel;
	}

	public void setUlevel(int ulevel) {
		this.ulevel = ulevel;
	}

	public int getUexp() {
		return uexp;
	}

	public void setUexp(int uexp) {
		this.uexp = uexp;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public int getPfid() {
		return pfid;
	}

	public void setPfid(int pfid) {
		this.pfid = pfid;
	}

	public int getUsid() {
		return usid;
	}

	public void setUsid(int usid) {
		this.usid = usid;
	}

	public String getUdevice() {
		return udevice;
	}

	public void setUdevice(String udevice) {
		this.udevice = udevice;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public int getUface() {
		return uface;
	}

	public void setUface(int uface) {
		this.uface = uface;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
