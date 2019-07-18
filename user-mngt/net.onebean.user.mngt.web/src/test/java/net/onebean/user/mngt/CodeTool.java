package net.onebean.user.mngt;

import net.onebean.core.extend.ApolloConfInitializer;
import net.onebean.tool.*;

public class CodeTool {

    public static void main(String[] args) {
        ApolloConfInitializer.init();
        /*生成mysql orm java代码*/
        CreateJavaTool.runIt();
//        /*生成mongodb orm java代码*/
//        CreateMongoJavaTool.runIt();
//        /*密码生成器*/
//        PasswordGetter.runIt();
    }
}
