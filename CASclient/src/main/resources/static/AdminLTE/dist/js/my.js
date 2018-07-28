var globe = new Globe();
var docName = "地图文档";
var QueryURL = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
var IP = "172.20.226.65";
var port = "6163";
var sceneID;
var DEMID;
var map;
var helpGeometry;
var range3Dstr = "";
var globe = new Globe();
var zkmodelId, mxmodelId,commodelId, labelId1, labelId2;
var openflag = false, cutflag = true, floodflag = true, addModelFlag = true, pipeFlag = true,findAttrFlag=true;
var mapId_img = 0;
var mapId_cia = 0;
var floodCount = 0;
var floodInfo = new FLoodAnalyzeInfo();
var flood_content, flood_colorFloodValue, flood_alphaSpinnerValue, flood_expanSpinnerValue, flood_highSpinnerValue;
var boomFlag=true;
var first, second;
var pipeFirst, pipeSecond;
var pnts = "";
var shapeType;
var COUNT, X1, X2, Y1, Y2;
var jsonpnts = {"PntArray": []};
var dotarr = [];

//地球载入初始化
function init() {

    // alert(window.innerHeight - 50);
    $("#macher2").css("height", window.innerHeight - 50)

    globe.load();

    mapId_img = globe.addTianditu("img");
    mapId_cia = globe.addTianditu("cia");
    //
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
    globe.addEventListener(EventType.PickLabel, pickModels);

    globe.startPickLabel();

    if (labelId == "") {
        alert("添加标注失败！");
    }

    if (mapId == -1) {
        alert("加载地图失败！");
    }


    // setTimeout("jump()",8000);

    //globe.goToSurfaceMode();
}

//地球视图跳转
// function jump() {
//     globe.jumpByPos(117.234124,37.232342,100.00,30,30,30);
// }

function pickModels() {
    addMap();
}

//********加载模型和地层******start
//加载模型
function addMap() {
    globe.load();

    globe.removeEventListener(EventType.PickLabel,pickModels);
    globe.stopPickLabel();

    //删除上一个标注
    if (labelId1 != "" || labelId2 != "") {
        globe.removeLabelByName(labelId1);
        globe.removeLabelByName(labelId2);
        // globe.removeAllLabel();
    }

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
    QueryURL = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
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


    zkmodelId = globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/钻孔模型-ys", 0, IP, port);
    commodelId=zkmodelId;
    QueryURL="gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/钻孔模型-ys";
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
    mxmodelId = globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys", 0, IP, port);
    commodelId=mxmodelId;
    QueryURL="gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
    globe.reset();
}

//加载DEM模型
function addDEM() {
    globe.load();
    if (sceneID > 0 || sceneID) {
        globe.removeAllDoc();
    }
    globe.goToSurfaceMode();
    DEMID = globe.addDoc("威海", IP, port, DocType.TypeG3D);
    if (DEMID < 0) {
        alert("加载失败！");
        return;
    }
    globe.reset();
}
//********加载模型和地层******end




//**********切割面切割********start
function showABSurface() {
    $("#FloodControl").removeClass("control-none");
    $("#FloodControl").attr("src", "/cutting");
}

//任意平面切割
function ABSurface(alphaValue, beltaValue) {
    //移除切割面
    removeCut();
    //三维切割辅助面
    helpGeometry = globe.createABSurface(range3Dstr, alphaValue, beltaValue);
    // alert(helpGeometry);
}

function showFirst() {
    globe.load();

    if (zkmodelId > 0 || mxmodelId > 0) {
        globe.removeMap(zkmodelId);
        globe.removeMap(mxmodelId);
    }

    if (sceneID > 0 || DEMID) {
        removeCut();
        globe.removeAllDoc();
    }
    alert(first)
    commodelId= globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + first, 0, IP, port);
    QueryURL="gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + first;
    globe.reset();
}

function showSecond() {
    globe.load();

    if (zkmodelId > 0 || mxmodelId > 0) {
        globe.removeMap(zkmodelId);
        globe.removeMap(mxmodelId);
    }

    if (sceneID > 0 || DEMID) {
        removeCut();
        globe.removeAllDoc();
    }
    alert(second);
    commodelId= globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + second, 0, IP, port);
    QueryURL="gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + second;
    globe.reset();

}

