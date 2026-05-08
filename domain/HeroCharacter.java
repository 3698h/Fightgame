package Fightgame.domain;

import java.util.ArrayList;

public class HeroCharacter extends Character{
    public ArrayList<String> skillList;
    public ArrayList<Consumable> packageList;

    public HeroCharacter() {
        super();
        skillList = new ArrayList<>();
        packageList = new ArrayList<>();
    }
    public HeroCharacter(String name, int HP, int mp,int attack, int defense) {
        super(name, HP,mp,attack, defense);
        skillList = new ArrayList<>();
        packageList = new ArrayList<>();
    }



    // 展示技能列表，逗号分隔
    public String showSkill(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            //添加数据
            sb.append(skillList.get(i));
            //如果不是最后一个元素，再添加逗号空格
            if (i!=skillList.size()-1){
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
