<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<f:link href="formEdit.css"></f:link>
		<f:link href="component.css"></f:link>
		<script type="text/javascript" src="${ctx}/js/echarts/echarts.min.js"></script>
	</head> 
	<body ng-controller="ctrl">
			
			<div id="report_one"  style="width: 950px;height:400px;" ></div>
			
		
	</body>
	<script>
		var app = angular.module('app', ['formDirective','arrayToolService']);
		app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
			
			var ids =   parent.$("#hiddenSelectIds").val();
			baseService.get(__ctx + "/bpmx/test/bpmTestCase/getReportData?ids="+ids).then(function(data){
				
				var reportData = {"xAxis":['A测试用例', 'B测试用例', 'C测试用例', 'D测试用例', 'E测试用例'],
						"end":[320, 332, 301, 334, 390],
						"unend":[220, 182, 191, 234, 290],
						"endProcess":[150, 232, 201, 154, 190]}
				if(data.value){
					reportData = data.value;
				}
				genChart(reportData);
			
			
			},function(){
				top.layer.alert("获取测试报告数据出错", {icon: 2}); 
			})
			
			function genChart(reportData){
				
				// 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById('report_one'));

		        // 指定图表的配置项和数据
		       var posList = [
				    'left', 'right', 'top', 'bottom',
				    'inside',
				    'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
				    'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
				];
				
				app.configParameters = {
				    rotate: {
				        min: -90,
				        max: 90
				    },
				    align: {
				        options: {
				            left: 'left',
				            center: 'center',
				            right: 'right'
				        }
				    },
				    verticalAlign: {
				        options: {
				            top: 'top',
				            middle: 'middle',
				            bottom: 'bottom'
				        }
				    },
				    position: {
				        options: echarts.util.reduce(posList, function (map, pos) {
				            map[pos] = pos;
				            return map;
				        }, {})
				    },
				    distance: {
				        min: 0,
				        max: 100
				    }
				};
				
				app.config = {
				    rotate: 90,
				    align: 'left',
				    verticalAlign: 'middle',
				    position: 'insideBottom',
				    distance: 15,
				    onChange: function () {
				        var labelOption = {
				            normal: {
				                rotate: app.config.rotate,
				                align: app.config.align,
				                verticalAlign: app.config.verticalAlign,
				                position: app.config.position,
				                distance: app.config.distance
				            }
				        };
				        myChart.setOption({
				            series: [{
				                label: labelOption
				            }, {
				                label: labelOption
				            }, {
				                label: labelOption
				            }, {
				                label: labelOption
				            }]
				        });
				    }
				};
				
				
				var labelOption = {
				    normal: {
				        show: true,
				        position: app.config.position,
				        distance: app.config.distance,
				        align: app.config.align,
				        verticalAlign: app.config.verticalAlign,
				        rotate: app.config.rotate,
				        formatter: '{c}  {name|{a}}',
				        fontSize: 16,
				        rich: {
				            name: {
				                textBorderColor: '#fff'
				            }
				        }
				    }
				};
				
				option = {
				    color: ['#003366', '#4cabce', '#e5323e'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    legend: {
				        data: ['end-已结束', 'unend-未结束', 'endProcess--人工结束']
				    },
				    toolbox: {
				        show: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        feature: {
				            mark: {show: true},
				            dataView: {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
				            restore: {show: true},
				            saveAsImage: {show: true}
				        }
				    },
				    calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            axisTick: {show: false},
				            data: reportData.xAxis
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: 'end-已结束',
				            type: 'bar',
				            barGap: 0,
				            label: labelOption,
				            data: reportData.end
				        },
				        {
				            name: 'unend-未结束',
				            type: 'bar',
				            label: labelOption,
				            data: reportData.unend
				        },
				        {
				            name: 'endProcess--人工结束',
				            type: 'bar',
				            label: labelOption,
				            data: reportData.endProcess
				        }
				    ]
				};

		        // 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
			}
	        
			
			
			
			
		}]);
	</script>
	
</html>