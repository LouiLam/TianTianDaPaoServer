package start;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public abstract class AbstractHttpRequestHandler {


	
	
	public void handle(HttpRequest request, Channel channel){
		
		HttpMethod method = request.getMethod();
		if(method.equals(HttpMethod.GET)){
			handle(request.getUri(), channel);
		}else if(method.equals(HttpMethod.POST)){
			ChannelBuffer channelBuffer = request.getContent();
			byte[] parametersBytes = new byte[channelBuffer.readableBytes()];
			channelBuffer.readBytes(parametersBytes);
			String parametersString = new String(parametersBytes);
			handle(parametersString, channel);
		}
	}
	
	protected abstract void handle(String parametersString, Channel channel);
	
	protected void sendResponse(String contextString, Channel channel){
		
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
		ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
		channelBuffer.writeBytes(contextString.getBytes());
		response.setContent(channelBuffer);
		response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
		channel.write(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	protected void sendError(ChannelBuffer channelBuffer, Channel channel){
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
		response.setContent(channelBuffer);
		response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
		channel.write(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	
}
