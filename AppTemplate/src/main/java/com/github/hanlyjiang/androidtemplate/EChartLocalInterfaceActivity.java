package com.github.hanlyjiang.androidtemplate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;

/**
 * 使用Echart Java库生成数据
 */
public class EChartLocalInterfaceActivity extends AppCompatActivity {

    private WebView mWebView;

    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart);

        mWebView = (WebView) findViewById(R.id.webView);

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在查询...");

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(true);

        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        mWebView.loadUrl("file:///android_asset/jsWeb/echart.html");
    }


    private void showDialog(){
        dialog.show();
    }

    private void dismissDialog(){
        dialog.dismiss();
    }

    /**
     * 注入到JS里的对象接口
     */
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
            GsonOption option = markLineChartOptions();
            LogUtils.d(option.toString());
            return option.toString();
        }

        @JavascriptInterface
        public void showToast(String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }

        /**
         * PieChart 或者柱状图数据示例
         * @param type 1 - 饼状图数据； 2 - 柱状图数据
         * @return
         */
        @JavascriptInterface
        public String getPieChartOptions(int type) {

            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    showDialog();
                }
            });
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //地址：http://echarts.baidu.com/doc/example/pie7.html
            GsonOption option = new GsonOption();
            // 设置标题
            option.title(new Title().text("某站点用户访问来源").subtext("纯属虚构").x(X.center));
            if(type == 2) {
                option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
            }else  if(type == 1){
                option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
            }
            // 设置图例
            option.legend().data("直接访问", "邮件营销", "联盟广告", "视频广告", "搜索引擎").orient(Orient.vertical).x(X.left);

            // 是否可以拖动以计算
            option.calculable(true);

            if(type == 2){
                // 构造数据
                Pie  pie1 = new Pie("访问来源");
                pie1.type(SeriesType.pie).radius("45%").center("50%","60%");
                pie1.data(new Data("直接访问",335),
                        new Data("邮件营销",310),
                        new Data("联盟广告",234),
                        new Data("视频广告",135),
                        new Data("搜索引擎",1548)
                );
                option.series(pie1);
            }else  if(type ==1) {
                // 构建柱状数据
                option.xAxis(new CategoryAxis().name("访问来源").data("视频广告","搜索引擎","联盟广告","邮件营销","直接访问"));
                option.yAxis(new ValueAxis());

                Bar bar = new Bar("访问来源");
                bar.data(11,4,14,45,67,88);
                option.series(bar);
            }
            Log.d("TAG",option.toString());
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            });
            return option.toString();
        }


        @JavascriptInterface
        public GsonOption markLineChartOptions() {
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
