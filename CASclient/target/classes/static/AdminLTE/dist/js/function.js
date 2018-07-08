function init() {
    globe.load();
    globe.jumpByPos(117.234124,37.232342,0.00,30,30,30);
    //globe.goToSurfaceMode();
}

//添加地图
function addMap() {
    globe.goToSurfaceMode();

    if (DEMID > 0) {
        //移除场景中数据
        globe.removeAllDoc();
    }
    
    sceneID = globe.addDoc(docName, IP, port, DocType.TypeG3D);
    if (sceneID < 0) {
        alert("加载失败！");
        return;
    }
    globe.reset();

    //获取被切割图层的Range3D
    range3Dstr = globe.getSceneProperty(sceneID, 0, "Range3D");//只能获取模型图层的空间范围
}

//二三维联动
function link2D() {
       //设置显示界面样式
       document.getElementById("mapCon").style.display ="block";
       document.getElementById("ocx").style.width = "50%"; 
        //初始化地图容器
        map = new OpenLayers.Map('mapCon', {
        numZoomLevels: 10,
        maxExtent: [-100, -100, 200, 200],
        maxResolution: 1.2,
        controls: [new OpenLayers.Control.Navigation(), new OpenLayers.Control.PanZoomBar()]
        });
        //注册鼠标左键释放事件
        globe.addEventListener(EventType.LButtonUp, linkTwoMap);

       //初始化地图文档图层对象
       var layer = new Zondy.Map.Doc("MapGIS IGS MapDocLayer", "二维景观平面", {
       ip: "127.0.0.1", port: "6163"
  });

    //将地图文档图层加载到地图中
  map.addLayers([layer]);
    //设置地图的初始化显示中心和级别
  map.setCenter(new OpenLayers.LonLat(0, 0), 2);
    //添加地图鼠标事件
  map.events.register('mouseup', map, function (evt) {
      //给矢量图层添加点击事件
      linkThreeGlobe();
  });
}

function linkTwoMap() {
    var curMapBound = globe.getViewRect();
    var num = curMapBound.toString().split(",");
    var x1 = parseFloat(num[0].split(":")[1]);
    var y1 = parseFloat(num[1].split(":")[1]);
    var x2 = parseFloat(num[2].split(":")[1]);
    var y2 = parseFloat(num[3].split(":")[1]);
    var x = (x1 + x2) / 2.0;
    var y = (y1 + y2) / 2.0;
    var bound = new OpenLayers.Bounds(x1, y1, x2, y2); 
    map.zoomToExtent(bound);
}

function linkThreeGlobe() {
    var size = map.getExtent();
    globe.setViewRect(size.left, size.bottom, size.right, size.top);
}

function stoplink2D() {
    globe.removeEventListener(EventType.LButtonUp, linkTwoMap)
    document.getElementById("mapCon").style.display = "none";
    document.getElementById("ocx").style.width = "100%";
    globe.reset();
}


function PickModelReady() {
    //移除鼠标事件
    globe.removeEventListener(EventType.LButtonDblClk, DataQuery);
    //注册鼠标事件
    globe.addEventListener(EventType.LButtonDblClk, DataQuery);
}

function DataQuery(flag, x, y, dx, dy, dz) {
    var queryParam = new G3DDocQuery();
    //要素结果集每页的记录数量
    queryParam.pageCount = '1000';
    //被查询图层的gdbpUrl
    queryParam.gdbp = encodeURI(QueryURL);
    //设置查询条件
    queryParam.geometryType = 'Point3D';
    //点的坐标
    queryParam.geometry = dx + "," + dy + "," + dz + ",0.1";  
    //指定查询结果的结构
    queryParam.structs = '{"IncludeAttribute":true,"IncludeGeometry":false,"IncludeWebGraphic":false}';
    // igs服务ip
    queryParam.serverIp =IP;
    //igs服务端口
    queryParam.serverPort = "6163";
    if (globe != null) {
        //三维要素查询
        globe.queryG3DFeature(queryParam, queryPolygonsCallback_modle, null, 'post');
    }
}

