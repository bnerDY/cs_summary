# TCP 协议

因特网的网络层只提供无连接、不可靠的尽力服务。它可以将分组从一个主机通过因特网传送到另一台主机，可能出现比特错、丢失、重复和错序到达的情形。

传输层建立在网络层之上，为进程之间的数据传输提供服务。传输层可以通过不可靠的因特网在两个进程之间建立一条可靠的逻辑链路，提供字节流传输服务。

因特网的传输层有两个协议UDP和TCP：

- UDP(User Datagram Protocol)只提供无连接的不可靠的服务，应用进程通过<远端IP地址，远端端口号>向远端进程发送数据，应用进程并不要求远端进程进行确认。
- TCP(Transmission Control Protocol)为应用程序之间提供面向连接的可靠的字节流服务。 TCP为全双工协议，提供流控制机制，即允许接收方控制发送方的发送速度，此外还提供拥塞控制功能。

下图即为两个端点之间TCP通信的简单示意图：

![][1]

源主机的TCP进程从上层收集应用进程的数据，并在满足一定条件时发送出去，TCP发送的数据称为分段(Segment)。

## TCP 报文结构

TCP头部数据格式如下：

![][2]

各个字段的信息说明如下：

- Source Port(Destination Port)：分别占用16位，表示源端口号和目的端口号；用于区别主机中的不同进程，源端口号和目的端口号配合上IP首部中的源IP地址和目的IP地址就能唯一的确定一个TCP连接；
- Sequence Number：用来标识从TCP发送端向TCP接收端发送的数据字节流，它表示在这个报文段中的的第一个数据字节在数据流中的序号，主要用来解决网络报乱序的问题；
- Acknowledgment Number：发送确认的一端所期望收到的**下一个序号**，因此，确认序号应当是上次已成功收到数据字节序号加1。不过，只有当标志位中的ACK标志为1时该确认序列号的字段才有效，该字段主要用来解决丢包的问题。
- Data offset：用来标识TCP头部的长度，该数字为头部中字(32 bit)的数目，需要这个值是因为任选字段的长度是可变的。这个字段占4bit，因此**TCP最多有60字节的头部**。然而，没有任选字段，正常的长度是20字节；
- Reserved：3个保留位，留作以后使用，全部设置为0；
- Window Size：窗口大小，也就是有名的`滑动窗口`，用来进行流量控制；
- 标志位：TCP头部中共有9个标志位，用于操控TCP的状态，主要有URG，ACK，PSH，RST，SYN，FIN，标志位的意思如下：
    - URG：此标志表示TCP包的紧急指针域有效，用来保证TCP连接不被中断，并且督促中间层设备要尽快处理这些数据；
    - ACK：此标志表示应答域有效，就是说前面所说的TCP应答号将会包含在TCP数据包中；
    - PSH：表示Push操作，数据包到达接收端以后，立即传送给应用程序，而不是在缓冲区中排队；
    - RST：表示连接复位请求，用来复位那些产生错误的连接，也被用来拒绝错误和非法的数据包；
    - SYN：表示同步序号，用来建立连接。SYN标志位和ACK标志位搭配使用，当连接请求的时候，SYN=1，ACK=0；连接被响应的时候，SYN=1，ACK=1；
    - FIN：表示发送端已经达到数据末尾，也就是说双方的数据传送完成，没有数据可以传送了。

## 三次握手建立连接

TCP协议提供可靠的连接服务，无论哪一方向另一方发送数据之前，都必须先在双方之间建立一条连接。在TCP/IP协议中，连接是通过三次握手进行初始化的，三次握手的过程如下：

![][3]

前两次握手，客户端进入连接状态，后两次握手，服务器进入连接状态。所以，三次握手之后，一个全双工的连接就建立起来了，之后，客户端和服务器端就可以开始传送数据。

- 第一次握手：客户端发送连接请求报文段，将SYN位设为1，SeqNum为随机数A；
- 第二次握手：服务器返回ACK，确认收到客户端发来的SYN，然后设置AckNum为A+1；此外，服务器发送自己的连接请求报文段，即发送SYN和随机数B作为SeqNum；
- 第三次握手：客户端返回ACK，确认收到服务器发来的SYN，然后设置AckNum=B+1。

**为什么需要三次握手建立连接？**

简单来说，为了防止已失效的连接请求报文段突然又传送到了服务端，因而产生错误。

考虑下面一种情况：client发出的第一个连接请求报文段并没有丢失，而是在某个网络结点长时间的滞留了，以致延误到连接释放以后的某个时间才到达server。server收到此失效的连接请求报文段后，误认为是client发出的一个新的连接请求。于是就向client发出确认报文段，同意建立连接。

假设不采用“三次握手”，那么只要server发出确认，新的连接就建立了。由于现在client并没有发出建立连接的请求，因此不会理睬server的确认，也不会向server发送数据。但server却以为新的连接已经建立，并一直等待client发来数据。这样，server的很多资源就白白浪费掉了。

采用“三次握手”的办法可以防止上述现象发生。例如刚才那种情况，client不会向server的确认发出确认。server由于收不到确认，就知道client并没有要求建立连接。”

## 四次握手断开连接

客户端和服务器数据传送完毕后，需要断开TCP连接，断开连接的时候需要进行四次握手。

![][4]

四次握手的过程如下：

- 第一次握手：发起端发送FIN和SeqNum=A，进入FIN_WAIT_1状态，用来关闭发起端到接收端的数据传送，也就是告诉接收端：不会再给你发新数据了(当然，在fin包之前发送出去的数据，如果没有收到对应的ack确认报文，发起端依然会重发这些数据)，但此时发起段还可以接受数据；
- 第二次握手：接收端收到FIN包后，发送一个ACK给对方，确认序号为收到序号+1（AckNum=A+1），此时接收端仍然可以给发起段发送数据（同意关闭连接请求，但是我还有数据需要传送，稍等...）；
- 第三次握手：接收端向发起端发送FIN，用来关闭到发起端的数据传送，也就是告诉发起端：我的数据也发送完了，不会再给你发数据了。此时接收端进入CLOSE_WAIT状态；
- 第四次握手：发起端发送ACK报文段，然后进入TIME_WAIT状态，接收端收到ACK报文段以后，就关闭连接。发起端等待2MSL后依然没有收到回复，则证明Server端已正常关闭，此时也可以关闭连接了。

