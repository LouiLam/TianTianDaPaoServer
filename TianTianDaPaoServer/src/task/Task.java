package task;

public class Task {
int id;
int arg1,arg2;
String name;
String des;
String reward;
String type;
int status;

public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getArg1() {
	return arg1;
}
public void setArg1(int arg1) {
	this.arg1 = arg1;
}
public int getArg2() {
	return arg2;
}
public void setArg2(int arg2) {
	this.arg2 = arg2;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDes() {
	return des;
}
public void setDes(String des) {
	this.des = des;
}
public String getReward() {
	return reward;
}
public void setReward(String reward) {
	this.reward = reward;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}

}
