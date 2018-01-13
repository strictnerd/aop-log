# aop-log

---

随着公司业务逻辑逐渐复杂，越来越多的项目采用了前后端分离进行开发，提高了开发效率，但是无形中增加了沟通和调试成本。故开发人员在代码中采用了打印前端或者终端传递过来参数信息，这样当出现问题时能够排查和说明问题出在何处。aop-log就是出于这样一种使用场景而出现。总共两种注解形式，一种是：注解加在Controller上时，能够把所有的请求参数和返回参数日志以json的格式输出。二是：把注解添加在方法上时，那么只输出这个函数的输入参数和返回值。


## 使用说明
1. 方法注解使用方式：

> 在controller上添加 @EnableMethodLog，在具体方法添加 @LogMethod
例如：
```
/**
 * Created by clq on 2017/8/23.
 */
@Controller
@RequestMapping("/admin/public")
@EnableMethodLog
public class TestController {
    @Autowired
    private TestDaoInteface testDaoInteface;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    @LogMethod
    public Object test(SysUser string){
        Object select = testDaoInteface.select();
       // throw new IllegalArgumentException("参数非法 ");
        return select;
    }
日志信息如下：
15:57:12.483 [2025955363@qtp-98801744-0] INFO  com.log.aop.MethodAspect - {"method":"test","params":[{"account":"43523werwre","id":234}],"retValue":{"account":"admin","id":1},"time":3980}
```

