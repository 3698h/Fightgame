package Fightgame.ui;

import Fightgame.domain.EnemyCharacter;
import Fightgame.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    // 复用Scanner对象，避免重复创建
    private Scanner sc = new Scanner(System.in);

    // 启动游戏：创建角色，循环匹配敌人战斗，每3胜增强双方属性
    public void gameStart(String username) {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("    🎮 " + username + "欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════╝");

        HeroCharacter player = createPlayer(username);
        System.out.println("角色已创建成功！");
        System.out.println("\uD83C\uDF1F 初始属性:" + player.show());
        System.out.println("\uD83C\uDF1F 拥有的技能:" + player.showSkill());


        //创建多个敌人列表
        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 15, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));

        //准备战斗(一次跟多个敌人战斗)
        int count = 1;
        int wins = 0;
        int lastEnhancedWins = 0; // 记录上次增强时的胜场，防止重复增强
        while (player.isAlive()) {
            // 每3胜增强所有敌人属性，避免重复增强
            if (wins != 0 && wins % 3 == 0 && wins != lastEnhancedWins) {
                lastEnhancedWins = wins;
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHP = c.maxHP + 10;
                    c.attack = c.attack + 3;
                    c.defense = c.defense + 2;
                }
                System.out.println("⭐ 敌人变强了！所有敌人属性提升！");
            }

            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            // 被击败的敌人恢复满血
            if (enemy.HP <= 0) {
                enemy.HP = enemy.maxHP;
            }
            enemy.defending = false;
            System.out.println(enemy.show());


            System.out.println("═══════════════════════════════════════");
            System.out.println("⚔\uFE0F 第" + count + "场战斗开始！对手: " + enemy.name);
            //我打敌人一下
            //7判断敌人的血量是否为e，如果为0，结束当前的战斗：继续跟下一个敌人进行战斗（结束内循环，继续执行外循环）
            int round = 1;
            while (player.isAlive()) {
                //显示生命值
                System.out.println("---------------------------------------");
                System.out.println("⚔\uFE0F 第 " + round + " 回合开始！");
                System.out.println(showHealthBar(player.name, player.HP, player.maxHP));
                System.out.println(showHealthBar(enemy.name, enemy.HP, enemy.maxHP));

                //玩家回合
                playerTurn(player, enemy);

                //判断敌人是否存活
                if (!enemy.isAlive()) {
                    //🎉 你击败了 敏捷刺客！
                    System.out.println("🎉 你击败了 " + enemy.name + "！");
                    System.out.println("恭喜你，你赢了！");
                    wins++;
                    break;
                }

                //敌人回合
                enemyTurn(enemy, player);
                //判断玩家血量
                if (!player.isAlive()){
                    System.out.println("💀 你被 " + enemy.name + " 击败了！");
                    System.out.println("游戏结束！");
                    break;
                }
                //击败对方
                round++;
            }

            //跟一个敌人战斗结束，恢复玩家血量
            if (player.isAlive()){
                //恢复玩家血量
                int healHP = r.nextInt(20, 41);
                player.heal(healHP);
                //提示
                System.out.println("💚 战斗结束！你恢复了 " + healHP + " 点生命值");
                System.out.println("🏆 当前胜场: " + wins);
            }
            
            // 每3胜提升玩家属性
            if (player.isAlive() && wins > 0 && wins % 3 == 0){
                System.out.println("⭐ 恭喜！ 你获得了属性提升！");
                player.maxHP+=30;
                player.attack+=5;
                player.defense+=3;
                System.out.println("最大生命值+30，攻击力+5，防御力+3");
                System.out.println("当前属性:"+player.show());
            }

            //询问是否继续
            if (player.isAlive()){
                System.out.println("是否继续战斗？(y/n)");
                String input = sc.next();
                if ("y".equalsIgnoreCase(input)){
                    count++;
                    continue;
                }else if("n".equalsIgnoreCase(input)){
                    System.out.println("游戏结束！");
                    break;
                }else{
                    System.out.println("输入错误！游戏继续");
                    continue;
                }


            }


        }

        System.out.println("========================");
        System.out.println("游戏结束！");
        System.out.println("总胜场："+wins);
        System.out.println("感谢游玩");



    }

    // 敌人回合：50%概率使用技能，否则普通攻击
    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("=====" + enemy.name + "的回合 =====");

        //计算当前是普通攻击，还是技能攻击
        String action = "普通攻击";

        //几率计算
        Random r = new Random();
        int num = r.nextInt(10);

        if (num >= 5) {
            action = enemy.skill;
        }
        //根据不同情况，采取不同手段
        switch (action) {
            case "普通攻击":
                System.out.println("敌人采取了普通攻击");
                int damage = calculateDamage(enemy.attack, player.defense);

                //⚔️ 敏捷刺客 对你使用了普通攻击，造成 15 点伤害！
                System.out.println("⚔\uFE0F " + enemy.name + " 对你使用了普通攻击，造成 " + damage + " 点伤害！");
                player.takeDamage(damage);
                break;
            case "猛击":
                System.out.println("当前的战士采取了猛击");
                damage = calculateDamage((int) (enemy.attack * 1.5), player.defense);
                System.out.println("⚔\uFE0F " + enemy.name + " 对你使用了猛击，造成 " + damage + " 点伤害！");
                player.takeDamage(damage);
                break;
            case "快速攻击":
                System.out.println("当前的刺客采取了快速攻击");
                int damage3= 0;
                for (int i = 0; i <2 ; i++) {
                    damage3 += calculateDamage(enemy.attack/2, player.defense);
                }
                player.takeDamage(damage3);
                System.out.println("⭕ " + enemy.name + " 对你使用了快速攻击，造成 " + damage3 + " 点伤害！");

                break;
            case "防御姿态":
                System.out.println("当前的坦克采取了防御姿态  buff");
                enemy.defending = true;
                //坦克采取防御姿态，本回合伤害减半
                System.out.println("🛡️ " + enemy.name + " 采取防御姿态，本回合受到的伤害减半！");
                break;
            case "火球术":
                System.out.println("当前的法师采取了火球术");
                damage = calculateDamage((int)(enemy.attack*1.8), player.defense);
                System.out.println("🔥 " + enemy.name + " 对你使用了火球术，造成 " + damage + " 点伤害！");
                player.takeDamage(damage);
                break;
        }
    }

    // 显示血条：█ 表示已损失血量，格式为 name:[████    ] HP/maxHP HP
    public String showHealthBar(String name, int HP, int maxHP) {
        int barLength = 20;

        int filled = (int) ((HP * 1.0 / maxHP) * barLength);

        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":[");

        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                sb.append("█");
            } else {
                sb.append(" ");
            }
        }

        sb.append("] ").append(HP).append("/").append(maxHP).append(" HP");

        return sb.toString();
    }


    // 创建玩家角色：分配20点属性到生命/攻击/防御，添加默认技能
    public HeroCharacter createPlayer(String username) {
        System.out.println("创建您的角色");
        System.out.println("您的角色名为：" + username);

        //属性分配
        int points = 20;
        /*Scanner sc=new Scanner(System.in);
        int hpPoint=sc.nextInt();

        if(hpPoint<0){
            System.out.println("输入错误,默认分配0点");
            hpPoint=0;
        }

        if (hpPoint>points){
            System.out.println("属性点不足！剩余属性点全部分配生命值");
            hpPoint=points;
        }

        //分配属性点
        points= points-hpPoint;

        System.out.println("攻击力 (每点+2ATK)");
        int atkPoint=sc.nextInt();

        if(atkPoint<0){
            System.out.println("输入错误,默认分配0点");
            atkPoint=0;
        }

        if (atkPoint>points){
            System.out.println("属性点不足！剩余属性点全部分配攻击力")
            atkPoint=points;;
        }

        points= points-atkPoint;
    }*/
        //提示：
        System.out.println("请分配您的角色属性(共20点)");
        System.out.println("1.生命值(每点 + 10HP)");
        System.out.println("2.攻击力(每点 + 20ATK)");
        System.out.println("3.防御力(每点 + 1 DEF)");

        Scanner sc = new Scanner(System.in);

        String[] attributes = {"生命值", "攻击力", "防御力"};
        int[] values = new int[3];

        //循环
        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到" + attributes[i] + "(剩余点数：" + points + "):");
            int input = sc.nextInt();

            //如果输入点数小于0
            if (input < 0) {
                System.out.println("输入错误,默认分配0点");
                input = 0;
            }

            //如果分配点超出剩余点数
            if (input > points) {
                System.out.println("属性点不足！剩余属性点全部分配" + attributes[i]);
                input = points;
            }

            //计算剩余属性点
            points = points - input;

            //保存到数组中
            values[i] = input;
        }

        //已知用户要分配的属性点--->  values[i]

        //创建角色
        HeroCharacter player = new HeroCharacter(
                username,
                100 + values[0] * 10,//生命值
                10 + values[1] * 2,//攻击力
                values[2]//防御力


        );

        //添加玩家技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");

        //返回玩家对象
        return player;

    }

    // 玩家回合：选择技能，1普通攻击 2强力一击(消耗10HP) 3生命汲取(消耗10HP)
    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("==== 你的回合 ====");
        System.out.println("请选择技能:");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击");
        System.out.println("3.生命汲取");
        String choose = sc.next();
        switch (choose) {
            default:
                System.out.println("没有操作默认使用普通攻击");
            case "1":
                int damage = calculateDamage(player.attack, enemy.defense);
                //⚔️你对 敏捷刺客 使用了，造成 31 点伤害！
                System.out.println("⚔️ 你对 " + enemy.name + " 使用了普通攻击，造成 " + damage + " 点伤害！");
                enemy.takeDamage(damage);
                break;
            case "2":

                if (player.HP > 10) {
                    player.HP -= 10;
                    int damage1 = calculateDamage((int) (player.attack * 1.8), enemy.defense);
                    //💥 消耗10HP，你对 敏捷刺客 使用了强力一击，造成 31 点伤害！
                    System.out.println("💥 消耗10HP，你对 " + enemy.name + " 使用了强力一击，造成 " + damage1 + " 点伤害！");
                    enemy.takeDamage(damage1);
                } else {
                    System.out.println("你的体力不足,无法使用");
                }

                break;
            case "3":

                if (player.HP > 10) {
                    player.HP -= 10;
                    Random random = new Random();
                    int healHP = random.nextInt(11) + 10;
                    player.heal(healHP);
                    //💚消耗10HP,你使用了生命汲取，恢复了10点生命值！
                    System.out.println("\uD83D\uDC9A消耗10HP,你使用了生命汲取，恢复了" + healHP + "点生命值！");
                } else {
                    System.out.println("你的体力不足,无法使用");
                }

                break;

        }
    }

    // 伤害计算：攻击力 - 防御力，最低为1
    public int calculateDamage(int attack, int defense) {
        //伤害计算
        //伤害=攻击力-防御力
        int damage = attack - defense;
        if (damage < 1) {
            damage = 1;
        }
        return damage;

    }

}
