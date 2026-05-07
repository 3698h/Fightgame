package Fightgame.domain;

public class EnemyCharacter extends Character {
    public String skill;
    public boolean defending;


    public EnemyCharacter() {
        super();
    }

    // 新增：5个参数的构造方法
    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
        this.defending = false;  // 默认不防御
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill, boolean defending) {
        super(name, HP, attack, defense);
        this.skill = skill;
        this.defending = defending;
    }

    // 受到伤害时，若处于防御状态则伤害减半，防御状态自动解除
    @Override
    public void takeDamage(int damage) {
        if (defending) {
            damage = damage / 2 > 1 ? damage / 2 : 1;
        }
        super.takeDamage(damage);
        defending = false;
    }

}
