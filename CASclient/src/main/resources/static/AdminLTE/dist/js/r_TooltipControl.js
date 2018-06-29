
/*
* 在tooltip中添加显示标注点详细信息的iframe
* param {String} left tooltip的左样式
* param {String} top tooltip的顶部样式
* param {String} width tooltip的宽度
* param {String} height tooltip的高度
* param {String} info tooltip中iframe的URL地址
* param {String} type 标志类型
* param {Array} fldName  显示信息(模型的属性名称)
* param {Array} fldValue  显示信息(模型的属性值)
*/
function addToolTipDiv(left, top, width, height, info, type, fldName, fldValue) {

    var tooltipDivEle = document.getElementById("toolTipDiv");
    if (tooltipDivEle == undefined) return;

    var tooltipFrameForURLEle = document.getElementById("toolTipFrameForURL");
    if (tooltipFrameForURLEle == undefined) return;

    var obj = document.getElementById("MapGIS_EarthControl");
    var pos = getAbsolutePos(obj);

    tooltipDivEle.style.left = (parseFloat(left) + pos[0]) + "px";
    //tooltipDivEle.style.top = (parseFloat(top) + pos[1]) + "px";
    tooltipDivEle.style.top = (parseFloat(top) + pos[1]) + "px";  
    tooltipDivEle.style.width = width + "px";
    tooltipDivEle.style.height = height + "px";

    if (type == "url") {
        tooltipFrameForURLEle.style.display = "";
        tooltipFrameForURLEle.src = info;
    }
    //显示
    tooltipDivEle.style.display = "";
}
function getAbsolutePos(obj) {
    var ow = [0, 0];
    var pw = obj;
    while (pw && pw.offsetParent) {
        ow[0] += pw.offsetLeft;
        ow[1] += pw.offsetTop;
        pw = pw.offsetParent;
    }
    return ow;
}