//显示属性信息
function queryPolygonsCallback_modle(data) {
    if (data.TotalCount > 0) {
        var objid = data.SFEleArray[0].FID;
        var info = "LayerIndex:1,ObjID:"+objid+",SddHandle:" + sceneID;
        globe.startModelDiplay(info,2,true);
        var att = data.AttStruct.FldName[0];
        var vaule= data.SFEleArray[0].AttValue[0];
        alert(att +"："+ vaule);
    }
}

var polygonProName;
function queryPolygonsCallback_old(data) {
    if (data.TotalCount > 0) {
        if (polygonProName != "" || polygonProName != null) {
            globe.analysis("Delete", 3, polygonProName);
            var polygons = new Array();
            for (var datalength = 0; datalength < data.TotalCount; datalength++) {
                var polygon = { "type": "polygon", "nelen": 1, "ne": [5], "dots": [] };
                var dotArray = [];
                var obj1 = Object();
                obj1.x = data.SFEleArray[datalength].bound.xmin;
                obj1.y = data.SFEleArray[datalength].bound.ymin;
                dotArray.push(obj1);
                var obj2 = Object();
                obj2.x = data.SFEleArray[datalength].bound.xmax;
                obj2.y = data.SFEleArray[datalength].bound.ymin;
                dotArray.push(obj2);
                var obj3 = Object();
                obj3.x = data.SFEleArray[datalength].bound.xmax;
                obj3.y = data.SFEleArray[datalength].bound.ymax;
                dotArray.push(obj3);
                var obj4 = Object();
                obj4.x = data.SFEleArray[datalength].bound.xmin;
                obj4.y = data.SFEleArray[datalength].bound.ymax;
                dotArray.push(obj4);
                var obj5 = Object();
                obj5.x = data.SFEleArray[datalength].bound.xmin;
                obj5.y = data.SFEleArray[datalength].bound.ymin;
                dotArray.push(obj5);
                polygon.dots = dotArray;
                polygons.push(polygon);
            }
            var param = { "maxsencez": 20, "transparence": 50, "layerindex": [0], "sddentity": scenceID.toString(), "reg": polygons };
            var jsoninfo = JSON.stringify(param);
            polygonProName = globe.analysis("Add", 3, jsoninfo);
            var deletediv = document.getElementById("Popup_bar");
            if (deletediv != null || deletediv != undefined) {
                deletediv.parentNode.removeChild(deletediv);
            }
            var datainfo = "<iframe name='Popup' id='Popup_bar' src='Addhtml/Popup.htm' frameborder='0' allowtransparency='true' scrolling='no'style='left:5%; top:30%;'></iframe>"
            $('#SceneControl').append(datainfo);
            var iframeDiv = top.document.getElementById("Popup_bar");
            iframeDiv.onreadystatechange = function () {
                if (iframeDiv.readyState == "complete") {
                    var iframeBody = iframeDiv.contentWindow.document.body;
                    var IFdiv = "<p>楼层：<input id='test1'type='text' value='" + data.SFEleArray[0].AttValue[3] + "'></p>" +
                    "<img id='closeX' src='../image/unchoose2k.png'onclick='parent.closeIFrame()' />" +
                    "<p>层高：<input type='text' value='" + data.SFEleArray[0].AttValue[4] + "'></p>";
                    var frameDoc = iframeDiv.contentWindow.document;
                    var tagdiv = frameDoc.getElementById('Popupdiv');
                    $(tagdiv).append(IFdiv);
                }
            }
        }
    }
}

