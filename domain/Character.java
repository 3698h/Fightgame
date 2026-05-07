package Fightgame.domain;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public Character() {

    }
    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }
    // 判断角色是否存活
    public boolean isAlive() {
        return HP > 0;
    }

    // 恢复血量，不超过最大生命值
    public void heal(int amount){
        HP+=amount;
        if (HP> maxHP){
            HP = maxHP;
        }
    }

    // 受到伤害，血量最低为0
    public  void takeDamage(int damage){
        HP -= damage;
        if (HP<0){
            HP = 0;
        }
    }

    // 展示角色属性
    public String show(){
        return name+"[当前生命："+HP+", 攻击："+attack+", 防御："+defense+"]";

    }
}