如果要正确的理解四次分手的原理，还需要了解四次分手过程中的状态变化。

- FIN_WAIT_1: FIN_WAIT_1状态实际上是当SOCKET在ESTABLISHED状态时，它想主动关闭连接，向对方发送了FIN报文，此时该SOCKET即进入到FIN_WAIT_1状态。而当对方回应ACK报文后，则进入到FIN_WAIT_2状态。（主动方）
- FIN_WAIT_2：FIN_WAIT_2状态下的SOCKET，表示半连接，也即有一方要求断开连接，但另外还告诉对方，我暂时还有点数据需要传送给你，稍后再关闭连接。（主动方）
- CLOSE_WAIT：在CLOSE_WAIT状态下，需要完成的事情是等待你去关闭连接。（被动方）
- LAST_ACK: 被动关闭一方在发送FIN报文后，最后等待对方的ACK报文。当收到ACK报文后，也即可以进入到CLOSED可用状态了。（被动方）
- TIME_WAIT: 表示收到了对方的FIN报文，并发送出了ACK报文，就等2MSL后即可回到CLOSED可用状态了。如果FINWAIT1状态下，收到了对方同时带FIN标志和ACK标志的报文时，可以直接进入到TIME_WAIT状态，而无须经过FIN_WAIT_2状态。（主动方）
- CLOSED: 表示连接中断。

**为什么要四次握手断开连接？**

TCP是全双工模式，这就意味着，当主机1发出FIN报文段时，只是表示主机1已经没有数据要发送了，主机1告诉主机2，它的数据已经全部发送完毕了；但是，这个时候主机1还是可以接受来自主机2的数据；当主机2返回ACK报文段时，表示它已经知道主机1没有数据发送了，但是主机2还是可以发送数据到主机1的；当主机2也发送了FIN报文段时，这个时候就表示主机2也没有数据要发送了，就会告诉主机1，我也没有数据要发送了，之后彼此就会中断这次TCP连接。

## TCP 状态转换图

![][5]

## TCP 重传机制

TCP要保证所有的数据包都可以到达，所以，必需要有重传机制。

注意，接收端给发送端的Ack确认只会确认最后一个连续的包，比如，发送端发了1,2,3,4,5一共五份数据，接收端收到了1，2，于是回ack 3，然后收到了4（注意此时3没收到），此时的TCP会怎么办？我们要知道，SeqNum和Ack是以字节数为单位，所以ack的时候，不能跳着确认，只能确认最大的连续收到的包，不然，发送端就以为之前的都收到了。

`超时重传机制`

每次发送数据包时，发送的数据报都有seq号，接收端收到数据后，会回复ack进行确认，表示某一seq号数据已经收到。发送方在发送了某个seq包后，等待一段时间，如果没有收到对应的ack回复，就会认为报文丢失，会重传这个数据包。

针对上面的情况，接收端不回ack，死等3，当发送方发现收不到3的ack超时后，会重传3。一旦接收方收到3后，会ack 回 4——意味着3和4都收到了。但是，这种方式会有比较严重的问题，那就是因为要死等3，所以会导致4和5即便已经收到了，而发送方也完全不知道发生了什么事，因为没有收到Ack，所以，发送方可能会悲观地认为也丢了，所以有可能也会导致4和5的重传。

`快速重传机制`

接受数据一方发现有数据包丢掉了。就会发送ack报文告诉发送端重传丢失的报文。如果发送端连续收到标号相同的ack包，则会触发客户端的快速重传。比较超时重传和快速重传，可以发现超时重传是发送端在傻等超时，然后触发重传；而快速重传则是接收端主动告诉发送端数据没收到，然后触发发送端重传。

比如：如果发送方发出了1，2，3，4，5份数据，第一份先到送了，于是就ack回2，结果2因为某些原因没收到，3到达了，于是还是ack回2，后面的4和5都到了，但是还是ack回2，因为2还是没有收到，于是发送端收到了三个ack=2的确认，知道了2还没有到，于是就马上重转2。然后，接收端收到了2，此时因为3，4，5都收到了，于是ack回6。

## TCP滑动窗口

TCP头里有一个字段叫Window，又叫Advertised-Window，这个字段是**接收端告诉发送端自己还有多少缓冲区可以接收数据**。于是发送端就可以根据这个接收端的处理能力来发送数据，而不会导致接收端处理不过来。滑动窗可以是提高TCP传输效率的一种机制。流量控制只关注发送端和接受端自身的状况，而没有考虑整个网络的通信情况。

为了说明滑动窗口，我们需要先看一下TCP缓冲区的一些数据结构：

![][6]

上图中，我们可以看到：

* 接收端LastByteRead指向了TCP缓冲区中读到的位置，NextByteExpected指向的地方是收到的连续包的最后一个位置，LastByteRcved指向的是收到的包的最后一个位置，我们可以看到中间有些数据还没有到达，所以有数据空白区。
* 发送端的LastByteAcked指向了被接收端Ack过的位置（表示成功发送确认），LastByteSent表示发出去了，但还没有收到成功确认的Ack，LastByteWritten指向的是上层应用正在写的地方。

于是：

* 接收端在给发送端回ACK中会汇报自己的AdvertisedWindow = MaxRcvBuffer – LastByteRcvd – 1;
* 而发送方会根据这个窗口来控制发送数据的大小，以保证接收方可以处理。

下面我们来看一下发送方的滑动窗口示意图：

![][7]

上图中分成了四个部分，分别是：（其中那个黑模型就是滑动窗口）

1. 已收到ack确认的数据。
2. 发还没收到ack的。
3. 在窗口中还没有发出的（接收方还有空间）。
4. 窗口以外的数据（接收方没空间）

下面是个滑动后的示意图（收到36的ack，并发出了46-51的字节）：