//执行任意面切割切割
function ABSurfaceexeCut(alphaValue, beltaValue) {
    var orgSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
    first = getUuid();
    var leftSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + first;
    second = getUuid();
    var rightSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + second;

    // var alphaValue = 0;
    // var beltaValue = 45;
    var scaleValue = "2:2:2";
    globe.exeCutByABSurface(orgSFClsStr, leftSFClsStr, rightSFClsStr, alphaValue, beltaValue, scaleValue, function () {
        alert("切割成功")
    }, errorCallback, IP, 6163);
}

//移除切割面
function removeCut() {
    if (helpGeometry) {
        globe.remove(helpGeometry);
    }
}

function stopCuttingAnaysis() {
    removeCut();
    $("#FloodControl").addClass("control-none");

    globe.removeEventListener(EventType.LButtonDblClk, AddPipePnt);
    globe.removeEventListener(EventType.RButtonDown, createpipe);
}

//**********切割面切割********end






//***********隧道钻孔切割**********start
function showPipeSurface() {
    $("#FloodControl").removeClass("control-none");
    $("#FloodControl").attr("src", "/pipe");

    globe.addEventListener(EventType.LButtonDblClk, AddPipePnt);
    globe.addEventListener(EventType.RButtonDown, createpipe);

    removeCut();
    dotarr = [];
}

//双击添加点
function AddPipePnt(flag, x, y, dx, dy, dz) {
    if (pipeFlag) {
        var obj1 = new Object();
        obj1.x = dx;
        obj1.y = dy;
        obj1.z = dz;
        alert("添加一点")
        dotarr.push(obj1);
        pipeFlag = false;
        setTimeout(function () {
            pipeFlag = true;
        }, 200);
    }

}

function createpipe() {
    if (pipeFlag) {
        removeCut();
        globe.removeEventListener(EventType.LButtonDblClk, AddPipePnt);
        jsonpnts.pntarray = dotarr;
        pnts = JSON.stringify(jsonpnts);
        var radius = 498.8;//半径
        var secNum = 36;//顶分段数
        var depth = 0;//深度
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
        alert("生成pipe");
        pipeFlag = false;
        globe.removeEventListener(EventType.RButtonDown, createpipe);
        setTimeout(function () {
            pipeFlag = true;
        }, 200);
    }
}

function createpipeByDepthHeight(depth1, height1) {
    globe.removeEventListener(EventType.LButtonDblClk, AddPipePnt);
    globe.removeEventListener(EventType.RButtonDown, createpipe);
    removeCut();
    globe.removeEventListener(EventType.LButtonDblClk, AddPipePnt);
    jsonpnts.pntarray = dotarr;
    pnts = JSON.stringify(jsonpnts);
    var radius = height1;//半径
    var secNum = 36;//顶分段数
    var depth = depth1;//深度
    var length = 20; // 类型为拱形时有效，表示多边形的长
    var height = 20; //类型为拱形时有效，表示多边形的高
    //三维切割辅助面
    var xmin = $.parseJSON(range3Dstr).range3d.xmin;
    var ymin = $.parseJSON(range3Dstr).range3d.ymin;
    var zmin = $.parseJSON(range3Dstr).range3d.zmin;
    var xmax = $.parseJSON(range3Dstr).range3d.xmax;
    var ymax = $.parseJSON(range3Dstr).range3d.ymax;
    var zmax = $.parseJSON(range3Dstr).range3d.zmax;
    helpGeometry = globe.createPipe(range3Dstr, 'circle', pnts, radius, secNum, depth, length, height);
}

//隧道体切割
function createpipeexeCut(depth1, height1) {
    var orgSFClsStr = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型-ys";
    pipeFirst=getUuid();
    var leftSFClsStr_k = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + pipeFirst;
    pipeSecond=getUuid();
    var rightSFClsStr_k = "gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + pipeSecond;
    // var pnts = "485710.625,4280000;486705.75,4282905;486678.125,4286303.5;487085.21875,4289067;486678.125,4286303.5;487085.21875,4289067"; //切割路线
    var ps="";
    var parray=JSON.parse(pnts).pntarray;
    for(var i=0;i<parray.length;i++){
        ps=ps+parray[i].x+","+parray[i].y+";";
    }
    ps=ps.substring(0,ps.length-1);
    var type = "circle";
    var radius = height1; //半径
    var number = 36;//顶分段数
    var depth = depth1; //深度
    var length = 20; // 类型为拱形时有效，表示多边形的长
    var height = 5; //类型为拱形时有效，表示多边形的高
    globe.exeCutByPipe(orgSFClsStr, leftSFClsStr_k, rightSFClsStr_k, ps, type, radius, number, depth, length, height, function(){alert("切割成功！")}, errorCallback, IP, 6163);
}

