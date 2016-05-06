###  appbar 收起时才展示标题

描述: 即当appbar展开状态时不要展示应用标题（或者设置的标题）,在appbar收起的过程中逐步显示;

解决办法: 当appbar展开时设置其透明展示即可

    <android.support.design.widget.CollapsingToolbarLayout
            android:id="@+id/toolbar_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/colorPrimary"
            android:fitsSystemWindows="true"
            ***app:expandedTitleTextAppearance="@style/CollapsingToolbarTitleStyle"***
            app:contentScrim="?attr/colorPrimaryDark"
            app:layout_scrollFlags="scroll|enterAlwaysCollapsed|exitUntilCollapsed">

            <ImageView
                android:background="@drawable/girl"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layout_collapseMode="parallax"/>

            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="@dimen/actionBarSize"
                android:minHeight="@dimen/actionBarSize"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/ThemeOverlay.AppCompat.ActionBar">
            </android.support.v7.widget.Toolbar>
        </android.support.design.widget.CollapsingToolbarLayout>
 


  
    <style name="CollapsingToolbarTitleStyle" parent="@android:style/TextAppearance">
        <item name="android:textColor">@android:color/transparent</item>
    </style>