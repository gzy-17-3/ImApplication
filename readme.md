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
3. ....   https://www.jianshu.com/p/82334cfc541a

# RecycleView 下拉刷新的功能

1. SwipeRefreshLayout
2. 在布局里将 RecycleView 节点包裹起来
3. findViewByid
4. setOnRefreshListener
5. 在监听里调用加载数据的方法
6. 先 dataList.clear 再 dataList.addAll(students);
7. swipeRefreshLayout.setRefreshing(false); 关掉转圈
8. 其他：设置颜色
    ```
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark
            );
    ```
