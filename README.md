# BigImageDemo
Android自定义大图显示控件

## BitmapRegionDecoder
解决超大图片的OOM问题，decodeRegion(Rect rect, BitmapFactory.Options options)方法可以加载超大图片中指定的矩形区域，通过自定义控件的onTouch事件，
根据手势实现上下左右拖动，刷新矩形框，显示大图片的不同区域。

## 效果图
![](https://github.com/xiaokc/BigImageDemo/blob/master/app/src/main/assets/效果图.gif)