function showPipeFrist() {
    globe.load();

    if (zkmodelId > 0 || mxmodelId > 0) {
        globe.removeMap(zkmodelId);
        globe.removeMap(mxmodelId);
    }

    if (sceneID > 0 || DEMID) {
        removeCut();
        globe.removeAllDoc();
    }
    alert(pipeFirst)
    commodelId= globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + pipeFirst, 0, IP, port);
    QueryURL="gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + pipeFirst;
    globe.reset();
}

function showPipeSecond() {
    globe.load();

    if (zkmodelId > 0 || mxmodelId > 0) {
        globe.removeMap(zkmodelId);
        globe.removeMap(mxmodelId);
    }

    if (sceneID > 0 || DEMID) {
        removeCut();
        globe.removeAllDoc();
    }
    alert(pipeSecond)
    commodelId= globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + pipeSecond, 0, IP, port);
    QueryURL="gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/地质模型_" + pipeSecond;
    globe.reset();
}

//***********隧道钻孔切割**********end






//*******矩形操作************start
function GetGemoPnt() {
    $("#FloodControl").removeClass("control-none");
    $("#FloodControl").attr("src", "/showModel");
    // alert("kaiqi");
    COUNT = 0;
    globe.removeEventListener(EventType.LButtonDblClk, AddPnt);
    globe.removeEventListener(EventType.RButtonDown, addGraphic);
    globe.addEventListener(EventType.LButtonDblClk, AddPnt);
    globe.addEventListener(EventType.RButtonDown, addGraphic);
}

function removeAllGraphic() {
    $("#FloodControl").addClass("control-none");
    globe.removeEventListener(EventType.LButtonDblClk, AddPnt);
    globe.removeEventListener(EventType.RButtonDown, addGraphic);
    //移除所有几何对象
    globe.removeAllGraphic();
}

function showModel() {
    // alert("hell");
    removeAllGraphic();
    var content = document.frames['FloodControl'].document;
    var n = parseFloat(content.getElementById('n').value) ;
    var innerLongX=(X2-X1)/n;
    var innerLongY=(Y2-Y1)/n;
    var state = globe.setSceneState(DEMID, 4, 5);
    for(var i=0;i<=n;i++){
        for(var j=0;j<=n;j++){
            createModle(X1+i*innerLongX,Y1+j*innerLongY,state);
        }
    }
}

function createModle(x,y,state) {
    // alert(globe.getTerrainEle(x, y));
    var point = x + "," + y + "," + globe.getTerrainEle(x, y);
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
}

function AddPnt(flag, x, y, dx, dy, dz) {

    shapeType = "1";
    if (addModelFlag) {
        if (COUNT == 0) {
            X1 = dx;
            Y1 = dy;
            pnts += dx + "," + dy + ";";
            // alert(pnts);
            addModelFlag = false;
            setTimeout(function () {
                addModelFlag = true;
            }, 300);
            COUNT++;
        } else {
            X2 = dx;
            Y2 = dy;
            pnts += dx + "," + dy + ";";
            // alert(pnts);
            addModelFlag = false;
            setTimeout(function () {
                addModelFlag = true;
            }, 300);
            COUNT = 0;
        }

    }

}

function addGraphic() {
    //绘制参数设置
    var drawInfo = new DrawInfo();
    //设置绘制类型：TypeLine = 0,TypeRect = 1,TypePolygon = 2,TypeCircle = 3,
    drawInfo.shapeType = shapeType;
    //设置背景色
    drawInfo.bdColor = 0xffff0000;
    //设置填充色
    drawInfo.fillColor = 0xff00ffff;
    //设置透明度
    drawInfo.transparence = 1;
    //设置线宽
    drawInfo.linWid = 10;
    //设置线类型 0:实线,1:折线,2:点线,3:折线点
    drawInfo.lineType = 0;

    //绘制几何
    var drawResult = globe.addGraphic(pnts, drawInfo);
    if (drawResult != "") {
        var ele = {id: drawResult, pnts: pnts};
        globe._drawElements.push(ele);
        geomId = ele.id;
    }
    pnts = "";
}

