var globe = new Globe();
var docName = "地图文档";
var QueryURL = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
var IP = "172.24.145.113";
var port = "6163";
var sceneID;
var DEMID;
var map;
var helpGeometry;
var range3Dstr = "";
var globe = new Globe();
var zkmodelId, mxmodelId, labelId1, labelId2;
var openflag = false;
var mapId_img = 0;
var mapId_cia = 0;

//地球载入初始化
function init() {
    globe.load();

    mapId_img = globe.addTianditu("img");
    mapId_cia = globe.addTianditu("cia");

    //删除上一个标注
    if (labelId1 != "" || labelId2 != "") {
        globe.removeLabelByName(labelId1);
        globe.removeLabelByName(labelId2);
    }

    var strObj = new Bubble();
    strObj.text = "胶东山区";       //标注文本
    strObj.x = 122.177;                   //标注点X
    strObj.y = 37.33;                    //标注点Y
    strObj.z = 0;                       //标注点Z
    strObj.fontsize = 20;
    strObj.fontcolor = 0xFFFFFFFF;      //字体颜色
    strObj.bgColor = 0xffCDAD00;        //标注区域的背景颜色
    strObj.opacity = 1.0;               //气泡标注透明度
    strObj.width = 30;                  //气泡标注的宽
    strObj.height = 25;                 //气泡标注的高
    strObj.attribute = this.text;

    //调用addBubble方法添加气泡标注
    labelId1 = globe.addBubble(strObj);

    var strObj1 = new Bubble();
    strObj1.text = "泰安山区";       //标注文本
    strObj1.x = 117.182;                   //标注点X
    strObj1.y = 36.323;                   //标注点Y
    strObj1.z = 0;                       //标注点Z
    strObj1.fontcolor = 0xFFFFFFFF;      //字体颜色
    strObj1.fontsize = 20;
    strObj1.bgColor = 0xffCDAD00;        //标注区域的背景颜色
    strObj1.opacity = 1.0;               //气泡标注透明度
    strObj1.width = 30;                  //气泡标注的宽
    strObj1.height = 25;                 //气泡标注的高
    strObj1.attribute = this.text;

    //调用addBubble方法添加气泡标注
    labelId2 = globe.addBubble(strObj1);

    //拾取标注的的监听事件
    globe.addEventListener(EventType.PickLabel, function (attribute) {
        alert(attribute.split(":")[0]);
        addMap();
    });

    globe.startPickLabel();

    if (labelId == "") {
        alert("添加标注失败！");
    }

    if (mapId == -1) {
        alert("加载地图失败！");
    }
    $("#macher2").css("height", window.innerHeight - 50)
    // setTimeout("jump()",8000);

    //globe.goToSurfaceMode();
}

//地球视图跳转
// function jump() {
//     globe.jumpByPos(117.234124,37.232342,100.00,30,30,30);
// }

//加载模型
function addMap() {
    globe.load();
    globe.removeAll();
    if (DEMID > 0 || DEMID) {
        removeCut();
        globe.removeAllDoc();
    }
    globe.goToSurfaceMode();//进入表面模式
    sceneID = globe.addDoc("地图文档", IP, "6163", DocType.TypeG3D);
    globe.reset();//定位到模型所在位置
    //获取被切割图层的Range3D
    range3Dstr = globe.getSceneProperty(sceneID, 0, "Range3D");//只能获取模型图层的空间范围
}

//加载钻孔
function addMap1() {
    globe.load();
    if (zkmodelId > 0 || mxmodelId > 0) {
        globe.removeMap(zkmodelId);
        globe.removeMap(mxmodelId);
    }

    if (sceneID > 0 || DEMID) {
        removeCut();
        globe.removeAllDoc();//清除一切模型体
    }

    zkmodelId = globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/钻孔模型-ys", 0, IP, port)
    globe.reset();
}

//加载地层模型
function addMap2() {
    globe.load();

    if (zkmodelId > 0 || mxmodelId > 0) {
        globe.removeMap(zkmodelId);
        globe.removeMap(mxmodelId);
    }

    if (sceneID > 0 || DEMID) {
        removeCut();
        globe.removeAllDoc();
    }
    mxmodelId = globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys", 0, IP, port)
    globe.reset();
}

//加载DEM模型
function addDEM() {
    globe.load();
    if (sceneID > 0 || sceneID) {
        globe.removeAllDoc();
    }
    globe.goToSurfaceMode();
    DEMID = globe.addDoc("高程DEM", IP, port, DocType.TypeG3D);
    if (DEMID < 0) {
        alert("加载失败！");
        return;
    }
    globe.reset();
}

//任意平面切割
function ABSurface() {
    //移除切割面
    removeCut();
    //三维切割辅助面
    helpGeometry = globe.createABSurface(range3Dstr, 0, 45);
    alert(helpGeometry);
}

//移除切割面
function removeCut() {
    if (helpGeometry) {
        globe.remove(helpGeometry);
    }
}

