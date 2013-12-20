package com.xinlan.controller.ui;

import com.xinlan.controller.logic.IActionDo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 控制器主面板
 * 
 * @Title:
 * @Description:
 * @Author:13120682
 * @Since:2013-12-20
 * @Version:
 */
public class ControllerView extends View
{
    private IActionDo actionDo;

    public ControllerView(Context context)
    {
        super(context);
    }

    public ControllerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 注册手指抬起事件
     * 
     * @Description:
     * @Author 13120682
     * @Date 2013-12-20
     */
    public void setActionDo(IActionDo action)
    {
        this.actionDo = action;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        System.out.println("--->" + event.getAction());

        if (actionDo != null)
        {
            actionDo.send();
        }
        return true;
    }

}// end class
