# DB
+ ##  Redis
  - 设计与实现，如果redis有一亿个key,使用keys命令是否会影响线上服务（会，因为是单线程模型，可以部署多个节点）
  - 持久化方式：aod and rdb，怎么实现
  - list怎么实现（ziplist压缩空间 and quicklist实现链表）
  - sorted set怎么实现（dict+skiplist）
  - skiplist数据结构，实现
  - Redis 底层用到了哪些数据结构？使用 Redis 的 set 来做过什么？
  - Redis 使用过程中遇到什么问题？搭建过 Redis 集群吗？
  - 存储一个 Blob 文件，如何建索引？如果前缀部分大都相似怎么办？文件大小相差太多怎么办？
  - Redis热点问题怎么解决
  - Hash, rehash怎么实现的
  - 为啥不能做持久化数据存储引擎
  - Redis的并发竞争问题如何解决了解Redis事务的CAS操作吗
  - 缓存机器增删如何对系统影响最小，一致性哈希的实现
  - Redis持久化的几种方式，优缺点是什么，怎么实现的
  - Redis的缓存失效策略
  - 缓存穿透的解决办法
  - redis集群，高可用，原理
  - mySQL里有2000w数据，redis中只存20w的数据，如何保证redis中的数据都是热点数据
  - 用Redis和任意语言实现一段恶意登录保护的代码，限制1小时内每用户Id最多只能登录5次
  - redis的数据淘汰策略

  
+ ## MySQL 
  - 索引(innodb, b+ tree, 为什么用b+树做索引节点，一个节点存了多少数据，怎么规定大小，与磁盘页对应)，联合索引
  - MySQL Hash 索引适用情况？举下例子？
  - 调优
  - 分库分表
  - 事务隔离级别
  - mongo ？
  - spark ?
  - MySQL 索引是不是越多越好？为什么？
  - 如何分析“慢查询”日志进行 SQL/索引 优化？
  - 聚簇索引和非聚簇索引

* HBase
* ACID

# Java
* ## 多线程 
  * concurrenthashmap 
  * countdownlatch
  * volatile
  * 内存模型
  * Java集合类哪些是线程安全的
  * ArrayList扩容
  * G1和CMS的区别
  * Kafka的整体架构
  * Netty的一次请求过程
  * Java1.7和1.8版本HashMap内部结构的区别
  * 自旋锁/偏向锁/轻量级锁
  * ThreadLocal如果引用一个static变量是不是线程安全的
  * 悲观锁，乐观锁
  * CAS
  * java线程如何交互
  * synchronized的底层实现
  * 多线程中断的原理
  * sleep, wait, notify, notifyAll
  * ThreadLocal, 线程池参数
  * ThreadLocal用过么，原理是什么，用的时候要注意什么
  * Synchronized和Lock的区别3、synchronized 的原理，什么是自旋锁，偏向锁，轻量级锁，什么叫可重入锁，什么叫公平锁和非公平锁
  * concurrenthashmap具体实现及其原理，jdk8下的改版
  * 用过哪些原子类，他们的参数以及原理是什么
  * cas是什么，他会产生什么问题（ABA问题的解决，如加入修改次数、版本号）
  * 如果让你实现一个并发安全的链表，你会怎么做
  * 简述ConcurrentLinkedQueue和LinkedBlockingQueue的用处和不同之处
  * 简述AQS的实现原理
  * countdowlatch和cyclicbarrier的用法，以及相互之间的差别?
  * concurrent包中使用过哪些类？分别说说使用在什么场景？为什么要使用？
  * LockSupport工具
  * Condition接口及其实现原理
  * Fork/Join框架的理解
  * jdk8的parallelStream的理解
  * 分段锁的原理,锁力度减小的思考
