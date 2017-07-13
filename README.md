# CarLock






<ul>
<li>
1.采用技术：netty+spring+springboot
</li>
<li>
2.项目采用netty作为班车pos的服务
</li>
<li>
3.项目类管理采用了spring
</li>
<li>
4.演示demo采用了springboot
</li>

<li>
5.项目启动顺序是是启动TcpServer，init_method ,startServer()方法。
</li>
<li>
6.demo 启动方式采用springboot 方式启动，运行com.kfit.App的main方法。
</li>

<p>
<strong>访问步骤：</strong>
<ul>
<li>
A.http://127.0.0.1:8080/hello 访问返回hello说明运行正常。
</li>
<li>
B.http://127.0.0.1:8080/list 查看连接上来的设备列表。 
</li>
<li>
C.http://127.0.0.1:8080/open?sn=xxxxxxxx(123456789) 下发开启指令。
</li>
</ul>
</p>

















<p>git 提交代码命令</p>
<pre>
<code>
1. git pull you git url
2. git checkout 
3. rm -r dirName
4. git add --all
5. git commit -m"remove dir"
6. git push you git url
7. input your name 
8. input your password
</code>
</pre>

<p><a href="https://rogerdudler.github.io/git-guide/index.zh.html">git 帮助</a></p>