//*******矩形操作************end




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



//******地层爆炸********start
function BoomAnalysis() {
    if(boomFlag){
        startBoomAnalysis();
        boomFlag=false;
    }else{
        stopBoomAnalysis();
        boomFlag=true;
    }
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

//******地层爆炸********end





//显示切割模型结果
function addcutModel() {
    if (sceneID > 0 || DEMID) {
        globe.removeAllDoc();
    }
    commodelId= globe.appendGeomByUrl("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/b1", 0, IP, port);
    QueryURL="gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/b1";
    globe.reset();
}




//*********交互输入填挖方量*******start
function cutfillanalysis1() {
    stopAnalysis();

    $("#FloodControl").removeClass("control-none");
    $("#FloodControl").attr("src", "/cutFill");

}

function fillCut() {
    //获取填挖方分析输入参数
    var content = document.frames['FloodControl'].document;
    var fillColorValue = content.getElementById('fillColorCut').value;
    var cutColorValue = content.getElementById('cutColorCut').value;
    var noneColorValue = content.getElementById('noneColorCut').value;
    var highTxtValue = content.getElementById('highTxtCut').value;

    //注册事件
    globe.addEventListener(EventType.FinishedAnalyze, getFillCutResult);

    var info = new CutFillInfo();
    //当前进行的分析类别,CutFill为填挖方分析
    info.type = AnalyseType.CutFill;
    //采用用户输入模式
    info.datatype = 0;
    info.bstartreg = 1;
    //当前高程
    info.height = parseInt(highTxtValue, 10);
    //挖的颜色
    info.cutclr = parseInt(cutColorValue, 16);
    //填的颜色
    info.fillclr = parseInt(fillColorValue, 16);
    //不填不挖的颜色
    info.nocutfillClr = parseInt(noneColorValue, 16);
    //设置当前进行的分析类别
    globe.setAnalyseType(AnalyseType.CutFill);
    //开启工具接口
    globe.startAnalyzeTool(info);

}

function getFillCutResult(result) {


    if (cutflag) {
        var jsonObj = eval('(' + result + ')');
        alert("填方量：" + jsonObj.fillvolume + "\r\n" + "挖方量：" + jsonObj.cutvolume + "\r\n" + "分析表面积：" + jsonObj.surfacearea);
        cutflag = false;
        setTimeout(function () {
            cutflag = true;
        }, 2000);
    }

}

//*********交互输入填挖方量*******end




//**********交互式分析洪水淹没******start
function flood1() {
    // stopAnalysis();

    $("#FloodControl").removeClass("control-none");
    $("#FloodControl").attr("src", "/flood");
}

function flood() {
    stopAnalysis();
    globe.addEventListener(EventType.LButtonDblClk, startPointCallback);
    alert("请设置观察点");
    //获取淹没分析输入参数
    flood_content = document.frames['FloodControl'].document;
    flood_colorFloodValue = flood_content.getElementById("viewColorFlood").value;
    flood_alphaSpinnerValue = flood_content.getElementById("alphaSpinnerFlood").value;
    flood_expanSpinnerValue = flood_content.getElementById("expanSpinnerFlood").value;
    flood_highSpinnerValue = flood_content.getElementById("highSpinnerFlood").value;
    floodCount = 0;
}

function startPointCallback(flag, x, y, dx, dy, dz) {
    if (floodflag) {
        if (floodCount == 0) {
            floodInfo.observepos.x = dx;
            floodInfo.observepos.y = dy;
            floodInfo.observepos.z = dz;
            alert("请设置第一个角点");
        } else if (floodCount == 1) {
            floodInfo.startpos.x = dx;
            floodInfo.startpos.y = dy;
            floodInfo.startpos.z = dz;
            alert("请设置第二个角点");
        } else if (floodCount == 2) {
            floodInfo.endpos.x = dx;
            floodInfo.endpos.y = dy;
            floodInfo.endpos.z = dz;
            floodCount = 0;
            globe.removeEventListener(EventType.LButtonDblClk, startPointCallback);
            //注册事件
            // globe.addEventListener(EventType.FinishedAnalyze, getFloodResult);

            //设置当前进行的分析类别为空
            globe.setAnalyseType(AnalyseType.Null);
            //设置当前分析得到的结果为空
            globe.setAnalyseInfo(null);
            //停止工具接口
            globe.stopAnalyzeTool(EnumCommToolType.TerrainAnalyze);
            globe.stopAnalyzeTool(EnumCommToolType.TerrainCut);


            //当前进行的分析类别,FLoodAnalyze为洪水淹没分析
            floodInfo.type = AnalyseType.FLoodAnalyze;
            //透明度(0-1.0之间有效)
            floodInfo.alpha = Number(flood_alphaSpinnerValue);
            //淹没区域颜色
            floodInfo.floodclr = parseInt(flood_colorFloodValue, 16);
            //淹没区域扩大倍数
            floodInfo.regzoom = Number(flood_expanSpinnerValue);
            //数据模式控制，交互拾取和用户输入两种。 0：交互拾取模式； 1: 用户输入模式。
            floodInfo.datatype = 1;
            //当前高程
            floodInfo.height = Number(flood_highSpinnerValue);

            //设置当前进行的分析类别
            globe.setAnalyseType(AnalyseType.FLoodAnalyze);
            //开启工具接口
            globe.startAnalyzeTool(floodInfo);
            // alert("开始分析");
        }
        floodflag = false;
        floodCount++;
        setTimeout(function () {
            floodflag = true;
        }, 500)
    }

}

//**********交互式分析洪水淹没******end





// function getFloodResult(data){
//     alert(data);
// }

//停止分析功能
function stopAnalysis() {
    //注销双击事件
    globe.removeEventListener(EventType.LButtonDblClk, startPointCallback);
    globe.removeEventListener(EventType.FinishedAnalyze, getFillCutResult);

    globe.removeEventListener(EventType.LButtonDblClk, AddPipePnt);
    globe.removeEventListener(EventType.RButtonDown, createpipe);
    // globe.removeEventListener(EventType.FinishedAnalyze, getFloodResult);
    //设置当前进行的分析类别为空
    globe.setAnalyseType(AnalyseType.Null);
    //设置当前分析得到的结果为空
    globe.setAnalyseInfo(null);
    //停止工具接口
    globe.stopAnalyzeTool(EnumCommToolType.TerrainAnalyze);
    globe.stopAnalyzeTool(EnumCommToolType.TerrainCut);
    $("#FloodControl").addClass("control-none");
}

//双击事件
function secondPnt(flag, x, y, dx, dy, dz) {
    //获取淹没分析输入参数
    var content = document.frames['FloodControl'].document;
    var colorFloodValue = content.getElementById("viewColorFlood").value;
    var alphaSpinnerValue = content.getElementById("alphaSpinnerFlood").value;
    var expanSpinnerValue = content.getElementById("expanSpinnerFlood").value;
    var highSpinnerValue = content.getElementById("highSpinnerFlood").value;

    //设置当前进行的分析类别为空
    globe.setAnalyseType(AnalyseType.Null);
    //设置当前分析得到的结果为空
    globe.setAnalyseInfo(null);
    //停止工具接口
    globe.stopAnalyzeTool(EnumCommToolType.TerrainAnalyze);
    globe.stopAnalyzeTool(EnumCommToolType.TerrainCut);
    //实例化一个分析对象
    var info = new FLoodAnalyzeInfo();
    //当前进行的分析类别,FLoodAnalyze为洪水淹没分析
    info.type = AnalyseType.FLoodAnalyze;
    //透明度(0-1.0之间有效)
    info.alpha = Number(alphaSpinnerValue);
    //淹没区域颜色
    info.floodclr = parseInt(colorFloodValue, 16);
    //淹没区域扩大倍数
    info.regzoom = Number(expanSpinnerValue);
    //数据模式控制，交互拾取和用户输入两种。 0：交互拾取模式； 1: 用户输入模式。
    info.datatype = 1;
    //当前高程
    info.height = Number(highSpinnerValue);
    //观察点
    info.observepos.x = startPoint.x;
    info.observepos.y = startPoint.y;
    info.observepos.z = startPoint.z;

    //起始点x、y、z坐标
    info.startpos.x = firstPntInfo.x;
    info.startpos.y = firstPntInfo.y;
    info.startpos.z = firstPntInfo.z;
    //终止点、y、z坐标
    info.endpos.x = dx;
    info.endpos.y = dy;
    info.endpos.z = dz;
    //设置当前进行的分析类别
    globe.setAnalyseType(AnalyseType.FLoodAnalyze);
    //开启工具接口
    globe.startAnalyzeTool(info);
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
function findAttr(){
    if(findAttrFlag){
        PickModelReady();
        $("#findAttrIcon").removeClass("fa fa-toggle-off");
        $("#findAttrIcon").addClass("fa  fa-toggle-on");
        findAttrFlag=false;
    }else{
        stopPickModelReady();
        $("#findAttrIcon").removeClass("fa  fa-toggle-on");
        $("#findAttrIcon").addClass("fa fa-toggle-off");
        findAttrFlag=true;
    }
}

function PickModelReady() {
    // alert("开启地层查询");
    $("#FloodControl").removeClass("control-none");
    $("#FloodControl").attr("src", "/attrs");
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
            }, 200);
        }
    }
}