//单体化显示查询模型要素
var geomID;
var object;
var infoJson;
function queryPolygonsCallback(data) {
    if (geomID != null)
    {
        globe.removeDocById(geomID);
    }
    if (data.TotalCount > 0) {
   infoJson = "{ \"ang\" : 0.0, \"endclr\" : 16256, \"fillclr\" : 25600," +
   "\"fillmode\" : 0, \"fullpatflg\" : 0, \"libID\" : 1, \"outpenw\" : 0.0, \"ovprnt\" : 3, " +
   "\"patID\" : -1, \"patclr\" : 0, \"pathei\" : 0.0, \"patwid\" : 0.0,  \"type\" : \"reginfo\"}";

        for (var datalength = 0; datalength < data.TotalCount; datalength++) {
            var geom = data.SFEleArray[datalength].fGeom;
            if (geom.SurfaceGeom || geom.EntityGeom) {
                var surFeature2obj = function (surfeature) {
                    var surface = new G3DSurfaceObject();
                    surface.pntcount = surfeature.points.length;
                    surface.dots = surfeature.points;
                    surface.trianglecount = surfeature.triangles.length / 3;
                    surface.triangles = surfeature.triangles;
                    surface.texturelayernum = surface.texturelayerind = 0;
                    surface.colors = surfeature.colors;
                    surface.texturepos = null;
                    surface.topo = surfeature.topos;
                    surface.normals = surfeature.normalVectors;
                    return surface;
                }
                object = new G3DGeometryObject();
                object.items = [];
                if (geom.SurfaceGeom) {
                    object.type = geom.SurfaceGeom.length === 1 ? "anysurface" : "multisurface";
                    for (var i = 0; i < geom.SurfaceGeom.length; i++) {
                        object.items.push(surFeature2obj(geom.SurfaceGeom[i]));
                    }
                    object.count = object.items.length;
                } else if (geom.EntityGeom) {
                    object.type = "multisurface";
                    for (var i = 0; i < geom.EntityGeom.length; i++) {
                        var entity = geom.EntityGeom[i];
                        for (var j = 0; j < entity.surfaces.length; j++) {
                            object.items.push(surFeature2obj(entity.surfaces[j]));
                        }
                    }
                    object.count = object.items.length;
                }
                geomID = globe.appendGeom(new Util().toJSON(object), infoJson);
            }
        }
    }
}

function stopPickModelReady(){
    globe.removeEventListener(EventType.LButtonDblClk, DataQuery);
    globe.stopModelDisplayAll();
}


function addDEM() {
    if (sceneID > 0) {
        globe.removeAllDoc();
    }  
    globe.goToSurfaceMode();
    DEMID = globe.addDoc("地形数据", IP, port, DocType.TypeG3D);
    if (DEMID < 0) {
        alert("加载失败！");
        return;
    }
    globe.reset();
}

//面积测量
function measureArea() {
    stopMeasure();
    //实例化一个分析对象
    var info = new MeasureInfo();
    //工具的启动方式:1-设置参数
    info.type = 1;
    //如果是面积量测,0=圆形面积,1=多边形面积
    info.measuretype = 1;
    //测量面积
    info.tooltype = "areameasure";
    info.color = 0x0ff671;
    globe.startAnalyzeTool(info);
}


function measureLength() {
    stopMeasure();
    //实例化一个分析对象
    var info = new MeasureInfo();

    //工具的启动方式:1-设置参数
    info.type = 1;
    //如果是面积量测,0=圆形面积,1=多边形面积
    info.measuretype = 1;
    //测量周长
    info.tooltype = "lengthmeasure";
    info.color = 0x0ff671;
    globe.startAnalyzeTool(info);
}

function stopMeasure() {
    globe.setAnalyseType(AnalyseType.Null);
    globe.setAnalyseInfo(null);
    globe.stopAnalyzeTool(EnumCommToolType.Measure);
}

//地形剖切
function terSectAnalysis() {
    stopAnalysis();
    //实例化一个分析对象
    var info = new TerSectInfo();
    info.type = 0;
    info.cutlinclr = 0xFFFFFF; //切割线颜色
    info.linwidth = 10; //线宽,大于0有效
    info.showsection = 1; //是否显示剖面
    info.state = 0; //剖面图模式：0=依线段长度采样高程点，1=平均采样高程点
    info.pntnum = 150; //采样点数
    info.graphclr = 0xFFFFFF; //剖面图颜色
    info.graphlinclr = 0xFFFFFF; //剖面线颜色
    globe.startAnalyzeTool(info);
}

//洪水淹没分析
function flood() {
    stopAnalysis();
    //实例化一个分析对象
    var info = new FLoodAnalyzeInfo();
    //当前进行的分析类别,FLoodAnalyze为洪水淹没分析
    info.type = AnalyseType.FLoodAnalyze;
    //透明度(0-1.0之间有效)
    info.alpha = 1;
    //淹没区域颜色
    info.floodclr = 0xFF0000;
    //淹没区域扩大倍数
    info.regzoom = 1;
    //数据模式控制，交互拾取和用户输入两种。 0：交互拾取模式； 1: 用户输入模式。
    info.datatype = 1;
    //当前高程
    info.height = 240;
    //观察点
    info.observepos.x = 566407.25;
    info.observepos.y = 4753745;
    info.observepos.z = 464.12;

    //起始点x、y、z坐标
    info.startpos.x = 565612.19;
    info.startpos.y = 4751662;
    info.startpos.z = 136.74;
    //终止点、y、z坐标
    info.endpos.x = 572949.88;
    info.endpos.y = 4754249;
    info.endpos.z = 390.29;
    //设置当前进行的分析类别
    globe.setAnalyseType(AnalyseType.FLoodAnalyze);
    //开启工具接口
    globe.startAnalyzeTool(info);
}

