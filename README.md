# GlideUtil工具类

[![](https://jitpack.io/v/EHENJOOM/GlideUtil.svg)](https://jitpack.io/#EHENJOOM/GlideUtil)

Glide是一个很强大的图片加载类，但用原生Glide加载图片，如果不进行处理，那么只能得到矩形的图片。有时候需求要做一个圆形的图片，或带有圆角的矩形图片，这时候如果采用自定义控件，那么就不能使用Glide加载图片。这时候，Glide工具类应运而生。GlideUtil包括了三个类：**GlideCacheUtil、GlideCircleTransformUtil、GlideRoundTransformUtil**

## 使用方法

### 1.添加依赖

首先，在build.gradle文件下加入 maven {url 'https://jitpack.io'}

```javascript
allprojects {
	repositories {
		google()
		jcenter()
		maven {url "https://jitpack.io"}
	}
}
```

然后在dependencies下加入依赖

```js
implementation 'com.github.EHENJOOM:BottomBarLayout:GlideUtil:1.1.0'
```

### 2.GlideCacheUtil

GlideCacheUtil是处理Glide缓存的类，该类可以获取Glide缓存大小、删除缓存等。下面是该类的方法：

|             函数名             |           作用           |
| :----------------------------: | :----------------------: |
|  GlideCacheUtil getInstance()  |        获取类实例        |
|  void clearDiskCache(Context)  |     清除图片磁盘缓存     |
| void clearMemoryCache(Context) |     清除图片内存缓存     |
|  void clearAllCache(Context)   |     清除图片所有缓存     |
|  String getCacheSize(Context)  | 获取图片缓存大小(格式化) |
|  String getFormatSize(double)  |        格式化单位        |

例：

```java
GlideCacheUtil.getInstance().clearAllCache(context);
```

### 3.GlideCircleTransformUtil

GlideCircleTransformUtil是将原图转化为圆形的类。例：

```java
Glide.with(context)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.RESULT)
    .bitmapTransform(new CenterCrop(context),new GlideCircleTransformUtil(context))
    .placeholder(R.drawable.placeholder)
    .error(R.mipmap.err)
    .into(imageView);
```

### 4.GlideRoundTransformUtil

GlideRoundTransformUtil是将原图转化为圆角矩形的类。该类默认圆角为5dp，可通过构造函数改为其他值。

```java
Glide.with(context)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.RESULT)
    .transform(new CenterCrop(context),new GlideRoundTransformUtil(context))
    .placeholder(R.drawable.placeholder)
    .error(R.mipmap.err)
    .into(imageView);
```

需要源码的，可以到[项目地址](https://github.com/EHENJOOM/GlideUtil)下载。

喜欢的欢迎star哦。