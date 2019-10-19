# ASimpleCrawler

# 一个简单的新浪手机网页爬取程序
主要用到mysql存储爬取的资源，elasticsearch做搜索引擎，两者都使用docker一键式启用并实现持久化。

主要采用htmlunit模拟浏览器来获取渲染后的资源，虽然爬取的效率很低，但好在链接资源齐全。
<br>
jsoup等非异步框架直接解析获得的资源不齐全，可以考虑在链接堆太多时采用。
<br>
两种方式读取的新闻内容是一样的，只有采取的链接数不同。
<br>

新闻主体只主要提取“.cn”类。“.com.cn”类的电脑端页面的content属性名较多，所以不做细处理。

## 使用前需要先启动mysql和elasticsearch
docker命令：本次测试便不再使用-v实现持久化
<br>
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=sl2147151 -d mysql:5.7.27
<br>
docker run -d --name elasticsearch  -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.4.0

