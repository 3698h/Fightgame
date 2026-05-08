package Fightgame.domain;

public class EnemyCharacter extends Character {
    public String skill;
    public boolean defending;


    public EnemyCharacter() {
        super();
    }

    // 5个参数的构造方法（默认0蓝量）
    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, 0, attack, defense);
        this.skill = skill;
        this.defending = false;
    }

    // 新增：6个参数的构造方法（包含mp）
    public EnemyCharacter(String name, int HP, int mp, int attack, int defense, String skill) {
        super(name, HP, mp, attack, defense);
        this.skill = skill;
        this.defending = false;
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill, boolean defending) {
        super(name, HP, 0, attack, defense);
        this.skill = skill;
        this.defending = defending;
    }

    public EnemyCharacter(String name, int HP, int mp, int attack, int defense, String skill, boolean defending) {
        super(name, HP, mp, attack, defense);
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
