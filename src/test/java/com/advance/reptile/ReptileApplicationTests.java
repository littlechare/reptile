package com.advance.reptile;

import com.advance.reptile.mongo.pojo.ChapterMogo;
import com.advance.reptile.mongo.service.ChapterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReptileApplicationTests {

	@Autowired
	private ChapterService chapterService;
//	@Autowired
//	private IBookService bookService;


	private final static String BASEIC_URL = "http://www.xinshubao.net/7/7828/";

	@Test
	public void saveChapter(){
		ChapterMogo chapter = new ChapterMogo();
		chapter.setDataStatus("1");
//		chapter.setDataTime(LocalDateTime.now());
		chapter.setTitle("test");
		chapter.setTxt("hahahaha");
		chapter.setUrl("123");
		chapterService.insertDocument(chapter);
//		System.err.println(chapterService.getChapter(chapter));
	}

//	@Test
//	public void contextLoads() throws IOException {
//		String url1 = "http://www.xinshubao.net/7/7828/23029653.html";
//		String url2 = "http://www.xinshubao.net/7/7828/23029653.html";
//		String url3  = "http://www.xinshubao.net/7/7828/23029653.html";
//		String text1 = Jsoup.connect(url1).get().select("#content").text().trim().replaceAll("br/>","");
//		String text2 = Jsoup.connect(url2).get().select("#content").text().trim().replaceAll("br/>","");
//		String text3 = Jsoup.connect(url3).get().select("#content").text().trim().replaceAll("br/>","");
//		Chapter chapter = new Chapter();
//		chapter.setUrl(url1);
//		chapter.setDataTime(LocalDateTime.now());
//		chapter.setDataStatus("1");
//		chapter.setTxt(text1+text2+text3);
//		chapter.setTitle(Jsoup.connect(url1).get().select(".bookname h1").text());
//		chapterService.insertDocument(chapter);
////		Document doc = Jsoup.connect(BASEIC_URL).get();
////		String bookName = doc.select("#info h1").text();
////		String author = doc.select("#info p").eq(0).text();
////		String status = doc.select("#info p").eq(1).text();
////		String last_update = doc.select("#info p").eq(3).text();
////		String intro = doc.select("#intro p").text();
////		Book book = new Book();
////		book.setId(CommonUtils.getUuid());
////		book.setName(bookName);
////		book.setAuthor(author);
////		book.setLastUpdateTime(last_update);
////		book.setUrl(BASEIC_URL);
////		book.setIntro(intro);
////		book.setStatus(status);
////		bookService.saveBook(book);
////		Elements links = doc.select("#list ._chapter a[href]");
////		String temp = links.eq(0).attr("href");
////		doNext(temp, new Chapter());
//	}
//
//	public void doNext(String url, Chapter chapter) throws IOException {
//		String next = Jsoup.connect(url).get().select(".bottem1 p:eq(1) a:eq(3)").attr("href");
//
//		if(!(url.lastIndexOf("_") > -1)){
//			if(CommonUtils.objNotEmpty(chapter.getTxt())){
//				chapterService.insertDocument(chapter);
//			}
//			String title = Jsoup.connect(url).get().select(".bookname h1").text();
//			chapter = new Chapter();
//			chapter.setUrl(url);
//			chapter.setDataTime(LocalDateTime.now());
//			chapter.setDataStatus("1");
//			chapter.setTxt(Jsoup.connect(url).get().select("#content").text().trim().replaceAll("br/>",""));
//			chapter.setTitle(title);
//		}else{
//			chapter.setTxt(chapter.getTxt() + Jsoup.connect(url).get().select("#content").text().trim().replaceAll("br/>",""));
//		}
//		if(url.equals(BASEIC_URL)){
//			chapterService.insertDocument(chapter);
//			return;
//		}
//		url = BASEIC_URL + next;
//
//		doNext(url, chapter);
//	}

}
