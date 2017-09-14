package com.zkzy.zyportal.system.api.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import static com.zkzy.portal.common.utils.RandomHelper.uuid;

public class MessageUtil {

	//1、群发消息接口
	//1.1上传多媒体文件获取id， 文件路径为系统路径
	public static JSONObject getMedia_id(String filePath, String type, String access_token)
			throws Exception {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		String fileName = file.getName();
		// 获取文件流
		DataInputStream in = new DataInputStream(new FileInputStream(file));

		URL urlObj = new URL(
				"http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
						+ access_token + "&type="+type);

		return postFile(urlObj,in,fileName);
	}

	//1.2上传图文消息获取图文消息id（用于群发图文消息）
	public static JSONObject getMpNewsId(String filePath, String author, String title,
			String desc, String content, String access_token) {
		//获取缩略图id
		String media_id = "";
		try {
			media_id = getMedia_id(filePath, "image", access_token).getString("media_id");
		} catch (Exception e) {
			System.out.println("获取缩略图id失败");
			e.printStackTrace();
		}
		System.out.println(media_id);
		//获取图文消息id
		JSONObject article = new JSONObject();
		JSONObject json = new JSONObject();
		if (media_id != "" && media_id != null) {
			article.put("thumb_media_id", media_id);
		}
		article.put("author", author);
		article.put("title", title);
		article.put("digest", desc);
		article.put("content", content);
		article.put("content_source_url", "");
		article.put("show_cover_pic", 1);
		JSONObject[] articles = { article };
		json.put("articles", articles);
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="
				+ access_token;
		return WeChatUtil.post(json, url);
	}

	//1.3获取视频信息的media_id
	public static JSONObject getVedioId(String filePath,String title,String description,String access_token){
		String media_id = null;
		try {
			media_id = getMedia_id(filePath,"video",access_token).getString("media_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token="+access_token;
		JSONObject json = new JSONObject();
		json.put("media_id", media_id);
		json.put("title", title);
		json.put("description", description);
		return WeChatUtil.post(json, url);
	}

	//amr文件转换为mp3
	public static void changeToMp3(String sourcePath, String targetPath) {
		File source = new File(sourcePath);
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();

		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);

		try {
			encoder.encode(source, target, attrs);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}

	//网络图片地址转换为微信地址
	public static String getImgUrl(String src, String access_token) throws Exception {
		// String access_token = WeChatUtil.getAccess_token(appid, secret);
		//new一个URL对象
		URL url = new URL(src);
		//打开链接
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//设置请求方式为"GET"
		conn.setRequestMethod("GET");
		//超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		//通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();
		DataInputStream in = new DataInputStream(inStream);

		String fileName = uuid()+src.substring(src.lastIndexOf("/")+1,src.length());
		if(!fileName.endsWith(".jpg")&&!fileName.endsWith(".png")&&!fileName.endsWith(".gif")){
			fileName = fileName + ".jpg";
		}
		URL urlObj = new URL(
				"https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="
						+ access_token);

		JSONObject json = postFile(urlObj,in,fileName);
		return json.getString("url");
	}

	//获取图文消息中img src路径集合
	public static Set<String> getImgStr(String htmlStr) {
		Set<String> pics = new HashSet<>();
		String img = "";
		Pattern p_image;
		Matcher m_image;
		//     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile
				(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			// 得到<img />数据
			img = m_image.group();
			// 匹配<img>中的src数据
			Matcher m = Pattern.compile("src\\s*=\\s*'?(.*?)('|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	//推送文件至指定url
	public static JSONObject postFile(URL urlObj,DataInputStream in,String fileName) throws Exception {
		/**
		 * 第一部分
		 */
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ fileName + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject json = (JSONObject) JSON.parse(result);
		return json;
	}
}
