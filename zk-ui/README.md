### zk-ui
zookeeper ui 管理页面
 
实现功能：
   页面中添加节点， 删除节点，修改节点数据
   节点树形菜单异步加载.

zookeeper 地址与 登录的用户名密码 在conf.properties文件中
###   connectString=192.168.1.34:2181   我的测试地址
###auth=admin:admin    用户名:密码


启动后访问地址： http://localhost:8080/zk-ui/toLogin  进行登录
不想直接使用的可以下载war包部署<war包太大 上传超时...>

使用框架为：springmvc4.1.2 + curator2.8 
zookeeper 测试版本为3.4.6


管理页面截图：

![输入图片说明](http://git.oschina.net/uploads/images/2015/0921/170902_406f9772_366428.png "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2015/0921/170917_8ddf15ed_366428.png "在这里输入图片标题")



copyright 
mail: ddh0220@126.com
### 如果有好的建议，可以发送邮件给我或者直接提交issues

