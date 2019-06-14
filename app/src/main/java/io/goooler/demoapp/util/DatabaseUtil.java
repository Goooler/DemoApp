package io.goooler.demoapp.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.util.List;

import io.goooler.demoapp.base.BaseApplication;

/**
 * 对 GreenDao 的简单封装，以便统一异常处理等操作
 * 现在有几个特定的方法，还有通用的异步插入方法，通配泛型
 */

public class DatabaseUtil {

    private static final int LATEST_ONE = 1;
    private static final int FIRST_INDEX = 0;

    /**
     * 异步任务但不回调
     */
    public static void insert(Object entity) {
        insertTrue(entity, null);
    }

    /**
     * 异步任务，需要回调
     *
     * @param entity 要插入的数据
     */
    public void insert(Object entity, AsyncOperationListener asyncOperationListener) {
        insertTrue(entity, asyncOperationListener);
    }

    /**
     * 真正实现插入的方法，仅内部调用
     * 异步任务，需要回调，支持单个插入或批量插入
     * bean 直接插入，list 取其中第一个对象通过反射判断类型
     * 这里使用 insertOrReplace 方法替代单纯的插入，可实现有则更新无则插入
     *
     * @param entity                 要插入的数据，可以是 list 或 bean，不能为空
     * @param asyncOperationListener 接口回调，为空代表不需要回调
     */
    @SuppressWarnings("unchecked")
    private static void insertTrue(@NonNull Object entity, @Nullable AsyncOperationListener asyncOperationListener) {
        //非空才插入，否则报错
        if (EmptyUtil.isNotEmpty(entity)) {
            try {
                if (entity instanceof List) {
                    getAsyncSession(asyncOperationListener).insertOrReplaceInTx(
                            ((List) entity).get(FIRST_INDEX).getClass(),
                            (List) entity);
                } else {
                    getAsyncSession(asyncOperationListener).insertOrReplace(entity);
                }
            } catch (DaoException e) {
            }
        }
    }

    /**
     * 获取数据库异步操作对象
     *
     * @param asyncOperationListener 异步任务完成后的监听回调
     */
    private static AsyncSession getAsyncSession(AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = BaseApplication.getDaoSession().startAsyncSession();
        asyncSession.setListener(asyncOperationListener);
        return asyncSession;
    }
}
