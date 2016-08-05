
function toast(msg){
    Android.showToast(msg);
}

function onPageLoad(){
    toast('页面加血中...');
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

function refreshChartUseStaticData(){
    var option = makeStaticOptions();
    if( typeof option === 'JSON'){//  typeof https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Operators/typeof
        toast('数据为Json 类型');
    }else{
        toast('数据不是Json 类型');
    }
    // 从Android 代码中获取Json格式字符串
    var chart2Doc = document.getElementById('chart2');
    var myChart2 = require('echarts').init(chart2Doc);

    myChart2.setOption(option);
    document.getElementById('textcontent').innerHTML=option;
    toast(option);

}


function makeStaticOptions(){

    var option = {
    "calculable": true,
    "toolbox": {
        "show": true,
        "feature": {
            "restore": {
                "show": true,
                "title": "还原"
            },
            "magicType": {
                "show": true,
                "title": {
                    "line": "折线图切换",
                    "stack": "堆积",
                    "bar": "柱形图切换",
                    "tiled": "平铺"
                },
                "type": ["line", "bar"]
            },
            "dataView": {
                "show": true,
                "title": "数据视图",
                "readOnly": false,
                "lang": ["Data View", "close", "refresh"]
            },
            "mark": {
                "show": true,
                "title": {
                    "mark": "辅助线开关",
                    "markClear": "清空辅助线",
                    "markUndo": "删除辅助线"
                },
                "lineStyle": {
                    "color": "#1e90ff",
                    "type": "dashed",
                    "width": 2
                }
            },
                "saveAsImage": {
                    "show": true,
                    "title": "保存为图片",
                    "type": "png",
                    "lang": ["点击保存"]
                }
            }
        },
        "tooltip": {
            "trigger": "axis",
            "formatter": "Temperature : \u003cbr/\u003e{b}km : {c}°C"
        },
        "legend": {
            "data": ["高度(km)与气温(°C)变化关系"]
        },
        "xAxis": [{
            "type": "value",
            "axisLabel": {
                "formatter": "{value} °C"
            }
        }],
        "yAxis": [{
            "type": "category",
            "boundaryGap": false,
            "axisLine": {
                "onZero": false
            },
            "axisLabel": {
                "formatter": "{value} km"
            },
            "data": [
                0, 10, 20, 30, 40, 50, 60, 70, 80
            ]
        }],
        "series": [{
            "smooth": true,
            "name": "高度(km)与气温(°C)变化关系",
            "type": "line",
            "itemStyle": {
                "normal": {
                    "lineStyle": {
                        "shadowColor": "rgba(0,0,0,0.4)"
                    }
                },
                "emphasis": {}
            },
            "data": [
                15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5
            ]
        }]
    };
    // 下面是使用Java 生成的json 数据 ，直接返回
    var op2 = {"calculable": true,"toolbox": {"feature": {"mark": {"show": true,"title": {"markUndo": "删除辅助线","markClear": "清空辅助线","mark": "辅助线开关"},"lineStyle": {"color": "#1e90ff","type": "dashed","width": 2}},"dataView": {"show": true,"title": "数据视图","readOnly": false,"lang": ["数据视图","关闭","刷新"]},"magicType": {"show": true,"title": {"bar": "柱形图切换","stack": "堆积","tiled": "平铺","line": "折线图切换"},"type": ["line","bar"]},"restore": {"show": true,"title": "还原"},"saveAsImage": {"show": true,"title": "保存为图片","type": "png","lang": ["点击保存"]}},"show": true},"tooltip": {"trigger": "axis","formatter": "Temperature : <br/>{b}km : {c}°C"},"legend": {"data": ["高度(km)与气温(°C)变化关系"]},"xAxis": [{"type": "value","axisLabel": {"formatter": "{value} °C"}}],"yAxis": [{"boundaryGap": false,"type": "category","axisLine": {"onZero": false},"axisLabel": {"formatter": "{value} km"},"data": [0,10,20,30,40,50,60,70,80]}],"series": [{"smooth": true,"name": "高度(km)与气温(°C)变化关系","type": "line","itemStyle": {"normal": {"lineStyle": {"shadowColor": "rgba(0,0,0,0.4)"}}},"data": [15,-50,-56.5,-46.5,-22.1,-2.5,-27.7,-55.7,-76.5]}]};
    return op2;
}

