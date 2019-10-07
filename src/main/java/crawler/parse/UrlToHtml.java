package crawler.parse;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.logging.Level;

public class UrlToHtml {
    private String url;

    public UrlToHtml(String url) {
        this.url = url;
    }


    public String parse(){
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
        webClient.getOptions().setTimeout(5000);
        try {
            HtmlPage Page = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(5000);
            return Page.asXml();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            webClient.close();
        }
    }
}
