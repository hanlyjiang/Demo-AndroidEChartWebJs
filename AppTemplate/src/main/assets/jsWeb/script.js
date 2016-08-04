var fileContent;
var globalStr;


function onPageLoad()
{
    globalStr = Android.onPageLoad();
//    document.write("<h1>" + globalStr + "</h1>")
}

function readFileContent() 
{
    
    fileContent = Android.readFile();
    showToast(fileContent);
    document.getElementById("commontext").innerHTML=fileContent;
}

function showToast(toast) {
    Android.showToast(toast);
//    alert(toast)
}

function makeOption(){
    
}