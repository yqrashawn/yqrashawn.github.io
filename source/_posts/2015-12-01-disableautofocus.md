---
title: Disable autofocus 禁用网站文本框自动聚焦
date: 2016-07-17 00:38:45
tags: Browser
---
## 避免Google等网站中的 textfield 的 autofocus 功能  
在 Chrome 等浏览器中使用 vim 类型的插件可以方便的浏览网页，但不便的情况是在像 Google 这样的网站中，按下字母键光标会自动聚焦到搜索框，可以使用如下脚本来禁用这项功能，脚本来源于 stakexchange  
  
```
var maxTime = 3000;
var timeoutInterval = 5;

var usedTime = 0;
var isManualFocus = false;
function check() {
    if (!isManualFocus && document.activeElement.tagName.toLowerCase() == "input") {
        console.log("BLURRED");
        document.activeElement.blur();
    }
    usedTime += timeoutInterval;
    if (usedTime < maxTime) {
        window.setTimeout(check, timeoutInterval);
    }
}
check();


document.body.addEventListener("click", function (evt) {
    if (evt.target.tagName == "INPUT") {
        console.log("MANUAL CLICK");
        isManualFocus = true;
    }
});

document.body.addEventListener("keydown", function (evt) {
    isManualFocus = true;
});
```   

### 两种用法
1.保存上述代码到 *disableautofocus.js* 文件（文件名可以任意），打开 Chrome 插件管理页面，将 .js 文件拖入其中即可；  
2.安装 *Tampermonkey* 插件，改插件同时支持 Chrome,Safari,UC Brower, [安装点此处](http://tampermonkey.net/)，然后利用插件的添加功能选定 .js 文件即可。这是一个对指定网页加载用户自制脚本的浏览器插件，插件中推荐了几个脚本分享网站可以看一下，还是有不少比较实用的脚本。     

### 不同浏览器 vim 式浏览插件  
Chrome: [vimium](https://chrome.google.com/webstore/detail/vimium/dbepggeogbaibhgnhhndojpepiihcmeb?hl=en)
Safari: [vimari](https://github.com/guyht/vimari)
Firefox:[vimium](https://addons.mozilla.org/en-US/firefox/addon/vimium/)  
  
  
用法参考各个插件说明，不赘述。
