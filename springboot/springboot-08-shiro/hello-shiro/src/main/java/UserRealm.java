import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {
//Authentication“验证” Authorization 授权、委托
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("执行了Authorization授权");
        return null;
    }

//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了Authentication认证");
        //用户名  密码
        String name="root";
        String password="123456";

        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        if(!userToken.getUsername().equals(name)){
            return null;//抛出异常
        }
        //密码认证 shiro做了
        return new SimpleAuthenticationInfo("",password,"");
    }
}