function stopAnalysis() {
    //设置当前进行的分析类别为空
    globe.setAnalyseType(AnalyseType.Null);
    //设置当前分析得到的结果为空
    globe.setAnalyseInfo(null);
    //停止工具接口
    globe.stopAnalyzeTool(EnumCommToolType.TerrainAnalyze);
    globe.stopAnalyzeTool(EnumCommToolType.TerrainCut);
}

function test() {
    //globe.goToSurfaceMode();
    var t = globe.appendGeomByUrl("gdbp://MapGisLocal/0525/sfcls/jianzhu1", "0", "192.168.0.12", "6163", "8");
    globe.reset();
    alert(t);
}

function cutfillanalysis() {
    stopAnalysis();
    var info = new CutFillInfo();
    //当前进行的分析类别,CutFill为填挖方分析
    info.type = AnalyseType.CutFill;
    //采用用户输入模式
    info.datatype = 1;
    //当前高程
    info.height = 130;
    //起始点x、y、z坐标
    info.startpos.x = 565612.19;
    info.startpos.y = 4751662;
    info.startpos.z = 136.74;
    //终止点、y、z坐标
    info.endpos.x = 572949.88;
    info.endpos.y = 4754249;
    info.endpos.z = 390.29;
    //挖的颜色
    info.cutclr = 0xFF0000;
    //填的颜色
    info.fillclr = 0xFFFF00;
    //不填不挖的颜色
    info.nocutfillClr = 0xFF00FF;

    //设置当前进行的分析类别
    globe.setAnalyseType(AnalyseType.CutFill);
    //开启工具接口
    globe.startAnalyzeTool(info);
}

//爆炸分析
function startBoomAnalysis() {
    //实例化一个分析对象
    var info = new BombInfo();
    info.type = 1;
    info.bombtype = 2;      //爆炸方式,0=任意爆炸;1=整体爆炸;2=沿轴向爆炸;
    info.axistype = 3;      //轴向，1=x轴;2=y轴;3=z轴;-1=x轴反向;-2=y轴反向;-3=z轴反向;
    info.expdis = 3000.0; 	//爆炸距离
    info.frame = 30;        //爆炸帧数
    info.radioscale = 0.75; //爆炸范围比例 
    info.bomrange = 0; //是否是整个场景爆炸， 1爆炸当前活动图层,0爆炸整个场景
    globe.startAnalyzeTool(info);
}

function stopBoomAnalysis() {
    globe.setAnalyseType(AnalyseType.Null);
    globe.setAnalyseInfo(null);
    globe.stopAnalyzeTool(EnumCommToolType.BombShow);
}

// //任意平面切割
// function ABSurface() {
//     //移除切割面
//     removeCut();
//     //三维切割辅助面
//     helpGeometry = globe.createABSurface(range3Dstr, 0, 45);
//     alert(helpGeometry);
// }

//执行任意面切割切割
function ABSurfaceexeCut() {
    var orgSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
    var leftSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_AA";
    var rightSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_BB";

    var alphaValue = 0;
    var beltaValue = 45;
    var scaleValue = "2:2:2";
    globe.exeCutByABSurface(orgSFClsStr, leftSFClsStr, rightSFClsStr, alphaValue, beltaValue, scaleValue, successCallback, errorCallback, "127.0.0.1", 6163);
}

function successCallback() {
    removeCut();
    alert("成功！");
}

function errorCallback() {
    removeCut();
    alert("失败！");
}

//清除结果
function removeCut() {
    if (helpGeometry) {
        globe.remove(helpGeometry);
    }
}

