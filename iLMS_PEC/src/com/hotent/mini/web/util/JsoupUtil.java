package com.hotent.mini.web.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {
	public static String prettyHtml(String html){
		Document doc = Jsoup.parseBodyFragment(html);
		return doc.body().html();
	}
}