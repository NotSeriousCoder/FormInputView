# FormInputView
用于表单的单项输入控件

## 引用方法
### 1.在项目的Gradle文件
  allprojects {
    repositories {
      .......
      maven { url 'https://jitpack.io' }
    }
  }
### 2.在你要用这个控件的模块gradle文件
  implementation 'com.github.NotSeriousCoder:FormInputView:Tag'
  版本号请自行看releases

### 简单使用方法，详细的使用方法请看Demo
    <com.bingor.forminputview.FormInputView
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      app:title="普通输入框">
      
##### 参数说明
      <!-- 标题相关 -->
      <!-- 标题 -->
      app:title="标题"
      <!-- 颜色 -->
      app:textColorTitle="#123456"
      <!-- 字号 -->
      app:textSizeTitle="16sp"        
      <!-- 偏移量 -->
      app:titleOffset="4dp"    
      
      <!-- 正文相关 -->
      <!-- 正文 -->
      app:text="abc"        
      <!-- 颜色 -->
      app:textColor="#123456"        
      <!-- 字号 -->
      app:textSize="16sp"        
      
      <!-- hint相关 -->
      <!-- hint -->
      app:hint="abc"  
      <!-- 颜色 -->
      app:textColorHint="#123456"  
      
      <!-- 边框 -->  
      <!-- 颜色 -->
      app:borderColor="#123456"  
      <!-- 笔触宽度 -->
      app:borderWidth="2dp"  
      <!-- 弧度 -->
      app:radius="16dp"  
      
      <!-- 图标相关 -->  
      <!-- 是否显示左边图标 -->
      app:showLeftIcon="true"  
      <!-- 是否显示密码可见按钮 -->
      app:showPswSwitch="true"  
      <!-- 是否显示右边图标 -->
      app:showRightIcon="true"  
      <!-- 左边图标 -->
      app:leftIcon="@drawable/xxx"  
      <!-- 密码可见按钮 -->
      app:pswSwitchIcon="@drawable/xxx"  
      <!-- 右边图标 -->
      app:rightIcon="@drawable/xxx"  

      <!-- 其他 -->  
      <!-- 输入类型 -->
      app:inputType="text"  
      <!-- 文字高亮颜色 -->
      app:textColorHighlight="#123456"  
      <!-- 超链接颜色 -->
      app:textColorLink="#123456"  
      <!-- 行数 -->
      app:lines="10"  
      <!-- 最大长度 -->
      app:maxLength="30"  
      <!-- 每行最大换行字数 -->
      app:maxEms="30"  
      <!-- 最大行数 -->
      app:maxLines="10"  
 
