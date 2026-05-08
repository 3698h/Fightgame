package Fightgame.domain;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;
    public int mp;
    public int maxMp;

    public Character() {

    }



    public Character(String name, int HP, int mp, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.mp = mp;
        this.maxMp = mp;
        this.attack = attack;
        this.defense = defense;
    }




    // 判断角色是否存活
    public boolean isAlive() {
        return HP > 0;
    }

    // 恢复血量，不超过最大生命值
    public void healHp(int amount){
        HP+=amount;
        if (HP> maxHP){
            HP = maxHP;
        }

    }
    // 恢复蓝量，不超过最大蓝量
    public void healMp(int amount){
        amount = (int)(amount * 1.3);
        mp += amount;
        if (mp > maxMp){
            mp = maxMp;
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
        return name+"[当前生命："+HP+", 当前蓝量："+mp+", 攻击："+attack+", 防御："+defense+"]";

    }
}