function queryPolygonsCallback_modle(data) {
    var content= document.frames['FloodControl'].document;
    content.getElementById("attrs").innerHTML="";
    if (data.TotalCount > 0) {
        for(var i=0;i<data.TotalCount;i++){
            var objid = data.SFEleArray[i].FID;
            // var info = "LayerIndex:0,ObjID:" + objid + ",SddHandle:" + sceneID;
            // globe.startModelDiplay(info, 2, true);
            var att = data.AttStruct.FldName[0];
            var vaule = data.SFEleArray[0].AttValue[0];
            content.getElementById("attrs").innerHTML+="<li data='"+objid+"' role='presentation'><a href='#'>"+objid+":"+att + "：" + vaule+"</a></li>";
            // alert(att + "：" + vaule);
        }
        FloodControl.window.ac()

    }
}

//高亮显示
function sparkModel(objid) {
    var info = "LayerIndex:"+"0"+",ObjID:" + objid + ",SddHandle:" + sceneID;
    globe.startModelDiplay(info, 1, true);
}

function stopPickModelReady() {
    // alert("关闭地层查询");
    $("#FloodControl").addClass("control-none");
    globe.removeEventListener(EventType.LButtonDblClk, DataQuery);
    globe.stopModelDisplayAll();
}

//***********开启地层查询end********end