* sync/async IO
* 垃圾回收算法
* Java 内存分配策略？多个线程同时请求内存，如何分配？
* final 关键字如何实现
* 集合类，hashmap，HashMap怎么实现的
* 类加载
* finalize
* CopyOnWriteArrayList
* 强引用，软引用，弱引用，虚引用
* java内部类为什么可以访问外部类的属性成员，什么是内部类，什么是匿名内部类
* 为啥要采用双亲委派模型？
* Exception
* integer与int区别
* 抽象类与接口类区别
* Java 8的内存分代改进
* JVM垃圾回收机制，何时触发MinorGC等操作
* jvm中一次完整的GC流程（从ygc到fgc）是怎样的，重点讲讲对象如何晋升到老年代，几种主要的jvm参数等


 
# 算法
 * kth number in an array
 * quick sort worst case, best case
 * heap sort
 * depth first traversal tree
 * insert node to sorted tree 
 * 有向有权图，如何判断是否有环(dfs,bfs),拓扑图
 * 二叉树，最长路径(某个子树深度特别大怎么办)
 * 36进制加法
 * 单链表加法
 * 寻找峰值
 * use two stack(push, pop, count) to implement queue(enqueue, dequeue)
 * 从list当中取和为sum的数
 * n个无序正整数列表，取出a1,a2,a3..., a1<a2<a3..., 并且a1+a2+a3最大
 * 有一条马路，马路上有很多树，树的高度不一。现在要统一剪树，剪到高度为h。意思就是，比h高的树都剪到h，比h低的树高度不变。所有的树剪掉的总长度为C。现在要使C>某个值的情况下(假设为MM)，使h最大。问怎么确定h。如果误差是十的-6次方，时间复杂度是多少
 * find number in rotated sorted array
 * 求最大连续子数组和
 * 求字符串差集
 * 求平方根
 * PathSum
 * n个字符串，要求在每个字符串上都存在的字符
 * 线程安全，单例
 * n个树中找出异或与最大的数
 * 排序链表
 * 给n个数，求这n个数组成的集合的所有子集
 * 平面上有n个点，求最多有多少个点在同一条直线上
 * 给n个数，求最大的区间和
 * 给n个非降序的数和一个数k，求出k在n个数中第一次出现的位置
 * 给一个01矩阵，求出全为1的最大矩形位置和面积
 * 红黑树/旋转
 * 大数相加
 * 桶排序
 * 蛇形打印
 * Minimum Window Substring
 * String shifting(O(n) using KMP)
 * 给定整数n和m, 将1到n的这n个整数按字典序排列之后, 求其中的第m个数字
 * 二叉树对称判断（递归，非递归）
 * 链表局部反转，每两个结点调换下位置
 * 输入一个数组，取一些数出来，使和最大，约束是不能取相邻的元素，有负数，有小数
 * 输入数组，将正数排到前面，负数在后面
 * 求二叉树中节点的最远距离
 * 给一个点的集合和一个值K，把在以k为半径的区域内存在其他点的点列表输出
 * 给一个数组，随机等概率删除数组中的一个数，要求高效
 * x的y次方
 * 链表倒数第k个节点
 * 分数转化成小数，循环部分用括号包起来
 * 给一个数组，能不能分成两个数组，它们平均值相等
 * Permutation
 * two sum, three sum, four sum
 * 两有序数组，共同中位数
 * 给很多单词，求某子串出现的次数
 * 给n个数，找到两个数使得他们的异或值最大。 用Trie轻松解决
 * 给n对括号，输出所有合法括号序列
 * 正则表达式匹配.*
 * 匹配数字-234.123e-23
 * 从 1 到 n 整数中 1 出现的次数
 * 统计一个数字在排序数组中出现的次数。
 * 并查集, friend relationship
 * valid number

* ## 图
  * 最小生成树: prime, kruskal
  * 最短路径: floyd,  dijkstra
  * 最长公共子序列
  * 拓扑排序
 
  
# operating system
 * linux 磁盘管理
 * linux 进程间通信方式 5
 * linux 共享内存如何实现
 * cgroup 在linux具体实现
 * 僵尸进程，孤儿进程(如何避免僵尸进程，父进程怎么知道子进程结束)
 * 进程死锁
 * 实现LRU cache（超时淘汰，LRU淘汰） linkedlist 和 hashmap实现
 * 内存管理
 * Buffered IO
 * Linux启动的过程，BootLoader做了什么
 * 虚拟内存映射怎么做，内存碎片怎么整理？用什么数据结构？
 * 中断机制
 * 页表
 * 协程和线程
 * 为啥在用户态和内核态之间切换调度成本比较高

