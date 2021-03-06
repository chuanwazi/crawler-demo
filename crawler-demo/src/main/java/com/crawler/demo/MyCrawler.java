package com.crawler.demo;

import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler{

	/**
     * 正则匹配指定的后缀文件
     */
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");
    /**
     * 这个方法主要是决定哪些url我们需要抓取，返回true表示是我们需要的，返回false表示不是我们需要的Url
     * 第一个参数referringPage封装了当前爬取的页面信息
     * 第二个参数url封装了当前爬取的页面url信息
     */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		 String href = url.getURL().toLowerCase();  // 得到小写的url
		 return !FILTERS.matcher(href).matches()   // 正则匹配，过滤掉我们不需要的后缀文件
	                && href.startsWith("http://music.163.com/");  // url必须是http://wwwbaidu.com/开头，规定站点
	}
	
	
	/**
     * 当我们爬到我们需要的页面，这个方法会被调用，我们可以尽情的处理这个页面
     * page参数封装了所有页面信息
     */
	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid(); // 获取docid url的唯一识别 类似主键
        String url = page.getWebURL().getURL();  // 获取url
        String domain = page.getWebURL().getDomain(); // 获取域名
        String path = page.getWebURL().getPath();  // 获取路径 
        String subDomain = page.getWebURL().getSubDomain(); // 获取子域名
        String parentUrl = page.getWebURL().getParentUrl(); // 获取上级Url
        String anchor = page.getWebURL().getAnchor(); // 获取锚点
 
        System.out.println("docid:"+docid);
        System.out.println("url:"+url);
        System.out.println("domain:"+domain);
        System.out.println("path:"+path);
        System.out.println("subDomain:"+subDomain);
        System.out.println("parentUrl:"+parentUrl);
        System.out.println("anchor:"+anchor);
 
         if (page.getParseData() instanceof HtmlParseData) {  // 判断是否是html数据
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); // 强制类型转换，获取html数据对象
             String text = htmlParseData.getText();  // 获取页面纯文本（无html标签）
             String html = htmlParseData.getHtml();  // 获取页面Html
             Set<WebURL> links = htmlParseData.getOutgoingUrls();  // 获取页面输出链接
                         
             System.out.println("纯文本长度: " + text.length());
             System.out.println("html长度: " + html.length());
             System.out.println("输出链接个数: " + links.size());
         }
	}
}