//得到uuid
function getUuid() {
    var len = 32;//32长度
    var radix = 16;//16进制
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
    if (len) {
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
    } else {
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
}

//执行任意面切割切割
function ABSurfaceexeCut() {
    var orgSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
    var leftSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + getUuid();
    var rightSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + getUuid();

    var alphaValue = 0;
    var beltaValue = 45;
    var scaleValue = "2:2:2";
    globe.exeCutByABSurface(orgSFClsStr, leftSFClsStr, rightSFClsStr, alphaValue, beltaValue, scaleValue, successCallback, errorCallback, IP, 6163);
}

//爆炸显示
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

//结束爆炸显示
function stopBoomAnalysis() {
    globe.setAnalyseType(AnalyseType.Null);
    globe.setAnalyseInfo(null);
    globe.stopAnalyzeTool(EnumCommToolType.BombShow);
}

//隧道体切割
function createpipe() {
    //移除切割面
    removeCut();

    var jsonpnts = {"PntArray": []};
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

    alert(helpGeometry + "隧道面生成完成！");
}


//隧道体切割
function createpipeexeCut() {
    var orgSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
    var leftSFClsStr_k = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + getUuid();
    var rightSFClsStr_k = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + getUuid();
    var pnts = "485710.625,4280000;486705.75,4282905;486678.125,4286303.5;487085.21875,4289067;486678.125,4286303.5;487085.21875,4289067"; //切割路线
    var type = "circle";
    var radius = 498.8; //半径
    var number = 36;//顶分段数
    var depth = -700; //深度
    var length = 20; // 类型为拱形时有效，表示多边形的长
    var height = 5; //类型为拱形时有效，表示多边形的高
    globe.exeCutByPipe(orgSFClsStr, leftSFClsStr_k, rightSFClsStr_k, pnts, type, radius, number, depth, length, height, successCallback, errorCallback, IP, 6163);
}

//显示切割模型结果
function addcutModel() {
    if (sceneID > 0 || DEMID) {
        globe.removeAllDoc();
    }
    globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/b1", 0, IP, port);
    globe.reset();
}

//*******添加3d模型********start
function addPnt() {
    //移除鼠标事件
    globe.removeEventListener(EventType.LButtonDblClk, add3Dpoint);
    //注册鼠标事件
    globe.addEventListener(EventType.LButtonDblClk, add3Dpoint);
}

function add3Dpoint(flag, x, y, dx, dy, dz) {
    var point = dx + "," + dy + "," + dz;
    var info = new Draw3DElementInfo();
    info.type = Enum3DShapeType.Type3DPoint;
    info.pnts = point;
    info.libID = '1';
    info.symID = '10000019';
    info.fillClr = '0';
    info.transparent = '0';
    info.scale = "20|20|20";
    info.att = 'modelID:1';

    id = globe.draw3DElement(info);
    if (id != "") {
        //globe.reset();
    }
}

function removeAll3DElem() {
    //移除所有三维对象
    globe.removeAll3DElement();
}

//*******添加3d模型********end

//******开启地层查询********start
function PickModelReady() {
    //移除鼠标事件
    globe.removeEventListener(EventType.LButtonDblClk, DataQuery);
    globe.removeEventListener(EventType.LButtonDblClk, modelQuery);
    //注册鼠标事件
    globe.addEventListener(EventType.LButtonDblClk, DataQuery);
    openflag = true;
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
    queryParam.serverIp = IP;
    //igs服务端口
    queryParam.serverPort = "6163";
    if (globe != null) {
        //三维要素查询
        if (openflag) {
            globe.queryG3DFeature(queryParam, queryPolygonsCallback_modle, null, 'post');
            openflag = false;
            setTimeout(function () {
                openflag = true;
            }, 2000);
        }
    }
}

function queryPolygonsCallback_modle(data) {
    if (data.TotalCount > 0) {
        var objid = data.SFEleArray[0].FID;
        var info = "LayerIndex:1,ObjID:" + objid + ",SddHandle:" + sceneID;
        globe.startModelDiplay(info, 2, true);
        var att = data.AttStruct.FldName[0];
        var vaule = data.SFEleArray[0].AttValue[0];
        alert(att + "：" + vaule);
    }
}

function stopPickModelReady() {
    globe.removeEventListener(EventType.LButtonDblClk, DataQuery);
    globe.stopModelDisplayAll();
}

//***********开启地层查询end********end

//******开启模型查询********start
function PickModel() {
    //移除鼠标事件
    globe.removeEventListener(EventType.LButtonDblClk, modelQuery);
    globe.removeEventListener(EventType.LButtonDblClk, DataQuery);
    //注册鼠标事件
    globe.addEventListener(EventType.LButtonDblClk, modelQuery);
    openflag = true;
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
    queryParam.serverIp = IP;
    //igs服务端口
    queryParam.serverPort = "6163";
    if (globe != null) {
        if (openflag) {
            //三维要素查询
            globe.queryG3DFeature(queryParam, queryPolygonsCallback_M, null, 'post');
            openflag = false;
            setTimeout(function () {
                openflag = true;
            }, 2000);
        }
    }
}

function queryPolygonsCallback_M(data) {
    if (data.TotalCount > 0) {
        window.open("https://720yun.com/t/c53jzshnkk1?scene_id=8971505");
    }
}

function stopPickModel_M() {
    globe.removeEventListener(EventType.LButtonDblClk, queryPolygonsCallback_M);
    globe.stopModelDisplayAll();
}

//***********开启地层查询end********end
