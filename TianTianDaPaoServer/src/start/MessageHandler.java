package start;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;

import config.ConfigFactory;


public class MessageHandler extends SimpleChannelUpstreamHandler {

	private static Logger logger = Logger.getLogger(MessageHandler.class);
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelConnected(ctx, e);
		logger.info("ip:" + e.getChannel().getRemoteAddress() + " connected in...");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		HttpRequest httpRequest = (HttpRequest) e.getMessage();
		String uri = httpRequest.getUri();
		String path = uri.split("[?]")[0].trim();
		try{
			logger.error("请求uri："+uri+"------path:"+path);
			if("/favicon.ico".equals(uri)){
				return;
			}
			AbstractHttpRequestHandler abstractHttpRequestHandler = ConfigFactory
					.getHttpRequestHandler(path);
			abstractHttpRequestHandler.handle(httpRequest, e.getChannel());
		}catch(NullPointerException e1){
			e1.printStackTrace();
			logger.error("非法请求：" + path + ", 地址：" + e.getChannel().getRemoteAddress());
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		Channel ch = e.getChannel();
		Throwable cause = e.getCause();
		if (cause instanceof TooLongFrameException) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}

		cause.printStackTrace();
		if (ch.isConnected()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
//		response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.setContent(ChannelBuffers.copiedBuffer(
				"Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		ctx.getChannel().write(response)
				.addListener(ChannelFutureListener.CLOSE);
	}

}