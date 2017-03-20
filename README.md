---
title: Android 上使用WebView 和Echart 展示图表数据
date: 2016-08-07 14:19:13
tags: 
- Android
- EChart
- JS
- Webview

category:
- Android
---


#Demo-AndroidEChartWebJs
__Android 上 使用WebView + HTML + Echart 实现数据从Android 端取，展示使用HTML__。 [点击在我的简书中查看文章](http://www.jianshu.com/p/7b226e0e5f12)





之前在项目中使用过MPAndroidChart 显示过数据图表，现在需要使用HTML + Echart 来显示，写了一个Demo,DEMO中实现了线图，饼图，还有柱状图。
    
示例代码地址:[点击跳转到github](https://github.com/hanlyjiang/Demo-AndroidEChartWebJs "示例代码")

## 使用到的开源项目 
* [EChart 库 -js](http://echarts.baidu.com/)
	- 版本：2.2.7
* [EChart java 对象库](http://git.oschina.net/free/ECharts) 这是一个针对ECharts3.x(2.x)版本的Java类库，实现了所有ECharts中的Json结构对应的Java对象，并且可以很方便的创建Option。这个库引用了Gson，所以build.gradle 中也要加入GSON 的依赖。
	- 版本 2.2.7 


## JS 和 html 编写
为方便测试，html页面和js代码放置于项目的assets 目录中，Android代码访问路径为：file:///android_asset/jsWeb/echart.html

### html 页面编写
1. 在head中引入 js 脚本，主要代码如下：([点击查看全部代码：_echart.html_](https://github.com/hanlyjiang/Demo-AndroidEChartWebJs/blob/master/AppTemplate/src/main/assets/jsWeb/echart.html))
```
    <head>
    <!-- 导入script -->
    <script src="http://echarts.baidu.com/echarts2/doc/example/www/js/echarts.js"></script>
    <script src="./android.js"></script>

    </head>
```
2. body中请求需要的EChart脚本，并添加对应控件,主要代码如下: ([ 点击查看全部代码：_echart.html_](https://github.com/hanlyjiang/Demo-AndroidEChartWebJs/blob/master/AppTemplate/src/main/assets/jsWeb/echart.html)) 。   我在其中添加了一个js中指定数据的EChart 表格用于测试   

```
        <div id="main" style="height:400px"></div>
        <script type="text/javascript">
                // 路径配置
                require.config({
                  paths: {
                    echarts: 'http://echarts.baidu.com/echarts2/doc/example/www/js'
                    //echarts: './www/js'
                  }
                });
                // 使用
                require(
                  [
                    'echarts',
                    'echarts/chart/line', // 使用柱状图就加载line模块，按需加载
                    'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                    'echarts/chart/pie', // 使用柱状图就加载pie模块，按需加载
                  ],
                  function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById('main'));
                    //设置数据
                    var option = {
                    // .... 数据部分
                };
                    // 为echarts对象加载数据
                    myChart.setOption(option);
                  }
                )
        </script>

        <hr/>
        <p>
        <b>从Android 代码中获取数据绘制图表</b><br/>
        <button onClick="loadALineChart()" style="height:40px">线状图</button>
        <button onClick="loadAChart(2)" style="height:40px">饼状图</button>
        <button onClick="loadAChart(1)" style="height:40px">柱状图</button>

        <p id="textcontent">没有内容</p>
        <div id="chart2" style="height:400px"/>
        </p>
```


### JS 脚本
这里有一点要注意：Android WebView 提供的接口返回String 类型的json option 字符串，而JS 端接受到字符串后需要用JSON.parse 方法转化成js的Object 对象，只有转换成对象之后才可以传入到Echart的setOption 方法中显示出来。    
主要代码如下，[点击查看全部代码](https://github.com/hanlyjiang/Demo-AndroidEChartWebJs/blob/master/AppTemplate/src/main/assets/jsWeb/android.js)

        function toast(msg){
        Android.showToast(msg);
        }

        function loadALineChart(){
            // 必须加JOSN.parse 转换数据类型
            var option = JSON.parse(Android.getLineChartOptions());

            var chart2Doc = document.getElementById('chart2');
            var myChart2 = require('echarts').init(chart2Doc);

            myChart2.setOption(option);
            document.getElementById('textcontent').innerHTML=option;
            toast(option);
        }

        /**
        type :  1 - 饼状图
                2 - 柱状图
        */
        function loadAChart(type){
            // 必须用JSON.parse() 转换一下，才可以显示，否则数据类型会不对
            var option = JSON.parse(Android.getPieChartOptions(type));
            var chart2Doc = document.getElementById('chart2');
            var myChart2 = require('echarts').init(chart2Doc);

            myChart2.setOption(option);
            document.getElementById('textcontent').innerHTML=option;
    	}

## Android 代码编写
### 1. WebAppInterface 定义和实现
[点击查看全部文件内容][java_code]

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
    

### 2. WebView 上启用JavaScript 并注入Java对象：
[点击查看全部文件内容][java_code]

     webSettings.setJavaScriptEnabled(true);
     mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
     

[java_code]: https://github.com/hanlyjiang/Demo-AndroidEChartWebJs/blob/master/AppTemplate/src/main/java/com/github/hanlyjiang/androidtemplate/EChartLocalInterfaceActivity.java
