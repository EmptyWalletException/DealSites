使用springBoot搭建的交易平台,将上次做的SSM框架的交易平台项目改造成springBoot框架,并且整合更多的组件;
2018/6/18

1:在linux下使用docker安装好了redis 并且在项目test类下成功向Linux虚拟机的redis里写入了数据;

2018/6/17

1:搭建了Linux环境,利用docker安装好了elasticsearch,同时在项目test里测试了elasticsearch,发现springboot data ES会报错nodes找不到,而jest却能正常存值和取值,试验了许多解决方案仍然不行,正在进一步排除此问题;

2018/6/16

1:将原来部分session传值方式改成了从前端页面元素中取值再通过post传值,这是为了避免用户同时修改两个产品或店铺时session中的值被覆盖的问题;

2018/6/15

1:解决了图片上传时的一个异常:StandardMultipartFile不能转换为CommonsMultipartFile,原因是springboot已经自带commonFileUpload工具组件,同时springboot里的multipartFile工具类可完全替代commonMultipartFile,并且不能互转,解决方式是移除掉commonMultipartFile,使用springBoot的multipartFile效率更高;

2:重新整理了一下项目的代码规范性,重点规范了前后端的url;

3:service层和Controller层完善了异常捕获;

2018/6/14

1:数据库新增商品收藏和店铺收藏两张表,与用户信息&商品&店铺这三张表关联;

2:新增并完善了店铺收藏和商品收藏功能,项目的功能模块已经开发结束,剩下需要优化前端页面的交互效果,优化js代码格式,以及优化后端代码,同时还需要增加较多的条件判断和异常捕获代码;


2018/6/13

1:开启了slf4j日志功能,控制台和本地文件同时记录trace级别及以上的日志,修复了首页图片地址规范化以及被security拦截的问题;

2:数据库新增购物车表,与用户和商品级联;

3:完成了用户模块的购物车功能,可以将商品添加进购物车,从购物车中删除商品,购物车中商品数量加一或减一,同时保证了合理的逻辑,不会出现重复商品和商品数量为0的情况;

2018/6/12

1:解决了springboot中tomcat加载本地图片的问题,只需要在继承WebMvcConfigurerAdapter类后重写addResourceHandlers方法即可;

2:整合了security5组件,并回显登录错误信息,同时将错误信息配置成了中文提示,发现的问题是如果不禁用csrf那么所有的post请求都会失效;

3:security5强制使用一个密码编译格式,否则报错There is no PasswordEncoder mapped for the id “null”;

4:security5版本很多规定非常耽误进度,例如密码强制使用一种加密方式,配置类里规定不能写ROLE_而数据库表中角色名又必须带ROLE,这种规定非常容易出问题,下次如果再遇到security的问题会换回4版本,目前security的配置工作已经结束了,现在security是使用数据库中三张表记录的账号到角色的关系来实现权限管理的;

2018/6/11

创建项目,关联GitHub仓库,将所有jsp页面改成了html页面供模板引擎使用,项目已经成功运行,目前需要解决的问题是
 
1:tomcat的图片虚拟路径需要重新配置;

2:security需要重新配置,经过测试发现security能成功运行进入security自带的登录页面,而在SSM框架时不能正常运行;

3:大量js代码可以被模板引擎替代,同时相应的后台代码也需要修改成非json的方式