//隧道体切割
function createpipe() {
    //移除切割面
    removeCut();

    var jsonpnts = { "PntArray": [] };
    var dotarr = [];
    var obj1 = new Object();
    obj1.x = 485710.625;
    obj1.y = 4280000;
    obj1.z = -700;

    dotarr.push(obj1);
    var obj2 = new Object();
    obj2.x = 486705.75;
    obj2.y = 4282905;
    obj2.z = -700;

    dotarr.push(obj2);
    var obj3 = new Object();
    obj3.x = 486678.125;
    obj3.y = 4286303.5;
    obj3.z = -700;

    dotarr.push(obj3);
    var obj4 = new Object();
    obj4.x = 487085.21875;
    obj4.y = 4289067;
    obj4.z = -700;

    dotarr.push(obj4);
    var obj5 = new Object();
    obj5.x = 486678.125;
    obj5.y = 4286303.5;
    obj5.z = -700;

    dotarr.push(obj5);
    var obj6 = new Object();
    obj6.x = 487085.21875;
    obj6.y = 4289067;
    obj6.z = -700;
    dotarr.push(obj6);

    jsonpnts.pntarray = dotarr;
    var pnts = JSON.stringify(jsonpnts);
    var radius = 498.8;//半径
    var secNum = 36;//顶分段数
    var depth = -700;//深度
    var length = 20; // 类型为拱形时有效，表示多边形的长
    var height = 5; //类型为拱形时有效，表示多边形的高
    //三维切割辅助面
    var xmin = $.parseJSON(range3Dstr).range3d.xmin;
    var ymin = $.parseJSON(range3Dstr).range3d.ymin;
    var zmin = $.parseJSON(range3Dstr).range3d.zmin;
    var xmax = $.parseJSON(range3Dstr).range3d.xmax;
    var ymax = $.parseJSON(range3Dstr).range3d.ymax;
    var zmax = $.parseJSON(range3Dstr).range3d.zmax;

    helpGeometry = globe.createPipe(range3Dstr, 'circle', pnts, radius, secNum, depth, length, height);

    alert(helpGeometry);
}

//隧道体切割
function createpipeexeCut() {
    var orgSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
    var leftSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_a";
    var rightSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_b";
    var pnts = "485710.625,4280000;486705.75,4282905;486678.125,4286303.5;487085.21875,4289067;486678.125,4286303.5;487085.21875,4289067"; //切割路线
    var type = "circle";
    var radius = 498.8; //半径
    var number = 36;//顶分段数
    var depth = -700; //深度
    var length = 20; // 类型为拱形时有效，表示多边形的长
    var height = 5; //类型为拱形时有效，表示多边形的高
    globe.exeCutByPipe(orgSFClsStr, leftSFClsStr, rightSFClsStr, pnts, type, radius, number, depth, length, height, successCallback, errorCallback, "localhost", 6163);
}

function addcutModel() {
    if (sceneID > 0 || DEMID) {
        globe.removeAllDoc();
    }
    globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/b1", 0, IP, port)
    globe.reset();
}

function PickModel() {
    //移除鼠标事件
    globe.removeEventListener(EventType.LButtonDblClk, modelQuery);
    //注册鼠标事件
    globe.addEventListener(EventType.LButtonDblClk, modelQuery);
}

function modelQuery(flag, x, y, dx, dy, dz) {
    var queryParam = new G3DDocQuery();
    //要素结果集每页的记录数量
    queryParam.pageCount = '1000';
    //被查询图层的gdbpUrl
    queryParam.gdbp = encodeURI("gdbp://MapGisLocal/sample/sfcls/查询体");
    //设置查询条件
    queryParam.geometryType = 'Point3D';
    //点的坐标
    queryParam.geometry = dx + "," + dy + "," + dz + ",0.1";
    //指定查询结果的结构
    queryParam.structs = '{"IncludeAttribute":true,"IncludeGeometry":false,"IncludeWebGraphic":false}';
    // igs服务ip
    queryParam.serverIp =IP;
    //igs服务端口
    queryParam.serverPort = "6163";
    if (globe != null) {
        //三维要素查询
        globe.queryG3DFeature(queryParam, queryPolygonsCallback_M, null, 'post');
    }
}

function queryPolygonsCallback_M(data){
    if (data.TotalCount > 0) {
        alert("查询成功！");
    }
}