# 虚拟化
 * docker 物理机 虚拟机 部署 实现
 * k8 ?

# 网络
+ ## TCP
  + 三次握手,不要第三次握手行不行，被黑客利用，对服务器DDos攻击：https://www.jianshu.com/p/1c65840fc02d
  + 四次挥手
  + 窗口 
  + recover
  + TCP 安全性体现在哪些方面，采用了哪些机制？
  + 为什么迅雷是基于UDP，而不是TCP的
  + 快速重传，拥塞机制
  + timewait
  + tcp 粘包问题怎么处理
 * DNS，HTTPDNS
 * 有哪些方法能加快网络连接速度
 * 浏览器输入网址后的全过程 
 * 发送大文件时客户端和服务端采取了什么机制？描述一下 Nagel 算法？
 * 要实现两台机器及时高效通讯应该怎么做？如何关闭 Nagel 算法？
 * socket 中 SO_REUSEPORT 和 SO_REUSEADDR 的区别？
 * select、epool 的区别？底层的数据结构是什么？
 * HTTP请求的方法和字段
 * ping命令用到了哪些协议
 * 网络通信的多路复用
 * HTTP断点续传
 * HTTP状态码
 * 长链接和短连接
 * NAT转换怎么实现的？
 * 网络带宽的定义是什么
 * TCP UDP端口扫描的实现方式 TCP三次握手的过程，seq的变化是一直都是加1吗？ 如何用UDP实现文件传输
 * IPv6的地址格式，保留地址，回环地址，IPv4与IPv6如何通信
 * DNS的原理、tcp、udp下的DNS有什么区别（或者说DNS的tcp实现和udp实现的应用场景）
 * HTTP协议的理解、HTTP包的格式、HTTP1.0和HTTP2.0的区别
 * NAT的原理，外网与内网或内网之间的通信中如何区分不同IP的数组包
 * ARP协议的作用
 * HTTPS 握手过程

# 消息队列
 * rmq
 * kafka

# 分布式
 * https://www.jianshu.com/p/7afd5cba67ca 分布式锁
 
# 其他
 * 项目当中印象深刻的难点，如何解决
 * 怎么设计微信朋友圈
 * 设置抢单功能，线程安全
 * 有两个TB级大文件，一个文件每行为id : name，另一个文件每行为id : age，要求合并成一个文件id : name, age 
 * Vue, React 双向绑定是如何实现的
 * python 协程
 * 海量日志查找某一段时间内的记录
 * 红黑树和AVL树的区别
 * B+，B-，红黑树，二叉搜索树，平衡搜索树
 * 短链接算法
 * md5多少位(128bit,32位十六进制)
 * 缓存同步
 * 保证日志上传的幂等性
 * 秒杀场景，分布式锁，分布式事务
 * 一致性hash如何保证负载均衡
 * 如何实现断点续传
 * 如何实现http请求上传一个图片，实时显示上传百分比，怎样获取这个比例呢？
 * cookie, session
 * 正则表达式匹配电话号码
 * zookeeper怎么保证原子性，怎么实现分布式锁
 * 两个大文件找重复行
 * AQS
 * bloom filter
 * 设计一个处理高并发请求的系统
 * 访问量统计系统设计
 * 设计一个电梯中控系统
 * 2TB数据，都是单词，输入一个单词，输出出现的次数。3TB IP数据，怎么统计各IP出现次数
 * 两个文件A和B，求A中没有但B中有的单词
 * 有很多餐厅坐标，和自己的坐标，怎么找到最近的餐厅
 * C++的多态怎么理解
 * 虚函数有什么用
 * 虚函数在内存上占多少字节
 * 打开网页没反应，你觉得有哪些可能的原因。
 * 给你很多很多（上千万条）URL，每个URL对应一个图片，请把它们都下载下来，你会如何实现（这个问题，我就只是说进行一个分布式下载，每台机器负责一部分下载，但是面试官貌似不太满意我的答案，后面问同学的时候说多线程下载也是一种方式）
 * 设计一个系统可以实时统计任意ip在过去一个小时的访问量
 * 设计实现一个git diff
 * 单点登录怎么实现
 * raft, paxos
 * netty, nio
