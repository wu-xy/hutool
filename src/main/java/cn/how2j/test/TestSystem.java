package cn.how2j.test;
 
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
 
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
 
import org.junit.Test;
 
import cn.how2j.test.TestCache.Comment;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
 
public class TestSystem {
 
    @Test
    @Comment("系统属性工具")
    public void test1(){
        p5("java虚拟机规范",StrUtil.trim(SystemUtil.getJvmSpecInfo().toString()));
        p5("当前虚拟机信息",StrUtil.trim(SystemUtil.getJvmInfo().toString()));
        p5("java规范",StrUtil.trim(SystemUtil.getJavaSpecInfo().toString()));
        p5("当前java信息",StrUtil.trim(SystemUtil.getJavaInfo().toString()));
        p5("java运行时信息",StrUtil.trim(SystemUtil.getJavaRuntimeInfo().toString()));
        p5("操作系统信息",StrUtil.trim(SystemUtil.getOsInfo().toString()));
        p5("用户信息",StrUtil.trim(SystemUtil.getUserInfo().toString()));
        p5("主机信息",StrUtil.trim(SystemUtil.getHostInfo().toString()));
        p5("内存信息",StrUtil.trim(SystemUtil.getRuntimeInfo().toString()));
    }
     
    private String preComment = null;
 
    private void c(String msg) {
        System.out.printf("\t备注：%s%n", msg);
    }
 
    private void p1(String type1, Object value1, String type2, Object value2) {
        p(type1, value1, type2, value2, "format1");
    }
 
    private void p2(String type1, Object value1, String type2, Object value2) {
        p(type1, value1, type2, value2, "format2");
    }
 
    private void p3(String type1, Object value1) {
        p(type1, value1, "", "", "format3");
    }
    private void p5(String type1, Object value1) {
        p(type1, value1, "", "", "format5");
    }
    private void p4(Object value) {
        p(null, value, "", "", "format4");
    }
 
    private void p(String type1, Object value1, String type2, Object value2, String format) {
        try {
            throw new Exception();
        } catch (Exception e) {
 
            String methodName = getTestMethodName(e.getStackTrace());
            Method m = ReflectUtil.getMethod(this.getClass(), methodName);
            Comment annotation = m.getAnnotation(Comment.class);
            if (null != annotation) {
                String comment = annotation.value();
                if (!comment.equals(preComment)) {
                    System.out.printf("%n%s 例子： %n%n", comment);
                    preComment = comment;
                }
 
            }
        }
        int padLength = 12;
        type1 = StrUtil.padEnd(type1, padLength, Convert.toSBC(" ").charAt(0));
        type2 = StrUtil.padEnd(type2, padLength, Convert.toSBC(" ").charAt(0));
        if ("format1".equals(format)) {
            System.out.printf("\t%s的:\t\"%s\" %n\t被转换为----->%n\t%s的 :\t\"%s\" %n%n", type1, value1, type2, value2);
        }
        if ("format2".equals(format)) {
            System.out.printf("\t基于 %s:\t\"%s\" %n\t获取 %s:\t\"%s\"%n%n", type1, value1, type2, value2);
        }
        if ("format3".equals(format)) {
            System.out.printf("\t%s:\t\"%s\" %n\t%n", type1, value1);
 
        }
        if ("format4".equals(format)) {
            System.out.printf("\t%s%n%n", value1);
        }
        if ("format5".equals(format)) {
            System.out.printf("---------%s-------:%n%s %n%n", type1, value1);
        }
    }
 
    private String getTestMethodName(StackTraceElement[] stackTrace) {
        for (StackTraceElement se : stackTrace) {
            String methodName = se.getMethodName();
            if (methodName.startsWith("test"))
                return methodName;
        }
        return null;
    }
 
    @Target({ METHOD, TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface Comment {
        String value();
    }
}