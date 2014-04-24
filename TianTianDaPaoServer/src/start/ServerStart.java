package start;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import server.ui.main.U;

public class ServerStart {

	private static Logger logger = Logger.getLogger(MessageHandler.class);

	public void init() throws DocumentException {

	}
	public void start() throws DocumentException {
		int port = ServerConfig.getInstance().getPort();
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
//		 bootstrap.setOption("tcpNoDelay", true);
//		 bootstrap.setOption("backlog", 100);
		bootstrap.bind(new InetSocketAddress(port));
		U.info("服务器绑定端口成功---------------" + "端口号："+port);
		logger.info("================================================");
		logger.info("================服务器绑定端口成功---------" + "端口号："+port + "===============");
		logger.info("================================================");
//		int period = Integer.parseInt(ConfigFactory
//				.getTimerConfig("RequestCenterServerGapTime"));
//		TaskScheduled.scheduleAtFixedRate(new GetHallInfoHttpGetRequest(), 0, period, TimeUnit.SECONDS);
	}

	private class ServerPipelineFactory implements ChannelPipelineFactory {
		public ChannelPipeline getPipeline() throws Exception {
			ChannelPipeline pipeline = Channels.pipeline();
			pipeline.addLast("decoder", new HttpRequestDecoder());
			pipeline.addLast("encoder", new HttpResponseEncoder());
			pipeline.addLast("handler", new MessageHandler());
			return pipeline;
		}
	}


}
