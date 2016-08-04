package com.github.hanlyjiang.androidtemplate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;

/**
 * 使用Echart Java库生成数据
 */
public class EChartLocalInterfaceActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart);

        mWebView = (WebView) findViewById(R.id.webView);


        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(true);

        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");


        mWebView.loadUrl("file:///android_asset/localhtml/doc/example/line5_1.html");

    }


    class WebAppInterface {
        Context mContext;

        public WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * 获取
         *
         * @return
         */
        @JavascriptInterface
        public String getLineChartOptions() {
            GsonOption option = makeChartJsonOption();
            LogUtils.d(option.toString());
            return option.toString();
        }

        @JavascriptInterface
        public void showToast(String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }

        /**
         * PieChart
         *
         * @return
         */
        @JavascriptInterface
        public String getPieChartOptions() {
            //地址：http://echarts.baidu.com/doc/example/pie7.html
//            Legend legend = new Legend();
//            legend.orient(Orient.vertical).x(X.left).data("直接访问","邮件营销","联盟广告","视频广告","搜索引擎");
//            option.legend(legend);
//
//            Title title = new Title().text("某站点用户访问来源").subtext("纯属虚构").x(X.center);
//            option.title(title);
//
//            Tooltip tooltip = new Tooltip();
//            tooltip.trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
//            option.setTooltip(tooltip);
//
//            MagicType magicType = new MagicType(Magic.pie,Magic.funnel);
//            MagicType.Option op = new MagicType.Option();
//            Funnel funnel = new Funnel();
//            funnel.setxAxisIndex(25);
//            funnel.setWidth(50);
//            funnel.setFunnelAlign(X.left);
//            funnel.max(1548);
//            op.setFunnel(funnel);
//            magicType.setOption(op);
//
//            Toolbox toolbox = new Toolbox();
//            toolbox.show(true).feature(Feature.mark.show(true),
//                    Feature.dataView.show(true).readOnly(false),
//                    magicType, new Restore().show(true), new SaveAsImage().show(true));
//            option.setToolbox(toolbox);
//
//            option.setCalculable(true);
//
////            ValueAxis valueAxis = new ValueAxis();
////            valueAxis.axisLabel().formatter("{value} ({value}%)");
////            valueAxis.type(AxisType.value);
//            Pie p1 = new Pie("1");
//            p1.item
            GsonOption option = new GsonOption();

            option.timeline().data("2013-01-01", "2013-02-01", "2013-03-01", "2013-04-01", "2013-05-01",
                    new LineData("2013-06-01", "emptyStart6", 8), "2013-07-01", "2013-08-01", "2013-09-01", "2013-10-01",
                    "2013-11-01", new LineData("2013-12-01", "star6", 8));
            option.timeline().autoPlay(true).label().formatter("function(s){return s.slice(0,7);}");
            //timeline变态的地方在于多个Option
            Option basic = new Option();
            basic.title().text("浏览器占比变化").subtext("纯属虚构");
            basic.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
            basic.legend().data("Chrome", "Firefox", "Safari", "IE9+", "IE8-");
            basic.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage, new MagicType(Magic.pie, Magic.funnel)
                    .option(new MagicType.Option().funnel(
                            new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548))));

            int idx = 1;
            basic.series(getPie(idx++).center("50%", "45%").radius("50%"));
            //加入
            option.options(basic);
            //构造11个数据
            Option[] os = new Option[11];
            for (int i = 0; i < os.length; i++) {
                os[i] = new Option().series(getPie(idx++));
            }
//            option.options(os);
//            option.exportToHtml("pie7.html");
//            option.view();
            LogUtils.d(option.toString());
            return option.toString();
        }


        /**
         * 获取饼图数据
         *
         * @param idx
         * @return
         */
        private Pie getPie(int idx) {
            return new Pie().name("浏览器（数据纯属虚构）").data(
                    new PieData("Chrome", idx * 128 + 80),
                    new PieData("Firefox", idx * 64 + 160),
                    new PieData("Safari", idx * 32 + 320),
                    new PieData("IE9+", idx * 16 + 640),
                    new PieData("IE8-", idx * 8 + 1280));
        }

        @JavascriptInterface
        public GsonOption makeChartJsonOption() {
            GsonOption option = new GsonOption();
            option.legend("高度(km)与气温(°C)变化关系");

            option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

            option.calculable(true);
            option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

            ValueAxis valueAxis = new ValueAxis();
            valueAxis.axisLabel().formatter("{value} °C");
            option.xAxis(valueAxis);

            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.axisLine().onZero(false);
            categoryAxis.axisLabel().formatter("{value} km");
            categoryAxis.boundaryGap(false);
            categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
            option.yAxis(categoryAxis);

            Line line = new Line();
            line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
            option.series(line);
            return option;
        }
    }
}