//******开启模型查询********start

function PickModel() {
    alert("开启全景模型查询");
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
    queryParam.gdbp = encodeURI("gdbp://MapGisLocal/钻孔自动建模-模型/sfcls/查询区_新0706");
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
        window.open("/globleImages");
    }
}

function stopPickModel_M() {
    alert("关闭全景模型查询")
    globe.removeEventListener(EventType.LButtonDblClk, queryPolygonsCallback_M);
    globe.stopModelDisplayAll();
}

//***********开启地层查询end********end

function loadMap() {
    var queryParam=new G3DDocQuery();
    queryParam.pageCount='1000';
    queryParam.gdbp=encodeURI(URL);
    queryParam.where='';
    queryParam.serverIp=ip;
    queryParam.serverPort=port;
    queryParam.structs='{"IncludeAttribute":true,"IncludeGeometry":false,"IncludeWebGraphic":false}';

    if(globe != null){
        this.globle.queryG3DFeature(queryParam,queryPolygonsCallback,null,'post');
    }
}
function queryPolygonsCallback(data) {
    for(var i=0;i<data.TotalCount;i++){
        modelID=globe.appendGeomByUrl(URL,ip,port,data.SFEleArray[i].FID);
        var model=new Object();
        model.modelID=modelID;
        model.FID=data.SFEleArray[i].FID;
        models.push(model);

        if(modelID > 0){
            globe.reset();
        }else{
            alert("添加失败");
        }
    }
}

function flash() {
    for(var i=0;i<models.length;i++){
        if(models[i].FID==5){
            var info="LayerIndex:0,ObjID:0"+"SddHandle:"+models[i].modelID;
            globe.startModelDiplay(info,EnumModelDispType.Flash,true);
        }
    }
}