package com.xinlan.controller.ui;

import com.xinlan.controller.R;
import com.xinlan.controller.logic.IActionDo;
import com.xinlan.controller.utils.VectorUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * �����������
 * 
 * @Title:
 * @Description:
 * @Author:13120682
 * @Since:2013-12-20
 * @Version:
 */
public class ControllerView extends View
{
    public static final int UP=1;
    public static final int LEFT=2;
    public static final int DOWN=3;
    public static final int RIGHT=4;
    
    private Context mContext;
    private IActionDo actionDo;
    private Bitmap touchBitmap;
    protected float start_x, start_y, end_x, end_y;
    protected float cube_x, cube_y;

    public ControllerView(Context context)
    {
        super(context);
        init(context);
    }

    public ControllerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    /**
     * �ؼ���Դ��ʼ��
     * 
     * @Description:
     * @Author 13120682
     * @Date 2013-12-20
     */
    private void init(Context context)
    {
        this.mContext = context;
        touchBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.touch);
    }

    /**
     * ע����ָ̧���¼�
     * 
     * @Description:
     * @Author 13120682
     * @Date 2013-12-20
     */
    public void setActionDo(IActionDo action)
    {
        this.actionDo = action;
    }

    /**
     * ��ָ�����¼�����
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int fingerNum = event.getPointerCount();
        if (fingerNum < 2)// ���㴥��
        {
            float x = event.getX(0);
            float y = event.getY(0);
            int motionStatus = MotionEventCompat.getActionMasked(event);
            switch (motionStatus)
            {
                case MotionEvent.ACTION_DOWN:
                    start_x = x;
                    start_y = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    end_x = x;
                    end_y = y;
                    detectDir(start_x, start_y, end_x, end_y);
                    if (actionDo != null)
                    {
                        actionDo.send(detectDir(start_x, start_y, end_x, end_y));
                    }
                    break;
            }
        }
        else
        // ��㴥��
        {
        }
        return true;
    }

    /**
     * ������������ �ж�����
     * 
     * @Description:
     * @Author 13120682
     * @Date 2013-12-20
     */
    private int detectDir(float x1, float y1, float x2, float y2)
    {
        float x3 = x1 + 1;
        float y3 = y1;
        float verAx = x2-x1;
        float verAy = y2-y1;
        float verBx = x3-x1,verBy = y3-y1;
        double angle = VectorUtil.calCosTwoVectorAngle(verAx, verAy, verBx, verBy);
        int a = (int)((180f/Math.PI)*angle);//������ת�Ƕ���
        if(y1<=y2){
            a=360-a;
        }
        if((a>=0 && a<45)|| (a>=315 && a<360)){//����
            System.out.println("����");
            return RIGHT;
        }else if(a>=45 && a<135){//����
            System.out.println("����");
            return UP;
        }else if(a>=135 && a<225){//����
            System.out.println("����");
            return LEFT;
        }else if(a>=225 && a<315){//����
            System.out.println("����");
            return DOWN;
        }
        return 0;
    }
    
}// end class
