package crawler.view;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.logging.Level;


/**
 * 获取Html页面.
 */
public class UrlToHtml {
    private String url;

    /**
     * 传入需要解析链接.
     */
    public UrlToHtml(String url) {
        this.url = url;
    }


    /**
     * 解析链接.
     * @return 返回解析的字符串结果
     */
    public String parse() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//屏蔽日志信息
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
//支持JavaScript
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(10000);
        try {
            HtmlPage page = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(10000);
            return page.asXml();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            webClient.close();
        }
    }
}