![][8]

下面我们来看一个接受端控制发送端的图示：

![][9]

上图可以看到一个处理缓慢的Server（接收端）是怎么把Client（发送端）的TCP Sliding Window给降成0的。如果Window变成0了，发送端就不发数据了，可以想像成“Window Closed”。

那么接收方一会儿Window size 可用了，怎么通知发送端呢？为了解决这个问题，TCP使用了Zero Window Probe技术，缩写为ZWP，也就是说，发送端在窗口变成0后，会发ZWP的包给接收方，让接收方来ack他的Window尺寸，一般这个值会设置成3次。如果3次过后还是0的话，有的TCP实现就会发RST把链接断了。

## TCP 拥塞控制

TCP通过滑动窗口来做流量控制，但是这还不够，因为滑动窗口仅依赖于连接的发送端和接收端，其并不知道网络中间发生了什么。TCP的设计者觉得，一个伟大而牛逼的协议仅仅做到流量控制并不够，因为流量控制只是网络模型4层以上的事，TCP的还应该更聪明地知道整个网络上的事。

考虑一下这样的场景：某一时刻网络上的延时突然增加，那么，TCP对这个事做出的应对只有重传数据，但是，重传会导致网络的负担更重，于是会导致更大的延迟以及更多的丢包，于是，这个情况就会进入恶性循环被不断地放大。试想一下，如果一个网络内有成千上万的TCP连接都这么行事，那么马上就会形成“网络风暴”，TCP这个协议就会拖垮整个网络。

所以，TCP不能忽略网络上发生的事情，而无脑地一个劲地重发数据，对网络造成更大的伤害。对此TCP的设计理念是：TCP不是一个自私的协议，当拥塞发生的时候，要做自我牺牲。就像交通阻塞一样，每个车都应该把路让出来，而不要再去抢路了。

