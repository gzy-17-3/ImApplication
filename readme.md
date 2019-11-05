# 网络
1. 网络获取  https://www.jianshu.com/p/8e404d9c160f
    - okhttp
        - 不封装的使用特别简单
    - 集成okhttp
        1. 添加依赖
        ```
            implementation 'com.squareup.okhttp3:okhttp:3.8.0'
        ```
        2. 网络权限
        ```
            <uses-permission android:name="android.permission.INTERNET" />
        ```
    - 安卓9 权限问题
        - https://www.jianshu.com/p/9d5ffa5b61e7

2. 数据解析
    - fastjson
        - json to model
            - 代码生成器 - 不用也可以
                - gsonformat
                    - 设置解析框架为 fastjson
        - json字符串转模型


# RecycleView https://www.jianshu.com/p/82334cfc541a
1. RecycleView 引入
    - implementation 'androidx.recyclerview:recyclerview:1.0.0'
2. 布局中添加RecycleView
    ```
        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        </androidx.recyclerview.widget.RecyclerView>
    ```
3. ....

# RecycleView 下拉刷新数据
1. Swi

