---
title: Height-responsive div, prevent scrollbar
date: 2016-07-17 00:42:59
tags: CSS3
---
# Dynamic adjust div size

```css
#viewerContainer{
    position:absolute;
    /*height:90%;*/
    /*width:98.6%;*/
    height:calc(100vh - 96px);
    width:100%;
}
/*
calc for calculator in css

vh
1/100th of the height of the viewport.

vw
1/100th of the width of the viewport.

vmin
1/100th of the minimum value between the height and the width of the viewport.

vmax
1/100th of the maximum value between the height and the width of the viewport.
*/
```