拥塞控制主要是四个[算法](http://ee.lbl.gov/papers/congavoid.pdf)：1）慢启动，2）拥塞避免，3）拥塞发生，4）快速恢复。 

`慢启动(Slow Start)`

慢启动的意思是，刚刚加入网络的连接，一点一点地提速。慢启动的算法如下(cwnd全称Congestion Window)：

1. 连接建好的开始先初始化cwnd = 1，表明可以传一个MSS大小的数据。
2. 每当收到一个ACK，cwnd++; 这样每当过了一个RTT，cwnd = cwnd*2。
3. 还有一个ssthresh（slow start threshold），是一个上限，当cwnd >= ssthresh时，就会进入“拥塞避免算法”

所以，我们可以看到，如果网速很快的话，ACK也会返回得快，`RTT`（Round Trip Time，也就是一个数据包从发出去到回来的时间）也会短，那么，这个慢启动就一点也不慢。下图说明了这个过程。

![][10]

`拥塞避免算法(Congestion Avoidance)`

ssthresh（slow start threshold）是一个上限，当cwnd >= ssthresh时，就会进入“拥塞避免算法”。一般来说ssthresh的值是65535，单位是字节，当cwnd达到这个值时后，算法如下：

收到一个ACK时，cwnd = cwnd + 1/cwnd，这样当每过一个RTT时，cwnd = cwnd + 1

这样就可以避免增长过快导致网络拥塞，慢慢的增加调整到网络的最佳值。很明显，是一个线性上升的算法。

`拥塞发生算法`

前面我们说过，当丢包的时候，会有两种情况：

1）等到RTO超时，重传数据包。TCP认为这种情况太糟糕，反应也很强烈。

* sshthresh =  cwnd /2
* cwnd 重置为 1
* 进入慢启动过程

2）快速重传，也就是在收到3个duplicate ACK时就开启重传，而不用等到RTO超时。

* TCP Tahoe的实现和RTO超时一样。
* TCP Reno的实现是：
    * cwnd = cwnd /2
    * sshthresh = cwnd
    * 进入快速恢复算法——Fast Recovery

`快速恢复算法(Fast Recovery)`

TCP Reno：[RFC5681](http://tools.ietf.org/html/rfc5681)
TCP New Reno：[RFC682](http://tools.ietf.org/html/rfc6582)

## 参考

[图解TCP-IP协议](http://www.cricode.com/3568.html)  
[简析TCP的三次握手与四次分手](http://www.jellythink.com/archives/705)  
[TCP 的那些事儿（上）](http://coolshell.cn/articles/11564.html)  
[TCP 的那些事儿（下）](http://coolshell.cn/articles/11609.html)  

[1]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_1.png
[2]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_2.png
[3]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_3.png
[4]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_4.png
[5]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_5.png
[6]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_6.png
[7]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_7.png
[8]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_8.png
[9]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_9.png
[10]: http://7xrlu9.com1.z0.glb.clouddn.com/Network_TCP_10.jpg


#HTTP协议

HTTP 是一个客户端终端（用户）和服务器端（网站）请求和应答的标准。通过使用Web浏览器、网络爬虫或者其它的工具，客户端发起一个HTTP请求到服务器上指定端口（默认端口为80），我们称这个客户端为用户代理程序（user agent）。应答的服务器上存储着一些资源，比如HTML文件和图像，我们称这个应答服务器为源服务器（origin server）。在用户代理和源服务器中间可能存在多个“中间层”，比如代理服务器、网关或者隧道（tunnel）。

HTTP协议中，并没有规定它支持的层。事实上，HTTP可以在任何互联网协议上，或其他网络上实现。HTTP假定其下层协议提供可靠的传输，因此，任何能够提供这种保证的协议都可以被其使用，在TCP/IP协议族使用TCP作为其传输层。

HTTP是一个应用层协议，主要用于Web开发，通常由HTTP客户端发起一个请求，创建一个到服务器指定端口（默认是80端口）的TCP连接。HTTP服务器则在那个端口监听客户端的请求。一旦收到请求，服务器会向客户端返回一个状态，比如"HTTP/1.1 200 OK"，以及返回的内容，如请求的文件、错误消息、或者其它信息。

![][1]

HTTP是一个无状态的协议，也就是说服务器不会去维护与客户交互的相关信息，因此它对于事务处理没有记忆能力。举个例子来讲，你通过服务器认证后成功请求了一个资源，紧接着再次请求这一资源时，服务器仍旧会要求你表明身份。 

无状态不代表HTTP不能保持TCP连接，更不能代表HTTP使用的是UDP协议（无连接）。事实上，HTTP正是基于TCP的协议，其在TCP/IP四层网络模型中的位置如下图所示： 

![][2]

# HTTP 协议基础

## HTTP 请求

http请求由三部分组成，分别是：`请求行、消息报头、请求正文`。

### 请求行

请求行的格式如下： 

> Method SP Request-URI SP HTTP-Version CRLF

请求行以一个方法符号开头，后面跟着请求的URI和协议的版本，中间以空格隔开。其中 

1. Method指出在由Request-URI标识的资源上所执行的方法，方法是大小写敏感的；
2. Request-URI是一个统一资源标识符(通过简单的格式化字符串，通过名称、位置、或其他任何特性标识某个资源)。
3. HTTP-Version 表示请求的HTTP协议版本；
4. CRLF表示回车和换行(除了作为结尾的 CRLF 外，不允许出现单独的 CR 或 LF 字符)

常用的请求方法如下： 

|方法名称          |含义             |
|-----------------|----------------| 
|GET              |获取由Request-URI标识的任何信息(以实体的形式)，如果Request-URI引用某个数据处理过程，则应该以它产生的数据作为在响应中的实体，而不是该过程的源代码文本，除非该过程碰巧输出该文本。 |
|POST             |用来请求原始服务器接受请求中封装的实体作为请求行中的Request-URI标识的副属。POST主要用于向数据处理过程提供数据块，如递交表单或者是通过追加操作来扩展数据库。 |
|PUT              |以提供的Request-URI存储封装的实体。 |
|DELETE           |请求原始服务器删除Request-URI标识的资源。 |
|HEAD             |除了服务器不能在响应中返回消息体，HEAD方法与GET相同。用来获取暗示实体的元信息，而不需要传输实体本身。常用于测试超文本链接的有效性、可用性和最近的修改。| 

简答例子如下：

    GET /index.html HTTP/1.1
    POST http://192.168.2.217:8080/index.jsp HTTP/1.1

### 消息报头

报头域是由名字+“:”+空格+值组成，消息报头域的名字是大小写无关的。请求消息报头包含了`普通报头、请求报头、实体报头`。

普通报头中，有少数报头域用于所有的请求和响应消息，但并不用于被传输的实体，只用于传输的消息。比如： 

* Cache-Control：用于指定缓存指令，缓存指令是单向的(响应中出现的缓存指令在请求中未必会出现)，且是独立的(一个消息的缓存指令不会影响另一个消息处理的缓存机制)；
* Date：表示消息产生的日期和时间；
* Connection：允许发送指定连接的选项，例如指定连接是连续，或者指定“close”选项，通知服务器在响应完成后关闭连接。

请求报头允许客户端向服务器端传递请求的附加信息以及客户端自身的信息。常用的请求报头如下： 

* Host：指定被请求资源的 Internet 主机和端口号，它通常是从HTTP URL中提取出来的；
* User-Agent：允许客户端将它的操作系统、浏览器和其它属性告诉服务器；
* Accept：指定客户端接受哪些类型的信息，eg:Accept:image/gif，表明客户端希望接受GIF图象格式的资源；
* Accept-Charset：指定客户端接受的字符集，缺省是任何字符集都可以接受；
* Accept-Encoding：指定可接受的内容编码，缺省是各种内容编码都可以接受；
* Authorization：证明客户端有权查看某个资源，当浏览器访问一个页面，如果收到服务器的响应代码为401(未授权)，可以发送一个包含Authorization请求报头域的请求，要求服务器对其进行验证。

### 请求正文


## HTTP 响应

在接收和解释请求消息后，服务器返回一个 HTTP 响应消息。HTTP 响应也是由三个部分组成，分别是：`状态行、消息报头、响应正文`。

### 状态行

所有HTTP响应的第一行都是状态行，依次是当前HTTP版本号，3位数字组成的状态代码，以及描述状态的短语，彼此由空格分隔。

> HTTP-Version Status-Code Reason-Phrase CRLF

HTTP状态码的作用：Web服务器用来告诉客户端，发生了什么事。状态代码的第一个数字代表当前响应的类型：

* 1xx消息——请求已被服务器接收，继续处理
* 2xx成功——请求已成功被服务器接收、理解、接受
* 3xx重定向——需要后续操作才能完成这一请求
* 4xx客户端错误——请求含有词法错误或者无法被执行
* 5xx服务器错误——服务器在处理某个正确请求时发生错误

虽然 RFC 2616 中已经推荐了描述状态的短语，例如"200 OK"，"404 Not Found"（“状态消息”更便于人理解）。但是WEB开发者仍然能够自行决定采用何种短语，用以显示本地化的状态描述或者自定义信息。常见的状态码有如下：

* 200 OK 服务器成功处理了请求；
* 206 Partial Content（部分内容）代表服务器已经成功处理了部分GET请求（只有发送GET 方法的request, web服务器才可能返回206）
* 301 Moved Permanently（永久重定向）请求的URL已移走。Response中应该包含一个Location URL, 说明资源现在所处的位置
* 302 Moved Temporarily（临时重定向） 
* 304 Not Modified（未修改）客户的缓存资源是最新的，要客户端使用缓存
* 400 Bad Request（坏请求）告诉客户端，它发送了一个错误的请求。
* 401 Unauthorized（未授权）需要客户端对自己认证
* 404 Not Found 未找到资源
* 500 Internal Server Error 服务器遇到一个错误，使其无法对请求提供服务

### 消息报头

响应消息报头包含了普通报头、响应报头、实体报头，普通报头和实体报头和请求消息报头中的普通报头、实体报头相同。

响应报头允许服务器传递不能放在状态行中的附加响应信息，以及关于服务器的信息和 对 Request-URI 所标识的资源进行下一步访问的信息。常用的响应报头如下： 

* Location：用于重定向接受者到一个新的位置，Location响应报头域常用在更换域名的时候；
* Server：包含了服务器用来处理请求的软件信息，与User-Agent请求报头域是相对应的；
* WWW-Authenticate：必须被包含在401(未授权的)响应消息中。

### 响应正文

消息正文类似HTTP请求的消息正文。

## HTTP协议之Get和Post

Http协议定义了很多与服务器交互的方法，最基本的有4种，分别是GET, POST, PUT, DELETE。一个URL地址用于描述一个网络上的资源，而HTTP中的GET, POST, PUT, DELETE就对应着对这个资源的查，改，增，删4个操作。我们最常见的就是GET和POST了。GET一般用于**获取/查询**资源信息，而POST一般用于**更新**资源信息，主要区别如下：

1. GET提交的数据会放在URL之后，以?分割URL和传输数据，参数之间以&相连，如EditPosts.aspx?name=test1&id=123456。POST方法是把提交的数据放在HTTP包的Body中。
2. GET提交的数据大小有限制（因为`浏览器对URL的长度有限制`，实际上HTTP协议规范没有对URL长度进行限制），而POST方法提交的数据没有限制。
3. GET方式需要使用Request.QueryString来取得变量的值，而POST方式通过Request.Form来获取变量的值，也就是说Get是通过地址栏来传值，而Post是通过提交表单来传值。
4. GET方式提交数据，会带来安全问题，比如一个登录页面，通过GET方式提交数据时，用户名和密码将出现在URL上，如果页面可以被缓存或者其他人可以访问这台机器，就可以从历史记录获得该用户的账号和密码.

## Http不同版本区别

HTTP/1.0版本，1.1版本主要区别如下：

* `带宽优化`
    
    HTTP/1.0中，存在一些浪费带宽的现象，例如客户端只是需要某个对象的一部分，而服务器却将整个对象送过来了。HTTP/1.1中在请求消息中引入了range头域，它允许只请求资源的某个部分

    另外一种情况是请求消息中如果包含比较大的实体内容，但不确定服务器是否能够接收该请求（如是否有权限），此时若贸然发出带实体的请求，如果被拒绝也会浪费带宽。HTTP/1.1加入了一个新的状态码100（Continue），客户端事先发送一个只带头域的请求，如果服务器因为权限拒绝了请求，就回送响应码401（Unauthorized）；如果服务器接收此请求就回送响应码100，客户端就可以继续发送带实体的完整请求了。

* `长连接`

    HTTP 1.0规定浏览器与服务器只保持短暂的连接，浏览器的每次请求都需要与服务器建立一个TCP连接，服务器完成请求处理后立即断开TCP连接，服务器不跟踪每个客户也不记录过去的请求。由于大多数网页的流量都比较小，一次TCP连接很少能通过slow-start区，不利于提高带宽利用率。
    
    HTTP 1.1支持长连接（PersistentConnection）和请求的流水线（Pipelining）处理，在一个TCP连接上可以传送多个HTTP请求和响应，减少了建立和关闭连接的消耗和延迟。
    
    HTTP 1.1还允许客户端不用等待上一次请求结果返回，就可以发出下一次请求，但服务器端必须按照接收到客户端请求的先后顺序依次回送响应结果，以保证客户端能够区分出每次请求的响应内容，这样也显著地减少了整个下载过程所需要的时间。

* `缓存`

    在HTTP/1.0 中，使用Expire头域来判断资源的fresh或stale，并使用条件请求来判断资源是否仍有效。例如，cache服务器通过If-Modified-Since头域向服务器验证资源的Last-Modefied头域是否有更新，源服务器可能返回304（Not Modified），则表明该对象仍有效；也可能返回200（OK）替换请求的Cache对象。
    
    HTTP/1.1在1.0的基础上加入了一些cache的新特性，当缓存对象的Age超过Expire时变为stale对象，cache不需要直接抛弃stale对象，而是与源服务器进行重新激活（revalidation）。
    
* `Host头域`

    在 HTTP1.0 中认为每台服务器都绑定一个唯一的IP地址，因此请求消息中的URL并没有传递主机名（hostname）。但随着虚拟主机技术的发展，在一台物理服务器上可以存在多个虚拟主机（Multi-homed Web Servers），并且它们共享一个IP地址。HTTP1.1的请求消息和响应消息都支持Host头域，请求消息中如果没有Host头域会报告一个错误（400 Bad Request）。

* `错误提示`

    HTTP/1.0 中只定义了16个状态响应码，对错误或警告的提示不够具体。HTTP/1.1 引入了一个Warning头域，增加对错误或警告信息的描述。
    
    此外，在HTTP/1.1中新增了24个状态响应码，如409（Conflict）表示请求的资源与资源的当前状态发生冲突；410（Gone）表示服务器上的某个资源被永久性的删除。

# HTTP 高级内容

## Cookie与Session

Cookie 和 Session 都为了用来保存状态信息，都是保存客户端状态的机制，它们都是为了解决HTTP无状态的问题而所做的努力。

### Cookie 机制

简单地说，cookie 就是浏览器储存在用户电脑上的一小段文本文件。cookie 是纯文本格式，不包含任何可执行的代码。一个 Web 页面或服务器告知浏览器按照一定规范来储存这些信息，并在随后的请求中将这些信息发送至服务器，Web 服务器就可以使用这些信息来识别不同的用户。大多数需要登录的网站在用户验证成功之后都会设置一个 cookie，只要这个 cookie 存在并可以，用户就可以自由浏览这个网站的任意页面。

cookie 会被浏览器自动删除，通常存在以下几种原因：

1. 会话 cooke (Session cookie) 在会话结束时（浏览器关闭）会被删除
2. 持久化 cookie（Persistent cookie）在到达失效日期时会被删除
3. 如果浏览器中的 cookie 数量达到限制，那么 cookie 会被删除以为新建的 cookie 创建空间。

大多数浏览器支持最大为 4096 字节的 Cookie。由于这限制了 Cookie 的大小，最好用 Cookie 来存储少量数据，或者存储用户 ID 之类的标识符。用户 ID 随后便可用于标识用户，以及从数据库或其他数据源中读取用户信息。 浏览器还限制站点可以在用户计算机上存储的 Cookie 的数量。大多数浏览器只允许每个站点存储 20 个 Cookie；如果试图存储更多 Cookie，则最旧的 Cookie 便会被丢弃。有些浏览器还会对它们将接受的来自所有站点的 Cookie 总数作出绝对限制，通常为 300 个。
	
使用 Cookie 的缺点：

* 不良站点用 Cookie 收集用户隐私信息；
* Cookie窃取：黑客以可以通过窃取用户的cookie来模拟用户的请求行为。（跨站脚本攻击XSS）

### Session 机制

Session机制是一种服务器端的机制，服务器使用一种类似于散列表的结构（也可能就是使用散列表）来保存信息。当程序需要为某个客户端的请求创建一个session的时候，服务器首先检查这个客户端的请求里是否已包含了一个session标识（session id）：

* 如果已包含一个session id 则说明以前已经为此客户端创建过session，服务器就按照session id把这个 session 检索出来使用（如果检索不到，可能会新建一个）。
* 如果客户端请求不包含session id，则为此客户端创建一个session并且生成一个与此session相关联的session id，session id的值应该是一个既不会重复，又不容易被找到规律以仿造的字符串，这个 session id将被在本次响应中返回给客户端保存。

具体实现方式：

* `Cookie方式`：服务器给每个Session分配一个唯一的JSESSIONID，并通过Cookie发送给客户端。当客户端发起新的请求的时候，将在Cookie头中携带这个JSESSIONID，这样服务器能够找到这个客户端对应的Session。
* `URL回写`：服务器在发送给浏览器页面的所有链接中都携带JSESSIONID的参数，这样客户端点击任何一个链接都会把JSESSIONID带回服务器。如果直接在浏览器输入服务端资源的url来请求该资源，那么Session是匹配不到的。

## Web 缓存
		
WEB缓存(cache)位于Web服务器和客户端之间，缓存机制会根据请求保存输出内容的副本，例如html页面，图片，文件，当下一个请求来到的时候：如果是相同的URL，缓存直接使用副本响应访问请求，而不是向源服务器再次发送请求。

有缓存的 Get 请求过程如下：

![][3]

主要分三种情况:

1. 未找到缓存(黑色线)：当没有找到缓存时，说明本地并没有这些数据，这种情况一般发生在我们首次访问网站，或者以前访问过，但是清除过缓存后。浏览器就会先访问服务器，然后把服务器上的内容取回来，内容取回来以后，就要根据情况来决定是否要保留到缓存中了。

2. 缓存未过期(蓝色线)：缓存未过期，指的是本地缓存没有过期，不需要访问服务器了，直接就可以拿本地的缓存作为响应在本地使用了。这样节省了不少网络成本，提高了用户体验过。

3. 缓存已过期(红色线)：当满足过期的条件时，会向服务器发送请求，发送的请求一般都会进行一个验证，目的是虽然缓存文档过期了，但是文档内容不一定会有什么改变，所以服务器返回的也许是一个新的文档，这时候的HTTP状态码是200，或者返回的只是一个最新的时间戳和304状态码。

    缓存过期后，有两种方法来判定服务端的文件有没有更新。第一种在上一次服务端告诉客户端约定的有效期的同时，告诉客户端该文件最后修改的时间，当再次试图从服务端下载该文件的时候，check下该文件有没有更新（对比最后修改时间），如果没有，则读取缓存；第二种方式是在上一次服务端告诉客户端约定有效期的同时，同时告诉客户端该文件的版本号，当服务端文件更新的时候，改变版本号，再次发送请求的时候check一下版本号是否一致就行了，如一致，则可直接读取缓存。

浏览器是依靠请求和响应中的的头信息来控制缓存的，如下：

* Expires与Cache-Control：服务端用来约定和客户端的有效时间的。Expires规定了缓存失效时间（Date为当前时间），而Cache-Control的max-age规定了缓存有效时间（2552s）。Expires是HTTP1.0的东西，而Cache-Control是HTTP1.1的，规定如果max-age和Expires同时存在，前者优先级高于后者。

* Last-Modified/If-Modified-Since：缓存过期后，check服务端文件是否更新的第一种方式。

* ETag/If-None-Match：缓存过期时check服务端文件是否更新的第二种方式。实际上ETag并不是文件的版本号，而是一串可以代表该文件唯一的字符串，当客户端发现和服务器约定的直接读取缓存的时间过了，就在请求中发送If-None-Match选项，值即为上次请求后响应头的ETag值，该值在服务端和服务端代表该文件唯一的字符串对比（如果服务端该文件改变了，该值就会变），如果相同，则相应HTTP304，客户端直接读取缓存，如果不相同，HTTP200，下载正确的数据，更新ETag值。

当然并不是所有请求都能被缓存。无法被浏览器缓存的请求：

1. HTTP信息头中包含Cache-Control:no-cache，pragma:no-cache（HTTP1.0），或Cache-Control:max-age=0等告诉浏览器不用缓存的请求
2. 需要根据Cookie，认证信息等决定输入内容的动态请求是不能被缓存的
3. POST请求无法被缓存

浏览器缓存过程还和用户行为有关。譬如先打开一个主页有个jquery的请求（假设访问后会缓存下来）。接着如果直接在地址栏输入 jquery 地址，然后回车，响应HTTP200（from cache），因为有效期还没过直接读取的缓存；如果ctrl+r进行刷新，则会相应HTTP304（Not Modified），虽然还是读取的本地缓存，但是多了一次服务端的请求；而如果是ctrl+shift+r强刷，则会直接从服务器下载新的文件，响应HTTP200。

## HTTP 代理

Web代理（proxy）服务器是网络的中间实体。代理位于Web客户端和Web服务器之间，扮演“中间人”的角色。HTTP的代理服务器即是Web服务器又是Web客户端。（Fiddler 是以代理web服务器的形式工作的,它使用代理地址:127.0.0.1, 端口:8888. 当Fiddler退出的时候它会自动注销代理，这样就不会影响别的程序。）

![][4]

代理服务器有许多用处：

* 跨过网络障碍。翻墙技术：局域网不能上网，只能通过局域网内的一台代理服务器上网。
* 匿名访问。HTTP代理服务器通过删除HTTP报文中的身份特性（比如客户端的IP地址，或cookie,或URI的会话ID），从而对远端服务器隐藏原始用户的IP地址以及其他细节。同时HTTP代理服务器上也不会记录原始用户访问记录的log。
* 通过代理缓存，加快上网速度。大部分代理服务器都具有缓存的功能，不断将新取得数据存储到它本地的存储器上，如果浏览器所请求的数据在它本机的存储器上已经存在而且是最新的，那么直接将存储器上的数据传给用户，这样就能显著提高浏览速度。
* 过滤指定内容。比如儿童过滤器，很多教育机构，会利用代理来阻止学生访问成人内容。

代理服务器和抓包工具（比如Fiddler）都能看到http request中的数据。如果我们发送的request中有敏感数据，比如用户名，密码，信用卡号码，就会被代理服务器看到。所以我们一般都是用HTTPS来加密Http request。

# 参考

[深入理解HTTP协议（二）——协议详解篇](http://www.voidcn.com/blog/huangjianxiang1875/article/p-1596378.html)  
[深入理解HTTP协议（三）——深入了解篇](http://www.voidcn.com/blog/huangjianxiang1875/article/p-1596379.html)  
[RFC2616 is Dead](https://www.mnot.net/blog/2014/06/07/rfc2616_is_dead)  
[HTTP协议 (四) 缓存](http://www.cnblogs.com/TankXiao/archive/2012/11/28/2793365.html)  
[HTTP cookies 详解](http://bubkoo.com/2014/04/21/http-cookies-explained/)  
[细说 Cookie](http://www.cnblogs.com/fish-li/archive/2011/07/03/2096903.html)  
[HTTP/1.1与HTTP/1.0的区别](http://blog.csdn.net/forgotaboutgirl/article/details/6936982)  
[浏览器缓存机制浅析](http://web.jobbole.com/82997/)  
[HTTP缓存机制](http://oohcode.com/2015/05/28/http-cache/)  


# Socket 套接字

Socket 起源于 Unix，而Unix基本哲学之一就是`一切皆文件`，都可以用“`打开open –> 读写write/read –> 关闭close`”模式来操作。Socket就是该模式的一个实现，网络的Socket数据传输是一种特殊的I/O，Socket也是一种文件描述符。Socket也具有一个类似于打开文件的函数调用：Socket()，该函数返回一个整型的Socket描述符，随后的连接建立、数据传输等操作都是通过该Socket实现的。

使用TCP/IP协议的应用程序通常采用应用编程接口：UNIX BSD的套接字（socket）和UNIX System V的TLI（已经被淘汰），来实现网络进程之间的通信。

常用的Socket类型有两种：流式Socket（SOCK_STREAM）和数据报式Socket（SOCK_DGRAM）。流式是一种面向连接的Socket，针对于面向连接的TCP服务应用；数据报式Socket是一种无连接的Socket，对应于无连接的UDP服务应用。（socket的类型有哪些？）

# Socket 接口函数

既然socket是“open—write/read—close”模式的一种实现，那么socket就提供了这些操作对应的函数接口。下面以TCP为例，介绍几个基本的socket接口函数。

`socket函数`：**使用给定的地址族、套接字类型、协议编号（默认为0）来创建套接字**。

socket函数对应于普通文件的打开操作。普通文件的打开操作返回一个文件描述字，而socket()用于创建一个socket描述符（socket descriptor），它唯一标识一个socket。这个socket描述字跟文件描述字一样，后续的操作都有用到它，把它作为参数，通过它来进行一些读写操作。

    int socket(int domain, int type, int protocol);

socket函数的三个参数分别为：

* domain：`协议域`。常用的协议族有AF_INET、AF_INET6等。协议族决定了socket的地址类型，在通信中必须采用对应的地址，如AF_INET决定了要用ipv4地址（32位的）与端口号（16位的）的组合。
* type：`socket类型`。常用的socket类型有，SOCK_STREAM、SOCK_DGRAM、SOCK_RAW、SOCK_PACKET、SOCK_SEQPACKET等等。
* protocol：`指定协议`。常用的协议有，IPPROTO_TCP、IPPTOTO_UDP等，它们分别对应TCP传输协议、UDP传输协议。

注意：type和protocol不可以随意组合的，如SOCK_STREAM不可以跟IPPROTO_UDP组合。当protocol为0时，会自动选择type类型对应的默认协议。

我们调用socket创建一个socket后，返回的socket描述符存在于协议族空间中，但没有一个具体的地址。如果想要给它赋值一个地址，就必须调用bind()函数，否则就当调用connect()、listen()时系统会自动随机分配一个端口。

## 服务器端函数

`bind函数`：**将套接字绑定到地址。**

    int bind(int sockfd, struct sockaddr * my_addr, int addrlen);

三个参数分别为：

* sockfd : 即socket描述字，通过socket()函数创建，唯一标识一个socket。
* my_addr : 结构体指针变量，指向要绑定给sockfd的协议地址。这个地址结构根据地址创建socket时的地址协议族的不同而不同。
* addrlen : 对应的是地址的长度。

通常服务器在启动的时候都会绑定一个地址（如ip地址+端口号），用于提供服务，客户就可以通过它来接连服务器；而客户端就不用指定，系统自动分配一个端口号和自身的ip地址组合。这就是为什么通常服务器端在listen之前会调用bind()，而客户端就不会调用，而是在connect()时由系统随机生成一个。

`listen函数`：使服务器的这个端口和IP处于监听状态，等待网络中某一客户机的连接请求。如果客户端有连接请求，端口就会接受这个连接。

    int listen(int sockfd, int backlog);

两个参数分别为：

* sockfd: socket描述字。
* backlog: 指定同时能处理的最大连接要求，通常为10或者5。最大值可设至128。

`accept函数`：接受远程计算机的连接请求，建立起与客户机之间的通信连接。服务器处于监听状态时，如果某时刻获得客户机的连接请求，此时并不是立即处理这个请求，而是将这个请求放在等待队列中，当系统空闲时再处理客户机的连接请求。

    int accept(int sockfd, struct sockaddr * addr,int * addrlen);

三个参数分别为：

* sockfd : socket描述字。
* addr: 为结构体指针变量，和bind的结构体是同种类型的，系统会把远程主机的信息（远程主机的地址和端口号信息）保存到这个指针所指的结构体中。
* addrlen : 表示结构体的长度

accept的第一个参数为服务器的socket描述字，是服务器开始调用socket()函数生成的，称为`监听socket描述字`；而accept函数返回的是`已连接的socket描述字`。一个服务器通常通常仅仅只创建一个监听socket描述字，它在该服务器的生命周期内一直存在。内核为每个由服务器进程接受的客户连接创建了一个已连接socket描述字，当服务器完成了对某个客户的服务，相应的已连接socket描述字就被关闭。

## 客户端函数

`connect函数`用来请求连接远程服务器.

    int connect (int sockfd,struct sockaddr * serv_addr,int addrlen);

三个参数分别为：

* sockfd : socket描述字，前面socket的返回值；
* serv_addr : 存储着远程服务器的IP与端口号信息；
* addrlen : 表示结构体变量的长度。

## 通用函数

`recv函数`：负责从缓冲区中读取内容。当读成功时，read返回实际所读的字节数，如果返回的值是0表示已经读到文件的结束了，小于0表示出现了错误。

    int recv(int sockfd,void *buf,int len,unsigned int flags);

四个参数分别为：

* sockfd : 为前面accept的返回值.也就是新的套接字。
* buf : 表示缓冲区
* len : 表示缓冲区的长度
* flags : 通常为0

`send函数`：将buf中的nbytes字节内容写入socket描述字。成功时返回写的字节数。失败时返回-1，并设置errno变量。

    int send(int sockfd,const void * msg,int len,unsigned int flags);

* sockfd : 为前面socket的返回值.
* msg : 一般为常量字符串
* len : 表示长度
* flags : 通常为0

`close函数`：关闭套接字。若顺利关闭则返回0，发生错误时返回-1。

    int close(int sockfd);

# TCP 通信

TCP中 Socket 通信的基本步骤如下：

![][1]

一个简单的 C/S 程序如下（客户端发出的数据, 服务器会回显到客户端的终端上。只是一个简单的模型, 没考虑错误处理等问题。）

服务器端如下：

    import socket   # socket模块
    
    BUF_SIZE = 1024  # 设置缓冲区大小
    server_addr = ('127.0.0.1', 8888)  # IP和端口构成表示地址
    
    server = socket.socket(socket.AF_INET,
                           socket.SOCK_STREAM)   # 生成一个新的socket对象
    server.setsockopt(socket.SOL_SOCKET,
                      socket.SO_REUSEADDR, 1)    # 设置地址复用
    server.bind(server_addr)  # 绑定地址
    server.listen(5)          # 监听, 最大监听数为5
    while True:
        client, client_addr = server.accept()  # 接收TCP连接, 并返回新的套接字和地址
        print 'Connected by', client_addr
        while True:
            data = client.recv(BUF_SIZE)       # 从客户端接收数据
            print data
            client.sendall(data)               # 发送数据到客户端
    server.close()

客户端如下：

    import socket
    
    BUF_SIZE = 1024
    server_addr = ('127.0.0.1', 8888)
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect(server_addr)
    while True:
        data = raw_input("Please input some string > ")
        client.sendall(data)
        data = client.recv(BUF_SIZE)
        print data
    client.close()

不过真实的网络编程环境中，一定要使用大量的错误处理，可以尽量的发现错误，也能够使代码显得更加严谨。

三次握手
SYN_SENT    connect() 阻塞 --- accept() 阻塞 SYS_RCV
ESTABLISHED connect() 返回 --- accept() 返回 ESTABLISHED

四次挥手
FIN_WAIT1 close() 阻塞 --- read() 读 0 字节 LAST_ACK
FIN_WAIT2 close()

# UDP Socket函数 

`sendto()`函数：发送UDP数据，将数据发送到套接字。返回实际发送的数据字节长度或在出现发送错误时返回-1。

    int sendto(int sockfd, const void *msg,int len,unsigned int flags,const struct sockaddr *to, int tolen);

`recvfrom()`函数：接受UDP套接字的数据, 与recv()类似。返回接收到的字节数或当出现错误时返回-1，并置相应的errno。

    int recvfrom(int sockfd,void *buf,int len,unsigned int flags,struct sockaddr *from,int *fromlen);

UDP通信流程图如下：

![][2]

简单的客户端服务器UDP连接，服务器端：

    #!/usr/bin/env python
    # -*- coding:utf-8 -*-
    
    import socket
    
    BUF_SIZE = 1024                     # 设置缓冲区大小
    server_addr = ('127.0.0.1', 8888)   # IP和端口构成表示地址
        
    # 生成新的套接字对象
    server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server.bind(server_addr)        # 套接字绑定IP和端口
        
    while True:
      print "waitting for data"
      # 从客户端接收数据
      data, client_addr = server.recvfrom(BUF_SIZE)
      print 'Connected by', client_addr, ' Receive Data : ', data
      # 发送数据给客户端
      server.sendto(data, client_addr)
    server.close()

客户端如下：

    import socket
    
    BUF_SIZE = 1024                     # 设置缓冲区
        server_addr = ('127.0.0.1', 8888)   # IP和端口构成表示地址
        
    client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    while True:
      data = raw_input('Please Input data > ')
      client.sendto(data, server_addr)  # 向服务器发送数据
      data, addr = client.recvfrom(BUF_SIZE)  # 从服务器接收数据
      print "Data : ", data
    client.close()


参考：  
[Python爬虫(三)-Socket网络编程](http://www.jianshu.com/p/e062b3dd110c)  


