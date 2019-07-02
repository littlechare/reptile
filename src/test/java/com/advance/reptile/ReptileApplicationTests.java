package com.advance.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReptileApplicationTests {

	@Test
	public void contextLoads() throws IOException {

		Document doc = Jsoup.connect("http://www.xinshubao.net/7/7828/").get();
		String bookName = doc.select("#info h1").text();
		String author = doc.select("#info p").eq(0).text();
		String status = doc.select("#info p").eq(1).text();
		String last_update = doc.select("#info p").eq(3).text();
		String intro = doc.select("#intro p").text();
		Elements links = doc.select("#list ._chapter a[href]");
		for (Element link : links) {
//			Document html = Jsoup.connect(link.attr("href")).get();
			String href = link.attr("href");
			String name = link.text();
			System.err.println(link.attr("href"));
			String context = Jsoup.connect(href).get().select("#intro").html();
			System.err.println(context);
//			break;
		}
//		Connection connect = Jsoup.connect("http://www.xinshubao.net/7/7828/");
//		Map<String, String> header = new HashMap<String, String>();
//		header.put("Host", "http://info.bet007.com");
//		header.put("User-Agent", "	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
//		header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		header.put("Accept-Language", "zh-cn,zh;q=0.5");
//		header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
//		header.put("Connection", "keep-alive");
//		Connection data = connect.data(header);
//		Document doc = data.ignoreContentType(true).get();
//		Element body = doc.body();
//		JSONObject json = JSONObject.parseObject(body.text());
//		JSONArray arrs = json.getJSONArray("data");
//		for(Object obj : arrs){
//			Map<String, Object> urlData = (obj instanceof Map) ? (Map<String, Object>)obj : null;
//			String url = "https://c84596.818tu.com/m/read/" + urlData.get("id").toString();
//			Connection connectUrl = Jsoup.connect(url);
//			Connection dataUrl = connectUrl.data(header);
//			Document docUrl = dataUrl.ignoreContentType(true).get();
//			Element bodyUrl = docUrl.body();
//			JSONObject jsonUrl = JSONObject.parseObject(bodyUrl.text());
//			System.err.println(jsonUrl);
//		}
//	9	Elements links = doc.select(".volume .chapter a[href]");
//		for (Element link : links) {
//			Document html = Jsoup.connect(link.attr("href")).get();
////			Elements context = html.select("div#content").after("』為您提供精彩小說閱讀。");
//			System.err.println(html.html());
//			break;
//		}
	}

}
