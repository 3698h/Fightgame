package Fightgame.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import Fightgame.domain.User;

public class Login {

    /**
     * 启动登录注册系统的主方法
     * 显示游戏菜单，提供登录、注册、退出三个选项
     * 根据用户选择调用相应的功能模块
     */
    public void start(){
        System.out.println("游戏登陆注册页面打开了");

        ArrayList<User> list=new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");

            String choose = sc.next();

            switch ( choose){
                case "1"->login(list);
                case "2"->register(list, sc);
                case "3"->{
                    System.out.println("游戏退出");
                    sc.close();
                    System.exit(0);
                }
                default -> System.out.println("输入有误，请重新输入");


            }
        }

    }
    /**
     * 用户登录功能
     * 接收用户输入的用户名，验证用户是否存在、是否被锁定
     * 先验证验证码（3次机会），再验证密码（3次机会）
     * @param list 已注册用户列表
     */
    public void login(ArrayList<User> list){
        System.out.println("选择登录");


        //用户名如果未注册提示：用户名未注册，请先注册
        //用户被锁定提示：用户xxx已经锁定，请联系黑马程序员官方客服：XXX-XXXXX
        //验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码
        // 判断用户名和密码是否正确，有3次机会，满3次账户锁定。

        //键盘录入用户名
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();

        //判断是否注册
        if (!contains(username,list)){
            System.out.println("用户名"+username+"未注册");
            return;
        }
        //判断账户状态
        int index = findIndex(list, username);

        // 根据索引获取用户对象
        User u = list.get(index);
       if (!u.isStatus()){
           System.out.println("用户"+username+"已锁定，请联系黑马程序员官方客服：XXX-XXXXX");
           return;
       }

        String rightPassword = u.getPassword();
        
        // 先验证验证码，给用户3次机会
        boolean codeVerified = false;
        for (int codeTry = 0; codeTry < 3; codeTry++) {
            String rightCode = getCode();
            System.out.println("验证码："+rightCode);
            System.out.println("请输入验证码：");
            String code = sc.next();

            if (code.equalsIgnoreCase(rightCode)){
                System.out.println("验证码正确");
                codeVerified = true;
                break;
            }else{
                if (codeTry < 2) {
                    System.out.println("验证码输入错误，请重新输入");
                } else {
                    System.out.println("验证码输入错误次数过多，请重新登录");
                    return;
                }
            }
        }
        
        // 验证码通过后，再验证密码
        if (codeVerified) {
            for (int i = 0; i < 3; i++) {
                System.out.println("请输入密码：");
                String password = sc.next();

                //密码验证
                if (rightPassword.equals(password)){
                    System.out.println("用户"+username+"登录成功,游戏启动");
                    //启动游戏
                    FightingGame fg=new FightingGame();
                    fg.gameStart(username);
                    break;
                }else{
                    if (i==2){
                        u.setStatus(false);
                        System.out.println("用户"+username+"已锁定，请联系黑马程序员官方客服：XXX-XXXXX");
                    } else {
                        System.out.println("密码输入错误，还剩" + (2-i) + "次机会");
                    }
                }
            }
        }

    }
    /**
     * 查找用户在列表中的索引位置
     * @param list 用户列表
     * @param username 用户名
     * @return 找到返回索引，未找到返回-1
     */
    public int findIndex(ArrayList<User> list, String username){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }
    /**
     * 用户注册功能
     * 引导用户输入用户名和密码，进行多项验证
     * 验证通过后创建新用户并添加到用户列表
     * 用户名验证：唯一性、长度3-16位、只能由字母和数字组成且不能是纯数字
     * 密码验证：长度3-8位、必须是字母和数字的组合、两次输入一致
     * @param list 已注册用户列表
     * @param sc 扫描器对象，用于读取用户输入
     */
    public void register(ArrayList<User> list, Scanner sc){
        System.out.println("选择注册");
        User u=new User();
        String username = null;
        while (true) {
            System.out.println("请输入用户名：");
            username = sc.next();
            //- 用户名唯一
            //- 长度必须在3 ~ 16位
            //- 只能由字母、数字组成，不能是纯数字
            if(!checkLen(3,16,username)){
                System.out.println("用户名长度在3 ~ 16位");
                continue;
            }
            if(!checkUsername(username)){
                System.out.println("用户名只能由字母、数字组成，不能是纯数字");
                continue;
            }
            if (contains(username,list)){
                System.out.println("用户名已存在");
                continue;
            }
            u.setUsername(username);
            break;

        }
        while (true) {
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请输入确认密码：");
            String password2 = sc.next();
            //- 长度3 ~ 8位
            if (!checkLen(3,8,password1)){
                System.out.println("密码长度3 ~ 8位");
                continue;
            }
            //- 只能是字母加数字的组合，不能有其他字母
            if (!checkPassword(password1)){
                System.out.println("密码只能是字母加数字的组合，不能有其他字母");
                continue;
            }
            if(!password1.equals(password2)){
                System.out.println("两次密码不一致");
                continue;
            }
            u.setPassword(password1);
            list.add(u);
            System.out.println(username + "注册成功！");
            break;
        }
    }
    /**
     * 检查用户名是否已存在于用户列表中
     * 遍历用户列表，查找是否有相同的用户名
     * @param username 待检查的用户名
     * @param list 已注册用户列表
     * @return 如果用户名已存在返回true，否则返回false
     */
    public boolean contains(String username,ArrayList<User> list){
        for (User u : list) {
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    /**
     * 统计字符串中字母、数字和其他字符的数量
     * 遍历字符串的每个字符，分类统计各类字符的出现次数
     * @param username 待统计的字符串
     * @return 长度为3的整型数组，[0]为字母数量，[1]为数字数量，[2]为其他字符数量
     */
    public int[] getCount(String username) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if (Character.isLetter(c)) {
                charCount++;
            } else if (Character.isDigit(c)) {
                numCount++;
            } else {
                otherCount++;
            }

        }
        return new int[]{charCount, numCount, otherCount};
    }
    /**
     * 验证用户名格式是否合法
     * 要求：至少包含1个字母、可以包含数字、不能包含其他字符、不能是纯数字
     * 通过getCount方法统计字符类型后进行判断
     * @param username 待验证的用户名
     * @return 如果用户名格式合法返回true，否则返回false
     */
    public boolean checkUsername(String username){
        int []arr=getCount(username);
        return arr[0]>0 && arr[1]>=0 && arr[2]==0;
    }
    /**
     * 验证密码格式是否合法
     * 要求：至少包含1个字母、至少包含1个数字、不能包含其他字符
     * 通过getCount方法统计字符类型后进行判断
     * @param password 待验证的密码
     * @return 如果密码格式合法返回true，否则返回false
     */
    public boolean checkPassword(String password){
        int []arr=getCount(password);
        return arr[0]>0 && arr[1]>0 && arr[2]==0;
    }
    /**
     * 检查字符串长度是否在指定范围内
     * 用于验证用户名和密码的长度是否符合要求
     * @param minLen 最小长度（包含）
     * @param maxLen 最大长度（包含）
     * @param str 待检查的字符串
     * @return 如果字符串长度在范围内返回true，否则返回false
     */
    public boolean checkLen(int minLen,int maxLen,String str){
        return str.length()>=minLen && str.length()<=maxLen;

    }
    /**
     * 生成5位随机验证码
     * 验证码由4位大小写字母和1位数字组成，同一个字母可重复
     * 数字可以出现在任意位置（通过随机打乱实现）
     * 生成步骤：
     * 1. 创建包含所有大小写字母的列表
     * 2. 随机选取4个字母
     * 3. 添加1个随机数字（0-9）
     * 4. 将数字随机交换到任意位置
     * 5. 转换为字符串返回
     * @return 生成的5位验证码字符串，例如："aQa1K"
     */
    public static String getCode(){
        //长度为5
        // 由4位大写或者小写字母和1位数字组成，同一个字母可重复
        //数字可以出现在任意位置
        //比如：aQa1K

        ArrayList<Character> list=new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }

        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);

        }

        sb.append(r.nextInt(10));

        char[] arr = sb.toString().toCharArray();
        int i=r.nextInt(arr.length);

        char temp=arr[i];
        arr[i]=arr[arr.length-1];
        arr[arr.length-1]=temp;

        String code= new String(arr);
        return code;
    }
}
