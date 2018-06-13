使用springBoot搭建的交易平台,将上次做的SSM框架的交易平台项目改造成springBoot框架,并且整合更多的组件;

2018/6/13
1:开启了slf4j日志功能,控制台和本地文件同时记录trace级别及以上的日志,修复了首页图片地址规范化以及被security拦截的问题;

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

4:计划改用REST风格的url;
