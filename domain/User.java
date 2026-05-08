package Fightgame.domain;

import java.util.Random;

public class User {
    //属性：id、用户名、密码、状态
    private String id;
    private String username;
    private String password;
    private boolean status;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {
        id=createId();
        //修改status的值
        status=true;
    }

    public User( String username, String password) {
        id=createId();
        this.username = username;
        this.password = password;
        status=true;
    }

    // 生成用户ID，格式为 heima + 5位随机数字
    public String createId(){
        StringBuilder sb=new StringBuilder("heima");
        Random r=new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
